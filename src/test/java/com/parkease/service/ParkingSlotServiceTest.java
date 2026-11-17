
package com.parkease.service;

import com.parkease.model.ParkingSlot;
import com.parkease.repository.ParkingSlotRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingSlotServiceTest {

    @Mock
    private ParkingSlotRepository parkingSlotRepository;

    @InjectMocks
    private ParkingSlotService parkingSlotService;

    @Test
    void shouldReturnAllSlots() {
        ParkingSlot slot = mock(ParkingSlot.class);

        when(parkingSlotRepository.findAll())
                .thenReturn(List.of(slot));

        List<ParkingSlot> result = parkingSlotService.getAllSlots();

        assertEquals(1, result.size());
        verify(parkingSlotRepository).findAll();
    }

    @Test
    void shouldReturnOnlyAvailableSlots() {
        ParkingSlot slot = mock(ParkingSlot.class);

        when(parkingSlotRepository.findByStatus("AVAILABLE"))
                .thenReturn(List.of(slot));

        List<ParkingSlot> result = parkingSlotService.getAvailableSlots();

        assertEquals(1, result.size());
        verify(parkingSlotRepository).findByStatus("AVAILABLE");
    }

    @Test
    void shouldAllocateSlotSuccessfully() {
        ParkingSlot slot = mock(ParkingSlot.class);

        when(slot.getStatus()).thenReturn("AVAILABLE");

        when(parkingSlotRepository.existsByVehicleNumber("KA05AB1234"))
                .thenReturn(false);

        when(parkingSlotRepository.findBySlotNumber("A101"))
                .thenReturn(Optional.of(slot));

        when(parkingSlotRepository.save(slot))
                .thenReturn(slot);

        ParkingSlot result =
                parkingSlotService.allocateSlot("KA05AB1234", "A101");

        verify(slot).setVehicleNumber("KA05AB1234");
        verify(slot).setStatus("OCCUPIED");
        verify(parkingSlotRepository).save(slot);

        assertSame(slot, result);
    }

    @Test
    void shouldRejectDuplicateVehicleAllocation() {
        when(parkingSlotRepository.existsByVehicleNumber("KA05AB1234"))
                .thenReturn(true);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> parkingSlotService.allocateSlot("KA05AB1234", "A102")
        );

        assertEquals(
                "This vehicle is already parked.",
                exception.getMessage()
        );

        verify(parkingSlotRepository, never()).save(any());
    }

    @Test
    void shouldRejectAllocationToOccupiedSlot() {
        ParkingSlot occupiedSlot = mock(ParkingSlot.class);

        when(occupiedSlot.getStatus()).thenReturn("OCCUPIED");

        when(parkingSlotRepository.existsByVehicleNumber("KA05AB1234"))
                .thenReturn(false);

        when(parkingSlotRepository.findBySlotNumber("A101"))
                .thenReturn(Optional.of(occupiedSlot));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> parkingSlotService.allocateSlot("KA05AB1234", "A101")
        );

        assertEquals(
                "This parking slot is already occupied.",
                exception.getMessage()
        );

        verify(parkingSlotRepository, never()).save(any());
    }

    @Test
    void shouldRejectAllocationToNonexistentSlot() {
        when(parkingSlotRepository.existsByVehicleNumber("KA05AB1234"))
                .thenReturn(false);

        when(parkingSlotRepository.findBySlotNumber("Z999"))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> parkingSlotService.allocateSlot("KA05AB1234", "Z999")
        );

        verify(parkingSlotRepository, never()).save(any());
    }

    @Test
    void shouldReleaseVehicleSuccessfully() {
        ParkingSlot occupiedSlot = mock(ParkingSlot.class);

        when(parkingSlotRepository.findByVehicleNumber("KA05AB1234"))
                .thenReturn(Optional.of(occupiedSlot));

        when(parkingSlotRepository.save(occupiedSlot))
                .thenReturn(occupiedSlot);

        ParkingSlot result =
                parkingSlotService.releaseSlot("KA05AB1234");

        verify(occupiedSlot).setVehicleNumber(null);
        verify(occupiedSlot).setStatus("AVAILABLE");
        verify(parkingSlotRepository).save(occupiedSlot);

        assertSame(occupiedSlot, result);
    }

    @Test
    void shouldRejectReleaseOfNonexistentVehicle() {
        when(parkingSlotRepository.findByVehicleNumber("KA99ZZ9999"))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> parkingSlotService.releaseSlot("KA99ZZ9999")
        );

        verify(parkingSlotRepository, never()).save(any());
    }

    @Test
    void shouldReturnVehicleParkingDetails() {
        ParkingSlot occupiedSlot = mock(ParkingSlot.class);

        when(parkingSlotRepository.findByVehicleNumber("KA05AB1234"))
                .thenReturn(Optional.of(occupiedSlot));

        ParkingSlot result =
                parkingSlotService.getVehicleParkingDetails("KA05AB1234");

        assertSame(occupiedSlot, result);

        verify(parkingSlotRepository)
                .findByVehicleNumber("KA05AB1234");
    }

    @Test
    void shouldRejectLookupForNonexistentVehicle() {
        when(parkingSlotRepository.findByVehicleNumber("KA99ZZ9999"))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> parkingSlotService.getVehicleParkingDetails("KA99ZZ9999")
        );
    }
}
