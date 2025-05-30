#!/bin/bash
set -e

# Define your image repo prefix and tag here
REPO_PREFIX="alexmercer1234556789"
TAG="latest"   # or use a dynamic tag, e.g. from git commit
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "Script dir: $SCRIPT_DIR"
echo "🚀 Starting full build with tag: $TAG"

echo "🔹 Building Book Service..."
$SCRIPT_DIR/build-book-service.sh "$REPO_PREFIX/book-service:$TAG"

echo "🔹 Building Club Service..."
$SCRIPT_DIR/build-club-service.sh "$REPO_PREFIX/club-service:$TAG"

echo "🔹 Building Event Service..."
$SCRIPT_DIR/build-event-service.sh "$REPO_PREFIX/event-service:$TAG"

echo "✅ All builds completed successfully!"
