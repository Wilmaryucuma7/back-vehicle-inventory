package com.technicaltest.services;

import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.controllers.request.VehicleDTO;
import com.technicaltest.exceptions.GlobalException;
import com.technicaltest.models.VehicleEntity;
import com.technicaltest.repositories.VehicleRepository;
import com.technicaltest.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class VehicleService {

    private static final String GET_VEHICLES_ERROR = "Error al obtener los vehiculos";
    private final VehicleRepository vehicleRepository;
    private final BrandService brandService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, BrandService brandService) {
        this.vehicleRepository = vehicleRepository;
        this.brandService = brandService;
    }

    public ResponseDTO getVehicles(int page, int size, String sortField, String sortDirection) {

        try {
            validateSortField(sortField);
            Page<VehicleEntity> vehiclePage = orderAndPageVehicles(page, size, sortField, sortDirection);
            Map<String, Object> response = new HashMap<>();
            response.put("vehicles", vehiclePage.getContent());
            response.put("totalPages", vehiclePage.getTotalPages() - 1);
            return ResponseDTO.builder()
                    .response(response)
                    .error(false)
                    .build();
        } catch (Exception e) {
            throw new GlobalException(GET_VEHICLES_ERROR);
        }
    }

    private void validateSortField(String sortField) {
        List<String> validFields = Arrays.asList("model", "year", "brandEntity.name");
        if (!validFields.contains(sortField)) {
            throw new IllegalArgumentException("Campo invalido para ordenar: " + sortField);
        }
    }

    private Page<VehicleEntity> orderAndPageVehicles(int page, int size, String sortField, String sortDirection) {
        PageRequest pageRequest = createPageRequest(page, size, sortField, sortDirection);
        return vehicleRepository.findAll(pageRequest);
    }

    private PageRequest createPageRequest(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("desc") ? sort.ascending() : sort.descending();
        return PageRequest.of(page, size, sort);
    }

    public ResponseDTO searchVehicles(String search, int page, int size) {
        try {

            Pageable pageable = PageRequest.of(page, size);
            Page<VehicleEntity> vehiclePage = vehicleRepository.findByBrandModelOrLicensePlate(search, pageable);
            Map<String, Object> response = new HashMap<>();
            response.put("vehicles", vehiclePage.getContent());
            response.put("totalPages", vehiclePage.getTotalPages() - 1 );

            return ResponseDTO.builder()
                    .response(response)
                    .error(false)
                    .build();
        } catch (Exception e) {
            throw new GlobalException(GET_VEHICLES_ERROR);
        }
    }

    public ResponseDTO getVehicleById(String id) {
        return ResponseDTO.builder()
                .response(vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El vehiculo no existe")))
                .error(false)
                .build();
    }

    public ResponseDTO addVehicle(VehicleDTO vehicleDTO) {

        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .id(UUID.randomUUID().toString())
                .color(vehicleDTO.getColor())
                .licensePlate(vehicleDTO.getLicensePlate())
                .model(vehicleDTO.getModel())
                .year(vehicleDTO.getYear())
                .brandEntity(brandService.getBrandById(vehicleDTO.getBrandId()))
                .build();

        vehicleRepository.save((vehicleEntity));

        return ResponseDTO.builder()
                .response(Constants.SUCCESS)
                .error(false)
                .build();
    }

    public ResponseDTO updateVehicle(String id, VehicleDTO vehicleDTO) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El vehiculo no existe"));

        vehicleEntity.setColor(vehicleDTO.getColor());
        vehicleEntity.setLicensePlate(vehicleDTO.getLicensePlate());
        vehicleEntity.setModel(vehicleDTO.getModel());
        vehicleEntity.setYear(vehicleDTO.getYear());
        vehicleEntity.setBrandEntity(brandService.getBrandById(vehicleDTO.getBrandId()));

        vehicleRepository.save(vehicleEntity);

        return ResponseDTO.builder()
                .response(Constants.SUCCESS)
                .error(false)
                .build();
    }

    public ResponseDTO deleteVehicle(String id) {
        try {
            vehicleRepository.deleteById(id);
            return ResponseDTO.builder()
                    .response(Constants.SUCCESS)
                    .error(false)
                    .build();
        } catch (Exception e) {
            throw new GlobalException("Error al eliminar el vehiculo");
        }
    }

}
