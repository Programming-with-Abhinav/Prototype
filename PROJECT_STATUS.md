# SIH26006 Freight Intelligence - Consolidated Project Status

**Last reviewed:** 2026-10-01  
**Current target:** Stage 3D - Repositories, DTOs & Service Layer  
**Document status:** This is the single source of project documentation. It replaces the previous README, database guide, phase design, technical prototype guide, fix guide, and project roadmap Markdown files. It also serves as the operational Guidebook for IDE agents and developers.

---

## 1. Project Purpose

SIH26006 is a decision-support prototype for freight forecasting, vessel chartering, bulk-cargo procurement, port compatibility, risk assessment, and recommendations for overseas cargo bound for the East Coast of India (e.g., Paradip, Visakhapatnam, Haldia).

The system does not make the final chartering decision. It explains inputs, constraints, risks, and ranked options to support a human decision-maker.

All current freight, vessel, port, forecast, and risk values are prototype or demo/synthetic data unless a future data-source integration explicitly identifies them as validated real data. The current forecast formula is explainable baseline logic, not a trained or independently validated ML model.

---

## 2. Current Technology Stack

| Area | Current technology | Status |
| --- | --- | --- |
| Frontend | HTML5, CSS3, vanilla JavaScript, Chart.js | Complete prototype UI (9 pages) |
| Backend | Java 21 / Java 17 target, Spring Boot 3.4.3, Spring Web, Spring Data JPA, Bean Validation | Stage 3 in progress |
| Database | MySQL 8 target (`sih26006_freight_intelligence`); H2 in MySQL mode fallback | Stage 3B complete (dual-profile verified) |
| Persistence | Jakarta Persistence (JPA) / Hibernate 6.x | 3 prototype entities active; 12-table mapping target (Stage 3C) |
| Forecasting / ML | Deterministic baseline; Python ML planned for later service | Stage 5 / Stage 7 target |
| Build Tool | Apache Maven 3.9.9 (bundled under `.tools/apache-maven-3.9.9/`) | Operational & verified |

---

## 3. Current Repository Structure

```text
frontend/             Stage 1 user interface (9 HTML pages, CSS, JS, Chart.js)
database/             Stage 2 SQL scripts (01-05 canonical scripts and schemas)
backend/              Stage 3 Spring Boot 3.4.3 application
  pom.xml
  src/main/resources/
    application.properties          Tracked default profile (H2 in MySQL mode for canonical schema)
    application-mysql.properties    Tracked MySQL profile for sih26006_freight_intelligence
  src/main/java/
    in/sih26006/freight/            Active, compiled Spring Boot 3 / Jakarta JPA package
    com/sih26006/                   Legacy reference models (excluded from Maven compilation)
  src/test/java/
    in/sih26006/freight/            Unit and integration tests
.tools/apache-maven-3.9.9/  Local Maven runtime
docker-compose.yml          Docker setup for local MySQL container
PROJECT_STATUS.md           Master project status and IDE Agent Guidebook
```

---

## 4. Phase Status

| Stage | Scope | Status | Evidence / Notes |
| --- | --- | --- | --- |
| 1 | Frontend UI | Complete | Nine user-facing HTML pages, responsive CSS, Chart.js visualizations, client-side demo flows, and demo-data notices. |
| 2 | Database design | Design complete | Numbered SQL scripts (01 to 05) define the canonical 12-table `sih26006_freight_intelligence` MySQL schema and sample data. |
| 3A | Spring Boot skeleton | Complete | Application compiles and runs with Spring Boot 3.4.3 on Java 21/17. `GET /api/health` operational. |
| 3B | Configuration & DB connection | Complete | Tracked `application.properties` and `application-mysql.properties` established. Environment variables supported. `DatabaseConnectionTest` and `MySqlConnectionIntegrationTest` verified with `mvn test` passing (7 tests run, 0 failures, 1 gracefully skipped when local port 3306 is offline). |
| 3C | All 12 entities and repositories | CURRENT TARGET | Map all 12 canonical tables into `in.sih26006.freight.entity` using Jakarta JPA (`jakarta.persistence.*`) and create corresponding repositories. |
| 3D | Repositories and DTOs | Pending | Complete DTO boundaries for all entities to prevent exposing JPA entities directly. |
| 3E | Services and validation | Pending | Domain services, input validation, and centralized exception handling (`@RestControllerAdvice`). |
| 3F | REST API contracts | Pending | Standardize endpoints, pagination, and consistent API error contracts across all modules. |
| 3G | Authentication and security | Pending | JWT, BCrypt, RBAC, restrictive CORS, and security tests. |
| 3H | Tests & OpenAPI documentation | Pending | Complete unit/integration test coverage and Swagger/OpenAPI setup. |
| 4 | Frontend-to-backend integration | Pending | Connect remaining 8 frontend pages to live Spring Boot REST endpoints. |
| 5 | Compatibility and risk engines | Pending | Implement explainable port compatibility and multidimensional risk calculation engines. |
| 6 | Measured freight baseline | Pending | Statistical baseline calculation and benchmark validation. |
| 7 | Python ML service | Planned | Independent microservice for time-series forecasting. |

