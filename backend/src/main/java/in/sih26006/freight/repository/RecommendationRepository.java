package in.sih26006.freight.repository;

import in.sih26006.freight.entity.Recommendation;
import in.sih26006.freight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation, Integer> {
    List<Recommendation> findByUserOrderByCreatedAtDesc(User user);
}
