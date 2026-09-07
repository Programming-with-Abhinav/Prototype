# PHASE 2: DATABASE DESIGN & SETUP
## SIH26006 - Intelligent Freight Forecasting & Vessel Chartering

**Status:** Phase 2 - Database Design  
**Date:** 2026-09-07  
**Technology:** MySQL 8.0+

---

## 📋 Overview

Phase 2 focuses on designing and implementing the MySQL database that will store all application data. This database will support:
- User management and authentication
- Vessel specifications and tracking
- Port infrastructure data
- Freight rate history
- Forecast records
- Risk assessments
- Recommendations
- Alert logs

---

## 🗄️ Database Schema

### Database Name
```sql
CREATE DATABASE sih26006_freight_intelligence;
USE sih26006_freight_intelligence;
```

---

## 📊 Database Tables

### 1. **USERS Table**
Stores user account information for authentication and profile management.

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    company_name VARCHAR(100),
    user_role ENUM('admin', 'manager', 'operator', 'viewer') DEFAULT 'viewer',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Authentication and access control  
**Fields:**
- `user_id` - Unique identifier
- `username` - Login username
- `password_hash` - Encrypted password (bcrypt/Argon2)
- `email` - User email
- `full_name` - Display name
- `company_name` - Organization
- `user_role` - Permission level
- `is_active` - Account status
- `created_at` - Account creation timestamp
- `updated_at` - Last modification timestamp
- `last_login` - Last login timestamp

---

### 2. **VESSELS Table**
Master data for supported vessel types and their specifications.

