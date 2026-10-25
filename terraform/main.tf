terraform {
  required_version = ">= 1.5.0"
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "stoploss_app" {

  ami           = "ami-123456789"
  instance_type = "t3.medium"

  tags = {
    Name = "stoploss-monitor"
  }
}