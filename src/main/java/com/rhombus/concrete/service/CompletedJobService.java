package com.rhombus.concrete.service;

import com.rhombus.concrete.entity.CompletedJob;
import com.rhombus.concrete.repository.CompletedJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompletedJobService {
    @Autowired
    private CompletedJobRepository completedRepository;

    public List<CompletedJob> getAllCompleted() {
        return completedRepository.findAllByOrderByTimeCompletedDesc();
    }

    public CompletedJob createCompletedJob(CompletedJob job) {
        // Ensure timeCompleted is set
        if (job.getTimeCompleted() == null) {
            job.setTimeCompleted(LocalDateTime.now());
        }
        
        // Calculate trip numbers based on previous trips for this truck
        Integer maxTripNumber = getMaxTripNumberForTruck(job.getTruckRegistration());
        Integer newTripNumber = maxTripNumber + 1;
        
        // Set trip number if not provided or if it's less than the calculated one
        if (job.getTripNumber() == null || job.getTripNumber() < newTripNumber) {
            job.setTripNumber(newTripNumber);
        }
        
        // Ensure required fields have defaults
        if (job.getDailyTripCount() == null) {
            // Count trips for the completion date (not necessarily today)
            LocalDateTime completionDate = job.getTimeCompleted();
            if (completionDate == null) {
                completionDate = LocalDateTime.now();
            }
            LocalDateTime dayStart = completionDate.withHour(0).withMinute(0).withSecond(0);
            LocalDateTime dayEnd = completionDate.withHour(23).withMinute(59).withSecond(59);
            long dailyTrips = completedRepository.findByTruckRegistration(job.getTruckRegistration())
                .stream()
                .filter(j -> j.getTimeCompleted() != null && 
                           j.getTimeCompleted().isAfter(dayStart) && 
                           j.getTimeCompleted().isBefore(dayEnd))
                .count();
            job.setDailyTripCount((int)(dailyTrips + 1));
        }
        
        // Total trip count should be the new trip number
        if (job.getTotalTripCount() == null) {
            job.setTotalTripCount(job.getTripNumber());
        }
        
        if (job.getTotalProcessingTime() == null || job.getTotalProcessingTime().isEmpty()) {
            job.setTotalProcessingTime("0h 0m");
        }
        if (job.getCompletedBy() == null || job.getCompletedBy().isEmpty()) {
            job.setCompletedBy("system");
        }
        return completedRepository.save(job);
    }

    public List<CompletedJob> findBySite(String siteName) {
        return completedRepository.findBySiteName(siteName);
    }

    public List<CompletedJob> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return completedRepository.findByTimeCompletedBetween(start, end);
    }

    public List<CompletedJob> findByDate(String date) {
        LocalDateTime start = LocalDateTime.parse(date + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(date + "T23:59:59");
        return completedRepository.findByTimeCompletedBetween(start, end);
    }

    public List<CompletedJob> findByDateRange(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        return completedRepository.findByTimeCompletedBetween(start, end);
    }

    public List<CompletedJob> findByTruckRegistration(String truckRegistration) {
        return completedRepository.findByTruckRegistration(truckRegistration);
    }

    public Integer getMaxTripNumberForTruck(String truckRegistration) {
        Integer maxTrip = completedRepository.findMaxTotalTripCountByTruckRegistration(truckRegistration);
        return maxTrip != null ? maxTrip : 0;
    }

    /**
     * Get trips per driver for a specific date
     * Returns map of driver name to trip count
     */
    public java.util.Map<String, Integer> getTripsPerDriverByDate(String date) {
        List<CompletedJob> jobs = findByDate(date);
        return jobs.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                CompletedJob::getDriverName,
                java.util.stream.Collectors.collectingAndThen(
                    java.util.stream.Collectors.counting(),
                    Long::intValue
                )
            ));
    }

    /**
     * Get trips per driver for a date range
     */
    public java.util.Map<String, Integer> getTripsPerDriverByDateRange(String startDate, String endDate) {
        List<CompletedJob> jobs = findByDateRange(startDate, endDate);
        return jobs.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                CompletedJob::getDriverName,
                java.util.stream.Collectors.collectingAndThen(
                    java.util.stream.Collectors.counting(),
                    Long::intValue
                )
            ));
    }

    /**
     * Get trips per driver with volume/capacity information
     */
    public java.util.List<java.util.Map<String, Object>> getTripsPerDriverDetailed(String date) {
        List<CompletedJob> jobs = findByDate(date);
        java.util.Map<String, java.util.List<CompletedJob>> grouped = jobs.stream()
            .collect(java.util.stream.Collectors.groupingBy(CompletedJob::getDriverName));
        
        return grouped.entrySet().stream()
            .map(entry -> {
                java.util.Map<String, Object> result = new java.util.HashMap<>();
                result.put("driverName", entry.getKey());
                result.put("tripCount", entry.getValue().size());
                double totalVolume = entry.getValue().stream()
                    .mapToDouble(CompletedJob::getConcreteAmount)
                    .sum();
                result.put("totalVolume", totalVolume);
                return result;
            })
            .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Get trips per driver with volume/capacity information for date range
     */
    public java.util.List<java.util.Map<String, Object>> getTripsPerDriverDetailedRange(String startDate, String endDate) {
        List<CompletedJob> jobs = findByDateRange(startDate, endDate);
        java.util.Map<String, java.util.List<CompletedJob>> grouped = jobs.stream()
            .collect(java.util.stream.Collectors.groupingBy(CompletedJob::getDriverName));
        
        return grouped.entrySet().stream()
            .map(entry -> {
                java.util.Map<String, Object> result = new java.util.HashMap<>();
                result.put("driverName", entry.getKey());
                result.put("tripCount", entry.getValue().size());
                double totalVolume = entry.getValue().stream()
                    .mapToDouble(CompletedJob::getConcreteAmount)
                    .sum();
                result.put("totalVolume", totalVolume);
                return result;
            })
            .collect(java.util.stream.Collectors.toList());
    }
}

