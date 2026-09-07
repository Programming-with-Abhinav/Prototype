package com.sih26006.repository;

import com.sih26006.entity.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortRepository extends JpaRepository<Port, Integer> {
    Optional<Port> findByPortName(String portName);
    List<Port> findByIsOperationalTrue();
    List<Port> findByStateProvince(String state);
    List<Port> findByCountry(String country);
}
