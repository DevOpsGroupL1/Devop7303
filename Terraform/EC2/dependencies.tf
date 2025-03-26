#Import outputs from VPC state file
data "terraform_remote_state" "vpc" {
  backend = "local"
  config = {
    path = "../VPC/terraform.tfstate"
  }
}