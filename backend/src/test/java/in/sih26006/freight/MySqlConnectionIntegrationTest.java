package in.sih26006.freight;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MySqlConnectionIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(MySqlConnectionIntegrationTest.class);

    @Value("${spring.datasource.url}")
    private String configuredDatasourceUrl;

    private boolean isPortReachable(String host, int port, int timeoutMs) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeoutMs);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Test
    @DisplayName("Verify MySQL connection and query canonical table when MySQL is available")
    void testMySqlConnectionIfAvailable() {
        String mysqlHost = System.getenv().getOrDefault("MYSQL_HOST", "localhost");
        int mysqlPort = Integer.parseInt(System.getenv().getOrDefault("MYSQL_PORT", "3306"));
        String dbName = "sih26006_freight_intelligence";
        String mysqlUrl = System.getenv().getOrDefault(
                "DATABASE_URL",
                "jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "/" + dbName + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
        );
        String username = System.getenv().getOrDefault("DATABASE_USERNAME", "root");
        String password = System.getenv().getOrDefault("DATABASE_PASSWORD", "");

        boolean mysqlReachable = isPortReachable(mysqlHost, mysqlPort, 1500);

        if (!mysqlReachable) {
            log.info("MySQL is not reachable at {}:{}. Safe local development fallback is active.", mysqlHost, mysqlPort);
        }

        Assumptions.assumeTrue(mysqlReachable,
                "MySQL instance is not reachable at " + mysqlHost + ":" + mysqlPort + " in this environment. Skipping live MySQL query.");

        // If reachable, test direct MySQL connection and query canonical table
        try (Connection connection = DriverManager.getConnection(mysqlUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM ports")) {

            assertTrue(rs.next(), "Query against canonical table 'ports' should return a row");
            int portCount = rs.getInt(1);
            log.info("Successfully connected to MySQL database '{}' and verified ports table. Row count: {}", dbName, portCount);
            assertTrue(portCount >= 0, "Canonical ports table row count should be non-negative");
        } catch (Exception e) {
            log.error("Failed to query canonical MySQL database: {}", e.getMessage());
            throw new RuntimeException("MySQL connection failed: " + e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("Verify configured database URL points to canonical database name")
    void testConfiguredDatabaseUrlContainsCanonicalName() {
        assertNotNull(configuredDatasourceUrl, "Datasource URL must not be null");
        assertTrue(configuredDatasourceUrl.contains("sih26006_freight_intelligence"),
                "Datasource URL should target canonical database sih26006_freight_intelligence");
    }
}
