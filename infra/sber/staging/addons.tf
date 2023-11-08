
data "sbercloud_cce_addon_template" "autoscaler" {
  cluster_id = module.cluster.cluster_id
  name       = "autoscaler"
  version    = "1.25.21"
}

resource "sbercloud_cce_addon" "addon_autoscaler" {
  cluster_id    = module.cluster.cluster_id
  template_name = "autoscaler"
  version       = "1.25.21"

  values {
    basic_json  = jsonencode(jsondecode(data.sbercloud_cce_addon_template.autoscaler.spec).basic)
    custom_json = jsonencode(merge(
      jsondecode(data.sbercloud_cce_addon_template.autoscaler.spec).parameters.custom,
      {
        cluster_id = module.cluster.cluster_id
        tenant_id  = "7d6588b32c154d96910f800a38ce880b"
      }
    ))
    flavor_json = jsonencode(jsondecode(data.sbercloud_cce_addon_template.autoscaler.spec).parameters.flavor2)
  }
}