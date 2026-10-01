package in.sih26006.freight.repository;

import in.sih26006.freight.entity.Origin;
import in.sih26006.freight.entity.Port;
import in.sih26006.freight.entity.Vessel;
import in.sih26006.freight.entity.VesselCompatibility;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VesselCompatibilityRepository extends JpaRepository<VesselCompatibility, Integer> {
    List<VesselCompatibility> findByOriginAndDestinationPort(Origin origin, Port destinationPort);
    Optional<VesselCompatibility> findByOriginAndDestinationPortAndVessel(Origin origin, Port destinationPort, Vessel vessel);
}
