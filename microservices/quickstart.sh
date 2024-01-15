#!/bin/bash

echo "Running Commands..."
echo "-------------------"
echo "Running command add-user fred "

response=$(docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest add-user "Bob")
echo "$response"

user_id=$(echo "$response" | grep -o 'user ID [0-9]*' | awk '{print $3}')
echo "-------------------"

echo "Running command post-video \"video title1\" $user_id \"hashtag1\" \"hashtag2\""

response=$(docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest post-video "video title1" "$user_id" "hashtag1" "hashtag2")
echo "$response"
video_id1=$(echo "$response" | grep -o 'with id [0-9]*' | awk '{print $3}')

echo "-------------------"

echo "Running command subscribe $user_id -name \"hashtag1\""

docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest subscribe $user_id -name "hashtag1"

echo "-------------------"

echo "Running command post-video \"video title2\" $user_id \"hashtag1\" \"hashtag3\""
response=$(docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest post-video "video title2" "$user_id" "hashtag1" "hashtag3")
echo "$response"
video_id2=$(echo "$response" | grep -o 'with id [0-9]*' | awk '{print $3}')

echo "-------------------"

echo "Running command post-video \"video title3\" $user_id \"hashtag1\" \"hashtag3\""
response=$(docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest post-video "video title3" "$user_id" "hashtag1" "hashtag3")
echo "$response"
video_id3=$(echo "$response" | grep -o 'with id [0-9]*' | awk '{print $3}')


echo "-------------------"

echo "Running command post-video \"video title4\" $user_id \"hashtag1\" \"hashtag3\""
response=$(docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest post-video "video title4" "$user_id" "hashtag1" "hashtag3")
echo "$response"
video_id3=$(echo "$response" | grep -o 'with id [0-9]*' | awk '{print $3}')


echo "-------------------"

echo "Running command post-video \"video title5\" $user_id \"hashtag1\" \"hashtag3\""

response=$(docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest post-video "video title5" "$user_id" "hashtag1" "hashtag2")
echo "$response"
video_id3=$(echo "$response" | grep -o 'with id [0-9]*' | awk '{print $3}')


echo "-------------------"

echo  "Running command like-video $user_id $video_id1"
docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest like-video $user_id $video_id1

echo "-------------------"

echo  "Running command like-video $user_id $video_id2"

docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest like-video $user_id $video_id2

echo "-------------------"

echo  "Running command like-video $user_id $video_id3"

docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest like-video $user_id $video_id3

echo "-------------------"

echo  "Running command watch-video $user_id $video_id3"

docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest watch-video $user_id $video_id3

echo "-------------------"

echo "Waiting for 5 seconds for the likes and watch to be processed by streams..."
sleep 5

echo "-------------------"

echo "Running command get-trending-hashtags"

docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest get-trending-hashtags

sleep 5

echo "-------------------"

echo "Running command subscription-videos $user_id -name hashtag1"

docker run --rm -e DISABLE_WELCOME_MESSAGE=1 --network host client:latest subscription-videos $user_id -name "hashtag1"