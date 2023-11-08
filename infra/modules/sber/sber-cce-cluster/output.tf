output "cluster_id" {
  description = "The ID of cluster"
  value       = try(sbercloud_cce_cluster.cluster.id, "")
}

output "nodes" {
  description = "The List of cluster_nodes"
  value       = sbercloud_cce_node.cluster_node
}
