package in.sih26006.freight.repository;

import in.sih26006.freight.entity.Alert;
import in.sih26006.freight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
    List<Alert> findByUserOrderByCreatedAtDesc(User user);
    List<Alert> findByUserAndIsReadFalseOrderByCreatedAtDesc(User user);
    List<Alert> findByPriorityLevel(String priorityLevel);
}
