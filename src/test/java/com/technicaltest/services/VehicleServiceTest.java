package com.technicaltest.services;


import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.controllers.request.VehicleDTO;
import com.technicaltest.exceptions.GlobalException;
import com.technicaltest.models.BrandEntity;
import com.technicaltest.models.VehicleEntity;
import com.technicaltest.repositories.BrandRepository;
import com.technicaltest.repositories.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    private static final String VEHICLES = "vehicles";
    private static final String TOTAL_PAGES = "totalPages";

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @InjectMocks
    private BrandService brandService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should throw EntityNotFoundException when vehicle does not exist")
    void shouldThrowEntityNotFoundExceptionWhenVehicleDoesNotExist() {
        String id = UUID.randomUUID().toString();
        VehicleDTO vehicleDTO = new VehicleDTO("Model", "A12345", "Blue", "2000", "1");

        when(vehicleRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> vehicleService.updateVehicle(id, vehicleDTO));
    }


    @Test
    @DisplayName("Should throw DataIntegrityViolationException when vehicle with same license plate already exists")
    void shouldThrowDataIntegrityViolationExceptionWhenVehicleWithSameLicensePlateAlreadyExists() {
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setLicensePlate("ABC123");
        vehicleDTO.setBrandId(UUID.randomUUID().toString());

        when(vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> vehicleService.addVehicle(vehicleDTO));
    }



    @Test
    @DisplayName("Should delete vehicle successfully when vehicle exists")
    void shouldDeleteVehicleSuccessfullyWhenVehicleExists() {
        String id = UUID.randomUUID().toString();

        when(vehicleRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> vehicleService.deleteVehicle(id));
        verify(vehicleRepository, times(1)).deleteById(id);
    }


    @Test
    @DisplayName("Should return vehicles successfully when search for vehicles does not fail")
    void shouldReturnVehiclesSuccessfullyWhenSearchForVehiclesDoesNotFail() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<VehicleEntity> vehiclePage = new PageImpl<>(new ArrayList<>());
        when(vehicleRepository.findByBrandModelOrLicensePlate(anyString(), any(Pageable.class))).thenReturn(vehiclePage);

        // Act
        ResponseDTO responseDTO = vehicleService.searchVehicles("search", 0, 10);

        // Assert
        assertFalse(responseDTO.getError());
        assertEquals(vehiclePage.getContent(), ((Map) responseDTO.getResponse()).get(VEHICLES));
        assertEquals(vehiclePage.getTotalPages() - 1, ((Map) responseDTO.getResponse()).get(TOTAL_PAGES));
    }

    @Test
    @DisplayName("Should return empty list when no vehicles found")
    void shouldReturnEmptyListWhenNoVehiclesFound() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<VehicleEntity> vehiclePage = new PageImpl<>(Collections.emptyList());
        when(vehicleRepository.findByBrandModelOrLicensePlate(anyString(), any(Pageable.class))).thenReturn(vehiclePage);

        // Act
        ResponseDTO responseDTO = vehicleService.searchVehicles("search", 0, 10);

        // Assert
        assertFalse(responseDTO.getError());
        assertTrue(((List) ((Map) responseDTO.getResponse()).get(VEHICLES)).isEmpty());
        assertEquals(vehiclePage.getTotalPages() - 1, ((Map) responseDTO.getResponse()).get(TOTAL_PAGES));
    }

    @Test
    @DisplayName("Should throw exception when search for vehicles fails")
    void shouldThrowExceptionWhenSearchForVehiclesFails() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(vehicleRepository.findByBrandModelOrLicensePlate(anyString(), any(Pageable.class))).thenThrow(new IllegalArgumentException());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> vehicleService.searchVehicles("search", 0, 10));
    }
}