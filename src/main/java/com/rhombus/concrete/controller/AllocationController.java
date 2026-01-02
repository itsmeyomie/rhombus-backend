package com.rhombus.concrete.controller;

import com.rhombus.concrete.entity.AllocatedTruck;
import com.rhombus.concrete.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/allocated")
@CrossOrigin(origins = "http://localhost:4200")
public class AllocationController {
    @Autowired
    private AllocationService allocationService;

    @GetMapping
    public ResponseEntity<List<AllocatedTruck>> getAllAllocated() {
        return ResponseEntity.ok(allocationService.getAllAllocated());
    }

    @PostMapping
    public ResponseEntity<AllocatedTruck> createAllocation(@RequestBody AllocatedTruck allocation) {
        return ResponseEntity.ok(allocationService.createAllocation(allocation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllocatedTruck> updateAllocation(@PathVariable Long id, @RequestBody AllocatedTruck allocation) {
        allocation.setId(id);
        return ResponseEntity.ok(allocationService.updateAllocation(allocation));
    }

    @GetMapping("/site/{siteName}")
    public ResponseEntity<List<AllocatedTruck>> getBySite(@PathVariable String siteName) {
        return ResponseEntity.ok(allocationService.findBySite(siteName));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AllocatedTruck>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(allocationService.findByStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllocation(@PathVariable Long id) {
        allocationService.deleteAllocation(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/daterange")
    public ResponseEntity<List<AllocatedTruck>> getByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return ResponseEntity.ok(allocationService.findByDateRange(startDate, endDate));
    }
}