---

## 5. Completed Work

### Stage 1 - Frontend
- 9 user-facing HTML pages: `index.html`, `dashboard.html`, `forecast.html`, `vessels.html`, `ports.html`, `recommendations.html`, `alerts.html`, `analytics.html`, `about.html`.
- Responsive CSS, Chart.js data visualizations, and clear demo notices.
- Forecast page integrates with `POST /api/forecast`.

### Stage 2 - Database Design
- Canonical schema: `sih26006_freight_intelligence` defined across 12 tables in `database/02_create_tables.sql`:
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
- Additional scripts provide sample data (`03_load_sample_data.sql`), user permissions (`04_create_users.sql`), and reference queries (`05_useful_queries.sql`).

### Stage 3A - Backend Skeleton
- Spring Boot 3.4.3 initialized with Maven pom configuring Spring Web, Spring Data JPA, MySQL Connector/J, H2, Validation, and Spring Boot Test.
- `in.sih26006.freight.FreightApplication` is the primary entry point.

### Stage 3B - Configuration & Verification
- Established tracked `application.properties` and `application-mysql.properties` under `backend/src/main/resources/`.
- Safe local development default: H2 in MySQL mode simulating `sih26006_freight_intelligence`.
- Full environment variable support: `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`, `MYSQL_HOST`, `MYSQL_PORT`.
- Verification completed on 2026-10-01:
  - `& ..\.tools\apache-maven-3.9.9\bin\mvn.cmd test` passed with `BUILD SUCCESS`.
  - 7 tests executed, 0 failures, 0 errors.
  - `ApiControllerTest`: verifies REST controller endpoints.
  - `DatabaseConnectionTest`: verifies DataSource injection, metadata, `SELECT 1`, and port repository connectivity.
  - `MySqlConnectionIntegrationTest`: gracefully skips live MySQL query when port 3306 is not bound, verifying canonical naming configuration.

---

## 6. Existing Backend Components

### Active prototype components (`in.sih26006.freight`)
| Component | Files | Description |
| --- | --- | --- |
| Entities | `Port`, `Vessel`, `CargoRequest` | Jakarta JPA entity models for initial prototype |
| Repositories | `PortRepository`, `VesselRepository`, `CargoRequestRepository` | Spring Data JPA interfaces |
| DTOs | `ForecastRequest`, `ForecastResponse` | Request and response contracts for forecast API |
| Services | `DecisionService`, `WeatherService` | Deterministic baseline logic and in-memory cached weather provider |
| Controller | `ApiController` | Exposes health, ports, vessels, cargo, forecast, and weather endpoints |
| Initializer | `DemoDataInitializer` | Automatically seeds demo ports and vessels if empty |

---

## 7. Current Architectural Issues & Resolutions

### 7.1 Database Schema Alignment
- Canonical target is `sih26006_freight_intelligence` (12 tables in `database/02_create_tables.sql`).
- Any legacy reference to `sih26006` (7 tables) is superseded. Stage 3C maps the full 12-table model.

### 7.2 Duplicate Java Trees
- Active code: `in.sih26006.freight.*` (Spring Boot 3, `jakarta.persistence.*`).
- Legacy reference: `com.sih26006.*` (Spring Boot 2 / `javax.persistence.*` with Lombok, excluded in `pom.xml`).
- *Resolution in Stage 3C:* Migrate the entity definitions into `in.sih26006.freight.entity` using standard Java (avoiding Lombok compilation issues) and Jakarta annotations, then retire the legacy directory.

