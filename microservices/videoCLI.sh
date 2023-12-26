docker run --rm \
    -e DISABLE_WELCOME_MESSAGE=1 \
    -e LOG_LEVEL="WARN" \
    --network host \
    client:latest \
    "$@"