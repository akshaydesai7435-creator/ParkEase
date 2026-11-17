package com.parkease.dto;

public record ParkingSummaryResponse(
        long totalSlots,
        long availableSlots,
        long occupiedSlots
) {}