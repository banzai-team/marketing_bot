
resource "sbercloud_cce_node" "cluster_node" {
  for_each = var.cluster_nodes

  name = each.value["name"]

  dynamic "taints" {
    for_each = lookup(each.value, "node_taints", {})
    content {
      key    = taints.key
      value  = taints.value.value
      effect = taints.value.effect
    }
  }
  labels     = lookup(each.value, "node_labels", {})
  cluster_id = sbercloud_cce_cluster.cluster.id

  os                       = each.value.os
  flavor_id                = each.value.flavor_id
  availability_zone        = lookup(each.value, "availability_zone", null)
  key_pair                 = each.value.key_pair

  dynamic "root_volume" {
    for_each = each.value["root_volume"]
    content {
      size       = root_volume.value["size"]
      volumetype = root_volume.value["volumetype"]
    }
  }

  dynamic "data_volumes" {
    for_each = each.value["data_volumes"]
    content {
      size       = data_volumes.value["size"]
      volumetype = data_volumes.value.volumetype
    }
  }
}