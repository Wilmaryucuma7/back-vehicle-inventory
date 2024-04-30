package com.technicaltest.repositories;

import com.technicaltest.models.VehicleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleRepositoryTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find VehicleEntity by brand, model or license plate")
    void shouldFindVehicleEntityByBrandModelOrLicensePlate() {
        String search = "BrandName";
        PageRequest pageable = PageRequest.of(0, 10);
        Page<VehicleEntity> vehiclePage = mock(Page.class);

        when(vehicleRepository.findByBrandModelOrLicensePlate(search, pageable)).thenReturn(vehiclePage);

        Page<VehicleEntity> foundVehiclePage = vehicleRepository.findByBrandModelOrLicensePlate(search, pageable);

        verify(vehicleRepository, times(1)).findByBrandModelOrLicensePlate(search, pageable);
        assertEquals(vehiclePage, foundVehiclePage);
    }

    @Test
    @DisplayName("Should check if VehicleEntity exists by license plate")
    void shouldCheckVehicleEntityExistsByLicensePlate() {
        String licensePlate = "ABC123";

        when(vehicleRepository.existsByLicensePlate(licensePlate)).thenReturn(true);

        boolean exists = vehicleRepository.existsByLicensePlate(licensePlate);

        verify(vehicleRepository, times(1)).existsByLicensePlate(licensePlate);
        assertTrue(exists);
    }

    @Test
    @DisplayName("Should check if VehicleEntity does not exist by license plate")
    void shouldCheckVehicleEntityDoesNotExistByLicensePlate() {
        String licensePlate = "NonExistingLicensePlate";

        when(vehicleRepository.existsByLicensePlate(licensePlate)).thenReturn(false);

        boolean exists = vehicleRepository.existsByLicensePlate(licensePlate);

        verify(vehicleRepository, times(1)).existsByLicensePlate(licensePlate);
        assertFalse(exists);
    }
}