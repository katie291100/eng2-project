#!/bin/bash


# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17."
    exit 1
fi

# Get Java version
java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')

# Check if Java version is 17
if [[ "$java_version" == "17"* ]]; then
    echo "Java version is 17: $java_version"
else
    echo "Error: Java version 17 is required, but found $java_version"
    exit 1
fi

# Check if Docker daemon is running
if ! docker info &>/dev/null; then
    echo "Error: Docker daemon is not running."
    echo "Please start the Docker daemon and run the script again."
    exit 1
fi

# Navigate to the /client directory and build the Docker image
cd client || exit
echo "Building Docker image for Video Microservice (VM)..."
./gradlew dockerBuild

cd ..

# Navigate to the /trending-hashtag-microservice directory and build the Docker image
cd trending-hashtag-microservice || exit
echo "Building Docker image for Trending Hashtag Microservice (THM)..."
./gradlew dockerBuild

cd ..

# Navigate to the /video-microservice directory and build the Docker image
cd video-microservice || exit
echo "Building Docker image for Video Microservice (VM)..."
./gradlew dockerBuild

echo "Docker build process completed for all services."


# Navigate to the /subscription-microservice directory and build the Docker image
#cd subscription-microservice || exit
#echo "Building Docker image for Subscription Microservice (VM)..."
#./gradlew dockerBuild
#
#echo "Docker build process completed for all services."