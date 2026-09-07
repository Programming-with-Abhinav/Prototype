-- ============================================
-- SIH26006 - Load Sample Data Script
-- Phase 2: Database Setup
-- ============================================

USE sih26006_freight_intelligence;

-- ============================================
-- Load Users
-- ============================================
INSERT INTO users (username, password_hash, email, full_name, company_name, user_role) VALUES
('demo', '$2y$10$YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', 'demo@freight.com', 'Demo User', 'Demo Company', 'manager'),
('admin', '$2y$10$YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAB', 'admin@freight.com', 'Administrator', 'SIH26006', 'admin'),
('manager1', '$2y$10$YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAC', 'manager1@freight.com', 'Manager One', 'Logistics Inc', 'manager'),
('operator1', '$2y$10$YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD', 'operator1@freight.com', 'Operator One', 'Logistics Inc', 'operator');

-- ============================================
-- Load Vessels
-- ============================================
INSERT INTO vessels (vessel_type, cargo_capacity_tonnes, draft_meters, loa_length_meters, beam_width_meters, age_years, fuel_consumption_per_day, average_day_rate_usd) VALUES
('Handysize', 35000, 9.2, 190, 28, 10, 4.5, 20000),
('Supramax', 52000, 10.5, 210, 30, 8, 6.5, 22000),
('Panamax', 65000, 11.8, 229, 32, 7, 8.0, 25000),
('Capesize', 170000, 15.5, 290, 45, 6, 13.0, 34000);

-- ============================================
-- Load Ports
-- ============================================
INSERT INTO ports (port_name, state_province, max_draft_meters, max_loa_meters, max_beam_meters, annual_cargo_capacity_millions, current_congestion_percentage) VALUES
('Paradip', 'Odisha', 12.5, 250, 35, 120, 65),
('Visakhapatnam', 'Andhra Pradesh', 13.5, 275, 38, 95, 72),
('Gangavaram', 'Andhra Pradesh', 14.0, 280, 40, 100, 55),
('Gopalpur', 'Odisha', 12.0, 230, 32, 50, 48),
('Dhamra', 'Odisha', 13.0, 260, 37, 80, 58),
('Haldia', 'West Bengal', 10.5, 210, 30, 45, 70);

-- ============================================
-- Load Origins
-- ============================================
INSERT INTO origins (origin_name, location_type, country) VALUES
('Australia', 'country', 'Australia'),
('USA', 'country', 'USA'),
('Mozambique', 'country', 'Mozambique'),
('Russia', 'country', 'Russia'),
('Indonesia', 'country', 'Indonesia'),
('South Africa', 'country', 'South Africa'),
('Canada', 'country', 'Canada'),
('Ukraine', 'country', 'Ukraine');

-- ============================================
-- Load Cargo Types
-- ============================================
INSERT INTO cargo_types (cargo_name, cargo_category, is_hazardous) VALUES
('Coal', 'bulk', FALSE),
('Iron Ore', 'bulk', FALSE),
('Grain', 'bulk', FALSE),
('Coking Coal', 'bulk', FALSE),
('Thermal Coal', 'bulk', FALSE),
('Container', 'container', FALSE),
('Breakbulk', 'breakbulk', FALSE),
('General Cargo', 'general', FALSE),
('Crude Oil', 'bulk', TRUE);

-- ============================================
-- Load Freight History (Last 12 months)
-- ============================================
INSERT INTO freight_history (origin_id, destination_port_id, cargo_type_id, vessel_type_id, freight_rate_per_tonne, recorded_date, volatility_index) VALUES
(1, 1, 1, 3, 85.45, '2025-09-07', 5.2),
(1, 1, 1, 3, 86.10, '2025-09-06', 4.8),
(1, 1, 1, 3, 85.20, '2025-09-05', 5.1),
(1, 1, 1, 3, 86.80, '2025-09-04', 5.4),
(1, 1, 1, 3, 85.50, '2025-09-03', 5.0),
(1, 1, 1, 3, 87.00, '2025-09-02', 5.3),
(1, 1, 1, 3, 86.50, '2025-09-01', 5.1),
(1, 1, 1, 3, 86.20, '2025-08-31', 4.9),
(1, 1, 1, 3, 85.80, '2025-08-30', 5.0),
(1, 1, 1, 3, 87.50, '2025-08-29', 5.5),
(1, 1, 1, 3, 86.00, '2025-08-28', 5.2),
(1, 1, 1, 3, 85.00, '2025-08-27', 4.8),
(2, 1, 2, 4, 92.30, '2025-09-07', 4.2),
(2, 1, 2, 4, 93.10, '2025-09-06', 4.5),
(2, 1, 2, 4, 91.80, '2025-09-05', 4.0),
(3, 2, 3, 2, 72.10, '2025-09-07', 6.1),
(3, 2, 3, 2, 71.90, '2025-09-06', 6.0),
(3, 2, 3, 2, 73.50, '2025-09-05', 6.3);

