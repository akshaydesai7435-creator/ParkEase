
package com.parkease.controller;

import com.parkease.dto.AllocateSlotRequest;
import com.parkease.dto.ParkingSlotResponse;
import com.parkease.model.ParkingSlot;
import com.parkease.service.ParkingSlotService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.parkease.dto.ParkingSummaryResponse;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final ParkingSlotService parkingSlotService;

    public StaffController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    // View all parking slots
    @GetMapping("/slots")
    public List<ParkingSlotResponse> getAllSlots() {

        return parkingSlotService.getAllSlots()
                .stream()
                .map(ParkingSlotResponse::new)
                .toList();
    }

    // View available parking slots
    @GetMapping("/slots/available")
    public List<ParkingSlotResponse> getAvailableSlots() {

        return parkingSlotService.getAvailableSlots()
                .stream()
                .map(ParkingSlotResponse::new)
                .toList();
    }

    // Allocate a parking slot
    @PostMapping("/allocate")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSlotResponse allocateSlot(
            @Valid @RequestBody AllocateSlotRequest request) {

        ParkingSlot slot = parkingSlotService.allocateSlot(
                request.getVehicleNumber(),
                request.getSlotNumber()
        );

        return new ParkingSlotResponse(slot);
    }

    // Release a vehicle's parking slot
    @PatchMapping("/release/{vehicleNumber}")
    public ParkingSlotResponse releaseSlot(
            @PathVariable String vehicleNumber) {

        ParkingSlot slot = parkingSlotService.releaseSlot(vehicleNumber);

        return new ParkingSlotResponse(slot);
    }

    @GetMapping("/slots/count")
public ParkingSummaryResponse getParkingSummary() {
    return parkingSlotService.getParkingSummary();
}
}
