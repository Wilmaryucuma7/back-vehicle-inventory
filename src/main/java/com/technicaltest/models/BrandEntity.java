package com.technicaltest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * BrandEntity class represents the "brands" table in the database.
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
@Table(name = "brands")
public class BrandEntity {
    /**
     * The ID of the brand. This field corresponds to the "brand_id" column in the "brands" table.
     */
    @Id
    @Column(name = "brand_id", nullable = false, length = 60)
    private String id;

    /**
     * The name of the brand. This field corresponds to the "brand_name" column in the "brands" table.
     */
    @Column(unique = true, name = "brand_name", nullable = false, length = 30)
    private String name;

    /**
     * The creation date of the brand. This field corresponds to the "brand_created_date" column in the "brands" table.
     */
    @CreationTimestamp()
    @Column(name = "brand_created_date", nullable = false, updatable = false)
    private LocalDateTime brandCreatedDate;

    /**
     * The list of vehicles associated with the brand. This field corresponds to the "vehicles" table in the database.
     * It is annotated with @OneToMany, indicating that it is a one-to-many relationship with the VehicleEntity.
     */
    @OneToMany(mappedBy = "brandEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<VehicleEntity> vehicles;
}