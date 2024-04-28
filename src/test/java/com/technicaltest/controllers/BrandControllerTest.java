package com.technicaltest.controllers;
import com.technicaltest.controllers.request.BrandDTO;
import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.models.BrandEntity;
import com.technicaltest.services.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BrandControllerTest {

    @InjectMocks
    private BrandController brandController;

    @Mock
    private BrandService brandService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return brands when brands exist")
    void shouldReturnBrandsWhenBrandsExist() {
        ResponseDTO responseDTO = ResponseDTO.builder().response(Collections.singletonList(new BrandEntity())).error(false).build();
        when(brandService.getBrands()).thenReturn(responseDTO);

        ResponseEntity<Object> response = brandController.getBrands();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(brandService, times(1)).getBrands();
    }

    @Test
    @DisplayName("Should add brand when valid brand is provided")
    void shouldAddBrandWhenValidBrandIsProvided() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("New Brand");

        when(brandService.addBrand(any(BrandDTO.class))).thenReturn(new ResponseDTO());

        ResponseEntity<ResponseDTO> response = brandController.addBrand(brandDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(brandService, times(1)).addBrand(any(BrandDTO.class));
    }

    @Test
    @DisplayName("Should update brand when valid id and brand are provided")
    void shouldUpdateBrandWhenValidIdAndBrandAreProvided() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("Updated Brand");

        when(brandService.updateBrand(anyString(), any(BrandDTO.class))).thenReturn(new ResponseDTO());

        ResponseEntity<ResponseDTO> response = brandController.updateBrand("1", brandDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(brandService, times(1)).updateBrand(anyString(), any(BrandDTO.class));
    }

    @Test
    @DisplayName("Should delete brand when valid id is provided")
    void shouldDeleteBrandWhenValidIdIsProvided() {
        when(brandService.deleteBrand(anyString())).thenReturn(new ResponseDTO());

        ResponseEntity<ResponseDTO> response = brandController.deleteBrand("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(brandService, times(1)).deleteBrand(anyString());
    }
}