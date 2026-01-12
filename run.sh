#!/bin/bash

# =============================
# PayPulse Project Run Script
# =============================

# Navigate to project root (optional, ensures script works from anywhere)
cd "$(dirname "$0")" || exit

echo "=== PayPulse Project Runner ==="

# Step 1: Build only the entrypoint module and its dependencies
echo ">>> Building paypulse-entrypoint module..."
mvn clean install -pl paypulse-entrypoint -am
if [ $? -ne 0 ]; then
  echo "Build failed! Exiting."
  exit 1
fi

# Step 2: Start Quarkus dev mode for entrypoint module
echo ">>> Starting PayPulse in Quarkus dev mode..."
cd paypulse-entrypoint || exit
mvn quarkus:dev
