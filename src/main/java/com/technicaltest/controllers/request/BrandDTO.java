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
public class BrandDTO {

    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚ ]*$", message = "El nombre de la marca no debe tener caracteres especiales")
    @NotBlank
    @Size(max = 30)
    private String name;
}
