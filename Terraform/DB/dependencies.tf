data "terraform_remote_state" "vpc" {
  backend = "local"
  config = {
    path = "../VPC/terraform.tfstate"
  }
}

data "terraform_remote_state" "ec2" {
  backend = "local"
  config = {
    path = "../EC2/terraform.tfstate"
  }
}