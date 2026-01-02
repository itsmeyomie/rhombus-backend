package com.rhombus.concrete.repository;

import com.rhombus.concrete.entity.TruckQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface TruckQueueRepository extends JpaRepository<TruckQueue, Long> {
    Optional<TruckQueue> findByTruckRegistration(String truckRegistration);
    List<TruckQueue> findAllByOrderByTimeInQueueAsc();
}



