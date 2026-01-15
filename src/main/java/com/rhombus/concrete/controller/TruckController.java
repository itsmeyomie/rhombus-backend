package com.rhombus.concrete.controller;

import com.rhombus.concrete.entity.Truck;
import com.rhombus.concrete.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trucks")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.2:4200"})
public class TruckController {
    @Autowired
    private TruckService truckService;

    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        return ResponseEntity.ok(truckService.getAllTrucks());
    }

    @PostMapping
    public ResponseEntity<Truck> createTruck(@RequestBody Truck truck) {
        return ResponseEntity.ok(truckService.createTruck(truck));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        truckService.deleteTruck(id);
        return ResponseEntity.ok().build();
    }
}



