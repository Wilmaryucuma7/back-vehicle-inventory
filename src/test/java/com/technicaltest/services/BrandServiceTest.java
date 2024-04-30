package com.technicaltest.services;

import com.technicaltest.controllers.request.BrandDTO;
import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.exceptions.EntityAlreadyExistsException;
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
import java.util.Arrays;
import java.util.List;
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
    @DisplayName("Should return brands when brands exist")
    void shouldReturnBrandsWhenBrandsExist() {
        when(brandRepository.findAll()).thenReturn(Arrays.asList(new BrandEntity(), new BrandEntity()));

        ResponseDTO response = brandService.getBrands();

        assertEquals(false, response.getError());
        assertEquals(2, ((List) response.getResponse()).size());
    }

    @Test
    @DisplayName("Should return brand when brand exists")
    void shouldReturnBrandWhenBrandExists() {
        when(brandRepository.findById(anyString())).thenReturn(Optional.of(new BrandEntity()));

        ResponseDTO response = brandService.getBrandById("1");

        assertEquals(false, response.getError());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when brand does not exist")
    void shouldThrowEntityNotFoundExceptionWhenBrandDoesNotExist() {
        when(brandRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> brandService.getBrandById("1"));
    }

    @Test
    @DisplayName("Should add brand when brand does not exist")
    void shouldAddBrandWhenBrandDoesNotExist() {
        when(brandRepository.findByName(anyString())).thenReturn(Optional.empty());

        ResponseDTO response = brandService.addBrand(new BrandDTO());

        assertEquals(Constants.SUCCESS, response.getResponse());
        assertEquals(false, response.getError());
    }

    @Test
    @DisplayName("Should throw EntityAlreadyExistsException when brand already exists")
    void shouldThrowEntityAlreadyExistsExceptionWhenBrandAlreadyExists() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("Existing Brand");

        when(brandRepository.findByName(brandDTO.getName())).thenReturn(Optional.of(new BrandEntity()));

        assertThrows(EntityAlreadyExistsException.class, () -> brandService.addBrand(brandDTO));
    }

    @Test
    @DisplayName("Should update brand when brand exists")
    void shouldUpdateBrandWhenBrandExists() {
        when(brandRepository.findById(anyString())).thenReturn(Optional.of(new BrandEntity()));

        ResponseDTO response = brandService.updateBrand("1", new BrandDTO());

        assertEquals(Constants.SUCCESS, response.getResponse());
        assertEquals(false, response.getError());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when updating brand that does not exist")
    void shouldThrowEntityNotFoundExceptionWhenUpdatingBrandThatDoesNotExist() {
        when(brandRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> brandService.updateBrand("1", new BrandDTO()));
    }

    @Test
    @DisplayName("Should delete brand when brand exists")
    void shouldDeleteBrandWhenBrandExists() {
        when(brandRepository.existsById(anyString())).thenReturn(true);

        ResponseDTO response = brandService.deleteBrand("1");

        assertEquals(Constants.SUCCESS, response.getResponse());
        assertEquals(false, response.getError());
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting brand that does not exist")
    void shouldThrowEntityNotFoundExceptionWhenDeletingBrandThatDoesNotExist() {
        when(brandRepository.existsById(anyString())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> brandService.deleteBrand("1"));
    }
}