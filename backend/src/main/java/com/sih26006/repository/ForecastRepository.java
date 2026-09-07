package com.sih26006.repository;

import com.sih26006.entity.Forecast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Integer> {
    Page<Forecast> findByUserId(Integer userId, Pageable pageable);
    List<Forecast> findByUserIdOrderByCreatedAtDesc(Integer userId);
    List<Forecast> findByForecastDateAfter(LocalDate date);
    List<Forecast> findByOriginIdAndDestinationPortId(Integer originId, Integer destinationPortId);
}
