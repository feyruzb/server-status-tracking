# Server Status Tracking

A REST API for monitoring server availability. Registers servers, pings them on a
schedule over HTTP, and records the results so you can see uptime history.

Built as a Spring Boot learning project.

## Stack

- Java 21, Spring Boot
- Spring Web, Spring Data JPA, Hibernate
- PostgreSQL 16 (Docker), H2 for tests
- Maven

## Running

Start the database:

```bash
docker compose up -d
```

Then the app:

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080`.

Data persists in a Docker volume, so it survives restarts. To wipe it,
`docker compose down -v`.

## Tests

```bash
./mvnw test
```

Controller tests use `@WebMvcTest` with mocked services, covering the happy path,
404 on a missing server, and 400 on validation failure.

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/servers` | List all registered servers |
| `POST` | `/api/servers` | Register a server |
| `GET` | `/api/servers/{id}` | Get one server |
| `PUT` | `/api/servers/{id}` | Update a server |
| `DELETE` | `/api/servers/{id}` | Remove a server |
| `POST` | `/api/servers/{id}/check` | Run a health check now |
| `GET` | `/api/servers/{id}/history` | Check history, newest first |

### Example

```bash
curl -X POST http://localhost:8080/api/servers \
  -H "Content-Type: application/json" \
  -d '{"ip":"example.com","name":"control","description":"known good"}'

curl -X POST http://localhost:8080/api/servers/1/check
curl http://localhost:8080/api/servers/1/history
```

## How it works

Servers are checked automatically every 60 seconds by a scheduled background task.
Each check makes an HTTP GET to the server's address with a 3 second timeout and
records the outcome as `UP` or `DOWN` along with the response time.

Architecture is layered: controllers handle HTTP, services hold business logic,
repositories handle persistence. DTOs separate the API contract from the database
entities, and a global exception handler maps domain exceptions to HTTP status codes.

## Roadmap

### Testing
- [x] `@WebMvcTest` controller tests with MockMvc
- [x] Cover the 404 and 400 error paths
- [X] Unit tests for the service layer with Mockito
- [X] `@SpringBootTest` integration test

### Data model
- [ ] `enabled` flag on `MonitoredServer`
- [ ] `port` field so checks aren't hardcoded to port 80
- [ ] `@Transactional` on write methods
- [ ] Scheduler skips disabled servers

### Queries
- [ ] Latest check result per server
- [ ] Uptime percentage over the last 24 hours
- [ ] Include latest status in the server list response

### Concurrency
- [ ] `@Async` so slow checks don't block each other
- [ ] Thread pool configuration

### Infrastructure
- [x] PostgreSQL in Docker
- [x] `docker-compose.yml`
- [ ] Spring Boot Actuator
- [ ] Spring Security with JWT

### Nice to have
- [ ] OpenAPI/Swagger documentation
- [ ] Flyway migrations instead of `ddl-auto: update`
- [ ] Pagination on the history endpoint