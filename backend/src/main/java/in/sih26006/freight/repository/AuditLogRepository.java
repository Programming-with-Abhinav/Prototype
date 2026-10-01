package in.sih26006.freight.repository;

import in.sih26006.freight.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
    List<AuditLog> findByTableNameAndRecordId(String tableName, Integer recordId);
    List<AuditLog> findByAction(String action);
}
