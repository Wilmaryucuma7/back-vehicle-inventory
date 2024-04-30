package com.technicaltest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * VehicleEntity class represents the "vehicles" table in the database.
 * It includes fields that correspond to the columns in the table.
 * It is annotated with @Entity, indicating that it is a JPA entity.
 * Lombok annotations are used to automatically generate getters, setters, constructors, and builder methods.
 *
 * @author Wilmaryucuma7
 * @version 1.0
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vehicles")
public class VehicleEntity {
    /**
     * The ID of the vehicle. This field corresponds to the "vehicle_id" column in the "vehicles" table.
     */
    @Id
    @Column(name = "vehicle_id", nullable = false, length = 60)
    private String id;

    /**
     * The model of the vehicle. This field corresponds to the "vehicle_model" column in the "vehicles" table.
     */
    @Column(name = "vehicle_model", nullable = false, length = 30)
    private String model;

    /**
     * The license plate of the vehicle. This field corresponds to the "vehicle_license_plate" column in the "vehicles" table.
     */
    @Column(name = "vehicle_license_plate", nullable = false, unique = true, length = 6)
    private String licensePlate;

    /**
     * The color of the vehicle. This field corresponds to the "vehicle_color" column in the "vehicles" table.
     */
    @Column(name = "vehicle_color", nullable = false, length = 20)
    private String color;

    /**
     * The year of the vehicle. This field corresponds to the "vehicle_year" column in the "vehicles" table.
     */
    @Column(name = "vehicle_year", nullable = false, length = 4)
    private String year;

    /**
     * The creation date of the vehicle. This field corresponds to the "vehicle_created_date" column in the "vehicles" table.
     */
    @CreationTimestamp()
    @Column(name = "vehicle_created_date", nullable = false, updatable = false)
    private LocalDateTime vehicleCreatedDate;

    /**
     * The brand of the vehicle. This field corresponds to the "brand_id" column in the "vehicles" table.
     * It is annotated with @ManyToOne, indicating that it is a many-to-one relationship with the BrandEntity.
     */
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brandEntity;

}