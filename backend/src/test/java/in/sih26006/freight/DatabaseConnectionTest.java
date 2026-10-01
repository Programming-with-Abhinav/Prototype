package in.sih26006.freight;

import in.sih26006.freight.repository.PortRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PortRepository portRepository;

    @Test
    @DisplayName("Verify DataSource connection and metadata")
    void testDataSourceConnection() throws SQLException {
        assertNotNull(dataSource, "DataSource bean should not be null");
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Active database connection must be established");
            assertFalse(connection.isClosed(), "Connection should be open");

            DatabaseMetaData metaData = connection.getMetaData();
            assertNotNull(metaData.getDatabaseProductName(), "Database product name should be available");
            assertNotNull(metaData.getURL(), "Database URL should be configured");
        }
    }

    @Test
    @DisplayName("Verify executing simple SQL query against active database")
    void testSimpleSqlQuery() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertNotNull(result, "Query result should not be null");
        assertEquals(1, result, "SELECT 1 should return 1");
    }

    @Test
    @DisplayName("Verify querying port repository")
    void testPortRepositoryQuery() {
        assertNotNull(portRepository, "PortRepository should be injected");
        long count = portRepository.count();
        assertTrue(count >= 0, "Port repository count should be non-negative");
    }
}
