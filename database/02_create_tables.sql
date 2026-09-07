-- ============================================
-- SIH26006 - Create Tables Script
-- Phase 2: Database Setup
-- ============================================

USE sih26006_freight_intelligence;

-- ============================================
-- 1. USERS Table
-- ============================================
CREATE TABLE IF NOT EXISTS users (
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
    INDEX idx_email (email),
    INDEX idx_user_role (user_role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 2. VESSELS Table
-- ============================================
CREATE TABLE IF NOT EXISTS vessels (
    vessel_id INT PRIMARY KEY AUTO_INCREMENT,
    vessel_type VARCHAR(50) NOT NULL UNIQUE,
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

-- ============================================
-- 3. PORTS Table
-- ============================================
CREATE TABLE IF NOT EXISTS ports (
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
    INDEX idx_port_name (port_name),
    INDEX idx_country (country),
    INDEX idx_is_operational (is_operational)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 4. ORIGINS Table
-- ============================================
CREATE TABLE IF NOT EXISTS origins (
    origin_id INT PRIMARY KEY AUTO_INCREMENT,
    origin_name VARCHAR(100) NOT NULL UNIQUE,
    location_type ENUM('port', 'country', 'region') DEFAULT 'country',
    country VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_origin_name (origin_name),
    INDEX idx_country (country),
    INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 5. CARGO_TYPES Table
-- ============================================
CREATE TABLE IF NOT EXISTS cargo_types (
    cargo_type_id INT PRIMARY KEY AUTO_INCREMENT,
    cargo_name VARCHAR(100) NOT NULL UNIQUE,
    cargo_category ENUM('bulk', 'breakbulk', 'container', 'general') DEFAULT 'bulk',
    is_hazardous BOOLEAN DEFAULT FALSE,
    special_requirements VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_cargo_name (cargo_name),
    INDEX idx_cargo_category (cargo_category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 6. FREIGHT_HISTORY Table
-- ============================================
CREATE TABLE IF NOT EXISTS freight_history (
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
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id) ON DELETE RESTRICT,
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id) ON DELETE RESTRICT,
    FOREIGN KEY (cargo_type_id) REFERENCES cargo_types(cargo_type_id) ON DELETE RESTRICT,
    FOREIGN KEY (vessel_type_id) REFERENCES vessels(vessel_id) ON DELETE RESTRICT,
    INDEX idx_recorded_date (recorded_date),
    INDEX idx_route (origin_id, destination_port_id),
    INDEX idx_cargo_type (cargo_type_id),
    UNIQUE KEY unique_route_date (origin_id, destination_port_id, cargo_type_id, vessel_type_id, recorded_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 7. FORECASTS Table
-- ============================================
CREATE TABLE IF NOT EXISTS forecasts (
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
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id) ON DELETE RESTRICT,
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id) ON DELETE RESTRICT,
    FOREIGN KEY (cargo_type_id) REFERENCES cargo_types(cargo_type_id) ON DELETE RESTRICT,
    FOREIGN KEY (vessel_type_id) REFERENCES vessels(vessel_id) ON DELETE RESTRICT,
    INDEX idx_user_id (user_id),
    INDEX idx_forecast_date (forecast_date),
    INDEX idx_route (origin_id, destination_port_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 8. VESSEL_COMPATIBILITY Table
-- ============================================
CREATE TABLE IF NOT EXISTS vessel_compatibility (
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
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id) ON DELETE RESTRICT,
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id) ON DELETE RESTRICT,
    FOREIGN KEY (vessel_type_id) REFERENCES vessels(vessel_id) ON DELETE RESTRICT,
    INDEX idx_route (origin_id, destination_port_id),
    UNIQUE KEY unique_compatibility (origin_id, destination_port_id, vessel_type_id, cargo_quantity_tonnes)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 9. RISK_ASSESSMENTS Table
-- ============================================
CREATE TABLE IF NOT EXISTS risk_assessments (
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
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (forecast_id) REFERENCES forecasts(forecast_id) ON DELETE SET NULL,
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id) ON DELETE RESTRICT,
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id) ON DELETE RESTRICT,
    INDEX idx_user_id (user_id),
    INDEX idx_risk_level (risk_level),
    INDEX idx_assessment_date (assessment_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 10. RECOMMENDATIONS Table
-- ============================================
CREATE TABLE IF NOT EXISTS recommendations (
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
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (forecast_id) REFERENCES forecasts(forecast_id) ON DELETE SET NULL,
    FOREIGN KEY (risk_assessment_id) REFERENCES risk_assessments(risk_assessment_id) ON DELETE SET NULL,
    FOREIGN KEY (origin_id) REFERENCES origins(origin_id) ON DELETE RESTRICT,
    FOREIGN KEY (destination_port_id) REFERENCES ports(port_id) ON DELETE RESTRICT,
    FOREIGN KEY (recommended_vessel_type_id) REFERENCES vessels(vessel_id) ON DELETE RESTRICT,
    INDEX idx_user_id (user_id),
    INDEX idx_created_at (created_at),
    INDEX idx_confidence_level (confidence_level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 11. ALERTS Table
-- ============================================
CREATE TABLE IF NOT EXISTS alerts (
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
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (related_forecast_id) REFERENCES forecasts(forecast_id) ON DELETE SET NULL,
    FOREIGN KEY (related_port_id) REFERENCES ports(port_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_priority_level (priority_level),
    INDEX idx_is_read (is_read),
    INDEX idx_created_at (created_at),
    INDEX idx_alert_type (alert_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- 12. AUDIT_LOG Table
-- ============================================
CREATE TABLE IF NOT EXISTS audit_log (
    audit_log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    action VARCHAR(100) NOT NULL,
    table_name VARCHAR(100),
    record_id INT,
    old_values JSON,
    new_values JSON,
    ip_address VARCHAR(45),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_action (action),
    INDEX idx_created_at (created_at),
    INDEX idx_table_name (table_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table Creation Summary
-- ============================================
SELECT 'All tables created successfully!' AS status;

-- Show table count
SELECT COUNT(*) as total_tables FROM information_schema.tables 
WHERE table_schema = 'sih26006_freight_intelligence';
