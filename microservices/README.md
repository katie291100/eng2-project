# Microservices 

This directory contains the source code for the microservices that make up the video social media system.

## Contents
- [Microservices](#microservices)
  - [Prerequisites](#prerequisites)
  - [Production](#production)
    - [Running the microservices](#running-the-microservices)
    - [Running the CLI](#running-the-cli)
  - [Development](#development)
    - [Running the microservices](#running-the-microservices-1)
    - [Running the CLI](#running-the-cli-1)
    - [Running the tests](#running-the-tests)
  - [Microservice Documentation](#microservice-documentation)
    - [Video Service](#video-service)
    - [Trending Hashtag Service](#trending-hashtag-service)
    - [Subscription Service](#subscription-service)

  
## Prerequisites
You must have the following installed and active on your system:
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Java 17](https://adoptium.net/en-GB/temurin/releases/?version=17)
- [Gradle](https://gradle.org/)
- (Recommended) A Java IDE eg. [Intellij](https://www.jetbrains.com/idea/)

The following environment variables can be set  locally:
- 'USE_TEST_CONTAINERS' - Defaults to true - set to 'false' to use your local docker containers for CLI/integration tests (see below for more info)

Docker Daemon must be running.

## Production

### Running the microservices
The microservices and CLI Docker Images can be build by running the following command from the microservices root directory of the project:
```shell
./docker-build.sh
```
you may need to run the following command to make the script executable:
```shell
chmod +x docker-build.sh
```
The microservices can be started by running the following command from the microservices root directory of the project:
```shell
docker-compose -f compose-prod.yml up -d
```
The microservices can be stopped by running the following command from the microservices root directory of the project:
```shell
docker-compose -f compose-prod.yml down
```
After running the above commands, the microservices will be available on the following ports:
- 8080 - Video Service
- 8081 - Trending Hashtag Service
- 8082 - Subscription Service

### Running the CLI
The CLI can be run by running the following command using the videoCLI bash script:
```shell
./videoCLI.sh <command> <args>
```
you may need to run the following command to make the script executable:
```shell
chmod +x videoCLI.sh
```
For further information on the CLI, see the section below. [CLI Client Usage](#cli-client-usage)

## Development

### Running the microservices
The microservices resources (DB, Kafka, Kafka-ui) can be started by running the following command from the microservices root directory of the project:
```shell
docker-compose -f compose.yml up -d
```
The microservices resources (DB, Kafka, Kafka-ui) can be stopped by running the following command from the microservices root directory of the project:
```shell
docker-compose -f compose.yml down
```
add the `–volumes` flag to the above command to remove the volumes as well as the containers. This is particularly important if making changes to the DB schema or settings.

The microservices can be started by running the following command from each microservice directory:
```shell
./gradlew run
```
alternatively you can run them via the Gradle run task in your IDE.

After running the above commands, the microservices will be available on the following ports:
- 8080 - Video Service
- 8081 - Trending Hashtag Service
- 8082 - Subscription Service
- 9000 - Kafka-ui
- 3306 - MariaDB (Video Service DB)
- 3307 - MariaDB (Trending Hashtag Service DB)

### Running the CLI
During development, the CLI can be run by running the following command from the `client` directory:
```shell
./gradlew run --args='<command> <args>'
```
eg. to run the `add-user` command:
```shell
./gradlew run --args='add-user Bob'
```

### Running the tests
The microservices can be tested by running the following command from each microservice directory:
```shell
./gradlew test
```
alternatively you can run them via the Gradle test task in your IDE.

All tests will kick up their own local Kafka and MariaDB instances using [Testcontainers](https://www.testcontainers.org/). 
This is done only if the docker containers are not already running for all microservices.

For CLI (client) unit tests, due to the reliance on other microservices and the inability to refresh the state, the tests will use testcontainers by default to run the microservices and their dependencies.

**Microservice and resource Docker containers must not be running for this to work.**

> ⚠️IMPORTANT ⚠️\
> This behaviour can be disabled by setting the `USE_TEST_CONTAINERS` environment variable to `false`. This is useful if you want to run the tests against your local microservice instances **however, this will cause some tests to fail due to the lack of state refresh.**


## Microservice Documentation

### Video Service
The Video Service is responsible for allowing the creation of user entities, the posting of videos, the creation of hashtags and the storing and retrieval of users, videos and hashtags. It also allows users to mark a video as liked, disliked and watched.

The Video Service exposes the following endpoints:
- [User endpoints](#user-endpoints)
  - [GET `/users`](#get-users---get-all-users)
  - [POST `/users`](#post-users---create-a-new-user)
  - [GET `/users/{id}`](#get-usersid---get-a-user-by-id)
  - [PUT `/users/{id}`](#put-usersid---update-a-user-entity)
  - [GET `/users/{id}/postedVideos`](#get-usersidpostedvideos---get-videos-posted-by-a-user)
  - [GET `/users/{id}/watchedVideos`](#get-usersidwatchedvideos---get-videos-watched-by-a-user)
  - [PUT `/users/{id}/watchedVideos/{videoId}`](#put-usersidwatchedvideosvideoid---mark-a-video-as-watched-by-a-user)
- [Video endpoints](#video-endpoints)
  - [GET `/videos`](#get-videos---get-all-videos)
  - [POST `/videos`](#post-videos---create-a-new-video)
  - [GET `/videos/{id}`](#get-videosid---get-a-video-by-id)
  - [PUT `/videos/{id}` (disabled)](#put-videosid---update-a-video-entity)
  - [GET `/videos/hashtag/{hashtag}`](#get-videoshashtaghashtag---get-videos-by-hashtag-name)
  - [PUT `/videos/{id}/like`](#put-videosidlike---mark-a-video-as-liked)
  - [PUT `/videos/{id}/dislike`](#put-videosiddislike---mark-a-video-as-disliked)
  - [DELETE `/videos/{id}` (disabled)](#delete-videosid---delete-a-video)
- [Hashtag endpoints](#hashtag-endpoints)
    - [GET `/hashtags`](#get-hashtags---get-all-hashtags)
    - [POST `/hashtags`](#post-hashtags---create-a-new-hashtag)
    - [GET `/hashtags/{id}`](#get-hashtagsid---get-a-hashtag-by-id)

#### GET `/healthcheck` - Healthcheck endpoint
Returns a 200 response if the service is up and running. \
Example Curl Request:
```shell
curl -i -X GET http://localhost:8080/healthcheck
```

### User Endpoints
#### GET `/users` - Get all users
Returns a list of all users in the system. \
Example Curl Request:
```shell
curl -i -X GET http://localhost:8080/users
```
Example response:
```json
[
  {
    "id": 1,
    "name": "Bob"
  },
  {
    "id": 2,
    "name": "Alice"
  }
]
```
#### POST `/users` - Create a new user
Creates a new user entity in the system.
Example Curl Request
```shell
curl -i -X POST -H "Content-Type: application/json" -d '{"name": "Bob"}' http://localhost:8080/users
```
Response expected: `HTTP/1.1 201 Created`

#### GET `/users/{id}` - Get a user by id
Returns a user entity with the given id.
Example Curl Request
```shell
curl -i -X GET http://localhost:8080/users/1
```
Example response:
```json
{
  "id": 1,
  "name": "Bob"
}
```
#### PUT `/users/{id}` - Update a user entity
Updates a user entity with the given id.
Example Curl Request
```shell
curl -i -X PUT -H "Content-Type: application/json" -d '{"name": "Bob"}' http://localhost:8080/users/1
```
Expected Response: `HTTP/1.1 200 OK` \
Response if user doesn't exist: `404 Not Found`

#### GET `/users/{id}/postedVideos` - Get videos posted by a user
Returns a list of videos posted by a user with the given id.
Example Curl Request
```shell
curl -i -X GET http://localhost:8080/users/1/postedVideos
```
Example response:
```json
[
  {
    "id": 1,
    "title": "My Video",
    "hashtags": [
      {
        "id": 1,
        "name": "myHashtag"
      }
    ],
    "likes": 0,
    "dislikes": 0,
    "postedBy":
        {
            "id": 1,
            "name": "Bob"
        }
  }
]
```
returns `null` if user doesn't exist

#### GET `/users/{id}/watchedVideos` - Get videos watched by a user
Returns a list of videos watched by a user with the given id.
Example Curl Request
```shell
curl -i -X GET http://localhost:8080/users/1/watchedVideos
```
Example response:
```json
[
  {
    "id": 1,
    "title": "My Video",
    "hashtags": [
      {
        "id": 1,
        "name": "myHashtag"
      }
    ],
    "likes": 0,
    "dislikes": 0,
    "postedBy":
        {
            "id": 1,
            "name": "Bob"
        }
  }
]
```
returns `null` if user doesn't exist
#### PUT `/users/{id}/watchedVideos/{videoId}` - Mark a video as watched by a user
Marks a video with the given id as watched by a user with the given id.
Example Curl Request
```shell
curl -i -X PUT http://localhost:8080/users/1/watchedVideos/1
```
Expected Response: `HTTP/1.1 200 OK` \
Response if user or video doesn't exist: `404 Not Found`

### Video Endpoints

#### GET `/videos` - Get all videos
Returns a list of all videos in the system. \
Example Curl Request:
```shell
curl -i -X GET http://localhost:8080/videos
```
Example response:
```json
[
  {
    "id": 1,
    "title": "My Video",
    "hashtags": [
      {
        "id": 1,
        "name": "myHashtag"
      }
    ],
    "likes": 0,
    "dislikes": 0,
    "postedBy":
        {
            "id": 1,
            "name": "Bob"
        }
  }
]
```
#### POST `/videos` - Create a new video
Creates a new video entity in the system.
Example Curl Request
```shell
curl -i -X POST -H "Content-Type: application/json" -d '{"title": "My Video", "hashtags": [{"name": "myHashtag"}], "postedBy": {"id": 1}}' http://localhost:8080/videos
```
Response expected: `HTTP/1.1 201 Created`
Response if user doesn't exist: `404 Not Found`

#### GET `/videos/{id}` - Get a video by id
Returns a video entity with the given id.
Example Curl Request
```shell
curl -i -X GET http://localhost:8080/videos/1
```
Example response:
```json
{
  "id": 1,
  "title": "My Video",
  "hashtags": [
    {
      "id": 1,
      "name": "myHashtag"
    }
  ],
  "likes": 0,
  "dislikes": 0,
  "postedBy":
      {
          "id": 1,
          "name": "Bob"
      }
}
```
- Returns `null` if video doesn't exist

#### PUT `/videos/{id}` - Update a video entity (disabled)
Updates a video entity with the given id.
Example Curl Request
```shell
curl -i -X PUT -H "Content-Type: application/json" -d '{"title": "My Video updated"}' http://localhost:8080/videos/1
```
Expected Response: `HTTP/1.1 200 OK` \
Response if video doesn't exist: `404 Not Found`

#### GET `/videos/hashtag/{hashtag}` - Get videos by hashtag name
Returns a list of videos with the given hashtag name.
Example Curl Request
```shell
curl -i -X GET http://localhost:8080/videos/hashtag/myHashtag
```
Example response:
```json
[
  {
    "id": 1,
    "title": "My Video",
    "hashtags": [
      {
        "id": 1,
        "name": "myHashtag"
      }
    ],
    "likes": 0,
    "dislikes": 0,
    "postedBy":
        {
            "id": 1,
            "name": "Bob"
        }
  }
]
```
- Returns `null` if hashtag doesn't exist
- Returns `[]` if no videos have the given hashtag

#### PUT `/videos/{id}/like` - Mark a video as liked
Marks a video with the given id as liked.
Example Curl Request
```shell
curl -i -X PUT http://localhost:8080/videos/1/like
```
Expected Response: `HTTP/1.1 200 OK` \
Response if video doesn't exist: `404 Not Found`

#### PUT `/videos/{id}/dislike` - Mark a video as disliked
Marks a video with the given id as disliked.
Example Curl Request
```shell
curl -i -X PUT http://localhost:8080/videos/1/dislike
```
Expected Response: `HTTP/1.1 200 OK` \
Response if video doesn't exist: `404 Not Found`

#### DELETE `/videos/{id}` - Delete a video (Disabled)
Deletes a video with the given id.
Example Curl Request
```shell
curl -i -X DELETE http://localhost:8080/videos/1
```
Expected Response: `HTTP/1.1 200 OK` \
Response if video doesn't exist: `404 Not Found`

### Hashtag Endpoints
#### `GET /hashtags` - Get all hashtags
Returns a list of all hashtags in the system. \
Example Curl Request:
```shell
curl -i -X GET http://localhost:8080/hashtags
```
Example response:
```json
[
  {
    "id": 1,
    "name": "myHashtag"
  }
]
```
#### `POST /hashtags` - Create a new hashtag
Creates a new hashtag entity in the system.
Example Curl Request
```shell
curl -i -X POST -H "Content-Type: application/json" -d '{"name": "myHashtag"}' http://localhost:8080/hashtags
```
Response expected: `HTTP/1.1 201 Created`

#### `GET /hashtags/{id}` - Get a hashtag by id
Returns a hashtag entity with the given id.
Example Curl Request
```shell
curl -i -X GET http://localhost:8080/hashtags/1
```
Example response:
```json
{
  "id": 1,
  "name": "myHashtag"
}
```

## Trending Hashtag Service
The Trending Hashtag Service is responsible for calculating the trending hashtags in the system. It does this by listening to the `video-liked` topic on Kafka and keeping track of the trending hashtags accordingly in a 1-hour sliding time window.

The Trending Hashtag Service exposes only the following endpoint:

#### GET `/trendingHashtags` - Get the trending hashtags
Returns a list of ids of the top 10 trending hashtags in the system. \
Example Curl Request:
```shell
curl -i -X GET http://localhost:8081/trendingHashtags
```
Example response:
```json
[1,2,3,4,5,6,7,8,9,10]
```


## Subscription Service

[//]: # (TODO: Add info here)


## CLI Client Usage
The CLI client can be used to interact with the microservices. It can be run as above in the [Running the CLI](#running-the-cli) section and the following commands are available:

### `add-user`
Adds a new user with the given name to the system.

```shell
add-user fred
```
Sample output:
```shell
Successfully created user with name fred
Remember your user ID 2007
```

### `get-user`
Gets the user with the given `id` from the system or by `name`.

```shell
list-users -name bob
list-users -id 1001
```
Sample output:
```shell
1001 - bob
```

### `list-users`
Lists all users in the system.

```shell
list-users
```
Sample output:
```shell
1001 - bob
1002 - alice
```

### `post-video`
Adds a new video with the given `title`, user posted by `id` and (optionsl) hashtags to the system.

```shell
post-video -title "My Video" 1 hashtag1 hashtag2
```
creates a video with title "My Video" posted by user with id 1 with hashtags "hashtag1" and "hashtag2"

Sample output:
```shell
Successfully created video with id 2001
```

### `get-video`
Gets the video with the given `id` from the system, or by hashtag `name` or by user posted by `id`.

```shell
get-video -id 2001
get-video -hashtag hashtag1
get-video -user 1001
```
Sample output:
```shell
2001 - My Video - bob - hashtag1, hashtag2
```

### `list-videos`
Lists all videos in the system.

```shell
list-videos
```
Sample output:
```shell
2001 - My Video - Hashtags: [hashtag1, hashtag2] - Posted By: bob
2002 - My Video2 - Hashtags: [] - Posted By: bob
```

### `like-video`
Marks the video with the given `id` as liked by a user.

```shell
like-video 1 2001
```
like video with id 2001 as user with id 1 \
Sample output:
```shell
Successfully liked video with id 2001
```

### `dislike-video`
Marks the video with the given `id` as disliked by a user.

```shell
dislike-video 1 2001
```
dislike video with id 2001 as user with id 1 \
Sample output:
```shell
Successfully disliked video with id 2001
```

### `watch-video`
Marks the video with the given `id` as watched by a user.

```shell
watch-video 1 2001
```
watch video with id 2001 as user with id 1\
Sample output:
```shell
Successfully watched video with id 2001
```

### `trending-hashtags`
Gets the top 10 trending hashtags in the system.

```shell
trending-hashtags
```
Sample output:
```shell
1 - hashtag1
2 - hashtag2
...
10 - hashtag10
```

### `subscribe`
Subscribes a user with the given `id` to the hashtag with the given `name`.

```shell
subscribe 1 hashtag1
```

[//]: # (TODO: finish this)

### `unsubscribe`
## CLI Client Usage
The CLI client can be used to interact with the microservices. It can be run as above in the [Running the CLI](#running-the-cli) section and the following commands are available:

### `add-user`
Adds a new user with the given name to the system.

```shell
add-user fred
```
Sample output:
```shell
Successfully created user with name fred
Remember your user ID 2007
```

### `get-user`
Gets the user with the given `id` from the system or by `name`.

```shell
list-users -name bob
list-users -id 1001
```
Sample output:
```shell
1001 - bob
```

### `list-users`
Lists all users in the system.

```shell
list-users
```
Sample output:
```shell
1001 - bob
1002 - alice
```

### `post-video`
Adds a new video with the given `title`, user posted by `id` and (optionsl) hashtags to the system.

```shell
post-video  "My Video" 1 hashtag1 hashtag2
```
creates a video with title "My Video" posted by user with id 1 with hashtags "hashtag1" and "hashtag2"

Sample output:
```shell
Successfully created video with id 2001
```

### `get-video`
Gets the video with the given `id` from the system, or by hashtag `name` or by user posted by `id`.

```shell
get-video -id 2001
get-video -hashtag hashtag1
get-video -postedBy 1001
```
Sample output:
```shell
2001 - My Video - bob - hashtag1, hashtag2
```

### `list-videos`
Lists all videos in the system.

```shell
list-videos
```
Sample output:
```shell
2001 - My Video - Hashtags: [hashtag1, hashtag2] - Posted By: bob
2002 - My Video2 - Hashtags: [] - Posted By: bob
```

### `like-video`
Marks the video with the given `id` as liked by a user.

```shell
like-video 1 2001
```
like video with id 2001 as user with id 1 \
Sample output:
```shell
Successfully liked video with id 2001
```

### `dislike-video`
Marks the video with the given `id` as disliked by a user.

```shell
dislike-video 1 2001
```
dislike video with id 2001 as user with id 1 \
Sample output:
```shell
Successfully disliked video with id 2001
```

### `watch-video`
Marks the video with the given `id` as watched by a user.

```shell
watch-video 1 2001
```
watch video with id 2001 as user with id 1\
Sample output:
```shell
Successfully watched video with id 2001
```

### `trending-hashtags`
Gets the top 10 trending hashtags in the system.

```shell
trending-hashtags
```
Sample output:
```shell
1 - hashtag1
2 - hashtag2
...
10 - hashtag10
```

### `subscribe`
Subscribes a user with the given `id` to the hashtag with the given `name`.

```shell
subscribe 1 hashtag1
```

[//]: # (TODO: finish this)

### `unsubscribe`
Unsubscribes a user with the given `id` from the hashtag with the given `name`.

```shell
unsubscribe 1 hashtag1
```

[//]: # (TODO: finish this)
Unsubscribes a user with the given `id` from the hashtag with the given `name`.

```shell
unsubscribe 1 hashtag1
```

[//]: # (TODO: finish this)