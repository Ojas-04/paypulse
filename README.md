# merchant-service

merchant-service is a modular Java service built with **Quarkus** following **Hexagonal Architecture** principles. It is the merchant identity component of the **PayPulse** system: it registers merchants, looks them up by name or alias, and publishes registration events.

---

## Project Structure

The project is organized into 6 modules:

| Module | Purpose |
|--------|---------|
| **merchant-entrypoint** | REST APIs, request mappers, and application entry points |
| **merchant-model** | Commands, DTOs, events, and error responses |
| **merchant-domain** | Core entities, enums, constants, and exceptions |
| **merchant-ports** | Interfaces for use cases (inbound) and repositories (outbound) |
| **merchant-application** | Service implementations containing business logic |
| **merchant-adapters** | JPA entities, repositories, Kafka adapters, and mappers |

---

## Tech Stack

- **Java 17**
- **Quarkus 3.8**
- **Maven**
- **MapStruct** (object mapping)
- **Lombok** (boilerplate reduction)
- **PostgreSQL** (database, H2 in-memory for tests)
- **Liquibase** (schema migrations)
- **Quarkus Cucumber / REST Assured / JUnit 5 + Mockito** (testing)

---

## Setup & Running

### 1. Clone the repository

```bash

git clone https://github.com/Ojas-04/merchant-service.git
cd merchant-service
```

### 2. Build the project
```bash

mvn clean install
```
Or use the alias:
```bash
mci
```

### 3. Run the project in Quarkus dev mode
```bash

./run.sh
```
### Configuration
The main configuration file is located at:

```bash

merchant-entrypoint/src/main/resources/application.properties
You can configure:

Server port

Logging level

Database connections (PostgreSQL)

Liquibase migrations

Other Quarkus-specific settings

GitHub Guidelines
Only commit source code, pom.xml, and run.sh

target/ directories, IDE files (.idea/), and OS files (.DS_Store) are ignored via .gitignore.
```
Notes
Merchant registration is working end-to-end (REST API, domain, persistence).

Lookup by name/alias, aliases, and event publishing are planned next.

Liquibase changelogs live in `merchant-adapters/src/main/resources/db.changelog/`.

---

## Current Features

### Register a Merchant
You can register a merchant using the REST API provided by the `merchant-entrypoint` module.

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
Returns the registered merchant details and a unique merchant ID.

More features will be added as development progresses.
