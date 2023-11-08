resource "sbercloud_swr_organization" "gazprom" {
  name = "gazprom-mrkt"
}

#resource "sbercloud_swr_repository" "test" {
#  organization = sbercloud_swr_organization.gazprom.id
#  name         = "%s"
#  description  = "Test repository"
#  category     = "linux"
#}