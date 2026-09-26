# SIH26006 Freight Intelligence - Consolidated Project Status

**Last reviewed:** 2026-09-12  
**Current target:** Stage 3 - Spring Boot Backend  
**Document status:** This is the single source of project documentation. It replaces the previous README, database guide, phase design, technical prototype guide, fix guide, and project roadmap Markdown files.

## 1. Project Purpose

SIH26006 is a decision-support prototype for freight forecasting, vessel chartering, bulk-cargo procurement, port compatibility, risk assessment, and recommendations for overseas cargo bound for the East Coast of India.

The system does not make the final chartering decision. It explains inputs, constraints, risks, and ranked options to support a human decision-maker.

All current freight, vessel, port, forecast, and risk values are prototype or demo/synthetic data unless a future data-source integration explicitly identifies them as validated real data. The current forecast formula is explainable baseline logic, not a trained or independently validated ML model.

## 2. Current Technology Stack

| Area | Current technology | Status |
| --- | --- | --- |
| Frontend | HTML5, CSS3, vanilla JavaScript, Chart.js | Complete prototype UI |
| Backend | Java 17 target, Spring Boot 3.4.3, Spring Web, Spring Data JPA, Bean Validation | Stage 3 in progress |
| Database | MySQL 8 target; H2 fallback for local prototype startup | MySQL integration pending |
| Persistence | Hibernate / JPA | Partial prototype mappings only |
| Forecasting / ML | Python is planned for a later service | Not started by design |
| Build | Maven | Local Maven 3.9.9 used for verification |

## 3. Current Repository Structure

```text
frontend/       Stage 1 user interface
database/       Stage 2 SQL scripts and sample data
backend/        Stage 3 Spring Boot application
docker-compose.yml
PROJECT_STATUS.md
```

The active, compiled backend package is `in.sih26006.freight`. A separate `com.sih26006` Java tree remains in the repository as legacy reference material, but Maven excludes it from compilation because it targets an incompatible JPA/Spring generation.

## 4. Phase Status

| Stage | Scope | Status | Evidence / notes |
| --- | --- | --- | --- |
| 1 | Frontend UI | Complete | Nine user-facing HTML pages, responsive CSS, Chart.js visualizations, client-side demo flows, and demo-data notices exist. |
| 2 | Database design | Design complete; database instance unverified | Numbered SQL scripts define the intended 12-table MySQL schema and sample data. No successful live MySQL setup/connection is recorded. |
| 3A | Spring Boot skeleton | Complete | Spring Boot application builds, starts, and exposes `GET /api/health`. |
| 3B | MySQL connection and JPA verification | Pending | Requires one canonical schema and an environment-based MySQL connection. |
| 3C | All entities and relationships | Pending | Only three prototype entities are active; the intended 12-table schema is not yet mapped safely. |
| 3D | Repositories and DTOs | Partial / pending | Prototype has three repositories and forecast DTOs; the documented MVP boundary is incomplete. |
| 3E | Services and validation | Partial / pending | Prototype has decision and weather services. Domain services and central exceptions are missing. |
| 3F | REST API | Partial / pending | Prototype endpoints work locally, but documented API coverage and consistent API error contracts are incomplete. |
| 3G | Authentication and security | Pending | JWT, BCrypt, RBAC, restrictive CORS, and security tests are not implemented. |
| 3H | Tests, API documentation, completion audit | Pending | No automated test classes or OpenAPI/Swagger configuration exists. |
| 4 | Frontend-to-backend integration | Partial / not current target | The Forecast form calls the local prototype API. Other frontend areas still use demo data. |
| 5 | Compatibility and risk engines | Pending | Current prototype behavior is not the documented, complete explainable engine. |
| 6 | Measured freight baseline | Pending | No time-series validation or MAE/RMSE evidence exists. |
| 7 | Python ML service | Not started intentionally | Build Java + MySQL reliability before introducing an ML service. |
| 8-11 | Recommendation orchestration, production hardening, deployment, final QA | Pending | Do not start before the earlier backend work is verified. |

## 5. Completed Work

### Stage 1 - Frontend

- Login, dashboard, forecast, vessels, ports, recommendations, alerts, analytics, and about pages exist.
- The UI is responsive and includes Chart.js visualizations.
- The forecast page sends its request to `http://localhost:8080/api/forecast` and displays an API failure instead of inventing a result when the backend is unavailable.
- Existing frontend design and files should not be redesigned or replaced during Stage 3 without an explicit request.

### Stage 2 - Database Design

The documented target schema is `sih26006_freight_intelligence` and contains these 12 tables:

1. `users`
2. `vessels`
3. `ports`
4. `origins`
5. `cargo_types`
6. `freight_history`
7. `forecasts`
8. `vessel_compatibility`
9. `risk_assessments`
10. `recommendations`
11. `alerts`
12. `audit_log`

The numbered scripts provide database creation, table definitions, sample data, database users, and useful reference queries. The planned schema includes foreign keys, indexes, timestamps, audit records, user roles, and sample records. Sample data must remain labelled as demo/synthetic data.

### Stage 3A - Backend Skeleton

- `backend/pom.xml` declares Spring Web, Spring Data JPA, MySQL Connector/J, H2, Validation, and Spring Boot Test.
- The application target is Java 17 and Spring Boot 3.4.3.
- `FreightApplication` is the application entry point.
- Maven compiles only the active `in.sih26006.freight` tree. The old `com.sih26006` tree is excluded because it uses `javax.persistence` and Lombok without a compatible dependency/configuration setup.
- Verification completed on 2026-09-12:
  - `mvn test` completed with `BUILD SUCCESS`.
  - No automated test classes currently exist, so Maven executed zero tests.
  - The application started with its local H2 fallback.
  - `GET /api/health` returned `{"mode":"prototype","status":"UP"}`.

