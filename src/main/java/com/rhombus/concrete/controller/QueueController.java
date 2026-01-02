package com.rhombus.concrete.controller;

import com.rhombus.concrete.entity.TruckQueue;
import com.rhombus.concrete.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/queue")
@CrossOrigin(origins = "http://localhost:4200")
public class QueueController {
    @Autowired
    private QueueService queueService;

    @GetMapping
    public ResponseEntity<List<TruckQueue>> getAllQueue() {
        return ResponseEntity.ok(queueService.getAllQueue());
    }

    @PostMapping
    public ResponseEntity<TruckQueue> addToQueue(@RequestBody TruckQueue queue) {
        return ResponseEntity.ok(queueService.addToQueue(queue));
    }

    @DeleteMapping("/{truckRegistration}")
    public ResponseEntity<Void> removeFromQueue(@PathVariable String truckRegistration) {
        queueService.removeFromQueue(truckRegistration);
        return ResponseEntity.ok().build();
    }
}



