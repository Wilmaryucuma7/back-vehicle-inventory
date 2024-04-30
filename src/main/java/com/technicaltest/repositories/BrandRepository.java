package com.technicaltest.repositories;

import com.technicaltest.models.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
* BrandRepository interface for performing operations on the BrandEntity table in the database.
* This interface extends JpaRepository which provides JPA related methods like save(), findOne(), findAll(), etc.
*
* @author Wilmaryucuma7
* @version 1.0
*
*/
@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, String>{

        /**
         * Method to find a BrandEntity by its name.
         *
         * @param name The name of the brand to be found.
         * @return An Optional that can contain the BrandEntity if it exists, or be empty if it does not.
         */
        Optional<BrandEntity> findByName(String name);

}
