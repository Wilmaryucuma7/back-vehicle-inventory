package com.technicaltest.repositories;

import com.technicaltest.models.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, String>{

        Optional<BrandEntity> findByName(String name);

}
