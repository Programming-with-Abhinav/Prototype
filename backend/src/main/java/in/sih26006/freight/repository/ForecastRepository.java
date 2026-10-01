package in.sih26006.freight.repository;

import in.sih26006.freight.entity.Forecast;
import in.sih26006.freight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ForecastRepository extends JpaRepository<Forecast, Integer> {
    List<Forecast> findByUserOrderByForecastDateDesc(User user);
}
