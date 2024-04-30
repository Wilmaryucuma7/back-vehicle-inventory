package com.technicaltest.services;

import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.controllers.request.VehicleDTO;
import com.technicaltest.models.VehicleEntity;
import com.technicaltest.repositories.VehicleRepository;
import com.technicaltest.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * Service class for managing vehicles.
 */
@Service
public class VehicleService {

    public static final String MODEL = "model";
    public static final String YEAR = "year";
    public static final String BRAND_ENTITY_NAME = "brandEntity.name";
    public static final String LICENSE_PLATE = "licensePlate";
    public static final String VEHICLE_DOES_NOT_EXIST = "El vehiculo no existe";
    private static final String VEHICLES = "vehicles";
    private static final String TOTAL_PAGES = "totalPages";
    private final VehicleRepository vehicleRepository;
    private final BrandService brandService;

    /**
     * Constructor for VehicleService.
     *
     * @param vehicleRepository the vehicle repository
     * @param brandService the brand service
     */
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, BrandService brandService) {
        this.vehicleRepository = vehicleRepository;
        this.brandService = brandService;
    }

    /**
     * Retrieves all vehicles with pagination and sorting.
     *
     * @param page the page number
     * @param size the page size
     * @param sortField the field to sort by
     * @param sortDirection the direction to sort (asc or desc)
     * @return a ResponseDTO containing a list of all vehicles
     */
    public ResponseDTO getVehicles(int page, int size, String sortField, String sortDirection) {
        validateSortField(sortField);
        Page<VehicleEntity> vehiclePage = orderAndPageVehicles(page, size, sortField, sortDirection);
        Map<String, Object> response = new HashMap<>();
        response.put(VEHICLES, vehiclePage.getContent());
        response.put(TOTAL_PAGES, vehiclePage.getTotalPages() - 1);
        return ResponseDTO.builder()
                .response(response)
                .error(false)
                .build();
    }

    /**
     * Validates the sort field.
     *
     * @param sortField the field to sort by
     */
    private void validateSortField(String sortField) {
        List<String> validFields = Arrays.asList(MODEL, YEAR, BRAND_ENTITY_NAME, LICENSE_PLATE);
        if (!validFields.contains(sortField)) {
            throw new IllegalArgumentException("Campo invalido para ordenar: " + sortField);
        }
    }

    /**
     * Orders and pages the vehicles.
     *
     * @param page the page number
     * @param size the page size
     * @param sortField the field to sort by
     * @param sortDirection the direction to sort (asc or desc)
     * @return a Page of VehicleEntity
     */
    private Page<VehicleEntity> orderAndPageVehicles(int page, int size, String sortField, String sortDirection) {
        System.out.println(page);
        PageRequest pageRequest = createPageRequest(page, size, sortField, sortDirection);
        return vehicleRepository.findAll(pageRequest);
    }

    /**
     * Creates a PageRequest for pagination and sorting.
     *
     * @param page the page number
     * @param size the page size
     * @param sortField the field to sort by
     * @param sortDirection the direction to sort (asc or desc)
     * @return a PageRequest
     */
    private PageRequest createPageRequest(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        return PageRequest.of(page, size, sort);
    }

    /**
     * Searches for vehicles by brand, model, or license plate.
     *
     * @param search the search term
     * @param page the page number
     * @param size the page size
     * @return a ResponseDTO containing the search results
     */
    public ResponseDTO searchVehicles(String search, int page, int size) {
        System.out.println(page);


        // Create a Pageable object with the provided page number and size
        Pageable pageable = PageRequest.of(page, size);

        // Use the vehicle repository to search for vehicles by brand, model, or license plate
        // The search results are paged according to the Pageable object
        Page<VehicleEntity> vehiclePage = vehicleRepository.findByBrandModelOrLicensePlate(search, pageable);

        // Create a Map to hold the response data
        Map<String, Object> response = new HashMap<>();

        // Add the list of found vehicles to the response
        response.put(VEHICLES, vehiclePage.getContent());

        // Add the total number of pages to the response
        response.put(TOTAL_PAGES, vehiclePage.getTotalPages() - 1 );

        // Build and return a ResponseDTO containing the response data
        return ResponseDTO.builder()
                .response(response)
                .error(false)
                .build();
    }

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id the ID of the vehicle
     * @return a ResponseDTO containing the vehicle
     */
    public ResponseDTO getVehicleById(String id) {
        return ResponseDTO.builder()
                .response(vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(VEHICLE_DOES_NOT_EXIST)))
                .error(false)
                .build();
    }

    /**
     * Adds a new vehicle.
     *
     * @param vehicleDTO the vehicle to add
     * @return a ResponseDTO indicating success
     */
    public ResponseDTO addVehicle(VehicleDTO vehicleDTO) {
        // Check if a vehicle with the same license plate already exists in the repository
        if(vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate())){
            // If a vehicle with the same license plate exists, throw an exception
            throw new DataIntegrityViolationException("Ya existe un vehiculo con esa placa");
        }

        // Create a new VehicleEntity object with the details from the VehicleDTO
        VehicleEntity vehicleEntity = VehicleEntity.builder()
                .id(UUID.randomUUID().toString())
                .color(vehicleDTO.getColor())
                .licensePlate(vehicleDTO.getLicensePlate())
                .model(vehicleDTO.getModel())
                .year(vehicleDTO.getYear())
                .brandEntity(brandService.findBrandById(vehicleDTO.getBrandId()))
                .build();

        // Save the new VehicleEntity to the repository
        vehicleRepository.save((vehicleEntity));

        // Return a ResponseDTO indicating success
        return ResponseDTO.builder()
                .response(Constants.SUCCESS)
                .error(false)
                .build();
    }

    /**
     * Updates a vehicle.
     *
     * @param id the ID of the vehicle to update
     * @param vehicleDTO the updated vehicle
     * @return a ResponseDTO indicating success
     */
    public ResponseDTO updateVehicle(String id, VehicleDTO vehicleDTO) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(VEHICLE_DOES_NOT_EXIST));

        if(vehicleRepository.existsByLicensePlate(vehicleDTO.getLicensePlate()) && !vehicleEntity.getLicensePlate().equals(vehicleDTO.getLicensePlate())){
            throw new DataIntegrityViolationException("Ya existe un vehiculo con esa placa");
        }

        vehicleEntity.setColor(vehicleDTO.getColor());
        vehicleEntity.setLicensePlate(vehicleDTO.getLicensePlate());
        vehicleEntity.setModel(vehicleDTO.getModel());
        vehicleEntity.setYear(vehicleDTO.getYear());
        vehicleEntity.setBrandEntity(brandService.findBrandById(vehicleDTO.getBrandId()));

        vehicleRepository.save(vehicleEntity);

        return ResponseDTO.builder()
                .response(Constants.SUCCESS)
                .error(false)
                .build();
    }
    /**
     * Deletes a vehicle.
     *
     * @param id the ID of the vehicle to delete
     * @return a ResponseDTO indicating success
     */
    public ResponseDTO deleteVehicle(String id) {
        if(!vehicleRepository.existsById(id)){
            throw new EntityNotFoundException(VEHICLE_DOES_NOT_EXIST);
        }
        vehicleRepository.deleteById(id);
        return ResponseDTO.builder()
                .response(Constants.SUCCESS)
                .error(false)
                .build();
    }

}