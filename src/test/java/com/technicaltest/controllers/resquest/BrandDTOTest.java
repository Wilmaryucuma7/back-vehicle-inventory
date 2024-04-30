package com.technicaltest.controllers.resquest;

import com.technicaltest.controllers.request.BrandDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BrandDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Should create BrandDTO with valid name")
    void shouldCreateBrandDTOWithValidName() {
        String name = "ValidBrandName";

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName(name);

        Set<ConstraintViolation<BrandDTO>> violations = validator.validate(brandDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should not create BrandDTO when name is null")
    void shouldNotCreateBrandDTOWhenNameIsNull() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName(null);

        Set<ConstraintViolation<BrandDTO>> violations = validator.validate(brandDTO);
        assertEquals(1, violations.size());
        assertEquals("no debe estar vacío", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Should not create BrandDTO when name is blank")
    void shouldNotCreateBrandDTOWhenNameIsBlank() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("");

        Set<ConstraintViolation<BrandDTO>> violations = validator.validate(brandDTO);
        assertEquals(1, violations.size());
        assertEquals("no debe estar vacío", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Should not create BrandDTO when name is too long")
    void shouldNotCreateBrandDTOWhenNameIsTooLong() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("ThisNameIsDefinitelyWayTooLongForTheBrandDTO");

        Set<ConstraintViolation<BrandDTO>> violations = validator.validate(brandDTO);
        assertEquals(1, violations.size());
        assertEquals("el tamaño debe estar entre 0 y 30", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Should not create BrandDTO when name contains special characters")
    void shouldNotCreateBrandDTOWhenNameContainsSpecialCharacters() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("BrandName!@#");

        Set<ConstraintViolation<BrandDTO>> violations = validator.validate(brandDTO);
        assertEquals(1, violations.size());
        assertEquals("El nombre de la marca no debe tener caracteres especiales", violations.iterator().next().getMessage());
    }
}