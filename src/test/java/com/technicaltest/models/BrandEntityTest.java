package com.technicaltest.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BrandEntityTest {

    @Test
    @DisplayName("Should create BrandEntity with provided values")
    void shouldCreateBrandEntityWithProvidedValues() {
        String id = "123";
        String name = "BrandName";
        LocalDateTime brandCreatedDate = LocalDateTime.now();
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setId("456");
        vehicleEntity.setBrandEntity(null);
        vehicleEntity.setColor("Blue");
        vehicleEntity.setModel("Model");
        vehicleEntity.setYear("2022");
        vehicleEntity.setLicensePlate("ABC123");

        BrandEntity brandEntity = BrandEntity.builder()
                .id(id)
                .name(name)
                .brandCreatedDate(brandCreatedDate)
                .vehicles(Collections.singletonList(vehicleEntity))
                .build();

        assertEquals(id, brandEntity.getId());
        assertEquals(name, brandEntity.getName());
        assertEquals(brandCreatedDate, brandEntity.getBrandCreatedDate());
        assertNotNull(brandEntity.getVehicles());
        assertEquals(1, brandEntity.getVehicles().size());
        assertEquals(vehicleEntity, brandEntity.getVehicles().get(0));
    }

    @Test
    @DisplayName("Should create BrandEntity with null vehicles when vehicles are not provided")
    void shouldCreateBrandEntityWithNullVehiclesWhenVehiclesAreNotProvided() {
        String id = "123";
        String name = "BrandName";
        LocalDateTime brandCreatedDate = LocalDateTime.now();

        BrandEntity brandEntity = BrandEntity.builder()
                .id(id)
                .name(name)
                .brandCreatedDate(brandCreatedDate)
                .build();

        assertEquals(id, brandEntity.getId());
        assertEquals(name, brandEntity.getName());
        assertEquals(brandCreatedDate, brandEntity.getBrandCreatedDate());
        assertNull(brandEntity.getVehicles());
    }
}