### 7.3 Runtime Configuration (RESOLVED in Stage 3B)
- `backend/src/main/resources/application.properties` and `application-mysql.properties` are tracked in git and support environment overrides.

---

## 8. Development Rules

1. **Incremental and verifiable:** Inspect first, implement one stage/task at a time, build and run tests, then report results.
2. **Preserve existing contracts:** Do not modify the frontend UI files or the canonical 12-table database schema unless explicitly asked.
3. **Layered architecture:** Strictly adhere to `Controller -> Service -> Repository -> Entity`.
4. **DTO boundary enforcement:** Never return JPA entities directly from final REST controllers. Always map to DTOs.
5. **Bean validation:** Use `jakarta.validation.constraints.*` on all request DTOs.
6. **Ground truth:** Do not fabricate real-time AIS, market data, or ML accuracy claims. Always label synthetic data clearly.
7. **Security:** Never hardcode credentials, tokens, or private keys.

---

## 9. Exact Next Implementation Task: Stage 3C

**Target:** Create all 12 JPA Entities and Repositories in `in.sih26006.freight`.

1. Map the 12 canonical tables defined in `database/02_create_tables.sql` into `in.sih26006.freight.entity`:
   - `User`, `Vessel`, `Port`, `Origin`, `CargoType`, `FreightHistory`, `Forecast`, `VesselCompatibility`, `RiskAssessment`, `Recommendation`, `Alert`, `AuditLog`.
2. Use `jakarta.persistence.*` annotations with exact table and column names matching `02_create_tables.sql`.
3. Configure proper relationships (`@ManyToOne`, `@JoinColumn`) with `FetchType.LAZY`.
4. Create the 12 corresponding Spring Data JPA repositories in `in.sih26006.freight.repository`.
5. Update `DemoDataInitializer` to support the new entity models cleanly.
6. Verify compilation and tests with `mvn test`.

---

## 10. Controlled Demo Scenario

For end-to-end integration and verification:
- **Origin:** Australia
- **Destination:** Paradip Port
- **Cargo:** Coal
- **Quantity:** 70,000 tonnes (Panamax range)
- **Contract duration:** 3 months
- **Expected flow:** Input validation -> route & cargo lookup -> freight history -> explainable forecast -> vessel & port compatibility -> risk scoring -> ranked recommendations -> dashboard visualization.

---

# 11. IDE Agent Guidebook: How to Execute & Complete Stages

This section is an explicit execution manual designed for **Open IDE Agents** (and engineers) to carry out the project roadmap autonomously and safely.

### 11.1 Agent Environment & Tool Execution Runbook

When executing shell commands on this machine:
- **OS:** Windows 11 (PowerShell)
- **Java:** Microsoft JDK 21 is installed and available in the system PATH.
- **Maven:** Use the bundled Apache Maven binary at:
  ```powershell
  # From project root:
  & .\.tools\apache-maven-3.9.9\bin\mvn.cmd <goals>
  
  # From backend directory:
  & ..\.tools\apache-maven-3.9.9\bin\mvn.cmd <goals>
  ```
  *(Do not call plain `mvn` as it is not in the system environment PATH).*
- **Run Tests:**
  ```powershell
  & ..\.tools\apache-maven-3.9.9\bin\mvn.cmd test
  ```
- **Run Application Locally (H2 Safe Mode):**
  ```powershell
  & ..\.tools\apache-maven-3.9.9\bin\mvn.cmd spring-boot:run
  ```
- **Run Application with MySQL:**
  ```powershell
  & ..\.tools\apache-maven-3.9.9\bin\mvn.cmd spring-boot:run -Dspring-boot.run.profiles=mysql
  ```

---

### 11.2 Stage 3C: Detailed Entity & Repository Blueprint

Every entity must be in package `in.sih26006.freight.entity`, use `jakarta.persistence.*`, include standard Java getters/setters and constructors, and match `database/02_create_tables.sql`.

#### Entity 1: `User` (`users`)
- Table: `users`
- Fields:
  - `userId` (`user_id`, INT, PK, AUTO_INCREMENT)
  - `username` (`username`, VARCHAR(50), UNIQUE, NOT NULL)
  - `passwordHash` (`password_hash`, VARCHAR(255), NOT NULL)
  - `email` (`email`, VARCHAR(100), UNIQUE, NOT NULL)
  - `fullName` (`full_name`, VARCHAR(100), NOT NULL)
  - `companyName` (`company_name`, VARCHAR(100))
  - `userRole` (`user_role`, VARCHAR(20) / Enum: `admin`, `manager`, `operator`, `viewer`)
  - `isActive` (`is_active`, BOOLEAN)
  - `createdAt`, `updatedAt`, `lastLogin` (`TIMESTAMP`)

