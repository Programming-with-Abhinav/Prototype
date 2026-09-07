# PHASE 2 IMPLEMENTATION GUIDE
## SIH26006 - Intelligent Freight Forecasting & Vessel Chartering

**Status:** Phase 2 - Database Setup Instructions  
**Date:** 2026-09-07

---

## 📋 Quick Start Guide

### Prerequisites
- MySQL Server 8.0 or higher installed
- MySQL command-line client or MySQL Workbench
- Administrative access to MySQL
- Basic understanding of SQL

---

## 🚀 Setup Steps

### Step 1: Create the Database

**Using MySQL Command Line:**
```bash
cd database/
mysql -u root -p < 01_create_database.sql
```

**Using MySQL Workbench:**
1. Open MySQL Workbench
2. Open connection to your MySQL server
3. File → Open SQL Script → `01_create_database.sql`
4. Click Execute (⚡)

**Expected Output:**
```
Database created successfully: sih26006_freight_intelligence
```

---

### Step 2: Create All Tables

```bash
mysql -u root -p sih26006_freight_intelligence < 02_create_tables.sql
```

**Expected Output:**
```
All tables created successfully!
total_tables: 12
```

**Tables Created:**
1. users
2. vessels
3. ports
4. origins
5. cargo_types
6. freight_history
7. forecasts
8. vessel_compatibility
9. risk_assessments
10. recommendations
11. alerts
12. audit_log

---

### Step 3: Load Sample Data

```bash
mysql -u root -p sih26006_freight_intelligence < 03_load_sample_data.sql
```

**Expected Output:**
```
Sample data loaded successfully!
users: 4
vessels: 4
ports: 6
origins: 8
cargo_types: 9
freight_history: 18
forecasts: 3
vessel_compatibility: 6
risk_assessments: 3
recommendations: 3
alerts: 5
audit_log: 0
```

---

### Step 4: Create Database Users

```bash
mysql -u root -p < 04_create_users.sql
```

**Users Created:**
- `freight_app` (Application user) - localhost
- `freight_readonly` (Read-only user) - localhost
- `freight_admin` (Admin user) - localhost
- `freight_app` (Remote user) - any host

**⚠️ Important:** Change default passwords in production!

---

### Step 5: Verify Setup

Run verification queries:

```bash
mysql -u root -p sih26006_freight_intelligence
```

Then execute in MySQL:

```sql
-- Check table count
SELECT COUNT(*) as total_tables FROM information_schema.tables 
WHERE table_schema = 'sih26006_freight_intelligence';

-- Should return: 12

-- Check sample data
SELECT * FROM users;
SELECT * FROM vessels;
SELECT * FROM ports;

-- Verify foreign keys
SELECT CONSTRAINT_NAME, TABLE_NAME, COLUMN_NAME, REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'sih26006_freight_intelligence' 
AND REFERENCED_TABLE_NAME IS NOT NULL;
```

---

## 🔌 Integration with Spring Boot (Phase 3)

### Database Configuration

**File:** `src/main/resources/application.properties`

```properties
# ============================================
# MySQL Database Configuration
# ============================================

spring.datasource.url=jdbc:mysql://localhost:3306/sih26006_freight_intelligence?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=freight_app
spring.datasource.password=freight_app_password_2026
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ============================================
# Hibernate Configuration
# ============================================

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true

# ============================================
# Connection Pool Configuration
# ============================================

spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000
```

---

## 📊 Entity Classes Structure (For Phase 3)

### User Entity Example

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String passwordHash;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String fullName;
    
    private String companyName;
    
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    
    private Boolean isActive;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    private LocalDateTime lastLogin;
    
    // Relationships
    @OneToMany(mappedBy = "user")
    private List<Forecast> forecasts;
    
    // Getters and Setters
}
```

---

## 🔄 Data Migration (If Upgrading)

### From Old System to Phase 2

1. Export data from old system to CSV
2. Write migration script with data transformation
3. Test on development database first
4. Validate data integrity
5. Execute migration on production

**Example Migration Script:**
```sql
-- Backup before migration
CREATE TABLE freight_history_backup AS SELECT * FROM freight_history;

-- Clear target table
DELETE FROM freight_history;

-- Load migrated data
LOAD DATA INFILE '/path/to/migrated_data.csv'
INTO TABLE freight_history
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
(origin_id, destination_port_id, cargo_type_id, vessel_type_id, freight_rate_per_tonne, recorded_date, volatility_index);

-- Verify counts
SELECT COUNT(*) FROM freight_history;
```

---

## 🔐 Backup & Recovery

### Regular Backups

```bash
# Daily backup
mysqldump -u freight_admin -p sih26006_freight_intelligence > backup_$(date +%Y%m%d).sql

# Backup to compressed file
mysqldump -u freight_admin -p sih26006_freight_intelligence | gzip > backup_$(date +%Y%m%d).sql.gz
```

### Restore from Backup

```bash
# Restore full database
mysql -u root -p sih26006_freight_intelligence < backup_20260907.sql

# Restore from compressed backup
gunzip < backup_20260907.sql.gz | mysql -u root -p sih26006_freight_intelligence
```

---

## 📈 Performance Optimization

### Index Management

Indexes are already created in `02_create_tables.sql`. To add additional indexes:

```sql
-- Add index for frequently queried columns
CREATE INDEX idx_freight_date_rate ON freight_history(recorded_date, freight_rate_per_tonne);
CREATE INDEX idx_user_forecast_date ON forecasts(user_id, forecast_date);
CREATE INDEX idx_port_congestion ON ports(current_congestion_percentage);

