package in.sih26006.freight.repository;

import in.sih26006.freight.entity.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VesselRepository extends JpaRepository<Vessel, Long> {

    Optional<Vessel> findByVesselTypeIgnoreCase(String vesselType);
}