-- ============================================
-- Load Forecasts (Sample)
-- ============================================
INSERT INTO forecasts (user_id, origin_id, destination_port_id, cargo_type_id, vessel_type_id, cargo_quantity_tonnes, contract_duration_months, forecast_period_days, current_freight_rate, predicted_freight_rate, forecast_trend, trend_percentage, confidence_level, forecast_date, forecasted_date, algorithm_used) VALUES
(1, 1, 1, 1, 3, 70000, 3, 30, 85.45, 88.92, 'Increasing', 4.1, 87, '2025-09-07', '2025-10-07', 'moving_average'),
(2, 2, 1, 2, 4, 90000, 6, 30, 92.30, 94.50, 'Increasing', 2.4, 82, '2025-09-07', '2025-10-07', 'linear_regression'),
(3, 3, 2, 3, 2, 45000, 1, 14, 72.10, 71.20, 'Decreasing', -1.2, 75, '2025-09-07', '2025-09-21', 'moving_average');

-- ============================================
-- Load Risk Assessments (Sample)
-- ============================================
INSERT INTO risk_assessments (user_id, forecast_id, origin_id, destination_port_id, freight_volatility_score, port_congestion_score, demand_uncertainty_score, vessel_compatibility_score, overall_risk_score, risk_level, recommendations) VALUES
(1, 1, 1, 1, 50, 25, 55, 20, 38, 'MEDIUM', 'Monitor freight volatility closely. Consider short-term contracts.'),
(2, 2, 2, 1, 35, 25, 35, 15, 27, 'LOW', 'Favorable conditions for medium-term charter agreements.'),
(3, 3, 3, 2, 65, 40, 70, 25, 50, 'MEDIUM', 'High demand uncertainty. Recommend cautious market entry.');

-- ============================================
-- Load Vessel Compatibility (Sample)
-- ============================================
INSERT INTO vessel_compatibility (origin_id, destination_port_id, vessel_type_id, cargo_quantity_tonnes, draft_compatible, loa_compatible, beam_compatible, capacity_sufficient, overall_compatibility, compatibility_score) VALUES
(1, 1, 1, 70000, TRUE, TRUE, TRUE, FALSE, 'Not Suitable', 60),
(1, 1, 2, 70000, TRUE, TRUE, TRUE, FALSE, 'Warning', 75),
(1, 1, 3, 70000, TRUE, TRUE, TRUE, TRUE, 'Suitable', 95),
(1, 1, 4, 70000, FALSE, FALSE, FALSE, TRUE, 'Not Suitable', 20),
(2, 1, 4, 90000, TRUE, TRUE, TRUE, TRUE, 'Suitable', 98),
(3, 2, 2, 45000, TRUE, TRUE, TRUE, TRUE, 'Suitable', 96);

-- ============================================
-- Load Recommendations (Sample)
-- ============================================
INSERT INTO recommendations (user_id, forecast_id, risk_assessment_id, origin_id, destination_port_id, recommended_vessel_type_id, recommended_strategy, expected_freight_cost, risk_level, market_entry_timing, contract_duration_recommendation, confidence_level) VALUES
(1, 1, 1, 1, 1, 3, 'Short-term/Medium-term charter during favorable forecast window', 6224400, 'MEDIUM', 'Within 2 weeks', 3, 87),
(2, 2, 2, 2, 1, 4, 'Medium-term contract for rate stability', 8505000, 'LOW', 'Immediate entry recommended', 6, 82),
(3, 3, 3, 3, 2, 2, 'Spot market monitoring before commitment', 3204000, 'MEDIUM', 'Wait for stabilization', 1, 75);

-- ============================================
-- Load Alerts (Sample)
-- ============================================
INSERT INTO alerts (user_id, alert_type, priority_level, title, message, related_port_id) VALUES
(1, 'freight_volatility', 'high', 'Freight Volatility Alert', 'Freight rates have exceeded historical volatility thresholds by 18%', NULL),
(1, 'port_congestion', 'high', 'Port Congestion Alert', 'Visakhapatnam port congestion has reached 85%. Turnaround time extended by 3-5 days.', 2),
(1, 'market_opportunity', 'medium', 'Market Trend Update', 'Freight rates showing slight upward trend. Favorable conditions for chartering decisions.', NULL),
(2, 'demand_alert', 'medium', 'Demand Uncertainty Alert', 'Industrial demand indicators show seasonal decline. Monitor cargo volumes closely.', NULL),
(3, 'system_notification', 'low', 'System Update', 'Forecast data updated with latest 24-hour market information.', NULL);

-- ============================================
-- Data Load Summary
-- ============================================
SELECT 'Sample data loaded successfully!' AS status;

-- Show record counts
SELECT 
    'users' as table_name, COUNT(*) as count FROM users
UNION ALL SELECT 'vessels', COUNT(*) FROM vessels
UNION ALL SELECT 'ports', COUNT(*) FROM ports
UNION ALL SELECT 'origins', COUNT(*) FROM origins
UNION ALL SELECT 'cargo_types', COUNT(*) FROM cargo_types
UNION ALL SELECT 'freight_history', COUNT(*) FROM freight_history
UNION ALL SELECT 'forecasts', COUNT(*) FROM forecasts
UNION ALL SELECT 'vessel_compatibility', COUNT(*) FROM vessel_compatibility
UNION ALL SELECT 'risk_assessments', COUNT(*) FROM risk_assessments
UNION ALL SELECT 'recommendations', COUNT(*) FROM recommendations
UNION ALL SELECT 'alerts', COUNT(*) FROM alerts;
