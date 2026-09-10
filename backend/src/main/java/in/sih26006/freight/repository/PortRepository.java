package in.sih26006.freight.repository;

import in.sih26006.freight.entity.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PortRepository extends JpaRepository<Port, Long> {

    Optional<Port> findByNameIgnoreCase(String name);
}
