resource "sbercloud_cce_cluster" "cluster" {
  name                   = "${local.project_name}-k8s-cluster"
  flavor_id              = var.cluster_flavor_id
  vpc_id                 = data.sbercloud_vpc.vpc.id
  subnet_id              = data.sbercloud_vpc_subnet.public_subnet.id
  container_network_type = var.container_network_type
  eip                    = sbercloud_vpc_eip.cluster_eip.address
}