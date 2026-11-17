
package com.parkease.dto;

import com.parkease.model.ParkingSlot;

public class ParkingSlotResponse {

    private Long id;
    private String slotNumber;
    private Integer floor;
    private String zone;
    private String status;
    private String vehicleNumber;

    public ParkingSlotResponse(ParkingSlot slot) {
        this.id = slot.getId();
        this.slotNumber = slot.getSlotNumber();
        this.floor = slot.getFloor();
        this.zone = slot.getZone();
        this.status = slot.getStatus();
        this.vehicleNumber = slot.getVehicleNumber();
    }

    public Long getId() {
        return id;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public String getZone() {
        return zone;
    }

    public String getStatus() {
        return status;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
}
