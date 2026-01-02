package com.rhombus.concrete.service;

import com.rhombus.concrete.entity.Truck;
import com.rhombus.concrete.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TruckService {
    @Autowired
    private TruckRepository truckRepository;

    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    public Optional<Truck> getTruckById(Long id) {
        return truckRepository.findById(id);
    }

    public Truck createTruck(Truck truck) {
        truck.setRegistrationNumber(truck.getRegistrationNumber().toUpperCase());
        return truckRepository.save(truck);
    }

    public boolean deleteTruck(Long id) {
        if (truckRepository.existsById(id)) {
            truckRepository.deleteById(id);
            return true;
        }
        return false;
    }
}



