package com.technicaltest.controllers.resquest;

import com.technicaltest.controllers.request.VehicleDTO;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void shouldNotValidateWhenModelHasSpecialCharacters() {
        VehicleDTO vehicle = new VehicleDTO("!@#model", "A12345", "Blue", "2000", "1");
        Set<ConstraintViolation<VehicleDTO>> violations = validator.validate(vehicle);
        assertEquals(1, violations.size());
        assertEquals("El modelo no debe tener caracteres especiales", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldNotValidateWhenLicensePlateStartsWithNumber() {
        VehicleDTO vehicle = new VehicleDTO("Model", "12345A", "Blue", "2000", "1");
        Set<ConstraintViolation<VehicleDTO>> violations = validator.validate(vehicle);
        assertEquals(1, violations.size());
        assertEquals("La placa debe empezar por letra y no debe tener caracteres especiales", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldNotValidateWhenColorHasSpecialCharacters() {
        VehicleDTO vehicle = new VehicleDTO("Model", "A12345", "Blu@e", "2000", "1");
        Set<ConstraintViolation<VehicleDTO>> violations = validator.validate(vehicle);
        assertEquals(1, violations.size());
        assertEquals("El color no debe tener caracteres especiales", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldNotValidateWhenYearIsNotInRange() {
        VehicleDTO vehicle = new VehicleDTO("Model", "A12345", "Blue", "3000", "1");
        Set<ConstraintViolation<VehicleDTO>> violations = validator.validate(vehicle);
        assertEquals(1, violations.size());
        assertEquals("El año debe estar entre 1900 y 2099", violations.iterator().next().getMessage());
    }

    @Test
    public void shouldValidateWhenAllFieldsAreCorrect() {
        VehicleDTO vehicle = new VehicleDTO("Model", "A12345", "Blue", "2000", "1");
        Set<ConstraintViolation<VehicleDTO>> violations = validator.validate(vehicle);
        assertTrue(violations.isEmpty());
    }
}