#### Entity 2: `Vessel` (`vessels`)
- Table: `vessels`
- Fields:
  - `vesselId` (`vessel_id`, INT, PK, AUTO_INCREMENT)
  - `vesselType` (`vessel_type`, VARCHAR(50), UNIQUE, NOT NULL)
  - `cargoCapacityTonnes` (`cargo_capacity_tonnes`, INT, NOT NULL)
  - `draftMeters` (`draft_meters`, BigDecimal(5,2), NOT NULL)
  - `loaLengthMeters` (`loa_length_meters`, INT, NOT NULL)
  - `beamWidthMeters` (`beam_width_meters`, INT, NOT NULL)
  - `ageYears` (`age_years`, INT)
  - `fuelConsumptionPerDay` (`fuel_consumption_per_day`, BigDecimal(8,2))
  - `averageDayRateUsd` (`average_day_rate_usd`, INT)
  - `isActive` (`is_active`, BOOLEAN)

#### Entity 3: `Port` (`ports`)
- Table: `ports`
- Fields:
  - `portId` (`port_id`, INT, PK, AUTO_INCREMENT)
  - `portName` (`port_name`, VARCHAR(100), UNIQUE, NOT NULL)
  - `country` (`country`, VARCHAR(50))
  - `stateProvince` (`state_province`, VARCHAR(50))
  - `maxDraftMeters` (`max_draft_meters`, BigDecimal(5,2), NOT NULL)
  - `maxLoaMeters` (`max_loa_meters`, INT, NOT NULL)
  - `maxBeamMeters` (`max_beam_meters`, INT, NOT NULL)
  - `annualCargoCapacityMillions` (`annual_cargo_capacity_millions`, INT)
  - `currentCongestionPercentage` (`current_congestion_percentage`, INT)
  - `isOperational` (`is_operational`, BOOLEAN)

#### Entity 4: `Origin` (`origins`)
- Table: `origins`
- Fields:
  - `originId` (`origin_id`, INT, PK, AUTO_INCREMENT)
  - `originName` (`origin_name`, VARCHAR(100), UNIQUE, NOT NULL)
  - `locationType` (`location_type`, VARCHAR(20) / Enum: `port`, `country`, `region`)
  - `country` (`country`, VARCHAR(50))
  - `isActive` (`is_active`, BOOLEAN)

#### Entity 5: `CargoType` (`cargo_types`)
- Table: `cargo_types`
- Fields:
  - `cargoTypeId` (`cargo_type_id`, INT, PK, AUTO_INCREMENT)
  - `cargoName` (`cargo_name`, VARCHAR(100), UNIQUE, NOT NULL)
  - `cargoCategory` (`cargo_category`, VARCHAR(20) / Enum: `bulk`, `breakbulk`, `container`, `general`)
  - `isHazardous` (`is_hazardous`, BOOLEAN)
  - `specialRequirements` (`special_requirements`, VARCHAR(255))

#### Entity 6: `FreightHistory` (`freight_history`)
- Table: `freight_history`
- Fields & Relationships:
  - `freightHistoryId` (`freight_history_id`, INT, PK, AUTO_INCREMENT)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "origin_id") Origin origin`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "destination_port_id") Port destinationPort`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "cargo_type_id") CargoType cargoType`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "vessel_type_id") Vessel vessel`
  - `freightRatePerTonne` (`freight_rate_per_tonne`, BigDecimal(10,2), NOT NULL)
  - `recordedDate` (`recorded_date`, LocalDate, NOT NULL)
  - `volatilityIndex` (`volatility_index`, BigDecimal(5,2))
  - `supplyDemandIndex` (`supply_demand_index`, INT)

