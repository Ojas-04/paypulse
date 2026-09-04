# merchant-service

merchant-service is a modular Java service built with **Quarkus** following **Hexagonal Architecture** principles. It is the merchant identity component of the **PayPulse** system: it registers merchants, resolves them by name or id, and publishes a registration event when a new merchant is created.

---

## Project Structure

The project is organized into 6 modules:

| Module | Purpose |
|--------|---------|
| **merchant-entrypoint** | REST APIs (interface + resource), request mappers, error mappers, and application entry points |
| **merchant-model** | Commands, DTOs, events, and error codes |
| **merchant-domain** | Core entities, enums, and domain concepts |
| **merchant-ports** | Interfaces for use cases (inbound) and repositories/events (outbound) |
| **merchant-application** | Service implementations containing business logic |
| **merchant-adapters** | JPA entities, repositories, Kafka/outbox adapters, and MapStruct mappers |

---

## Tech Stack

- **Java 17**
- **Quarkus 3.8**
- **Maven**
- **MapStruct** (object mapping)
- **Lombok** (boilerplate reduction)
- **PostgreSQL** (database, H2 in-memory for tests)
- **Liquibase** (schema migrations)
- **Kafka** (event publishing via the outbox pattern)
- **Quarkus Cucumber / REST Assured / JUnit 5 + Mockito** (testing)

---

## Setup & Running

### 1. Load the environment

Most config is driven by environment variables exported in `env.sh` (Kafka broker, topic names, database URL/credentials).

```bash
source env.sh
```

### 2. Clone the repository

```bash
git clone https://github.com/Ojas-04/merchant-service.git
cd merchant-service
```

### 3. Build the project

```bash
mvn clean install
```

Or use the alias `mci`.

### 4. Run the project in Quarkus dev mode

```bash
./run.sh
```

### Configuration

The main configuration file is located at:

```
merchant-entrypoint/src/main/resources/application.properties
```

Additional Kafka/messaging keys live in:

```
merchant-entrypoint/src/main/resources/messaging.properties
```

You can configure:

- Server port
- Logging level
- Database connections (PostgreSQL)
- Liquibase migrations
- Kafka broker / topics
- Other Quarkus-specific settings

> **GitHub Guidelines:** Only commit source code, `pom.xml`, and `run.sh`. `target/` directories, IDE files (`.idea/`), and OS files (`.DS_Store`) are ignored via `.gitignore`.

---

## Current Features

### Register a Merchant

Registers a merchant via `merchant-entrypoint`. Registration is **idempotent**: registering the same merchant name twice returns the existing merchant (HTTP `200`) instead of failing. When a merchant is genuinely new, a `paypulse.merchant.registered.v1` event is published to Kafka through the outbox pattern.

**Endpoint:**
```
POST /merchants/register
```

**Request Body Example:**
```json
{
  "name": "Merchant Name",
  "email": "merchant@example.com",
  "phone": "1234567890"
}
```

**Response:**
Returns the registered (or existing) merchant details and a unique merchant ID.

### Resolve a Merchant

Looks up a merchant by its case-insensitive `merchantName`, by its `id` (UUID), or by both when they agree. At least one of `name`/`id` must be provided.

- `200` — merchant found
- `400` — neither `name` nor `id` supplied, or the two resolve to different merchants (`VALIDATION_FAILED`)
- `404` — no merchant matches the supplied `name`/`id` (`MERCHANT_NOT_FOUND`)

**Endpoint:**
```
GET /merchants/resolve?name=SWIGGY&id=...
```

### Standard error responses

All errors share a consistent shape: `errorCode`, `message`, `timestamp`, and `traceId`. Machine-readable codes are defined in the `ErrorCode` enum:

| ErrorCode | HTTP status | Meaning |
|-----------|-------------|---------|
| `VALIDATION_FAILED` | 400 | Invalid request |
| `MERCHANT_NOT_FOUND` | 404 | Merchants not found for the supplied name/id |
| `MERCHANT_ALREADY_EXISTS` | 409 | Merchant already exists |
| `INTERNAL_ERROR` | 500 | Unexpected failure |

### Event publishing (outbox pattern)

- A new merchant is saved **and** an outbox row is written in the **same DB transaction**.
- A background `OutboxRelay` polls unpublished rows every 5s and publishes them to Kafka, marking them published only after the broker acknowledges (at-least-once delivery).
- Events use a common `EventEnvelope` (eventId, eventType, occurredAt, correlationId, producerService, payload). The topic `paypulse.merchant.registered.v1` carries `merchant.registered.v1` events.

### Health / readiness probes

Exposed for downstream synchronous callers (e.g. transaction-service):

- `/health/live`
- `/health/ready`

---

## Notes

- Merchant registration and resolution are working end-to-end (REST API, domain, persistence, event publishing).
- Lookup/aliases registration (`merchant_alias`) is planned next.
- Liquibase changelogs live in `merchant-adapters/src/main/resources/db.changelog/`.

More features will be added as development progresses.
