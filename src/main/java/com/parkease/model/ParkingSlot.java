
package com.parkease.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "parking_slots",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "slot_number"),
        @UniqueConstraint(columnNames = "vehicle_number")
    }
)
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slot_number", nullable = false)
    private String slotNumber;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String zone;

    @Column(nullable = false)
    private String status = "AVAILABLE";

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    public ParkingSlot() {
    }

    public ParkingSlot(String slotNumber, Integer floor, String zone) {
        this.slotNumber = slotNumber;
        this.floor = floor;
        this.zone = zone;
        this.status = "AVAILABLE";
    }

    public Long getId() {
        return id;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
