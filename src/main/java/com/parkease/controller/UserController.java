
package com.parkease.controller;

import com.parkease.dto.ParkingSlotResponse;
import com.parkease.service.ParkingSlotService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ParkingSlotService parkingSlotService;

    public UserController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    // Find parking details using vehicle registration number
    @GetMapping("/parking/{vehicleNumber}")
    public ParkingSlotResponse getParkingDetails(
            @PathVariable String vehicleNumber) {

        return new ParkingSlotResponse(
                parkingSlotService.getVehicleParkingDetails(vehicleNumber)
        );
    }
}
