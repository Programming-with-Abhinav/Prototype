package in.sih26006.freight.repository;

import in.sih26006.freight.entity.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VesselRepository extends JpaRepository<Vessel, Integer> {
    Optional<Vessel> findByVesselTypeIgnoreCase(String vesselType);
    List<Vessel> findByIsActiveTrue();
}
