package com.rhombus.concrete.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "truck_queue")
public class TruckQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "truck_registration", nullable = false)
    private String truckRegistration;
    
    @Column(name = "driver_name", nullable = false)
    private String driverName;
    
    @Column(nullable = false)
    private Double capacity;
    
    @Column(name = "time_in_queue")
    private LocalDateTime timeInQueue;
    
    @Column(name = "current_trip_number")
    private Integer currentTripNumber = 1;
    
    @Column(name = "turnaround_time")
    private String turnaroundTime = "0h 0m";
    
    @PrePersist
    protected void onCreate() {
        timeInQueue = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTruckRegistration() { return truckRegistration; }
    public void setTruckRegistration(String truckRegistration) { this.truckRegistration = truckRegistration; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public Double getCapacity() { return capacity; }
    public void setCapacity(Double capacity) { this.capacity = capacity; }
    public LocalDateTime getTimeInQueue() { return timeInQueue; }
    public void setTimeInQueue(LocalDateTime timeInQueue) { this.timeInQueue = timeInQueue; }
    public Integer getCurrentTripNumber() { return currentTripNumber; }
    public void setCurrentTripNumber(Integer currentTripNumber) { this.currentTripNumber = currentTripNumber; }
    public String getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(String turnaroundTime) { this.turnaroundTime = turnaroundTime; }
}


