package com.sih26006.repository;

import com.sih26006.entity.FreightHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FreightHistoryRepository extends JpaRepository<FreightHistory, Integer> {
    List<FreightHistory> findByOriginIdAndDestinationPortId(Integer originId, Integer destinationPortId);
    List<FreightHistory> findByRecordedDateAfter(LocalDate date);
    List<FreightHistory> findByOriginIdAndDestinationPortIdAndRecordedDateAfterOrderByRecordedDateDesc(
            Integer originId, Integer destinationPortId, LocalDate date);
}
