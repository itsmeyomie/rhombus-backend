package com.rhombus.concrete.repository;

import com.rhombus.concrete.entity.CompletedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompletedJobRepository extends JpaRepository<CompletedJob, Long> {
    List<CompletedJob> findAllByOrderByTimeCompletedDesc();
    List<CompletedJob> findBySiteName(String siteName);
    List<CompletedJob> findByTimeCompletedBetween(LocalDateTime start, LocalDateTime end);
    List<CompletedJob> findByTruckRegistration(String truckRegistration);
    List<CompletedJob> findByTruckRegistrationOrderByTimeCompletedDesc(String truckRegistration);
    
    // Get max trip number for a truck (industry practice: track total trips per vehicle)
    @Query("SELECT MAX(c.totalTripCount) FROM CompletedJob c WHERE c.truckRegistration = :truckRegistration")
    Integer findMaxTotalTripCountByTruckRegistration(String truckRegistration);
}



