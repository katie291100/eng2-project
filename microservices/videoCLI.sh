docker run --rm \
    -e DISABLE_WELCOME_MESSAGE=1 \
    --network host \
    client:latest \
    "$@"