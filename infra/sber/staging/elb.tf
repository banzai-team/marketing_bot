data "sbercloud_elb_flavors" "flavors" {
  type            = "L4"
  max_connections = 500000
  cps             = 10000
  bandwidth       = 50
}

# Create Elastic IP (EIP) for ELB
resource "sbercloud_vpc_eip" "elb_eip" {
  publicip {
    type = "5_bgp"
  }
  bandwidth {
    name        = "elb_bandwidth"
    size        = 3
    share_type  = "PER"
    charge_mode = "bandwidth"
  }
}

resource "sbercloud_lb_loadbalancer" "elb" {
  name          = "elb-frontend"
  vip_subnet_id = module.vpc.subnets["public_subnet"].subnet_id
}


resource "sbercloud_networking_eip_associate" "elb_eip_associate" {
  public_ip = sbercloud_vpc_eip.elb_eip.address
  port_id   = sbercloud_lb_loadbalancer.elb.vip_port_id
}

resource "sbercloud_lb_listener" "listener" {
  name            = "listener_http"
  protocol        = "HTTP"
  protocol_port   = 80
  loadbalancer_id = sbercloud_lb_loadbalancer.elb.id
}

resource "sbercloud_lb_pool" "group" {
  name        = "group"
  protocol    = "HTTP"
  lb_method   = "ROUND_ROBIN"
  listener_id = sbercloud_lb_listener.listener.id
}

resource "sbercloud_lb_monitor" "health_check" {
  name           = "health_check"
  type           = "HTTP"
  url_path       = "/"
  expected_codes = "200-202"
  delay          = 10
  timeout        = 5
  max_retries    = 3
  pool_id        = sbercloud_lb_pool.group.id
}

resource "sbercloud_lb_member" "members" {
  for_each = module.cluster.nodes

  address       = each.value.private_ip
  protocol_port = 80
  weight        = 1
  pool_id       = sbercloud_lb_pool.group.id
  subnet_id     = module.vpc.subnets["private_subnet"].subnet_id
}
