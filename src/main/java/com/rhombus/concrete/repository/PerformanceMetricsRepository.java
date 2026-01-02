package com.rhombus.concrete.repository;

import com.rhombus.concrete.entity.PerformanceMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PerformanceMetricsRepository extends JpaRepository<PerformanceMetrics, Long> {
    Optional<PerformanceMetrics> findByMetricDate(LocalDate date);
}



