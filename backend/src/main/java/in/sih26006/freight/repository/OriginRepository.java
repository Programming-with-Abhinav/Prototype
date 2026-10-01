package in.sih26006.freight.repository;

import in.sih26006.freight.entity.Origin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OriginRepository extends JpaRepository<Origin, Integer> {
    Optional<Origin> findByOriginNameIgnoreCase(String originName);
    List<Origin> findByIsActiveTrue();
    List<Origin> findByCountryIgnoreCase(String country);
}
