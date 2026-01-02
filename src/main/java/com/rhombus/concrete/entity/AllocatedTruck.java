package com.rhombus.concrete.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "allocated_trucks")
public class AllocatedTruck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "allocation_id", unique = true, nullable = false)
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
    
    @Column(nullable = false)
    private String status = "allocated";
    
    @Column(name = "time_allocated")
    private LocalDateTime timeAllocated;
    
    @Column(name = "wait_time", nullable = false)
    private String waitTime;
    
    @Column(name = "turnaround_time")
    private String turnaroundTime;
    
    @PrePersist
    protected void onCreate() {
        timeAllocated = LocalDateTime.now();
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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getTimeAllocated() { return timeAllocated; }
    public void setTimeAllocated(LocalDateTime timeAllocated) { this.timeAllocated = timeAllocated; }
    public String getWaitTime() { return waitTime; }
    public void setWaitTime(String waitTime) { this.waitTime = waitTime; }
    public String getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(String turnaroundTime) { this.turnaroundTime = turnaroundTime; }
}


