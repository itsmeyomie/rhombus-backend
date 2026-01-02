package com.rhombus.concrete.controller;

import com.rhombus.concrete.entity.CompletedJob;
import com.rhombus.concrete.service.CompletedJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/completed")
@CrossOrigin(origins = "http://localhost:4200")
public class CompletedJobController {
    @Autowired
    private CompletedJobService completedService;

    @GetMapping
    public ResponseEntity<List<CompletedJob>> getAllCompleted() {
        return ResponseEntity.ok(completedService.getAllCompleted());
    }

    @PostMapping
    public ResponseEntity<CompletedJob> createCompletedJob(@RequestBody CompletedJob job) {
        try {
            // Ensure timeCompleted is set if not provided
            if (job.getTimeCompleted() == null) {
                job.setTimeCompleted(java.time.LocalDateTime.now());
            }
            return ResponseEntity.ok(completedService.createCompletedJob(job));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/site/{siteName}")
    public ResponseEntity<List<CompletedJob>> getBySite(@PathVariable String siteName) {
        return ResponseEntity.ok(completedService.findBySite(siteName));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<CompletedJob>> getByDate(@PathVariable String date) {
        return ResponseEntity.ok(completedService.findByDate(date));
    }

    @GetMapping("/daterange")
    public ResponseEntity<List<CompletedJob>> getByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(completedService.findByDateRange(startDate, endDate));
    }

    @GetMapping("/truck/{truckRegistration}")
    public ResponseEntity<List<CompletedJob>> getByTruck(@PathVariable String truckRegistration) {
        return ResponseEntity.ok(completedService.findByTruckRegistration(truckRegistration));
    }

    @GetMapping("/truck/{truckRegistration}/maxtrip")
    public ResponseEntity<Integer> getMaxTripNumber(@PathVariable String truckRegistration) {
        return ResponseEntity.ok(completedService.getMaxTripNumberForTruck(truckRegistration));
    }
}

