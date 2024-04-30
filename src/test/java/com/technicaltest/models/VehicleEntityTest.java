package com.technicaltest.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VehicleEntityTest {

    @Test
    @DisplayName("Should create VehicleEntity successfully when all fields are valid")
    void shouldCreateVehicleEntitySuccessfullyWhenAllFieldsAreValid() {
        BrandEntity brandEntity = new BrandEntity();
        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .id("123")
                .model("Model")
                .licensePlate("ABC123")
                .color("Blue")
                .year("2022")
                .brandEntity(brandEntity)
                .build();

        assertNotNull(vehicleEntity);
        assertEquals("123", vehicleEntity.getId());
        assertEquals("Model", vehicleEntity.getModel());
        assertEquals("ABC123", vehicleEntity.getLicensePlate());
        assertEquals("Blue", vehicleEntity.getColor());
        assertEquals("2022", vehicleEntity.getYear());
        assertEquals(brandEntity, vehicleEntity.getBrandEntity());
    }

    @Test
    @DisplayName("Should create VehicleEntity successfully when only required fields are valid")
    void shouldCreateVehicleEntitySuccessfullyWhenOnlyRequiredFieldsAreValid() {
        BrandEntity brandEntity = new BrandEntity();
        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .id("123")
                .model("Model")
                .licensePlate("ABC123")
                .color("Blue")
                .year("2022")
                .brandEntity(brandEntity)
                .build();

        assertNotNull(vehicleEntity);
        assertEquals("123", vehicleEntity.getId());
        assertEquals("Model", vehicleEntity.getModel());
        assertEquals("ABC123", vehicleEntity.getLicensePlate());
        assertEquals("Blue", vehicleEntity.getColor());
        assertEquals("2022", vehicleEntity.getYear());
        assertEquals(brandEntity, vehicleEntity.getBrandEntity());
    }
}