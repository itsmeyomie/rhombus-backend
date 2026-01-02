package com.rhombus.concrete.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "completed_jobs")
public class CompletedJob {
    public CompletedJob() {
        // Default constructor
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "allocation_id", nullable = false)
    private String allocationId;
    
    @Column(name = "truck_registration", nullable = false)
    private String truckRegistration;
    
    @Column(name = "driver_name", nullable = false)
    private String driverName;
    
    @Column(name = "site_name", nullable = false)
    private String siteName;
    
    @Column(name = "concrete_amount", nullable = false)
    private Double concreteAmount;
    
    @Column(name = "concrete_grade", nullable = false)
    private String concreteGrade;
    
    @Column(name = "trip_number", nullable = false)
    private Integer tripNumber;
    
    @Column(name = "daily_trip_count", nullable = false)
    private Integer dailyTripCount;
    
    @Column(name = "total_trip_count", nullable = false)
    private Integer totalTripCount;
    
    @Column(name = "time_allocated", nullable = false)
    private LocalDateTime timeAllocated;
    
    @Column(name = "time_completed")
    private LocalDateTime timeCompleted;
    
    @Column(name = "total_processing_time", nullable = false)
    private String totalProcessingTime;
    
    @Column(name = "turnaround_time")
    private String turnaroundTime;
    
    private String notes;
    
    @Column(name = "completed_by", nullable = false)
    private String completedBy;
    
    @PrePersist
    protected void onCreate() {
        timeCompleted = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAllocationId() { return allocationId; }
    public void setAllocationId(String allocationId) { this.allocationId = allocationId; }
    public String getTruckRegistration() { return truckRegistration; }
    public void setTruckRegistration(String truckRegistration) { this.truckRegistration = truckRegistration; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public Double getConcreteAmount() { return concreteAmount; }
    public void setConcreteAmount(Double concreteAmount) { this.concreteAmount = concreteAmount; }
    public String getConcreteGrade() { return concreteGrade; }
    public void setConcreteGrade(String concreteGrade) { this.concreteGrade = concreteGrade; }
    public Integer getTripNumber() { return tripNumber; }
    public void setTripNumber(Integer tripNumber) { this.tripNumber = tripNumber; }
    public Integer getDailyTripCount() { return dailyTripCount; }
    public void setDailyTripCount(Integer dailyTripCount) { this.dailyTripCount = dailyTripCount; }
    public Integer getTotalTripCount() { return totalTripCount; }
    public void setTotalTripCount(Integer totalTripCount) { this.totalTripCount = totalTripCount; }
    public LocalDateTime getTimeAllocated() { return timeAllocated; }
    public void setTimeAllocated(LocalDateTime timeAllocated) { this.timeAllocated = timeAllocated; }
    public LocalDateTime getTimeCompleted() { return timeCompleted; }
    public void setTimeCompleted(LocalDateTime timeCompleted) { this.timeCompleted = timeCompleted; }
    public String getTotalProcessingTime() { return totalProcessingTime; }
    public void setTotalProcessingTime(String totalProcessingTime) { this.totalProcessingTime = totalProcessingTime; }
    public String getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(String turnaroundTime) { this.turnaroundTime = turnaroundTime; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getCompletedBy() { return completedBy; }
    public void setCompletedBy(String completedBy) { this.completedBy = completedBy; }
}

