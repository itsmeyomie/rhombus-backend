package com.rhombus.concrete.controller;

import com.rhombus.concrete.entity.PerformanceMetrics;
import com.rhombus.concrete.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/metrics")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.2:4200"})
public class MetricsController {
    @Autowired
    private MetricsService metricsService;

    @GetMapping("/{date}")
    public ResponseEntity<PerformanceMetrics> getMetrics(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        PerformanceMetrics metrics = metricsService.getOrCalculateMetricsForDate(localDate);
        return ResponseEntity.ok(metrics);
    }

    @PostMapping
    public ResponseEntity<PerformanceMetrics> saveMetrics(@RequestBody PerformanceMetrics metrics) {
        return ResponseEntity.ok(metricsService.saveOrUpdateMetrics(metrics));
    }
}