```sql
CREATE TABLE vessels (
    vessel_id INT PRIMARY KEY AUTO_INCREMENT,
    vessel_type VARCHAR(50) NOT NULL,
    cargo_capacity_tonnes INT NOT NULL,
    draft_meters DECIMAL(5, 2) NOT NULL,
    loa_length_meters INT NOT NULL,
    beam_width_meters INT NOT NULL,
    age_years INT,
    fuel_consumption_per_day DECIMAL(8, 2),
    average_day_rate_usd INT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_vessel_type (vessel_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Store vessel type specifications  
**Fields:**
- `vessel_id` - Unique identifier
- `vessel_type` - Type name (Handysize, Supramax, Panamax, Capesize)
- `cargo_capacity_tonnes` - Maximum cargo capacity
- `draft_meters` - Maximum draft depth
- `loa_length_meters` - Length overall
- `beam_width_meters` - Width of vessel
- `age_years` - Average age of this class
- `fuel_consumption_per_day` - Fuel cost metric
- `average_day_rate_usd` - Charter rate
- `is_active` - Data validity flag

**Sample Data:**
```sql
INSERT INTO vessels (vessel_type, cargo_capacity_tonnes, draft_meters, loa_length_meters, beam_width_meters, age_years, fuel_consumption_per_day, average_day_rate_usd) VALUES
('Handysize', 35000, 9.2, 190, 28, 10, 4.5, 20000),
('Supramax', 52000, 10.5, 210, 30, 8, 6.5, 22000),
('Panamax', 65000, 11.8, 229, 32, 7, 8.0, 25000),
('Capesize', 170000, 15.5, 290, 45, 6, 13.0, 34000);
```

---

### 3. **PORTS Table**
Infrastructure data for destination ports on India's East Coast.

```sql
CREATE TABLE ports (
    port_id INT PRIMARY KEY AUTO_INCREMENT,
    port_name VARCHAR(100) NOT NULL UNIQUE,
    country VARCHAR(50) DEFAULT 'India',
    state_province VARCHAR(50),
    max_draft_meters DECIMAL(5, 2) NOT NULL,
    max_loa_meters INT NOT NULL,
    max_beam_meters INT NOT NULL,
    annual_cargo_capacity_millions INT,
    current_congestion_percentage INT DEFAULT 0,
    is_operational BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_port_name (port_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Store port infrastructure and status  
**Fields:**
- `port_id` - Unique identifier
- `port_name` - Port name
- `country` - Country location
- `state_province` - State/region
- `max_draft_meters` - Maximum vessel draft allowed
- `max_loa_meters` - Maximum vessel length
- `max_beam_meters` - Maximum vessel width
- `annual_cargo_capacity_millions` - Handling capacity
- `current_congestion_percentage` - Real-time congestion status
- `is_operational` - Operational status

**Sample Data:**
```sql
INSERT INTO ports (port_name, state_province, max_draft_meters, max_loa_meters, max_beam_meters, annual_cargo_capacity_millions, current_congestion_percentage) VALUES
('Paradip', 'Odisha', 12.5, 250, 35, 120, 65),
('Visakhapatnam', 'Andhra Pradesh', 13.5, 275, 38, 95, 72),
('Gangavaram', 'Andhra Pradesh', 14.0, 280, 40, 100, 55),
('Gopalpur', 'Odisha', 12.0, 230, 32, 50, 48),
('Dhamra', 'Odisha', 13.0, 260, 37, 80, 58),
('Haldia', 'West Bengal', 10.5, 210, 30, 45, 70);
```

---

### 4. **ORIGINS Table**
Source ports/countries where cargo originates.

```sql
CREATE TABLE origins (
    origin_id INT PRIMARY KEY AUTO_INCREMENT,
    origin_name VARCHAR(100) NOT NULL UNIQUE,
    location_type ENUM('port', 'country', 'region') DEFAULT 'country',
    country VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_origin_name (origin_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Track cargo origin locations  
**Sample Data:**
```sql
INSERT INTO origins (origin_name, location_type, country) VALUES
('Australia', 'country', 'Australia'),
('USA', 'country', 'USA'),
('Mozambique', 'country', 'Mozambique'),
('Russia', 'country', 'Russia'),
('Indonesia', 'country', 'Indonesia');
```

---

### 5. **CARGO_TYPES Table**
Classification of different cargo types.

```sql
CREATE TABLE cargo_types (
    cargo_type_id INT PRIMARY KEY AUTO_INCREMENT,
    cargo_name VARCHAR(100) NOT NULL UNIQUE,
    cargo_category ENUM('bulk', 'breakbulk', 'container', 'general') DEFAULT 'bulk',
    is_hazardous BOOLEAN DEFAULT FALSE,
    special_requirements VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_cargo_name (cargo_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Cargo type master data  
**Sample Data:**
```sql
INSERT INTO cargo_types (cargo_name, cargo_category, is_hazardous) VALUES
('Coal', 'bulk', FALSE),
('Iron Ore', 'bulk', FALSE),
('Grain', 'bulk', FALSE),
('Container', 'container', FALSE),
('Breakbulk', 'breakbulk', FALSE);
```

---

### 6. **FREIGHT_HISTORY Table**
Historical freight rate data for forecasting and analysis.

```sql
CREATE TABLE freight_history (
    freight_history_id INT PRIMARY KEY AUTO_INCREMENT,
    origin_id INT NOT NULL,
    destination_port_id INT NOT NULL,
    cargo_type_id INT NOT NULL,
    vessel_type_id INT NOT NULL,
    freight_rate_per_tonne DECIMAL(10, 2) NOT NULL,
    recorded_date DATE NOT NULL,
    volatility_index DECIMAL(5, 2),
    supply_demand_index INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id),
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id),
    FOREIGN KEY (cargo_type_id) REFERENCES cargo_types(cargo_type_id),
    FOREIGN KEY (vessel_type_id) REFERENCES vessels(vessel_id),
    INDEX idx_recorded_date (recorded_date),
    INDEX idx_route (origin_id, destination_port_id),
    UNIQUE KEY unique_route_date (origin_id, destination_port_id, cargo_type_id, vessel_type_id, recorded_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Store historical freight rate data for analysis  
**Fields:**
- `freight_history_id` - Record ID
- `origin_id` - Foreign key to origins
- `destination_port_id` - Foreign key to ports
- `cargo_type_id` - Foreign key to cargo types
- `vessel_type_id` - Foreign key to vessels
- `freight_rate_per_tonne` - Rate in currency
- `recorded_date` - Date of record
- `volatility_index` - Price volatility metric
- `supply_demand_index` - Market indicator
- `created_at` - Record creation time

---

### 7. **FORECASTS Table**
Freight rate forecast records generated by the system.

```sql
CREATE TABLE forecasts (
    forecast_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    origin_id INT NOT NULL,
    destination_port_id INT NOT NULL,
    cargo_type_id INT NOT NULL,
    vessel_type_id INT NOT NULL,
    cargo_quantity_tonnes INT NOT NULL,
    contract_duration_months INT,
    forecast_period_days INT NOT NULL,
    current_freight_rate DECIMAL(10, 2) NOT NULL,
    predicted_freight_rate DECIMAL(10, 2) NOT NULL,
    forecast_trend VARCHAR(50),
    trend_percentage DECIMAL(6, 2),
    confidence_level INT,
    forecast_date DATE NOT NULL,
    forecasted_date DATE NOT NULL,
    algorithm_used VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id),
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id),
    FOREIGN KEY (cargo_type_id) REFERENCES cargo_types(cargo_type_id),
    FOREIGN KEY (vessel_type_id) REFERENCES vessels(vessel_id),
    INDEX idx_user_id (user_id),
    INDEX idx_forecast_date (forecast_date),
    INDEX idx_route (origin_id, destination_port_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Store forecast results  
**Fields:**
- `forecast_id` - Record ID
- `user_id` - User who generated forecast
- `origin_id` - Cargo origin
- `destination_port_id` - Destination port
- `cargo_type_id` - Type of cargo
- `vessel_type_id` - Recommended vessel
- `cargo_quantity_tonnes` - Cargo volume
- `contract_duration_months` - Contract length
- `forecast_period_days` - Days ahead forecasted
- `current_freight_rate` - Current rate
- `predicted_freight_rate` - Predicted rate
- `forecast_trend` - Increasing/Decreasing/Stable
- `trend_percentage` - % change
- `confidence_level` - Model confidence (0-100)
- `algorithm_used` - Forecasting algorithm name

---

### 8. **VESSEL_COMPATIBILITY Table**
Pre-calculated vessel-route compatibility results.

```sql
CREATE TABLE vessel_compatibility (
    compatibility_id INT PRIMARY KEY AUTO_INCREMENT,
    origin_id INT NOT NULL,
    destination_port_id INT NOT NULL,
    vessel_type_id INT NOT NULL,
    cargo_quantity_tonnes INT NOT NULL,
    draft_compatible BOOLEAN,
    loa_compatible BOOLEAN,
    beam_compatible BOOLEAN,
    capacity_sufficient BOOLEAN,
    overall_compatibility VARCHAR(50),
    compatibility_score INT,
    notes VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id),
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id),
    FOREIGN KEY (vessel_type_id) REFERENCES vessels(vessel_id),
    INDEX idx_route (origin_id, destination_port_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Store vessel compatibility checks  
**Fields:**
- `draft_compatible` - Draft check pass/fail
- `loa_compatible` - Length check pass/fail
- `beam_compatible` - Width check pass/fail
- `capacity_sufficient` - Capacity check pass/fail
- `overall_compatibility` - Suitable/Warning/Not Suitable
- `compatibility_score` - 0-100 score

---

### 9. **RISK_ASSESSMENTS Table**
Risk analysis results for routes and charterings.

```sql
CREATE TABLE risk_assessments (
    risk_assessment_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    forecast_id INT,
    origin_id INT NOT NULL,
    destination_port_id INT NOT NULL,
    freight_volatility_score INT,
    port_congestion_score INT,
    demand_uncertainty_score INT,
    vessel_compatibility_score INT,
    overall_risk_score INT,
    risk_level VARCHAR(50),
    risk_factors_json JSON,
    recommendations TEXT,
    assessment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (forecast_id) REFERENCES forecasts(forecast_id),
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id),
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id),
    INDEX idx_user_id (user_id),
    INDEX idx_risk_level (risk_level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Store risk assessment results  
**Fields:**
- Individual risk component scores (0-100)
- `overall_risk_score` - Weighted total
- `risk_level` - Low/Medium/High
- `risk_factors_json` - JSON detailed breakdown
- `recommendations` - Risk mitigation suggestions

---

### 10. **RECOMMENDATIONS Table**
Chartering recommendations generated by the system.

```sql
CREATE TABLE recommendations (
    recommendation_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    forecast_id INT,
    risk_assessment_id INT,
    origin_id INT NOT NULL,
    destination_port_id INT NOT NULL,
    recommended_vessel_type_id INT NOT NULL,
    recommended_strategy VARCHAR(255),
    strategy_rationale TEXT,
    expected_freight_cost DECIMAL(15, 2),
    risk_level VARCHAR(50),
    market_entry_timing VARCHAR(100),
    contract_duration_recommendation INT,
    confidence_level INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (forecast_id) REFERENCES forecasts(forecast_id),
    FOREIGN KEY (risk_assessment_id) REFERENCES risk_assessments(risk_assessment_id),
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id),
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id),
    FOREIGN KEY (recommended_vessel_type_id) REFERENCES vessels(vessel_id),
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Store recommendation records  
**Fields:**
- `recommended_vessel_type_id` - Best vessel for this route
- `recommended_strategy` - Chartering approach
- `strategy_rationale` - Why this recommendation
- `expected_freight_cost` - Estimated cost
- `market_entry_timing` - When to enter market
- `contract_duration_recommendation` - Suggested contract length

---

### 11. **ALERTS Table**
System-generated alerts and notifications.

```sql
CREATE TABLE alerts (
    alert_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    alert_type VARCHAR(50) NOT NULL,
    priority_level ENUM('low', 'medium', 'high') DEFAULT 'medium',
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    related_forecast_id INT,
    related_port_id INT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (related_forecast_id) REFERENCES forecasts(forecast_id),
    FOREIGN KEY (related_port_id) REFERENCES ports(port_id),
    INDEX idx_user_id (user_id),
    INDEX idx_priority_level (priority_level),
    INDEX idx_is_read (is_read),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Alert and notification system  
**Fields:**
- `alert_type` - Type of alert (Freight, Port, Vessel, Market)
- `priority_level` - Alert severity
- `title` - Alert headline
- `message` - Full alert message
- `is_read` - Read status
- `read_at` - When user read it

**Alert Types:**
- `freight_volatility` - Freight rate changes
- `port_congestion` - Port congestion alerts
- `vessel_compatibility` - Compatibility issues
- `market_opportunity` - Favorable market conditions
- `demand_alert` - Demand fluctuations
- `system_notification` - System updates

---

### 12. **AUDIT_LOG Table**
System-wide audit trail for compliance and debugging.

```sql
CREATE TABLE audit_log (
    audit_log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    action VARCHAR(100) NOT NULL,
    table_name VARCHAR(100),
    record_id INT,
    old_values JSON,
    new_values JSON,
    ip_address VARCHAR(45),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_user_id (user_id),
    INDEX idx_action (action),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

**Purpose:** Track all system changes  
**Fields:**
- `action` - INSERT, UPDATE, DELETE, etc.
- `table_name` - Which table changed
- `record_id` - Which record
- `old_values` - Previous data
- `new_values` - New data
- `ip_address` - Source IP

---

## 📊 Database Relationships

```
users (1) ──→ (N) forecasts
users (1) ──→ (N) recommendations
users (1) ──→ (N) risk_assessments
users (1) ──→ (N) alerts
users (1) ──→ (N) audit_log

origins (1) ──→ (N) freight_history
origins (1) ──→ (N) forecasts
origins (1) ──→ (N) recommendations

ports (1) ──→ (N) freight_history
ports (1) ──→ (N) forecasts
ports (1) ──→ (N) recommendations

cargo_types (1) ──→ (N) freight_history
cargo_types (1) ──→ (N) forecasts

vessels (1) ──→ (N) freight_history
vessels (1) ──→ (N) forecasts
vessels (1) ──→ (N) recommendations

forecasts (1) ──→ (N) risk_assessments
forecasts (1) ──→ (N) recommendations
```

---

## 🔑 Key Indexes

Indexes are created on:
- `users.username`, `users.email` - For login queries
- `freight_history.recorded_date` - For historical data queries
- `freight_history.route` - For route-specific queries
- `forecasts.user_id`, `forecasts.forecast_date` - For user queries
- `alerts.user_id`, `alerts.is_read` - For notification retrieval
- `recommendations.created_at` - For recent recommendations
- `ports.port_name` - For port lookups

---

## 💾 Sample Data Load Script

```sql
-- Load demo data
INSERT INTO users (username, password_hash, email, full_name, company_name, user_role) VALUES
('demo', '$2y$10$YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', 'demo@freight.com', 'Demo User', 'Demo Company', 'manager'),
('admin', '$2y$10$YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa', 'admin@freight.com', 'Administrator', 'SIH26006', 'admin');

-- Vessels (already shown above)
INSERT INTO vessels ... VALUES ...;

-- Ports (already shown above)
INSERT INTO ports ... VALUES ...;

-- Origins (already shown above)
INSERT INTO origins ... VALUES ...;

-- Cargo Types (already shown above)
INSERT INTO cargo_types ... VALUES ...;

-- Sample freight history
INSERT INTO freight_history (origin_id, destination_port_id, cargo_type_id, vessel_type_id, freight_rate_per_tonne, recorded_date, volatility_index) VALUES
(1, 1, 1, 3, 85.45, '2025-09-07', 5.2),
(1, 1, 1, 3, 86.10, '2025-09-06', 4.8),
(1, 1, 1, 3, 85.20, '2025-09-05', 5.1);
```

---

## 🔧 Setup Instructions

### 1. Create Database
```bash
mysql -u root -p < create_database.sql
```

### 2. Create Tables
```bash
mysql -u root -p sih26006_freight_intelligence < create_tables.sql
```

### 3. Load Sample Data
```bash
mysql -u root -p sih26006_freight_intelligence < load_sample_data.sql
```

### 4. Create Database User (Production)
```sql
CREATE USER 'freight_user'@'localhost' IDENTIFIED BY 'secure_password_here';
GRANT ALL PRIVILEGES ON sih26006_freight_intelligence.* TO 'freight_user'@'localhost';
FLUSH PRIVILEGES;
```

---

## 📋 Database Configuration for Backend

### Connection String (Spring Boot application.properties)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sih26006_freight_intelligence?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=freight_user
spring.datasource.password=secure_password_here
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

## 🔄 Data Flow

```
User Input (Frontend)
    ↓
Forecast Request
    ↓
Query freight_history Table
    ↓
Apply Forecasting Algorithm
    ↓
Store in forecasts Table
    ↓
Calculate Risk Assessment
    ↓
Store in risk_assessments Table
    ↓
Generate Recommendation
    ↓
Store in recommendations Table
    ↓
Create Alerts if needed
    ↓
Display Results to User
```

---

## 📈 Query Examples

### Get Recent Forecasts for User
```sql
SELECT f.*, o.origin_name, p.port_name, v.vessel_type, c.cargo_name
FROM forecasts f
JOIN origins o ON f.origin_id = o.origin_id
JOIN ports p ON f.destination_port_id = p.port_id
JOIN vessels v ON f.vessel_type_id = v.vessel_id
JOIN cargo_types c ON f.cargo_type_id = c.cargo_type_id
WHERE f.user_id = ? 
ORDER BY f.created_at DESC 
LIMIT 10;
```

### Get Port Congestion Trend
```sql
SELECT p.port_name, p.current_congestion_percentage, COUNT(f.freight_history_id) as volume_last_month
FROM ports p
LEFT JOIN freight_history f ON p.port_id = f.destination_port_id 
AND f.recorded_date >= DATE_SUB(NOW(), INTERVAL 30 DAY)
GROUP BY p.port_id, p.port_name
ORDER BY p.current_congestion_percentage DESC;
```

### Get Route Recommendations
```sql
SELECT r.*, v.vessel_type, o.origin_name, p.port_name
FROM recommendations r
JOIN vessels v ON r.recommended_vessel_type_id = v.vessel_id
JOIN origins o ON r.origin_id = o.origin_id
JOIN ports p ON r.destination_port_id = p.port_id
WHERE r.user_id = ? AND r.created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
ORDER BY r.confidence_level DESC;
```

---

## 🔒 Security Considerations

1. **Password Hashing:** Use bcrypt or Argon2, never store plain text
2. **SQL Injection:** Use parameterized queries in backend
3. **Data Encryption:** Encrypt sensitive fields (rates, costs)
4. **Access Control:** Implement role-based access (user_role field)
5. **Audit Trail:** Log all data modifications in audit_log
6. **Backups:** Regular automated backups
7. **Authentication:** Implement JWT or OAuth2

---

## 📊 Database Statistics (Estimated)

| Table | Estimated Rows | Growth Rate |
|-------|-----------------|------------|
| users | 100 | Slow |
| vessels | 4 | Static |
| ports | 6 | Static |
| origins | 10 | Slow |
| cargo_types | 5 | Static |
| freight_history | 10,000+ | 100/day |
| forecasts | 5,000+ | 50/day |
| risk_assessments | 5,000+ | 50/day |
| recommendations | 5,000+ | 50/day |
| alerts | 10,000+ | 100/day |
| audit_log | 50,000+ | 500/day |

---

## 🚀 Migration Strategy

### Development → Testing → Production

1. **Development:** Local MySQL instance
2. **Testing:** Test server with realistic data
3. **Production:** Managed database service (RDS, Cloud SQL, etc.)

---

## 📝 Next Steps (Phase 3)

Once database is set up:
1. Create Spring Boot JPA Entities
2. Create Repository interfaces
3. Create Service classes
4. Build REST API endpoints
5. Integrate with frontend

---

## 📚 Files to Create

1. `create_database.sql` - Database creation script
2. `create_tables.sql` - Table creation script
3. `load_sample_data.sql` - Sample data script
4. `create_indexes.sql` - Index creation script
5. `create_user.sql` - Database user creation
6. `database_diagram.sql` - Visual schema (using MySQL Workbench)

---

## ✅ Phase 2 Checklist

- [ ] Create MySQL database
- [ ] Create all 12 tables with relationships
- [ ] Set up indexes for performance
- [ ] Load sample data
- [ ] Create database user account
- [ ] Test connectivity
- [ ] Validate foreign keys
- [ ] Backup database schema
- [ ] Document all tables
- [ ] Create query examples
- [ ] Set up automated backups

---

**Phase 2 Complete:** Ready for Phase 3 (Spring Boot Backend Development)

---

*Generated for SIH26006 - Intelligent Freight Forecasting & Vessel Chartering*  
*Date: 2026-09-07*
