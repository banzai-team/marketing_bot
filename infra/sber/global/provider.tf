terraform {
  required_providers {
    sbercloud = {
      source = "tf.repo.sbc.space/sbercloud-terraform/sbercloud"
    }
  }
  required_version = ">= 1.3.7"

#  backend "s3" {
#    endpoint   = "https://obs.ru-moscow-1.hc.sbercloud.ru"
#    bucket     = "repairai-tf-state"
#    region     = "ru-central1"
#    key        = "repair-me-tf-state/global/state.tfstate"
#
#    skip_region_validation      = true
#    skip_credentials_validation = true
#    skip_metadata_api_check     = true
#  }
}

provider "sbercloud" {
  auth_url = "https://iam.ru-moscow-1.hc.sbercloud.ru/v3"
  region     = "ru-moscow-1"
}