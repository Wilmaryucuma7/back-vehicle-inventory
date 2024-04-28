package com.technicaltest.services;

import com.technicaltest.controllers.request.BrandDTO;
import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.models.BrandEntity;
import com.technicaltest.repositories.BrandRepository;
import com.technicaltest.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class BrandServiceTest {

    @InjectMocks
    private BrandService brandService;

    @Mock
    private BrandRepository brandRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should update brand when brand exists")
    void shouldUpdateBrandWhenBrandExists() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("Updated Brand");

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("Existing Brand");

        when(brandRepository.findById(anyString())).thenReturn(Optional.of(brandEntity));

        ResponseDTO response = brandService.updateBrand("1", brandDTO);

        assertEquals(Constants.SUCCESS, response.getResponse());
        assertEquals(false, response.getError());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when brand does not exist")
    void shouldThrowEntityNotFoundExceptionWhenBrandDoesNotExist() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("Updated Brand");

        when(brandRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> brandService.updateBrand("1", brandDTO));
    }
}