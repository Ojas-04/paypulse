#!/usr/bin/env bash
#
# PayPulse shared environment — source before running any service:
#   source env.sh
#
# Holds the cross-service constants (topics, DB schema, broker) referenced by
# env-var placeholders in each service's Quarkus config (01-PHASE0-CONVENTIONS.md).

set -a

# ---------------------------------------------------------------------------
# Kafka broker (§2)
# ---------------------------------------------------------------------------
export KAFKA_BOOTSTRAP_SERVERS="localhost:9092"

# ---------------------------------------------------------------------------
# Topics (§2) — paypulse.<domain>.<event-name>.v<version>
# ---------------------------------------------------------------------------
export TOPIC_MERCHANT_REGISTERED="paypulse.merchant.registered.v1"

# ---------------------------------------------------------------------------
# Database
# ---------------------------------------------------------------------------
export DB_URL="jdbc:postgresql://localhost:5432/paypulse"
# Local DB username
export DB_USERNAME="YOUR_USERNAME"
# Local DB password
export DB_PASSWORD="password"

set +a
