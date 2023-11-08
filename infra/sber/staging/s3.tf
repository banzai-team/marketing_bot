resource "sbercloud_obs_bucket" "model-storage" {
  bucket = "gazprom-model-storage"

  acl    = "public-read"
}