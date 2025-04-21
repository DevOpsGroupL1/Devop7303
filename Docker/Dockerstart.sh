#!/bin/bash
# This script is used to start a Docker container for a frontend application.
# Usage: ./dockerstart.sh <container_name> <port mapping> <image_name>
# Example: ./dockerstart.sh my_container 5173:5173 my_image

echo -e "\nusage: $0 <container_name> <port mapping> <image_name>\n"
sleep 1

echo -e "\nThis script will run a Docker container for a frontend application.\n"
echo -e "\nPlease provide the following parameters:\n"

echo -e -n "\nEnter the container name: "
read container_name

echo -e -n "\nEnter the port mapping (e.g., 5173:5173): "
read port_mapping

echo -e -n "\nEnter the image name: "
read image_name

# Validate inputs
if [[ -z "${container_name}" || -z "${port_mapping}" || -z "${image_name}" ]]; then
    echo -e "\nAll parameters are required."
    echo -e "\nusage: $0 <container_name> <port mapping> <image_name>\n"
    exit 1
fi

# Validate and sanitize inputs
while [[ ! $container_name =~ ^[a-zA-Z]+$ ]]; do
    echo -e "\nInvalid container name. Only lower and uppercase letters are allowed.\n"
    read -p "Enter the container name: " container_name
done

while [[ ! $port_mapping =~ ^[0-9]+:[0-9]+$ ]]; do
    echo -e "\nInvalid port mapping. Use the format <host_port>:<container_port>.\n"
    read -p "Enter the port mapping (e.g., 5173:5173): " port_mapping
done

while [[ ! $image_name =~ ^[a-zA-Z0-9_]+$ ]]; do
    echo -e "\nInvalid image name. Only letters, numbers, and underscores are allowed.\n"
    read -p "Enter the image name: " image_name
done

# Check if the container is already running
if [[ $(docker ps -q -f name=${container_name}) ]]; then
    echo -e "\nContainer ${container_name} is already running."
    echo -e "\nYou can access it at http://localhost:${port_mapping%%:*}\n"
    exit 0
fi

# Check if the container exists but is not running
if [[ $(docker ps -aq -f name=${container_name}) ]]; then
    echo -e "\nContainer ${container_name} exists but is not running.\n"
    echo -e "\nStarting the existing container ${container_name}...\n"
    docker start ${container_name}
    # Check if the container started successfully
    if [[ $? -eq 0 ]]; then
        echo -e "\nContainer ${container_name} started successfully.\n"
        exit 0
    else
        echo -e "\nFailed to start the container ${container_name}.\n"
        exit 1
    fi
fi

# Check if the image exists
if [[ -z $(docker images -q ${image_name}) ]]; then
    echo -e "\nImage ${image_name} does not exist. Please build the image first.\n"
    exit 1
fi

# Check if the port is already in use
if [[ $(lsof -i -P -n | grep LISTEN | grep ${port_mapping%%:*}) ]]; then
    echo -e "\nPort ${port_mapping%%:*} is already in use. Please choose a different port.\n"
    exit 1
fi

# Check if the container name is already in use
if [[ $(docker ps -aq -f name=${container_name}) ]]; then
    echo -e "\nContainer name ${container_name} is already in use. Please choose a different name.\n"
    exit 1
fi

echo -e "\nRunning the Docker container ${container_name}...\n"
docker run -d --name=${container_name} -p ${port_mapping} ${image_name}
# Check if the container started successfully
if [[ $? -eq 0 ]]; then
    echo -e "\nContainer ${container_name} started successfully.\n"
else
    echo -e "\nFailed to start the container ${container_name}.\n"
    exit 1
fi
echo -e "\nContainer ${container_name} is running and accessible at http://localhost:${port_mapping%%:*}\n"
