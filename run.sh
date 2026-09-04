#!/bin/bash

# =============================
# PayPulse Project Run Script
# =============================
# Builds and runs ONLY the entrypoint module. Sibling modules (ports/model/domain/
# application/adapters) must already be installed in ~/.m2 and their target/classes
# present (Quarkus dev-mode live-reloads from them). Rebuild siblings only when you
# change their source:
#   mvn install -pl merchant-ports,merchant-model,merchant-domain,merchant-application,merchant-adapters

cd "$(dirname "$0")" || exit

echo "=== PayPulse Project Runner ==="

echo ">>> Building merchant-entrypoint module (no clean, tests skipped)..."
mvn install -pl merchant-entrypoint -DskipTests
if [ $? -ne 0 ]; then
  echo "Build failed! Exiting."
  exit 1
fi

echo ">>> Starting PayPulse in Quarkus dev mode..."
cd merchant-entrypoint || exit
mvn quarkus:dev
