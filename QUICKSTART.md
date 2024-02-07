# Quickstart

Hello Examiner!

This is a quickstart guide to get you up and running with the microservices.

## Prerequisites
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 17](https://adoptium.net/en-GB/temurin/releases/?version=17)
- [Gradle](https://gradle.org/)
- [Eclipse](https://www.eclipse.org/ide/)
- [Epsilon >2.4 (set up for module, see vle)](https://www.eclipse.org/epsilon/download/)
- [Graphviz](https://graphviz.org/download/)

## Setup
(Optional, done before submission) Gradle `run` task in modelling project. 
This will generate the microservice code from the Epsilon model.

```bash
cd modelling
./gradlew run
```

Ensure docker daemon is running.

Build the docker images for the microservices and client.

To do this I have made a script for your convenience 
```bash
cd microservices
./dockerbuild.sh
```
You may need to run `chmod +x dockerbuild.sh` to make it executable.

Otherwise, you can go into the directory of each microservice and client and run `./gradlew dockerbuild` to build the docker images.

## Running the system

To run the system, run the following command in the microservices directory.

```bash
docker compose -f docker-compose-gen.yml -f docker-compose-secrets.yml up -d  
```

This will start the system in the background. To stop the system run the following command in the microservices directory.

```bash
docker compose -f docker-compose-gen.yml -f docker-compose-secrets.yml down
```

## Running the client

To run the client, you can run the following from the `microservice` directory. This is a wrapper around a `docker run` command.
```bash
./videoCLI.sh <command> <args>
```
(again you may need to run `chmod +x videoCLI.sh` to make it executable)
eg. `./videoCLI.sh add-user fred` will add a user with the username fred.

Alternatively you can run the following command in the client directory.
```bash
./gradlew run --args="<command> <args>"
```
eg. `./gradlew run --args="add-user fred"` will add a user with the username fred.

## Quickstart script
I have made a script that will create a user, a number of videos, like the videos, subscribe to a hashtag, list trending videos and list the videos of the subscribed hashtag.
From the `microservices` directory run the following command:
```bash
./quickstart.sh
```
You may need to run `chmod +x quickstart.sh` to make it executable.

This script will run the following commands in order:
```bash
./videoCLI.sh add-user fred
./videoCLI.sh post-video "video title1" $user_id "hashtag1" "hashtag2"
./videoCLI.sh subscribe $user_id -name "hashtag1"
./videoCLI.sh post-video "video title2" $user_id "hashtag1" "hashtag2"
./videoCLI.sh post-video "video title3" $user_id "hashtag1" "hashtag2"
./videoCLI.sh post-video "video title4" $user_id "hashtag1" "hashtag2"
./videoCLI.sh post-video "video title5" $user_id "hashtag1" "hashtag2"
./videoCLI.sh like-video $user_id $video_id
./videoCLI.sh like-video $user_id $video_id2
./videoCLI.sh like-video $user_id $video_id3
./videoCLI.sh get-trending-hashtags
./videoCLI.sh subscription-videos $user_id -name hashtag1
```

