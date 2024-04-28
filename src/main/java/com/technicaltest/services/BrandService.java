package com.technicaltest.services;

import com.technicaltest.controllers.request.BrandDTO;
import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.exceptions.EntityAlreadyExistsException;
import com.technicaltest.exceptions.GlobalException;
import com.technicaltest.models.BrandEntity;
import com.technicaltest.repositories.BrandRepository;
import com.technicaltest.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {

    public static final String GET_BRANDS_ERROR = "Error al obtener las marcas";
    public static final String ADD_BRAND_ERROR = "Error al agregar la marca";
    public static final String UPDATE_BRAND_ERROR = "Error al actualizar la marca";
    public static final String UPDATE_BRAND_NOT_FOUND = "Error al actualizar, marca no encontrada";
    public static final String DELETE_BRAND_ERROR = "Error al eliminar la marca";


    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    public ResponseDTO getBrands() {
        try {
            return ResponseDTO.builder()
                    .response(brandRepository.findAll())
                    .error(false)
                    .build();
        } catch (Exception e) {
            throw new GlobalException(GET_BRANDS_ERROR);
        }
    }

    public BrandEntity getBrandById(String id) {
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

        try {
            brandRepository.deleteById(id);
            return ResponseDTO.builder()
                    .response(Constants.SUCCESS)
                    .error(false)
                    .build();
        } catch (Exception e) {
            throw new GlobalException(DELETE_BRAND_ERROR);
        }
    }
}
