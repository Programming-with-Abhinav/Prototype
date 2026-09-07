-- ============================================
-- SIH26006 - Useful Queries Reference
-- Phase 2: Database Helper Queries
-- ============================================

USE sih26006_freight_intelligence;

-- ============================================
-- 1. USER MANAGEMENT QUERIES
-- ============================================

-- Get all active users
SELECT user_id, username, email, full_name, user_role, last_login
FROM users
WHERE is_active = TRUE
ORDER BY created_at DESC;

-- Get user with specific role
SELECT user_id, username, full_name
FROM users
WHERE user_role = 'manager' AND is_active = TRUE;

-- Update last login
UPDATE users SET last_login = NOW() WHERE user_id = ?;

-- ============================================
-- 2. VESSEL QUERIES
-- ============================================

-- Get all vessel types
SELECT vessel_id, vessel_type, cargo_capacity_tonnes, draft_meters, loa_length_meters, beam_width_meters
FROM vessels
WHERE is_active = TRUE
ORDER BY cargo_capacity_tonnes;

-- Find best vessel for cargo quantity
SELECT vessel_id, vessel_type, cargo_capacity_tonnes
FROM vessels
WHERE is_active = TRUE
AND cargo_capacity_tonnes >= ?
ORDER BY cargo_capacity_tonnes
LIMIT 1;

-- ============================================
-- 3. PORT QUERIES
-- ============================================

-- Get all operational ports
SELECT port_id, port_name, state_province, max_draft_meters, max_loa_meters, max_beam_meters, current_congestion_percentage
FROM ports
WHERE is_operational = TRUE
ORDER BY port_name;

-- Get ports by congestion level
SELECT port_id, port_name, current_congestion_percentage
FROM ports
WHERE is_operational = TRUE
ORDER BY current_congestion_percentage ASC;

-- Update port congestion (simulating real-time data)
UPDATE ports 
SET current_congestion_percentage = ?
WHERE port_id = ?;

-- ============================================
-- 4. FREIGHT HISTORY QUERIES
-- ============================================

-- Get freight history for a specific route (last 30 days)
SELECT fh.freight_history_id, fh.recorded_date, fh.freight_rate_per_tonne, 
       o.origin_name, p.port_name, v.vessel_type, c.cargo_name,
       fh.volatility_index
FROM freight_history fh
JOIN origins o ON fh.origin_id = o.origin_id
JOIN ports p ON fh.destination_port_id = p.port_id
JOIN vessels v ON fh.vessel_type_id = v.vessel_id
JOIN cargo_types c ON fh.cargo_type_id = c.cargo_type_id
WHERE fh.origin_id = ? 
AND fh.destination_port_id = ?
AND fh.cargo_type_id = ?
AND fh.recorded_date >= DATE_SUB(NOW(), INTERVAL 30 DAY)
ORDER BY fh.recorded_date DESC;

-- Get average freight rate for route
SELECT AVG(freight_rate_per_tonne) as avg_rate,
       MIN(freight_rate_per_tonne) as min_rate,
       MAX(freight_rate_per_tonne) as max_rate,
       COUNT(*) as data_points
FROM freight_history
WHERE origin_id = ? 
AND destination_port_id = ?
AND recorded_date >= DATE_SUB(NOW(), INTERVAL 90 DAY);

-- ============================================
-- 5. FORECAST QUERIES
-- ============================================

-- Get recent forecasts for a user
SELECT f.forecast_id, f.forecast_date, f.forecasted_date,
       o.origin_name, p.port_name, v.vessel_type, c.cargo_name,
       f.current_freight_rate, f.predicted_freight_rate, 
       f.forecast_trend, f.trend_percentage, f.confidence_level
FROM forecasts f
JOIN origins o ON f.origin_id = o.origin_id
JOIN ports p ON f.destination_port_id = p.port_id
JOIN vessels v ON f.vessel_type_id = v.vessel_id
JOIN cargo_types c ON f.cargo_type_id = c.cargo_type_id
WHERE f.user_id = ?
ORDER BY f.created_at DESC
LIMIT 10;

