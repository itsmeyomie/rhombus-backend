package com.rhombus.concrete.service;

import com.rhombus.concrete.entity.TruckQueue;
import com.rhombus.concrete.entity.CompletedJob;
import com.rhombus.concrete.repository.TruckQueueRepository;
import com.rhombus.concrete.repository.CompletedJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QueueService {
    @Autowired
    private TruckQueueRepository queueRepository;
    
    @Autowired
    private CompletedJobRepository completedJobRepository;

    public List<TruckQueue> getAllQueue() {
        List<TruckQueue> queue = queueRepository.findAllByOrderByTimeInQueueAsc();
        // Update turnaround times for all queue items
        queue.forEach(this::updateTurnaroundTime);
        return queue;
    }

    public TruckQueue addToQueue(TruckQueue queue) {
        queue.setTruckRegistration(queue.getTruckRegistration().toUpperCase());
        TruckQueue saved = queueRepository.save(queue);
        updateTurnaroundTime(saved);
        return saved;
    }

    public boolean removeFromQueue(String truckRegistration) {
        Optional<TruckQueue> queue = queueRepository.findByTruckRegistration(truckRegistration);
        if (queue.isPresent()) {
            queueRepository.delete(queue.get());
            return true;
        }
        return false;
    }

    public Optional<TruckQueue> findByTruckRegistration(String truckRegistration) {
        Optional<TruckQueue> queue = queueRepository.findByTruckRegistration(truckRegistration);
        queue.ifPresent(this::updateTurnaroundTime);
        return queue;
    }

    /**
     * Update turnaround time based on entry and exit times from completed jobs
     * turnaround_time = exit_time (from completed job) - entry_time (timeInQueue)
     */
    private void updateTurnaroundTime(TruckQueue queueItem) {
        LocalDateTime entryTime = queueItem.getTimeInQueue();
        if (entryTime == null) {
            queueItem.setTurnaroundTime("0h 0m");
            return;
        }
        
        // Check if there's a completed job for this truck with entry time matching queue entry
        // For now, we'll use the most recent completed job for this truck
        List<CompletedJob> completedJobs = completedJobRepository.findByTruckRegistration(
            queueItem.getTruckRegistration()
        );
        
        if (!completedJobs.isEmpty()) {
            // Find the most recent completed job
            CompletedJob latestJob = completedJobs.stream()
                .filter(job -> job.getTimeAllocated() != null && 
                             job.getTimeAllocated().isAfter(entryTime))
                .max((a, b) -> a.getTimeCompleted().compareTo(b.getTimeCompleted()))
                .orElse(null);
            
            if (latestJob != null && latestJob.getTimeCompleted() != null) {
                // Calculate: exit_time - entry_time
                Duration duration = Duration.between(entryTime, latestJob.getTimeCompleted());
                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;
                queueItem.setTurnaroundTime(String.format("%dh %dm", hours, minutes));
                return;
            }
        }
        
        // If no completed job found, calculate based on current time
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(entryTime, now);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        queueItem.setTurnaroundTime(String.format("%dh %dm", hours, minutes));
    }

    /**
     * Update turnaround time periodically for all queue items
     */
    @Scheduled(fixedRate = 60000) // Update every minute
    public void updateAllTurnaroundTimes() {
        List<TruckQueue> queue = queueRepository.findAll();
        queue.forEach(this::updateTurnaroundTime);
        queueRepository.saveAll(queue);
    }
}



