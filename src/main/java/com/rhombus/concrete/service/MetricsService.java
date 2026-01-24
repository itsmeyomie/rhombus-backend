package com.rhombus.concrete.service;

import com.rhombus.concrete.entity.PerformanceMetrics;
import com.rhombus.concrete.entity.AllocatedTruck;
import com.rhombus.concrete.entity.CompletedJob;
import com.rhombus.concrete.repository.PerformanceMetricsRepository;
import com.rhombus.concrete.repository.AllocatedTruckRepository;
import com.rhombus.concrete.repository.CompletedJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MetricsService {
    @Autowired
    private PerformanceMetricsRepository metricsRepository;
    
    @Autowired
    private AllocatedTruckRepository allocatedRepository;
    
    @Autowired
    private CompletedJobRepository completedRepository;

    public Optional<PerformanceMetrics> getMetricsByDate(LocalDate date) {
        return metricsRepository.findByMetricDate(date);
    }

    /**
     * Calculate metrics for a specific date
     * Trucks Processed = COUNT(DISTINCT truck_id) for that date
     */
    public PerformanceMetrics calculateMetricsForDate(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);
        
        // Get all allocations for the date
        java.util.List<AllocatedTruck> allocations = allocatedRepository.findByTimeAllocatedBetween(start, end);
        
        // Count DISTINCT trucks (not trips)
        Set<String> distinctTrucks = allocations.stream()
            .map(AllocatedTruck::getTruckRegistration)
            .collect(Collectors.toSet());
        
        int trucksProcessed = distinctTrucks.size();
        
        // Calculate concrete allocated
        double concreteAllocated = allocations.stream()
            .mapToDouble(AllocatedTruck::getConcreteAmount)
            .sum();
        
        // Calculate wait times
        double totalWaitTimeMinutes = 0.0;
        int waitTimeCount = 0;
        // Note: Wait time calculation would need to parse the waitTime string
        // For now, we'll use a simplified approach
        
        PerformanceMetrics metrics = new PerformanceMetrics();
        metrics.setMetricDate(date);
        metrics.setTrucksProcessed(trucksProcessed);
        metrics.setConcreteAllocated(concreteAllocated);
        metrics.setTotalWaitTimeMinutes(totalWaitTimeMinutes);
        metrics.setWaitTimeCount(waitTimeCount);
        
        return metrics;
    }

    /**
     * Get or calculate metrics for a date
     */
    public PerformanceMetrics getOrCalculateMetricsForDate(LocalDate date) {
        Optional<PerformanceMetrics> existing = metricsRepository.findByMetricDate(date);
        if (existing.isPresent()) {
            return existing.get();
        }
        // Calculate and save
        PerformanceMetrics calculated = calculateMetricsForDate(date);
        return metricsRepository.save(calculated);
    }

    public PerformanceMetrics saveOrUpdateMetrics(PerformanceMetrics metrics) {
        Optional<PerformanceMetrics> existing = metricsRepository.findByMetricDate(metrics.getMetricDate());
        if (existing.isPresent()) {
            PerformanceMetrics existingMetrics = existing.get();
            existingMetrics.setTrucksProcessed(metrics.getTrucksProcessed());
            existingMetrics.setConcreteAllocated(metrics.getConcreteAllocated());
            existingMetrics.setTotalWaitTimeMinutes(metrics.getTotalWaitTimeMinutes());
            existingMetrics.setWaitTimeCount(metrics.getWaitTimeCount());
            return metricsRepository.save(existingMetrics);
        }
        return metricsRepository.save(metrics);
    }
}