-- Get forecast accuracy (compare with actual rates - if available)
SELECT f.forecast_id, f.predicted_freight_rate,
       fh.freight_rate_per_tonne as actual_rate,
       ABS(f.predicted_freight_rate - fh.freight_rate_per_tonne) as error,
       ROUND(ABS(f.predicted_freight_rate - fh.freight_rate_per_tonne) / fh.freight_rate_per_tonne * 100, 2) as error_percentage
FROM forecasts f
LEFT JOIN freight_history fh ON f.origin_id = fh.origin_id
AND f.destination_port_id = fh.destination_port_id
AND f.forecasted_date = fh.recorded_date
WHERE f.user_id = ?;

-- ============================================
-- 6. RISK ASSESSMENT QUERIES
-- ============================================

-- Get risk assessments for a route
SELECT ra.risk_assessment_id, ra.assessment_date,
       o.origin_name, p.port_name,
       ra.freight_volatility_score, ra.port_congestion_score,
       ra.demand_uncertainty_score, ra.vessel_compatibility_score,
       ra.overall_risk_score, ra.risk_level
FROM risk_assessments ra
JOIN origins o ON ra.origin_id = o.origin_id
JOIN ports p ON ra.destination_port_id = p.port_id
WHERE ra.origin_id = ? AND ra.destination_port_id = ?
ORDER BY ra.assessment_date DESC;

-- Get high-risk routes
SELECT ra.*, o.origin_name, p.port_name
FROM risk_assessments ra
JOIN origins o ON ra.origin_id = o.origin_id
JOIN ports p ON ra.destination_port_id = p.port_id
WHERE ra.risk_level = 'HIGH'
AND ra.assessment_date >= DATE_SUB(NOW(), INTERVAL 7 DAY)
ORDER BY ra.overall_risk_score DESC;

-- ============================================
-- 7. RECOMMENDATION QUERIES
-- ============================================

-- Get recommendations for a user
SELECT r.recommendation_id, r.created_at,
       o.origin_name, p.port_name, v.vessel_type,
       r.recommended_strategy, r.expected_freight_cost,
       r.risk_level, r.confidence_level
FROM recommendations r
JOIN origins o ON r.origin_id = o.origin_id
JOIN ports p ON r.destination_port_id = p.port_id
JOIN vessels v ON r.recommended_vessel_type_id = v.vessel_id
WHERE r.user_id = ?
ORDER BY r.created_at DESC
LIMIT 10;

-- Get high-confidence recommendations
SELECT r.*, v.vessel_type, o.origin_name, p.port_name
FROM recommendations r
JOIN vessels v ON r.recommended_vessel_type_id = v.vessel_id
JOIN origins o ON r.origin_id = o.origin_id
JOIN ports p ON r.destination_port_id = p.port_id
WHERE r.confidence_level >= 80
ORDER BY r.confidence_level DESC;

-- ============================================
-- 8. ALERT QUERIES
-- ============================================

-- Get unread alerts for a user
SELECT alert_id, alert_type, priority_level, title, message, created_at
FROM alerts
WHERE user_id = ? AND is_read = FALSE
ORDER BY priority_level DESC, created_at DESC;

-- Get high-priority alerts
SELECT alert_id, alert_type, title, message, created_at
FROM alerts
WHERE user_id = ? AND priority_level = 'high' AND is_read = FALSE
ORDER BY created_at DESC;

-- Mark alert as read
UPDATE alerts SET is_read = TRUE, read_at = NOW() WHERE alert_id = ?;

-- Get alert summary
SELECT priority_level, COUNT(*) as count
FROM alerts
WHERE user_id = ? AND is_read = FALSE
GROUP BY priority_level;

-- ============================================
-- 9. VESSEL COMPATIBILITY QUERIES
-- ============================================

-- Check if vessel is compatible with route
SELECT vc.compatibility_id, vc.overall_compatibility, vc.compatibility_score,
       v.vessel_type, o.origin_name, p.port_name,
       v.draft_meters, p.max_draft_meters,
       v.loa_length_meters, p.max_loa_meters,
       v.beam_width_meters, p.max_beam_meters,
       v.cargo_capacity_tonnes, vc.cargo_quantity_tonnes
