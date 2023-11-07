resource "sbercloud_cce_cluster" "cluster" {
  name                   = "${var.project}-k8s-cluster"
  flavor_id              = var.cluster_flavor_id
  vpc_id                 = var.vpc_id
  subnet_id              = var.master_node_subnet_id
  container_network_type = var.container_network_type
  eip                    = var.cluster_eip
  eni_subnet_id          = var.eni_subnet_id
  eni_subnet_cidr        = var.eni_subnet_cidr
}