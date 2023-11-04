locals {
  azs = ["ru-moscow-1a",]
  #  azs = ["ru-moscow-1a", "ru-moscow-1b"]

  #  azs = []
}

module "vpc" {
  source = "../../modules/sber/sber-vpc"

  name = "tf-vpc-${var.environment}"
  cidr = "10.0.0.0/16"

  azs     = local.azs
  subnets = [
    {
      cidr       = "10.0.0.0/24"
      gateway_ip = "10.0.0.1"

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
      cidr       = "10.0.1.0/24"
      gateway_ip = "10.0.1.1"
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