package com.rhombus.concrete.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "performance_metrics")
public class PerformanceMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "metric_date", unique = true, nullable = false)
    private LocalDate metricDate;
    
    @Column(name = "trucks_processed")
    private Integer trucksProcessed = 0;
    
    @Column(name = "concrete_allocated")
    private Double concreteAllocated = 0.0;
    
    @Column(name = "total_wait_time_minutes")
    private Double totalWaitTimeMinutes = 0.0;
    
    @Column(name = "wait_time_count")
    private Integer waitTimeCount = 0;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getMetricDate() { return metricDate; }
    public void setMetricDate(LocalDate metricDate) { this.metricDate = metricDate; }
    public Integer getTrucksProcessed() { return trucksProcessed; }
    public void setTrucksProcessed(Integer trucksProcessed) { this.trucksProcessed = trucksProcessed; }
    public Double getConcreteAllocated() { return concreteAllocated; }
    public void setConcreteAllocated(Double concreteAllocated) { this.concreteAllocated = concreteAllocated; }
    public Double getTotalWaitTimeMinutes() { return totalWaitTimeMinutes; }
    public void setTotalWaitTimeMinutes(Double totalWaitTimeMinutes) { this.totalWaitTimeMinutes = totalWaitTimeMinutes; }
    public Integer getWaitTimeCount() { return waitTimeCount; }
    public void setWaitTimeCount(Integer waitTimeCount) { this.waitTimeCount = waitTimeCount; }
}