#### Entity 7: `Forecast` (`forecasts`)
- Table: `forecasts`
- Fields & Relationships:
  - `forecastId` (`forecast_id`, INT, PK, AUTO_INCREMENT)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") User user`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "origin_id") Origin origin`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "destination_port_id") Port destinationPort`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "cargo_type_id") CargoType cargoType`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "vessel_type_id") Vessel vessel`
  - `cargoQuantityTonnes` (`cargo_quantity_tonnes`, INT, NOT NULL)
  - `contractDurationMonths` (`contract_duration_months`, INT)
  - `forecastPeriodDays` (`forecast_period_days`, INT, NOT NULL)
  - `currentFreightRate` (`current_freight_rate`, BigDecimal(10,2), NOT NULL)
  - `predictedFreightRate` (`predicted_freight_rate`, BigDecimal(10,2), NOT NULL)
  - `forecastTrend` (`forecast_trend`, VARCHAR(50))
  - `trendPercentage` (`trend_percentage`, BigDecimal(6,2))
  - `confidenceLevel` (`confidence_level`, INT)
  - `forecastDate` (`forecast_date`, LocalDate, NOT NULL)
  - `forecastedDate` (`forecasted_date`, LocalDate, NOT NULL)
  - `algorithmUsed` (`algorithm_used`, VARCHAR(100))

#### Entity 8: `VesselCompatibility` (`vessel_compatibility`)
- Table: `vessel_compatibility`
- Fields & Relationships:
  - `compatibilityId` (`compatibility_id`, INT, PK, AUTO_INCREMENT)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "origin_id") Origin origin`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "destination_port_id") Port destinationPort`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "vessel_type_id") Vessel vessel`
  - `cargoQuantityTonnes` (`cargo_quantity_tonnes`, INT, NOT NULL)
  - `draftCompatible` (`draft_compatible`, BOOLEAN)
  - `loaCompatible` (`loa_compatible`, BOOLEAN)
  - `beamCompatible` (`beam_compatible`, BOOLEAN)
  - `capacitySufficient` (`capacity_sufficient`, BOOLEAN)
  - `overallCompatibility` (`overall_compatibility`, VARCHAR(50))
  - `compatibilityScore` (`compatibility_score`, INT)
  - `notes` (`notes`, VARCHAR(255))

#### Entity 9: `RiskAssessment` (`risk_assessments`)
- Table: `risk_assessments`
- Fields & Relationships:
  - `riskAssessmentId` (`risk_assessment_id`, INT, PK, AUTO_INCREMENT)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") User user`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "forecast_id") Forecast forecast` (Nullable)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "origin_id") Origin origin`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "destination_port_id") Port destinationPort`
  - `freightVolatilityScore` (`freight_volatility_score`, INT)
  - `portCongestionScore` (`port_congestion_score`, INT)
  - `demandUncertaintyScore` (`demand_uncertainty_score`, INT)
  - `vesselCompatibilityScore` (`vessel_compatibility_score`, INT)
  - `overallRiskScore` (`overall_risk_score`, INT)
  - `riskLevel` (`risk_level`, VARCHAR(50))
  - `riskFactorsJson` (`risk_factors_json`, VARCHAR(2000) or TEXT)
  - `recommendations` (`recommendations`, TEXT)
  - `assessmentDate` (`assessment_date`, LocalDateTime)

#### Entity 10: `Recommendation` (`recommendations`)
- Table: `recommendations`
- Fields & Relationships:
  - `recommendationId` (`recommendation_id`, INT, PK, AUTO_INCREMENT)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") User user`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "forecast_id") Forecast forecast` (Nullable)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "risk_assessment_id") RiskAssessment riskAssessment` (Nullable)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "origin_id") Origin origin`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "destination_port_id") Port destinationPort`
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "recommended_vessel_type_id") Vessel recommendedVessel`
  - `recommendedStrategy` (`recommended_strategy`, VARCHAR(255))
  - `strategyRationale` (`strategy_rationale`, TEXT)
  - `expectedFreightCost` (`expected_freight_cost`, BigDecimal(15,2))
  - `riskLevel` (`risk_level`, VARCHAR(50))
  - `marketEntryTiming` (`market_entry_timing`, VARCHAR(100))
  - `contractDurationRecommendation` (`contract_duration_recommendation`, INT)
  - `confidenceLevel` (`confidence_level`, INT)

#### Entity 11: `Alert` (`alerts`)
- Table: `alerts`
- Fields & Relationships:
  - `alertId` (`alert_id`, INT, PK, AUTO_INCREMENT)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") User user`
  - `alertType` (`alert_type`, VARCHAR(50), NOT NULL)
  - `priorityLevel` (`priority_level`, VARCHAR(20) / Enum: `low`, `medium`, `high`)
  - `title` (`title`, VARCHAR(255), NOT NULL)
  - `message` (`message`, TEXT, NOT NULL)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "related_forecast_id") Forecast relatedForecast` (Nullable)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "related_port_id") Port relatedPort` (Nullable)
  - `isRead` (`is_read`, BOOLEAN)
  - `createdAt`, `readAt` (`TIMESTAMP`)

