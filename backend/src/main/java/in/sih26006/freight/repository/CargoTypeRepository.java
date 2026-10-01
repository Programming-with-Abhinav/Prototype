package in.sih26006.freight.repository;

import in.sih26006.freight.entity.CargoType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CargoTypeRepository extends JpaRepository<CargoType, Integer> {
    Optional<CargoType> findByCargoNameIgnoreCase(String cargoName);
    java.util.List<CargoType> findByCargoCategory(String cargoCategory);
}
