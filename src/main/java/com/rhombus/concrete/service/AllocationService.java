package com.rhombus.concrete.service;

import com.rhombus.concrete.entity.AllocatedTruck;
import com.rhombus.concrete.repository.AllocatedTruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AllocationService {
    @Autowired
    private AllocatedTruckRepository allocatedRepository;

    public List<AllocatedTruck> getAllAllocated() {
        return allocatedRepository.findAllByOrderByTimeAllocatedDesc();
    }

    public AllocatedTruck createAllocation(AllocatedTruck allocation) {
        return allocatedRepository.save(allocation);
    }

    public Optional<AllocatedTruck> findByTruckRegistration(String truckRegistration) {
        return allocatedRepository.findByTruckRegistration(truckRegistration);
    }

    public List<AllocatedTruck> findBySite(String siteName) {
        return allocatedRepository.findBySiteName(siteName);
    }

    public List<AllocatedTruck> findByStatus(String status) {
        return allocatedRepository.findByStatus(status);
    }

    public AllocatedTruck updateAllocation(AllocatedTruck allocation) {
        return allocatedRepository.save(allocation);
    }

    public void deleteAllocation(Long id) {
        allocatedRepository.deleteById(id);
    }

    public List<AllocatedTruck> findByDateRange(String startDate, String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
        return allocatedRepository.findByTimeAllocatedBetween(start, end);
    }
}

