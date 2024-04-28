package com.technicaltest.repositories;

import com.technicaltest.models.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {
    @Query("SELECT v FROM VehicleEntity v WHERE " +
            "v.brandEntity.name LIKE %:search% OR " +
            "v.model LIKE %:search% OR " +
            "v.licensePlate LIKE %:search%")
    Page<VehicleEntity> findByBrandModelOrLicensePlate(@Param("search") String search, Pageable pageable);
}
