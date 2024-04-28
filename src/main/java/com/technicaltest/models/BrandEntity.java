package com.technicaltest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "brands")
public class BrandEntity {
    @Id
    @Column(name = "brand_id", nullable = false, length = 60)
    private String id;

    @Column(unique = true, name = "brand_name", nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "brandEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<VehicleEntity> vehicles;
}
