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

@Service
public class BrandService {
    public static final String UPDATE_BRAND_NOT_FOUND = "Error al actualizar, marca no encontrada";
    public static final String DELETE_BRAND_NOT_FOUND = "Error al eliminar, marca no encontrada";

    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    public ResponseDTO getBrands() {
            List<BrandEntity> brandEntities = brandRepository.findAll();
            List<BrandResponseDTO> brands = brandEntities.stream()
                    .map(brandEntity -> BrandResponseDTO.builder()
                            .id(brandEntity.getId())
                            .name(brandEntity.getName())
                            .build())
                    .collect(Collectors.toList());

            return ResponseDTO.builder()
                    .response(brands)
                    .error(false)
                    .build();
    }

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

    public BrandEntity findBrandById(String id) {
        return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La marca no existe"));
    }

    public ResponseDTO addBrand(BrandDTO brandDTO) {

        Optional<BrandEntity> existingBrand = brandRepository.findByName(brandDTO.getName());

        if (existingBrand.isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe una marca con ese nombre");
        }
        BrandEntity brandEntity = BrandEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(brandDTO.getName())
                .build();
        brandRepository.save(brandEntity);

        return ResponseDTO.builder()
                .response(Constants.SUCCESS)
                .error(false)
                .build();
    }

    public ResponseDTO updateBrand(String id, BrandDTO brandDTO) {
            BrandEntity brandEntity = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(UPDATE_BRAND_NOT_FOUND));
            brandEntity.setName(brandDTO.getName());
            brandRepository.save(brandEntity);

            return ResponseDTO.builder()
                    .response(Constants.SUCCESS)
                    .error(false)
                    .build();
    }

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
