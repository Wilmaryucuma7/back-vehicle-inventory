package com.technicaltest.controllers.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {

    @NotBlank
    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚ ]*$", message = "El modelo no debe tener caracteres especiales")
    private String model;

    @NotBlank
    @Size(max = 6)
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]*$", message = "La placa debe empezar por letra y no debe tener caracteres especiales")
    private String licensePlate;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "El color no debe tener caracteres especiales")
    private String color;

    @NotBlank
    @Size(max = 4)
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "El año debe estar entre 1900 y 2099")
    private String year;

    @NotBlank
    private String brandId;
}
