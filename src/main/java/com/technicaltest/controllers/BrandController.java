package com.technicaltest.controllers;

import com.technicaltest.controllers.request.BrandDTO;
import com.technicaltest.controllers.request.ResponseDTO;
import com.technicaltest.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/get-brands")
    public ResponseEntity<Object> getBrands() {
        return new ResponseEntity<>(this.brandService.getBrands(), HttpStatus.OK);
    }

    @PostMapping("/add-brand")
    public ResponseEntity<ResponseDTO> addBrand(@Valid @RequestBody BrandDTO brandDTO) {
        return new ResponseEntity<>(brandService.addBrand(brandDTO), HttpStatus.CREATED);
    }

    @GetMapping("/get-brand/{id}")
    public ResponseEntity<ResponseDTO> getBrandById(@PathVariable String id) {
        return new ResponseEntity<>(brandService.getBrandById(id), HttpStatus.OK);
    }

    @PutMapping("/update-brand/{id}")
    public ResponseEntity<ResponseDTO> updateBrand(@PathVariable String id, @Valid @RequestBody BrandDTO brandDTO) {

        return new ResponseEntity<>(brandService.updateBrand(id, brandDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete-brand/{id}")
    public ResponseEntity<ResponseDTO> deleteBrand(@PathVariable String id) {
        return new ResponseEntity<>(brandService.deleteBrand(id), HttpStatus.OK);
    }
}
