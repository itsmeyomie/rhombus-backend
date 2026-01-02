package com.rhombus.concrete.service;

import com.rhombus.concrete.entity.PerformanceMetrics;
import com.rhombus.concrete.repository.PerformanceMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class MetricsService {
    @Autowired
    private PerformanceMetricsRepository metricsRepository;

    public Optional<PerformanceMetrics> getMetricsByDate(LocalDate date) {
        return metricsRepository.findByMetricDate(date);
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



