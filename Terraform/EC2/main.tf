#Create Key pair
resource "aws_key_pair" "main" {
  key_name   = var.key_name
  public_key = file("/home/hardarmyyy/swe-7302.pub")
  lifecycle {
    ignore_changes = [public_key]
  }
}

#Create Instances
resource "aws_instance" "ec2-public" {
  count                       = var.pub_ec2_count
  ami                         = var.ami
  instance_type               = var.instance_type
  key_name                    = aws_key_pair.main.key_name
  vpc_security_group_ids      = [aws_security_group.sg-public.id]
  subnet_id                   = data.terraform_remote_state.vpc.outputs.public_subnet_ids[count.index < 1 ? 0 : 1]
  associate_public_ip_address = true
  tags = {
    Name = "SWE7302-${count.index}"
  }
}

#Create security groups
resource "aws_security_group" "sg-public" {
  name        = var.security_group_name
  description = "Security group for ec2 instances"
  vpc_id      = data.terraform_remote_state.vpc.outputs.vpc_id

  dynamic "ingress" {
    for_each = var.ingress_rules
    content {
      description = ingress.value["description"]
      from_port   = ingress.value["from_port"]
      to_port     = ingress.value["to_port"]
      protocol    = ingress.value["protocol"]
      cidr_blocks = ingress.value["cidr_blocks"]
    }
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = var.grp1_ec2_egress_cidr_blocks
  }

  tags = {
    Name = "SWE7302 EC2 Security Group"
  }
}



