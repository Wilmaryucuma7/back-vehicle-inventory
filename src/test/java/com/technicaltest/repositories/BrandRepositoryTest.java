package com.technicaltest.repositories;

import com.technicaltest.models.BrandEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandRepositoryTest {

    @Mock
    private BrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find BrandEntity by name")
    void shouldFindBrandEntityByName() {
        String brandName = "BrandName";
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName(brandName);

        when(brandRepository.findByName(brandName)).thenReturn(Optional.of(brandEntity));

        Optional<BrandEntity> foundBrandEntity = brandRepository.findByName(brandName);

        verify(brandRepository, times(1)).findByName(brandName);
        assertTrue(foundBrandEntity.isPresent());
        assertEquals(brandName, foundBrandEntity.get().getName());
    }

    @Test
    @DisplayName("Should not find BrandEntity by non-existing name")
    void shouldNotFindBrandEntityByNonExistingName() {
        String nonExistingBrandName = "NonExistingBrandName";

        when(brandRepository.findByName(nonExistingBrandName)).thenReturn(Optional.empty());

        Optional<BrandEntity> foundBrandEntity = brandRepository.findByName(nonExistingBrandName);

        verify(brandRepository, times(1)).findByName(nonExistingBrandName);
        assertFalse(foundBrandEntity.isPresent());
    }
}