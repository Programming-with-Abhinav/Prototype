-- ============================================
-- SIH26006 - Create Database Script
-- Phase 2: Database Setup
-- ============================================

-- Create Database
CREATE DATABASE IF NOT EXISTS sih26006_freight_intelligence;

-- Select Database
USE sih26006_freight_intelligence;

-- Set character set
ALTER DATABASE sih26006_freight_intelligence CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Confirmation
SELECT 'Database created successfully: sih26006_freight_intelligence' AS status;
