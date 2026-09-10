-- MySQL 8+ production-style schema. Values below are prototype/demo data only.
CREATE DATABASE IF NOT EXISTS sih26006 CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sih26006;

CREATE TABLE
    users (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        display_name VARCHAR(100) NOT NULL,
        email VARCHAR(190) NOT NULL UNIQUE,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE
    ports (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL UNIQUE,
        max_draft DOUBLE NOT NULL,
        max_loa DOUBLE NOT NULL,
        max_beam DOUBLE NOT NULL,
        congestion_status VARCHAR(20) NOT NULL,
        latitude DOUBLE NOT NULL,
        longitude DOUBLE NOT NULL
    );

CREATE TABLE
    vessels (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        vessel_type VARCHAR(60) NOT NULL UNIQUE,
        capacity_tonnes DOUBLE NOT NULL,
        draft DOUBLE NOT NULL,
        loa DOUBLE NOT NULL,
        beam DOUBLE NOT NULL
    );

CREATE TABLE
    cargo_requests (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        user_id BIGINT NULL,
        origin VARCHAR(100) NOT NULL,
        destination VARCHAR(100) NOT NULL,
        cargo_type VARCHAR(80) NOT NULL,
        quantity_tonnes DOUBLE NOT NULL,
        preferred_vessel VARCHAR(60) NOT NULL,
        contract_months INT NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_cargo_user FOREIGN KEY (user_id) REFERENCES users (id),
        INDEX idx_cargo_created (created_at),
        INDEX idx_cargo_user_created (user_id, created_at)
    );

CREATE TABLE
    forecasts (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        cargo_request_id BIGINT NOT NULL,
        current_freight DOUBLE NOT NULL,
        predicted_freight DOUBLE NOT NULL,
        trend VARCHAR(30) NOT NULL,
        model_version VARCHAR(60) NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_forecast_cargo FOREIGN KEY (cargo_request_id) REFERENCES cargo_requests (id),
        INDEX idx_forecast_created (created_at)
    );

CREATE TABLE
    recommendations (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        cargo_request_id BIGINT NOT NULL,
        vessel_type VARCHAR(60) NOT NULL,
        risk_score INT NOT NULL,
        strategy TEXT NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_recommendation_cargo FOREIGN KEY (cargo_request_id) REFERENCES cargo_requests (id)
    );

CREATE TABLE
    risk_alerts (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        cargo_request_id BIGINT NULL,
        severity VARCHAR(10) NOT NULL,
        category VARCHAR(30) NOT NULL,
        message VARCHAR(500) NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        INDEX idx_alert_created (created_at)
    );