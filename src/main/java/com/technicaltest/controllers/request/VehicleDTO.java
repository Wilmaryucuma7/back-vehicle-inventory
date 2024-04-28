package com.technicaltest.controllers.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    private String id;

    @NotBlank
    @Size(max = 30)
    private String model;

    @NotBlank
    @Size(max = 10)
    private String licensePlate;

    @NotBlank
    @Size(max = 20)
    private String color;

    @NotBlank
    @Size(max = 4)
    private String year;

    @NotBlank
    private String brandId;
}
