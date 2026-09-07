-- ============================================
-- SIH26006 - Database User Creation
-- Phase 2: Security Setup
-- ============================================

-- Create Application User (for Spring Boot)
CREATE USER 'freight_app'@'localhost' IDENTIFIED WITH mysql_native_password BY 'freight_app_password_2026';

-- Grant all privileges on freight intelligence database
GRANT ALL PRIVILEGES ON sih26006_freight_intelligence.* TO 'freight_app'@'localhost';

-- Grant specific privileges (more restrictive alternative)
-- GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX ON sih26006_freight_intelligence.* TO 'freight_app'@'localhost';

-- Flush privileges to apply changes
FLUSH PRIVILEGES;

-- Verify user creation
SELECT user, host FROM mysql.user WHERE user = 'freight_app';

-- Test connection (run from command line):
-- mysql -u freight_app -p sih26006_freight_intelligence
-- Password: freight_app_password_2026

-- ============================================
-- Additional Users for Different Roles
-- ============================================

-- Read-only user (for reporting/analytics)
CREATE USER 'freight_readonly'@'localhost' IDENTIFIED WITH mysql_native_password BY 'readonly_password_2026';
GRANT SELECT ON sih26006_freight_intelligence.* TO 'freight_readonly'@'localhost';
FLUSH PRIVILEGES;

-- Admin user (for backups and maintenance)
CREATE USER 'freight_admin'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin_password_2026';
GRANT ALL PRIVILEGES ON sih26006_freight_intelligence.* TO 'freight_admin'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;

-- Remote connection user (if needed for external servers)
CREATE USER 'freight_app'@'%' IDENTIFIED WITH mysql_native_password BY 'freight_remote_password_2026';
GRANT ALL PRIVILEGES ON sih26006_freight_intelligence.* TO 'freight_app'@'%';
FLUSH PRIVILEGES;

-- Show all users for this database
SELECT DISTINCT user, host FROM mysql.user WHERE user LIKE 'freight%';

-- ============================================
-- User Creation Summary
-- ============================================
SELECT CONCAT('User accounts created:',
    CHAR(13), '- freight_app (Application)',
    CHAR(13), '- freight_readonly (Read-only)',
    CHAR(13), '- freight_admin (Admin)',
    CHAR(13), '- freight_app@% (Remote)'
) AS summary;

-- IMPORTANT: Change these default passwords in production!
-- Use strong, unique passwords for each user
-- Store passwords securely (not in version control)
