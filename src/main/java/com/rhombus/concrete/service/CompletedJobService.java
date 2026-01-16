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
            // Count trips for today
            LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
            long dailyTrips = completedRepository.findByTruckRegistration(job.getTruckRegistration())
                .stream()
                .filter(j -> j.getTimeCompleted() != null && 
                           j.getTimeCompleted().isAfter(todayStart) && 
                           j.getTimeCompleted().isBefore(todayEnd))
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
}

