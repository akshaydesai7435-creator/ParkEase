
package com.parkease.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AllocateSlotRequest {

    @NotBlank(message = "Vehicle number is required")
    @Pattern(
        regexp = "^[A-Za-z0-9-]{5,15}$",
        message = "Invalid vehicle number"
    )
    private String vehicleNumber;

    @NotBlank(message = "Slot number is required")
    private String slotNumber;

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }
}
