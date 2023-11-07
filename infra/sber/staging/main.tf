locals {
  azs           = ["ru-moscow-1a"]
  key_pair_name = "sshkey-1"
  #  azs = ["ru-moscow-1a", "ru-moscow-1b"]

  #  azs = []
}

module "vpc" {
  source = "../../modules/sber/sber-vpc"

  name = "gazprom-${var.environment}"
  cidr = "10.0.0.0/16"

  azs     = local.azs
  subnets = [
    {
      availability_zone = local.azs[0]
      name              = "public_subnet"
      cidr              = "10.0.0.0/24"
      gateway_ip        = "10.0.0.1"

      nat_gw = {
        spec = "1"
      }

      eip = {
        type        = "5_bgp"
        share_type  = "PER"
        size        = 1
        charge_mode = "traffic"
      }

      # vip_routes = [
      #   {
      #     destination = "192.168.100.0/24"
      #     nexthop     = "10.0.0.117"
      #     description = "test"
      #   }
      # ]

      ecs_routes     = []
      eni_routes     = []
      nat_routes     = []
      peering_routes = []
      vpn_routes     = []
      dc_routes      = []
      cc_routes      = []

      # existing_eip = "xxxx-xxx-xxx" # excludes eip creation and use existing one, has precedence over eip creation

    },
    {
      availability_zone = local.azs[0]
      name              = "private_subnet"
      cidr              = "10.0.1.0/24"
      gateway_ip        = "10.0.1.1"

      nat_gw = {
        spec = "1"
      }

      eip = {
        type        = "5_bgp"
        share_type  = "PER"
        size        = 1
        charge_mode = "traffic"
      }
    },
  ]

  dhcp_enable   = true
  primary_dns   = "100.125.13.59"
  secondary_dns = "8.8.8.8"

  tags = {
    env = var.environment
  }

  # default_route_table_routes = [
  #   {
  #     destination = "10.0.x.x/0"
  #     type        = "xxx"
  #     nexthop     = "xxxxx-xxx-xxx"
  #   },
  # ]
}

#resource "sbercloud_vpc_eip" "cluster_eip" {
#  publicip {
#    type = "5_bgp"
#  }
#  bandwidth {
#    name        = "test"
#    size        = 8
#    share_type  = "PER"
#    charge_mode = "traffic"
#  }
#}

resource "sbercloud_compute_keypair" "keypair" {
  name       = "sshkey-1"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCnKjVmVDtR3SXlPg4lKurIbuLUfaVri8xBNR+2ji9bwXOAQpoL5d+g9phV5jc8eEIIjLlXggnTIlu4ShX5JmUFM7l5bUp+ITZI9MyHofTd7Rfdwg1vZTzGT9UCdpdVo9ss3tfqoHs4K8vu3Inxcdm+mG1VfVHiDK/XwJFRrxGVOEL/YmgFab1NhfNTYZR3dMpDt+4e18rcjg8IfevE4zNrIHI/Krv/e3rYSlG7g+kOhSp55xRtD5AbWvG/UUgt6oYdclgC642Gew4HuSUE1LNprYKAl/fAZSvyaaq8qW1aSj4TpXVJVxj+uMPiBsAfRHSfc1mWWa6hejWfijsjdAnR Generated-by-Nova\n"
}

module "cluster" {
  source = "../../modules/sber/sber-cce-cluster"

  environment = var.environment
  name        = "${var.project}-cluster"

  project = var.project
  vpc_id  = module.vpc.id

  master_node_subnet_id = module.vpc.subnet_ids[0]

  eni_subnet_id          = module.vpc.subnets["public_subnet"].ipv4_subnet_id
  eni_subnet_cidr        = module.vpc.subnets["public_subnet"].cidr
  container_network_type = "eni"

  cluster_nodes = {
    "sber-k8s-nginx-node" = {
      name              = "ingress-nodes"
      os                = "CentOS 7.6"
      flavor_id         = "c7n.large.4"
      availability_zone = local.azs[0]
      key_pair          = sbercloud_compute_keypair.keypair.name
      type              = "vm"
      subnet_id         = module.vpc.subnets["public_subnet"].ipv4_subnet_id

      root_volume = [
        {
          size       = 40
          volumetype = "SAS"
        }
      ]

      data_volumes = [
        {
          size       = 100
          volumetype = "SAS"
        }
      ]

      node_taints = {
        scope = {
          value : "ingress"
          effect : "NoSchedule"
        }
      }
      node_labels = {
        env   = var.environment
        scope = "ingress"
      }
    }
  }

  cluster_node_groups = {
    "sber-k8s-public-ng" = {
      name                     = "public-pool"
      os                       = "CentOS 7.6"
      initial_node_count       = 1
      flavor_id                = "c7n.xlarge.4"
      #      flavor_id                = "s7n.large.2"
      availability_zone        = local.azs[0]
      key_pair                 = sbercloud_compute_keypair.keypair.name
      scall_enable             = true
      min_node_count           = 1
      max_node_count           = 2
      scale_down_cooldown_time = 100
      priority                 = 1
      type                     = "vm"
      subnet_id                = module.vpc.subnets["public_subnet"].ipv4_subnet_id

      root_volume = [
        {
          size       = 40
          volumetype = "SAS"
        }
      ]

      data_volumes = [
        {
          size       = 100
          volumetype = "SAS"
        }
      ]

      node_taints = {
        scope = {
          value : "apps"
          effect : "NoSchedule"
        }
      }
      node_labels = {
        env   = var.environment
        scope = "apps"
      }
    }

    "sber-k8s-private-ng" = {
      subnet_id          = module.vpc.subnets["private_subnet"].ipv4_subnet_id
      name               = "private-pool"
      os                 = "CentOS 7.6"
      initial_node_count = 1
      flavor_id          = "c7n.xlarge.4"
      availability_zone  = local.azs[0]
      key_pair           = sbercloud_compute_keypair.keypair.name
      scall_enable       = true
      min_node_count     = 1
      max_node_count     = 2
      #      scale_down_cooldown_time = 100
      #      priority                 = 1
      type               = "vm"

      root_volume = [
        {
          size       = 40
          volumetype = "SAS"
        }
      ]

      data_volumes = [
        {
          size       = 100
          volumetype = "SAS"
        }
      ]

      node_taints = {
      }
      node_labels = {
        env   = var.environment
        scope = "tools"
      }
    }
  }
}
