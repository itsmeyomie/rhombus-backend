package com.rhombus.concrete.service;

import com.rhombus.concrete.entity.TruckQueue;
import com.rhombus.concrete.repository.TruckQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QueueService {
    @Autowired
    private TruckQueueRepository queueRepository;

    public List<TruckQueue> getAllQueue() {
        return queueRepository.findAllByOrderByTimeInQueueAsc();
    }

    public TruckQueue addToQueue(TruckQueue queue) {
        queue.setTruckRegistration(queue.getTruckRegistration().toUpperCase());
        return queueRepository.save(queue);
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
        return queueRepository.findByTruckRegistration(truckRegistration);
    }
}



