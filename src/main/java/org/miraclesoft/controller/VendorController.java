package org.miraclesoft.controller;

import org.miraclesoft.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.miraclesoft.domain.Vendor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/vendors")
public class VendorController {
    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // Get all Vendors
    @GetMapping
    public Flux<ResponseEntity<Vendor>> getAllVendors() {
        return Flux.fromIterable(vendorService.getAllVendors()) // Convert List to Flux
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByWarehouseId/{warehouseId}")
    public Flux<ResponseEntity<Vendor>> getAllVendorsByWarehouseId(@PathVariable String warehouseId) {
        return Flux.fromIterable(vendorService.getAllVendorsByWarehouse(warehouseId)) // Convert List to Flux
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Get Vendor by ID
    @GetMapping("/{vendorId}")
    public Mono<ResponseEntity<?>> getVendor(@PathVariable String vendorId) {
        Vendor vendor = vendorService.getVendorById(vendorId);
        if (vendor != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(vendor));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found with ID " + vendorId));
        }
    }

    // Create a Vendor
    @PostMapping
    public Mono<ResponseEntity<Vendor>> createVendor(@RequestBody Vendor vendor) {
        vendor.setVendorId(UUID.randomUUID().toString());
        Vendor savedVendor = vendorService.saveVendor(vendor);
        savedVendor.setWarehouse(null);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(savedVendor));
    }

    // Update a Vendor
    @PutMapping("/{id}")
    public Mono<ResponseEntity<?>> updateVendor(@PathVariable String id, @RequestBody Vendor updatedVendor) {
        Vendor existingVendor = vendorService.getVendorById(id);
        if (existingVendor != null) {
            existingVendor.setWarehouseId(updatedVendor.getWarehouseId());
            //existingVendor.setWarehouse(updatedVendor.getWarehouse());
            existingVendor.setBusinessName(updatedVendor.getBusinessName());
            existingVendor.setType(updatedVendor.getType());
            existingVendor.setEmail(updatedVendor.getEmail());
            existingVendor.setPhone(updatedVendor.getPhone());
            existingVendor.setLocation(updatedVendor.getLocation());

            Vendor updatedVendorEntity = vendorService.saveVendor(existingVendor);
            updatedVendorEntity.setWarehouse(null);
            return Mono.just(ResponseEntity.ok(updatedVendorEntity));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found with ID: " + id));
        }
    }

    // Delete a Vendor
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteVendor(@PathVariable String id) {
        String deletedId = vendorService.deleteVendor(id);

        if (deletedId != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK)
                    .body("Vendor with ID: " + deletedId + " has been deleted."));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vendor not found with ID: " + id));
        }
    }
}