-- Analyze tables for query optimization
ANALYZE TABLE freight_history, forecasts, recommendations;

-- Optimize tables
OPTIMIZE TABLE freight_history, forecasts, recommendations;
```

### Query Performance Tips

1. Always use indexes on WHERE clauses
2. Use LIMIT for pagination
3. Join only necessary tables
4. Use appropriate data types
5. Archive old records periodically

---

## 🧪 Testing the Database

### Unit Test Connection (Java/Spring Boot)

```java
@SpringBootTest
public class DatabaseConnectionTest {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Test
    public void testDatabaseConnection() {
        String query = "SELECT COUNT(*) FROM users";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class);
        assertTrue(count >= 0);
    }
    
    @Test
    public void testVesselTableExists() {
        String query = "SELECT COUNT(*) FROM vessels";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class);
        assertEquals(4, count);
    }
}
```

---

## 🛠️ Maintenance Tasks

### Regular Maintenance Schedule

| Task | Frequency | Command |
|------|-----------|---------|
| Backup | Daily | `mysqldump ...` |
| Optimize | Weekly | `OPTIMIZE TABLE ...` |
| Analyze | Weekly | `ANALYZE TABLE ...` |
| Check Integrity | Monthly | `CHECK TABLE ...` |
| Archive Old Data | Monthly | DELETE old records |

### Health Check Query

```sql
-- Check database health
SELECT 
    'Users' as check_name, COUNT(*) as count 
FROM users 
WHERE is_active = TRUE
UNION ALL
SELECT 'Alerts (Unread)', COUNT(*) FROM alerts WHERE is_read = FALSE
UNION ALL
SELECT 'Recent Forecasts', COUNT(*) FROM forecasts WHERE created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
UNION ALL
SELECT 'High Risk Routes', COUNT(*) FROM risk_assessments WHERE risk_level = 'HIGH';
```

---

## 🔗 Environment Variables (For Security)

Create `.env` file (DO NOT commit to version control):

```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=sih26006_freight_intelligence
DB_USER=freight_app
DB_PASSWORD=freight_app_password_2026
```

**Spring Boot Property File:** `application-secrets.properties`

```properties
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=false&serverTimezone=UTC
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

---

## 📝 Database Documentation

### Schema Diagram Commands (MySQL Workbench)

1. Database → Reverse Engineer
2. Select sih26006_freight_intelligence
3. Create Diagram
4. Export as PDF/PNG

### Generate Schema Documentation

```bash
# Using MySQL Workbench
# File → Export → Forward Engineer SQL Create Script

# Or using command line tools
mysqldump --no-data -u root -p sih26006_freight_intelligence > schema_only.sql
```

---

## ✅ Phase 2 Completion Checklist

- [ ] MySQL Server installed and running
- [ ] Database created successfully
- [ ] All 12 tables created with correct structure
- [ ] Foreign key relationships verified
- [ ] Sample data loaded successfully
- [ ] Database users created with appropriate permissions
- [ ] Connection tested successfully
- [ ] Backup strategy defined
- [ ] Recovery procedures documented
- [ ] Performance indexes created
- [ ] Health check queries verified
- [ ] Documentation complete

---

## 🚀 Moving to Phase 3

Once Phase 2 is complete, you're ready for Phase 3 (Spring Boot Backend):

1. **Create Spring Boot Project** with Spring Data JPA
2. **Define Entity Classes** based on database tables
3. **Create Repository Interfaces** for data access
4. **Build REST API Endpoints** for all operations
5. **Integrate with Frontend** from Phase 1

---

## 🆘 Troubleshooting

### Common Issues

**Issue:** Access Denied for user 'freight_app'@'localhost'
```bash
# Solution: Verify user was created and password is correct
mysql -u freight_app -p sih26006_freight_intelligence
# Enter password: freight_app_password_2026
```

**Issue:** Table doesn't exist
```bash
# Solution: Verify all create_tables.sql executed successfully
mysql -u root -p sih26006_freight_intelligence < 02_create_tables.sql
```

**Issue:** Foreign key constraint error
```bash
# Solution: Ensure data is loaded in correct order (parents before children)
mysql -u root -p sih26006_freight_intelligence < 03_load_sample_data.sql
```

**Issue:** Slow queries
```sql
-- Solution: Check and optimize indexes
SHOW INDEX FROM freight_history;
ANALYZE TABLE freight_history;
OPTIMIZE TABLE freight_history;
```

---

## 📞 Support & Resources

- MySQL Official Documentation: https://dev.mysql.com/doc/
- MySQL Workbench Tutorial: https://www.mysql.com/products/workbench/
- Spring Boot Database Guide: https://spring.io/guides/gs/accessing-data-mysql/
- SIH26006 Project Repository: [Your Repository URL]

---

## 📄 Files in This Phase

1. **PHASE_2_DATABASE_DESIGN.md** - Complete database design documentation
2. **01_create_database.sql** - Database creation script
3. **02_create_tables.sql** - Table creation with relationships
4. **03_load_sample_data.sql** - Sample data for testing
5. **04_create_users.sql** - Database user setup
6. **05_useful_queries.sql** - Query reference for development
7. **PHASE_2_IMPLEMENTATION_GUIDE.md** - This file

---

## ✨ Next Phase (Phase 3)

**Backend Development with Spring Boot:**
- Spring Boot project setup
- JPA Entity mapping
- Repository interfaces
- REST API endpoints
- Database connection integration
- Basic CRUD operations

---

**Phase 2 Complete!** Ready for Phase 3 Backend Development

*SIH26006 - Intelligent Freight Forecasting & Vessel Chartering*  
*Generated: 2026-09-07*