#### Entity 12: `AuditLog` (`audit_log`)
- Table: `audit_log`
- Fields & Relationships:
  - `auditLogId` (`audit_log_id`, INT, PK, AUTO_INCREMENT)
  - `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") User user` (Nullable)
  - `action` (`action`, VARCHAR(100), NOT NULL)
  - `tableName` (`table_name`, VARCHAR(100))
  - `recordId` (`record_id`, INT)
  - `oldValues` (`old_values`, VARCHAR(2000) or TEXT)
  - `newValues` (`new_values`, VARCHAR(2000) or TEXT)
  - `ipAddress` (`ip_address`, VARCHAR(45))
  - `createdAt` (`created_at`, LocalDateTime)

---

### 11.3 Repositories Specification

All 12 repositories must reside in `in.sih26006.freight.repository` and extend `JpaRepository<EntityClass, Integer>`:
1. `UserRepository` (`findByUsername`, `findByEmail`, `existsByUsername`)
2. `VesselRepository` (`findByVesselType`, `findByIsActiveTrue`)
3. `PortRepository` (`findByPortName`, `findByCountry`)
4. `OriginRepository` (`findByOriginName`, `findByIsActiveTrue`)
5. `CargoTypeRepository` (`findByCargoName`)
6. `FreightHistoryRepository` (`findByOriginAndDestinationPortOrderByRecordedDateDesc`)
7. `ForecastRepository` (`findByUserOrderByForecastDateDesc`)
8. `VesselCompatibilityRepository` (`findByOriginAndDestinationPortAndVessel`)
9. `RiskAssessmentRepository` (`findByUserOrderByAssessmentDateDesc`)
10. `RecommendationRepository` (`findByUserOrderByCreatedAtDesc`)
11. `AlertRepository` (`findByUserAndIsReadFalseOrderByCreatedAtDesc`)
12. `AuditLogRepository` (`findByTableNameAndRecordId`)

---

### 11.4 Step-by-Step Implementation Sequence for Agent

```mermaid
flowchart TD
    S3C1["1. Map 12 Entities in in.sih26006.freight.entity"] --> S3C2["2. Create 12 JPA Repositories in in.sih26006.freight.repository"]
    S3C2 --> S3C3["3. Update DemoDataInitializer for canonical data seeding"]
    S3C3 --> S3C4["4. Retire or remove legacy com.sih26006 directory"]
    S3C4 --> S3C5["5. Compile & Run Tests: & ..\\.tools\\apache-maven-3.9.9\\bin\\mvn.cmd test"]
    S3C5 --> S3D["6. Proceed to Stage 3D: DTOs & Service Layer"]
```

1. **Step 1:** Write the 12 Entity classes in `backend/src/main/java/in/sih26006/freight/entity/`.
2. **Step 2:** Write the 12 Repository interfaces in `backend/src/main/java/in/sih26006/freight/repository/`.
3. **Step 3:** Update `DemoDataInitializer` to populate canonical demo records for Origins, Ports, Vessels, and Cargo Types so startup remains self-contained.
4. **Step 4:** Safely clean up the excluded `com.sih26006` directory once new entities are in place.
5. **Step 5:** Run test verification using:
   ```powershell
   & ..\.tools\apache-maven-3.9.9\bin\mvn.cmd test
   ```
6. **Step 6:** Confirm `BUILD SUCCESS` and update Phase Status in this document to mark Stage 3C complete.

---

### 11.5 Agent Verification & Acceptance Criteria

Before declaring Stage 3C complete, the agent must verify:
- [ ] Maven build succeeds with `mvn clean test-compile test`.
- [ ] Zero compile errors in `in.sih26006.freight`.
- [ ] Spring context loads successfully with all 12 repositories registered.
- [ ] Existing `ApiControllerTest` and `DatabaseConnectionTest` continue to pass.
- [ ] No regression on H2 local development mode or MySQL profile.
