package com.technicaltest.controllers;

import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.controllers.request.VehicleDTO;
import com.technicaltest.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @GetMapping("/get-vehicles/{sortField}/{sortDirection}/{currentPage}")
    public ResponseEntity<ResponseDTO> getVehicles(@PathVariable String sortField, @PathVariable String sortDirection, @PathVariable int currentPage) {
        if(sortField.equals("brand"))
            sortField = "brandEntity.name";
        return new ResponseEntity<>(this.vehicleService.getVehicles(currentPage, 10, sortField, sortDirection), HttpStatus.OK);
    }

    @GetMapping("/search-vehicles/{search}/{currentPage}")
    public ResponseEntity<ResponseDTO> searchVehicles(@PathVariable String search, @PathVariable int currentPage) {
        return new ResponseEntity<>(this.vehicleService.searchVehicles(search,currentPage,10), HttpStatus.OK);
    }

    @PostMapping("/add-vehicle")
    public ResponseEntity<ResponseDTO> addVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        return new ResponseEntity<>(this.vehicleService.addVehicle(vehicleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update-vehicle/{id}")
    public ResponseEntity<ResponseDTO> updateVehicle(@PathVariable String id, @Valid @RequestBody VehicleDTO vehicleDTO) {
        return new ResponseEntity<>(this.vehicleService.updateVehicle(id, vehicleDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete-vehicle/{id}")
    public ResponseEntity<ResponseDTO> deleteVehicle(@PathVariable String id) {
        return new ResponseEntity<>(this.vehicleService.deleteVehicle(id), HttpStatus.OK);
    }

}