FROM vessel_compatibility vc
JOIN vessels v ON vc.vessel_type_id = v.vessel_id
JOIN origins o ON vc.origin_id = o.origin_id
JOIN ports p ON vc.destination_port_id = p.port_id
WHERE vc.origin_id = ? AND vc.destination_port_id = ?
ORDER BY vc.compatibility_score DESC;

-- ============================================
-- 10. ROUTE ANALYTICS QUERIES
-- ============================================

-- Get route performance summary
SELECT o.origin_name, p.port_name, c.cargo_name,
       COUNT(fh.freight_history_id) as data_points,
       AVG(fh.freight_rate_per_tonne) as avg_rate,
       STDDEV(fh.freight_rate_per_tonne) as volatility,
       MAX(fh.recorded_date) as last_update
FROM freight_history fh
JOIN origins o ON fh.origin_id = o.origin_id
JOIN ports p ON fh.destination_port_id = p.port_id
JOIN cargo_types c ON fh.cargo_type_id = c.cargo_type_id
WHERE fh.recorded_date >= DATE_SUB(NOW(), INTERVAL 180 DAY)
GROUP BY o.origin_id, p.port_id, c.cargo_type_id
ORDER BY avg_rate DESC;

-- Get top routes by volume
SELECT o.origin_name, p.port_name, c.cargo_name,
       COUNT(*) as volume,
       AVG(fh.freight_rate_per_tonne) as avg_rate
FROM freight_history fh
JOIN origins o ON fh.origin_id = o.origin_id
JOIN ports p ON fh.destination_port_id = p.port_id
JOIN cargo_types c ON fh.cargo_type_id = c.cargo_type_id
WHERE fh.recorded_date >= DATE_SUB(NOW(), INTERVAL 90 DAY)
GROUP BY o.origin_id, p.port_id, c.cargo_type_id
ORDER BY volume DESC
LIMIT 10;

-- ============================================
-- 11. AUDIT LOG QUERIES
-- ============================================

-- Get recent changes by user
SELECT audit_log_id, action, table_name, record_id, created_at, ip_address
FROM audit_log
WHERE user_id = ?
ORDER BY created_at DESC
LIMIT 20;

-- Track changes to recommendations
SELECT al.audit_log_id, al.action, al.created_at,
       u.username, al.old_values, al.new_values
FROM audit_log al
JOIN users u ON al.user_id = u.user_id
WHERE al.table_name = 'recommendations'
ORDER BY al.created_at DESC;

-- ============================================
-- 12. MAINTENANCE QUERIES
-- ============================================

-- Get database size
SELECT table_name, ROUND(((data_length + index_length) / 1024 / 1024), 2) AS size_mb
FROM information_schema.tables
WHERE table_schema = 'sih26006_freight_intelligence'
ORDER BY (data_length + index_length) DESC;

-- Get table row counts
SELECT table_name, table_rows
FROM information_schema.tables
WHERE table_schema = 'sih26006_freight_intelligence'
ORDER BY table_rows DESC;

-- Check for orphaned records (no foreign key reference)
SELECT fh.freight_history_id
FROM freight_history fh
LEFT JOIN origins o ON fh.origin_id = o.origin_id
WHERE o.origin_id IS NULL;

-- ============================================
-- 13. SAMPLE DATA REFRESH (for testing)
-- ============================================

-- Clear and reload forecasts
DELETE FROM forecasts WHERE forecast_date <= DATE_SUB(NOW(), INTERVAL 30 DAY);

-- Clear and reload alerts
DELETE FROM alerts WHERE created_at <= DATE_SUB(NOW(), INTERVAL 90 DAY);

-- Archive old audit logs
DELETE FROM audit_log WHERE created_at <= DATE_SUB(NOW(), INTERVAL 6 MONTH);

-- ============================================
-- 14. PERFORMANCE TUNING QUERIES
-- ============================================

-- Analyze tables for query optimization
ANALYZE TABLE users, vessels, ports, origins, cargo_types, freight_history, forecasts, risk_assessments, recommendations, alerts;

-- Optimize tables to reclaim space
OPTIMIZE TABLE freight_history, forecasts, risk_assessments, alerts, audit_log;

-- Show table statistics
SHOW TABLE STATUS FROM sih26006_freight_intelligence;

-- ============================================
-- END OF QUERY REFERENCE
-- ============================================
