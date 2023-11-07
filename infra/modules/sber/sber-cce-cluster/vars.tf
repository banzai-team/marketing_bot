variable "project" {
  type = string
}

variable "environment" {
  type = string
}

variable "cluster_flavor_id" {
  type        = string
  default     = "cce.s2.small"
  description = "Defaults up to 50 nodes HA Cluster"
}

variable "container_network_type" {
  type        = string
  default     = "overlay_l2"
  description = "Kind of network of the containers, by default overlay l2"
}

#variable "node_flavor_id" {
#  type        = string
#  default     = "c6.large.4"
#  description = "defaults to s3.large.4"
#}
#
variable "master_node_subnet_id" {
  type = string
}

# variable "initial_node_count" {
#   type        = number
#   default     = 0
#   description = "defaults to 0"
# }

#variable "node_count" {
#  type        = number
#  default     = 2
#  description = "defaults to 2"
#}

variable "name" {
  type = string
}

variable "public" {
  type    = bool
  default = true
}

variable "region" {
  type    = string
  default = "ru-central1"
}

variable "kube_version" {
  type    = string
  default = "1.25"
}

variable "release_channel" {
  type    = string
  default = "STABLE"
}

variable "vpc_id" {
  type = string
}
#
#variable "location_subnets" {
#  type = list(object({
#    id             = string
#    zone           = string
#    v4_cidr_blocks = list(string)
#  }))
#}

#variable "cluster_service_account_id" {
#  type = string
#}
#
#variable "node_service_account_id" {
#  type = string
#}
#
#variable "cluster_node_groups" {
#  type = map(object({
#    name                     = string
#    flavor_id                = string
#    os                       = string
#    initial_node_count       = number
#    availability_zone        = string
#    key_pair                 = string
#    scall_enable             = bool
#    min_node_count           = number
#    max_node_count           = number
#    scale_down_cooldown_time = number
#    priority                 = number
#    type                     = string
#
#    root_volume = list(object({
#      size       = number
#      volumetype = string
#    }))
#
#    data_volumes = list(object({
#      size       = number
#      volumetype = string
#    }))
#    #
#    #    disk = object({
#    #      size = number
#    #      type = string
#    #    })
#    #    network_interface = object({
#    #      nat : bool
#    #      subnet_ids         = []
#    #      security_group_ids = []
#    #    })
#    #    fixed_scale = list(number)
#    #    auto_scale  = list(object({
#    #      max     = number
#    #      min     = number
#    #      initial = number
#    #    }))
#    node_labels = map(string)
#    node_taints = list(string)
#  }))
#}

variable "cluster_node_groups" {
  type = any
}

variable "cluster_nodes" {
  type = any
  default = null
}

variable "service_ipv4_range" {
  type    = string
  default = null
}

variable "cluster_ipv4_range" {
  type    = string
  default = null
}
#
#variable "cluster_access_net_addr" {
#  type = string
#}

variable "zonal" {
  type    = bool
  default = false
}

variable "eni_subnet_cidr" {
  type    = string
  default = null
}

variable "eni_subnet_id" {
  type    = string
  default = null
}

variable "cluster_eip" {
  type = string
  default = null
}
