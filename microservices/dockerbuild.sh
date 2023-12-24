#!/bin/bash

# Function to check if Docker daemon is running
check_docker_daemon() {
    if ! docker info &>/dev/null; then
        echo "Error: Docker daemon is not running."
        echo "Please start the Docker daemon and run the script again."
        exit 1
    fi
}

# Check if Docker daemon is running
check_docker_daemon

# Navigate to the /thm directory
cd trending-hashtag-microservice || exit
echo "Building Docker image for Trending Hashtag Microservice (THM)..."
./gradlew dockerBuild

# Navigate to the /vm directory
cd ../video-microservice || exit
echo "Building Docker image for Video Microservice (VM)..."
./gradlew dockerBuild

## Navigate to the /cli directory
#cd /path/to/cli
#echo "Building Docker image for Command-Line Interface (CLI)..."
#./gradlew dockerBuild

echo "Docker build process completed for all services."