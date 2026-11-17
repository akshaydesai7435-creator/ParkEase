
package com.parkease.service;

import com.parkease.model.ParkingSlot;
import com.parkease.repository.ParkingSlotRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parkease.dto.ParkingSummaryResponse;

import java.util.List;

@Service
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    // Get all parking slots
    @Transactional(readOnly = true)
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    // Get only available parking slots
    @Transactional(readOnly = true)
    public List<ParkingSlot> getAvailableSlots() {
        return parkingSlotRepository.findByStatus("AVAILABLE");
    }

    // Allocate a slot to a vehicle
    @Transactional
    public ParkingSlot allocateSlot(String vehicleNumber, String slotNumber) {

        // Check whether the vehicle is already parked
        if (parkingSlotRepository.existsByVehicleNumber(vehicleNumber)) {
            throw new IllegalStateException(
                "This vehicle is already parked."
            );
        }

        // Find the requested parking slot
        ParkingSlot slot = parkingSlotRepository.findBySlotNumber(slotNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Parking slot not found."
                ));

        // Check whether the slot is available
        if (!"AVAILABLE".equals(slot.getStatus())) {
            throw new IllegalStateException(
                "This parking slot is already occupied."
            );
        }

        // Allocate the slot
        slot.setVehicleNumber(vehicleNumber);
        slot.setStatus("OCCUPIED");

        return parkingSlotRepository.save(slot);
    }

    // Release a vehicle's parking slot
    @Transactional
    public ParkingSlot releaseSlot(String vehicleNumber) {

        ParkingSlot slot = parkingSlotRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Vehicle is not currently parked."
                ));

        slot.setVehicleNumber(null);
        slot.setStatus("AVAILABLE");

        return parkingSlotRepository.save(slot);
    }

    // Find parking details using vehicle number
    @Transactional(readOnly = true)
    public ParkingSlot getVehicleParkingDetails(String vehicleNumber) {

        return parkingSlotRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No parking record found for this vehicle."
                ));
    }

    public ParkingSummaryResponse getParkingSummary() {
    long total = parkingSlotRepository.count();
    long available = parkingSlotRepository.countByStatus("AVAILABLE");
    long occupied = parkingSlotRepository.countByStatus("OCCUPIED");

    return new ParkingSummaryResponse(total, available, occupied);
}
}
