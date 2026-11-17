
package com.parkease.repository;

import com.parkease.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    List<ParkingSlot> findByStatus(String status);

    Optional<ParkingSlot> findByVehicleNumber(String vehicleNumber);

    Optional<ParkingSlot> findBySlotNumber(String slotNumber);

    boolean existsByVehicleNumber(String vehicleNumber);

    long countByStatus(String status);
}
