package com.technicaltest.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleResponseDTO {

        private String id;
        private String model;
        private String licensePlate;
        private String color;
        private String year;
        private BrandResponseDTO brand;
}
