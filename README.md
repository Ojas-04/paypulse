# Paypulse

Paypulse is a modular Java project built with **Quarkus** following **Hexagonal Architecture** principles. The project handles payment and merchant management and provides REST APIs for external interaction.

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
- **PostgreSQL** (database)
- **Testcontainers** (for integration tests)
- **REST Assured / JUnit 5 + Mockito** (testing)

---

## Setup & Running

### 1. Clone the repository

```bash

git clone https://github.com/Ojas-04/paypulse.git
cd paypulse
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

merchant-entrypoint/src/resources/application.yml
You can configure:

Server port

Logging level

Database connections (PostgreSQL)

Other Quarkus-specific settings

GitHub Guidelines
Only commit source code, pom.xml, and run.sh

target/ directories, IDE files (.idea/), and OS files (.DS_Store) are ignored via .gitignore.
```
Notes
The project is currently a skeleton structure with modules in place.

Flyway or Liquibase can be added for database migrations.

Kafka adapters and JPA repositories are prepared in the adapters module.

---

## Current Features

### Register a Merchant
You can register a merchant using the REST API provided by the `merchant-entrypoint` module.

**Endpoint:**
```
POST /api/merchant/register
```
**Request Body Example:**
```json
{
  "name": "Merchant Name",
  "email": "merchant@example.com",
  "address": "123 Main St"
}
```
**Response:**
Returns the registered merchant details and a unique merchant ID.

More features will be added as development progresses.
