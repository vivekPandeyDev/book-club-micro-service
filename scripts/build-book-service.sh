#!/bin/bash
set -e

IMAGE=$1
TIMESTAMP=$(date -u +"%Y-%m-%dT%H:%M:%SZ")
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "Building Book Service Image: $IMAGE"

cd "$SCRIPT_DIR/../book-service"
./mvnw compile jib:buildTar -Dimage=$IMAGE -Djib.container.creationTime=$TIMESTAMP
docker load -i target/jib-image.tar
