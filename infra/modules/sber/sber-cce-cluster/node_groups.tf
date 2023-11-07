resource "sbercloud_cce_node_pool" "cluster_node_pool" {
  for_each = var.cluster_node_groups

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

  initial_node_count = each.value.initial_node_count

  os                       = each.value.os
  flavor_id                = each.value.flavor_id
  availability_zone        = lookup(each.value, "availability_zone", null)
  key_pair                 = each.value.key_pair
  scall_enable             = lookup(each.value, "scall_enable", null)
  min_node_count           = lookup(each.value, "min_node_count", null)
  max_node_count           = lookup(each.value, "max_node_count", null)
  scale_down_cooldown_time = lookup(each.value, "scale_down_cooldown_time", null)
  priority                 = lookup(each.value, "priority", null)
  type                     = lookup(each.value, "type", null)
  security_groups          = lookup(each.value, "security_groups", null)
  pod_security_groups      = lookup(each.value, "pod_security_groups", null)

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

  #  storage {
  #    selectors {
  #      name              = "cceUse"
  #      type              = "evs"
  #      match_label_size  = "100"
  #      match_label_count = "1"
  #    }
  #
  #    selectors {
  #      name                           = "user"
  #      type                           = "evs"
  #      match_label_size               = "100"
  #      match_label_metadata_encrypted = "1"
  #      match_label_metadata_cmkid     = var.kms_key_id
  #      match_label_count              = "1"
  #    }
  #
  #    groups {
  #      name           = "vgpaas"
  #      selector_names = ["cceUse"]
  #      cce_managed    = true
  #
  #      virtual_spaces {
  #        name        = "kubernetes"
  #        size        = "10%"
  #        lvm_lv_type = "linear"
  #      }
  #
  #      virtual_spaces {
  #        name = "runtime"
  #        size = "90%"
  #      }
  #    }
  #
  #    groups {
  #      name           = "vguser"
  #      selector_names = ["user"]
  #
  #      virtual_spaces {
  #        name        = "user"
  #        size        = "100%"
  #        lvm_lv_type = "linear"
  #        lvm_path    = "/workspace"
  #      }
  #    }
  #  }
}