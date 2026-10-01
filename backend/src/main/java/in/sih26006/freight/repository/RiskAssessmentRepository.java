package in.sih26006.freight.repository;

import in.sih26006.freight.entity.RiskAssessment;
import in.sih26006.freight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Integer> {
    List<RiskAssessment> findByUserOrderByAssessmentDateDesc(User user);
    List<RiskAssessment> findByRiskLevel(String riskLevel);
}
