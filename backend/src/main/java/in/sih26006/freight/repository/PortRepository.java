package in.sih26006.freight.repository;

import in.sih26006.freight.entity.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PortRepository extends JpaRepository<Port, Integer> {

    Optional<Port> findByPortNameIgnoreCase(String portName);

    /**
     * Legacy alias used by DecisionService and WeatherService.
     * Routes to portName field via explicit JPQL query.
     */
    @Query("SELECT p FROM Port p WHERE LOWER(p.portName) = LOWER(:name)")
    Optional<Port> findByNameIgnoreCase(@Param("name") String name);

    List<Port> findByCountry(String country);

    List<Port> findByIsOperationalTrue();
}
