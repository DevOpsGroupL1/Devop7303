#!/bin/bash
# This script builds a Docker image using the specified image name, tag, and Dockerfile.
# Usage: ./dockerbuild.sh <image_name> <image_tag> <docker_file>
# Example: ./dockerbuild.sh my_image latest Dockerfile

echo -e "\nusage: $0 <image_name> <image_tag> <docker_file>\n"
sleep 1
echo -e "\nThis script will build a Docker image using the specified image name, tag, and Dockerfile.\n"
echo -e "\nPlease provide the following parameters:\n"

echo -e -n "\nEnter the image name: "
read image_name
echo -e -n "\nEnter the image tag: "
read image_tag
echo -e -n "\nEnter the name of docker file to build: "
read docker_file


# Validate inputs
if [[ -z "${image_name}" || -z "${image_tag}" || -z "${docker_file}" ]]; then
    echo -e "\nAll parameters are required."
    echo -e "\nusage: $0 <image_name> <image_tag> <docker_file>\n"
    exit 1
fi

# Validate and sanitize inputs
while [[ ! $image_name =~ ^[a-z]+$ ]]; do
    echo -e "\nInvalid image name. Only letters are allowed.\n"
    read -p "Enter the image name: " image_name
done

while [[ ! $image_tag =~ ^[a-zA-Z0-9]+$ ]]; do
    echo -e "\nInvalid image tag. Only alphanumeric characters are allowed.\n"
    read -p "Enter the image tag: " image_tag
done

while [[ ! $docker_file =~ ^Docker/Dockerfile.* ]]; do
    echo -e "\nInvalid Dockerfile name. It must start with 'Docker/Dockerfile.'.\n"
    read -p "Enter the name of the Dockerfile: " docker_file
done

# Check if the Dockerfile exists 
if [[ ! -f "${docker_file}" ]]; then
    echo -e "\nDockerfile not found: ${docker_file}\n"
    exit 1
fi

# Build the Docker image
echo -e "\a\nBuilding Docker image...\n"
docker build -t $image_name:$image_tag -f $docker_file .
