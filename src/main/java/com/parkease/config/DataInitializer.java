
package com.parkease.config;

import com.parkease.model.ParkingSlot;
import com.parkease.repository.ParkingSlotRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeParkingSlots(ParkingSlotRepository repository) {
        return args -> {

            if (repository.count() == 0) {

                List<ParkingSlot> slots = List.of(
                    new ParkingSlot("A101", 1, "A"),
                    new ParkingSlot("A102", 1, "A"),
                    new ParkingSlot("A103", 1, "A"),
                    new ParkingSlot("A104", 1, "A"),
                    new ParkingSlot("A105", 1, "A"),

                    new ParkingSlot("B201", 2, "B"),
                    new ParkingSlot("B202", 2, "B"),
                    new ParkingSlot("B203", 2, "B"),
                    new ParkingSlot("B204", 2, "B"),
                    new ParkingSlot("B205", 2, "B")
                );

                repository.saveAll(slots);

                System.out.println("ParkEase: 10 parking slots initialized.");
            } else {
                System.out.println("ParkEase: Existing slots found. Skipping initialization.");
            }
        };
    }
}
