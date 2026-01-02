package com.rhombus.concrete.repository;

import com.rhombus.concrete.entity.AllocatedTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AllocatedTruckRepository extends JpaRepository<AllocatedTruck, Long> {
    Optional<AllocatedTruck> findByTruckRegistration(String truckRegistration);
    List<AllocatedTruck> findAllByOrderByTimeAllocatedDesc();
    List<AllocatedTruck> findBySiteName(String siteName);
    List<AllocatedTruck> findByStatus(String status);
    List<AllocatedTruck> findByTimeAllocatedBetween(LocalDateTime start, LocalDateTime end);
}

