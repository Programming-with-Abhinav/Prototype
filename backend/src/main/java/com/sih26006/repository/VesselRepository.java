package com.sih26006.repository;

import com.sih26006.entity.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, Integer> {
    Optional<Vessel> findByVesselType(String vesselType);
    List<Vessel> findByIsActiveTrue();
    List<Vessel> findByCargoCapacityTonnesGreaterThanEqual(Integer capacity);
}
