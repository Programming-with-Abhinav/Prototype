package in.sih26006.freight.repository;

import in.sih26006.freight.entity.CargoRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRequestRepository extends JpaRepository<CargoRequest, Long> {
}
