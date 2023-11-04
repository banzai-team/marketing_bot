terraform {
  required_version = ">= 1.3.7"

  required_providers {
    sbercloud = {
      source = "tf.repo.sbc.space/sbercloud-terraform/sbercloud"
    }
  }
}
