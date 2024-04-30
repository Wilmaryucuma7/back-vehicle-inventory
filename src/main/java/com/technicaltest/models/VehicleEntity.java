package com.technicaltest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vehicles")
public class VehicleEntity {
    @Id
    @Column(name = "vehicle_id", nullable = false, length = 60)
    private String id;

    @Column(name = "vehicle_model", nullable = false, length = 30)
    private String model;

    @Column(name = "vehicle_license_plate", nullable = false, unique = true, length = 6)
    private String licensePlate;

    @Column(name = "vehicle_color", nullable = false, length = 20)
    private String color;

    @Column(name = "vehicle_year", nullable = false, length = 4)
    private String year;

    @CreationTimestamp()
    @Column(name = "vehicle_created_date", nullable = false, updatable = false)
    private LocalDateTime vehicleCreatedDate;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brandEntity;

}