## 6. Existing Backend Components

### Active prototype components

| Component | Current state |
| --- | --- |
| Entities | `Port`, `Vessel`, `CargoRequest` |
| Repositories | Port, vessel, and cargo-request JPA repositories |
| DTOs | Forecast request and response DTOs |
| Services | Deterministic decision service and cached weather service |
| Controller | One `ApiController` |
| Demo initializer | Seeds prototype ports and vessels when the local database is empty |

### Existing prototype endpoints

| Method | Endpoint | Current purpose |
| --- | --- | --- |
| GET | `/api/health` | Health response |
| GET | `/api/ports` | Prototype port data |
| GET | `/api/vessels` | Prototype vessel data |
| GET | `/api/cargo?page=0&size=20` | Paginated prototype cargo requests |
| POST | `/api/forecast` | Saves a prototype cargo input and returns deterministic forecast/recommendation data |
| GET | `/api/weather/{port}` | Cached weather lookup for configured prototype ports |

These endpoints are not yet the final documented API contract. They must not be presented as real-time freight, AIS, or validated market data.

## 7. Current Problems and Inconsistencies

### 7.1 Two incompatible database schemas

This is the most important outstanding issue.

- The numbered Stage 2 scripts define `sih26006_freight_intelligence` with 12 tables. This is the documented database target for Stage 3.
- `database/schema.sql`, `database/sample_data.sql`, and `docker-compose.yml` define a different database named `sih26006` with seven tables: `users`, `ports`, `vessels`, `cargo_requests`, `forecasts`, `recommendations`, and `risk_alerts`.
- The active three-entity backend matches the smaller Docker/H2 prototype more closely than the documented 12-table schema.

The documented 12-table schema is the canonical target for future Stage 3 work. Do not alter it casually. Stage 3B and 3C must adapt the backend to it rather than silently creating a third schema.

### 7.2 Duplicate Java implementations

- `in.sih26006.freight` is active Spring Boot 3/Jakarta code.
- `com.sih26006` is legacy partial entity/repository code using `javax.persistence` and Lombok.
- The legacy source is intentionally excluded from Maven compilation. It has not been deleted, rewritten, or treated as a reliable entity mapping.

### 7.3 Runtime configuration is not currently tracked in the backend resources directory

`backend/src/main/resources/application.properties` is currently deleted from its tracked location, while an untracked `application.properties` exists at the repository root. Spring Boot does not load that root file when it runs from `backend`, so the successful Stage 3A runtime check used Spring Boot's embedded H2 defaults.

Before Stage 3B, establish an intentionally tracked, environment-variable-based backend configuration without hard-coding credentials.

### 7.4 Security and production gaps

- Current controller CORS permits all origins; restrictive CORS belongs in Stage 3G.
- There is no authentication, password hashing, JWT, RBAC, rate limiting, secrets management, audit implementation, or authorization testing.
- There is no global exception handler, consistent error-response format, OpenAPI/Swagger configuration, or automated backend test suite.
- Docker Compose contains development credentials. They must never be reused in production.
- Live vessel AIS data is not implemented and requires a licensed provider and credentials.
- Weather data is an optional external prototype lookup, not a market-data source.

## 8. Development Rules

1. Keep changes small and verifiable: inspect first, implement one requested task, build/test, then report results.
2. Preserve the existing frontend and the documented 12-table database contract unless a requested task explicitly changes them.
3. Use the architecture `controller -> service -> repository -> entity`.
4. Keep DTOs at API boundaries; do not expose JPA entities directly from final REST endpoints.
5. Validate all external input and keep controllers thin.
6. Do not invent database columns, APIs, market data, accuracy claims, or ML features.
7. Use Java/Spring Boot/MySQL for the core application. Use Python only later for separately deployed ML training/inference.
8. Never commit production passwords, API keys, JWT secrets, or private keys.
9. Clearly label demo/synthetic values and keep recommendations explainable.
10. Do not claim that the system makes autonomous chartering decisions.

## 9. Exact Next Implementation Task

Implement **Stage 3B only**.

1. Restore a tracked backend configuration file under `backend/src/main/resources/` using environment variables for database URL, username, and password.
2. Configure a safe local development option and a MySQL option for the canonical `sih26006_freight_intelligence` database.
3. Do not alter any table, SQL script, frontend file, or Docker schema in this task.
4. Verify the application connects to MySQL and can query one existing canonical table.
5. Add a focused connection/integration test if the available environment can run MySQL.
6. Run the Maven build/tests and report the exact database verification result.

After Stage 3B, implement Stage 3C: map all 12 documented tables and relationships safely with Jakarta JPA, validation, and DTO separation. Do not implement forecasting, recommendations, authentication, or Python ML as part of either task.

## 10. Controlled Demo Scenario

Use this scenario for future end-to-end testing after the underlying stages are complete:

- Origin: Australia
- Destination: Paradip
- Cargo: Coal
- Quantity: 70,000 tonnes
- Contract duration: 3 months

Expected flow: input validation -> route/cargo lookup -> freight history -> explainable forecast -> vessel and port compatibility -> risk factors -> ranked decision-support recommendation -> dashboard display.

Every number in the controlled scenario remains demo/synthetic until its source and validation evidence are recorded.
