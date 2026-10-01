package in.sih26006.freight.repository;

import in.sih26006.freight.entity.FreightHistory;
import in.sih26006.freight.entity.Origin;
import in.sih26006.freight.entity.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface FreightHistoryRepository extends JpaRepository<FreightHistory, Integer> {
    List<FreightHistory> findByOriginAndDestinationPortOrderByRecordedDateDesc(Origin origin, Port destinationPort);
    List<FreightHistory> findByRecordedDateBetween(LocalDate from, LocalDate to);
}
