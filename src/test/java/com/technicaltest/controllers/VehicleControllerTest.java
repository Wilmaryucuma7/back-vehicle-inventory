package com.technicaltest.controllers;

import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.controllers.request.VehicleDTO;
import com.technicaltest.services.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    @InjectMocks
    VehicleController vehicleController;

    @Mock
    VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return correct response when getting vehicles")
    void shouldReturnCorrectResponseWhenGettingVehicles() {
        ResponseDTO responseDTO = new ResponseDTO();
        when(vehicleService.getVehicles(anyInt(), anyInt(), anyString(), anyString())).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = vehicleController.getVehicles("brand", "asc", 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    @DisplayName("Should return correct response when searching vehicles")
    void shouldReturnCorrectResponseWhenSearchingVehicles() {
        ResponseDTO responseDTO = new ResponseDTO();
        when(vehicleService.searchVehicles(anyString(), anyInt(), anyInt())).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = vehicleController.searchVehicles("searchTerm", 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    @DisplayName("Should return correct response when getting vehicle by id")
    void shouldReturnCorrectResponseWhenGettingVehicleById() {
        ResponseDTO responseDTO = new ResponseDTO();
        when(vehicleService.getVehicleById(anyString())).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = vehicleController.getVehicle("id");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    @DisplayName("Should return correct response when adding vehicle")
    void shouldReturnCorrectResponseWhenAddingVehicle() {
        ResponseDTO responseDTO = new ResponseDTO();
        VehicleDTO vehicleDTO = new VehicleDTO();
        when(vehicleService.addVehicle(vehicleDTO)).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = vehicleController.addVehicle(vehicleDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    @DisplayName("Should return correct response when updating vehicle")
    void shouldReturnCorrectResponseWhenUpdatingVehicle() {
        ResponseDTO responseDTO = new ResponseDTO();
        VehicleDTO vehicleDTO = new VehicleDTO();
        when(vehicleService.updateVehicle(anyString(), eq(vehicleDTO))).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = vehicleController.updateVehicle("id", vehicleDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    @DisplayName("Should return correct response when deleting vehicle")
    void shouldReturnCorrectResponseWhenDeletingVehicle() {
        ResponseDTO responseDTO = new ResponseDTO();
        when(vehicleService.deleteVehicle(anyString())).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = vehicleController.deleteVehicle("id");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }
}