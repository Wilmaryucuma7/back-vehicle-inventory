package com.technicaltest.services;

import com.technicaltest.controllers.request.BrandDTO;
import com.technicaltest.controllers.request.BrandResponseDTO;
import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.exceptions.EntityAlreadyExistsException;
import com.technicaltest.models.BrandEntity;
import com.technicaltest.repositories.BrandRepository;
import com.technicaltest.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing brands.
 */
@Service
public class BrandService {
    public static final String UPDATE_BRAND_NOT_FOUND = "Error al actualizar, marca no encontrada";
    public static final String DELETE_BRAND_NOT_FOUND = "Error al eliminar, marca no encontrada";

    private final BrandRepository brandRepository;

    /**
     * Constructor for BrandService.
     *
     * @param brandRepository the brand repository
     */
    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Retrieves all brands.
     *
     * @return a ResponseDTO containing a list of all brands
     */
    public ResponseDTO getBrands() {
        // Retrieve all BrandEntity objects from the repository
        List<BrandEntity> brandEntities = brandRepository.findAll();

        // Convert each BrandEntity to a BrandResponseDTO
        List<BrandResponseDTO> brands = brandEntities.stream()
                .map(brandEntity -> BrandResponseDTO.builder()
                        .id(brandEntity.getId())
                        .name(brandEntity.getName())
                        .build())
                .collect(Collectors.toList());

        // Build and return a ResponseDTO containing the list of BrandResponseDTOs
        return ResponseDTO.builder()
                .response(brands)
                .error(false)
                .build();
    }

    /**
     * Retrieves a brand by its ID.
     *
     * @param id the ID of the brand
     * @return a ResponseDTO containing the brand
     */
    public ResponseDTO getBrandById(String id) {
        BrandEntity brandEntity = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La marca no existe"));
        BrandResponseDTO brand = BrandResponseDTO.builder()
                .id(brandEntity.getId())
                .name(brandEntity.getName())
                .build();

        return ResponseDTO.builder()
                .response(brand)
                .error(false)
                .build();
    }

    /**
     * Finds a brand by its ID.
     *
     * @param id the ID of the brand
     * @return the BrandEntity
     */
    public BrandEntity findBrandById(String id) {
        return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La marca no existe"));
    }

    /**
     * Adds a new brand.
     *
     * @param brandDTO the brand to add
     * @return a ResponseDTO indicating success
     */
    public ResponseDTO addBrand(BrandDTO brandDTO) {

        // Check if a brand with the same name already exists in the repository
        Optional<BrandEntity> existingBrand = brandRepository.findByName(brandDTO.getName());

        // If a brand with the same name exists, throw an exception
        if (existingBrand.isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe una marca con ese nombre");
        }

        // Create a new BrandEntity object with the details from the BrandDTO
        BrandEntity brandEntity = BrandEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(brandDTO.getName())
                .build();

        // Save the new BrandEntity to the repository
        brandRepository.save(brandEntity);

        // Return a ResponseDTO indicating success
        return ResponseDTO.builder()
                .response(Constants.SUCCESS)
                .error(false)
                .build();
    }

    /**
     * Updates a brand.
     *
     * @param id the ID of the brand to update
     * @param brandDTO the updated brand
     * @return a ResponseDTO indicating success
     */
    public ResponseDTO updateBrand(String id, BrandDTO brandDTO) {
        BrandEntity brandEntity = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(UPDATE_BRAND_NOT_FOUND));
        brandEntity.setName(brandDTO.getName());
        brandRepository.save(brandEntity);

        return ResponseDTO.builder()
                .response(Constants.SUCCESS)
                .error(false)
                .build();
    }

    /**
     * Deletes a brand.
     *
     * @param id the ID of the brand to delete
     * @return a ResponseDTO indicating success
     */
    public ResponseDTO deleteBrand(String id) {
        if (!brandRepository.existsById(id)) {
            throw new EntityNotFoundException(DELETE_BRAND_NOT_FOUND);
        }
        brandRepository.deleteById(id);
        return ResponseDTO.builder()
                .response(Constants.SUCCESS)
                .error(false)
                .build();

    }
}