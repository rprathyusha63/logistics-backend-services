package org.miraclesoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.miraclesoft.domain.Warehouse;
import org.miraclesoft.service.WarehouseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/warehouses")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    // Get all Warehouses
    @GetMapping
    public Flux<ResponseEntity<Warehouse>> getAllWarehouses() {
        return Flux.fromIterable(warehouseService.getAllWarehouses()) // Convert List to Flux
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Get Warehouse by ID
    @GetMapping("/{warehouseId}")
    public Mono<ResponseEntity<?>> getWarehouse(@PathVariable String warehouseId) {
        Warehouse warehouse = warehouseService.getWarehouseById((warehouseId));
        if (warehouse != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(warehouse));
        } else {
            return Mono.just(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Warehouse not found with ID " + warehouseId));
        }
    }

    // Create a Warehouse
    @PostMapping
    public Mono<ResponseEntity<Warehouse>> createWarehouse(@RequestBody Warehouse warehouse) {
        warehouse.setWarehouseId(UUID.randomUUID().toString());
        warehouse.setCreatedOn(new Timestamp(new Date().getTime()));
        Warehouse savedWarehouse = warehouseService.saveWarehouse(warehouse);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(savedWarehouse));
    }

    // Update a Warehouse
    @PutMapping("/{warehouseId}")
    public Mono<ResponseEntity<?>> updateWarehouse(@PathVariable String warehouseId,
            @RequestBody Warehouse updatedWarehouse) {
        Warehouse existingWarehouse = warehouseService.getWarehouseById(warehouseId);
        if (existingWarehouse != null) {
            existingWarehouse.setWarehouseName(updatedWarehouse.getWarehouseName());
            existingWarehouse.setLocation(updatedWarehouse.getLocation());
            existingWarehouse.setWarehouseManager(updatedWarehouse.getWarehouseManager());
            existingWarehouse.setPhone(updatedWarehouse.getPhone());
            existingWarehouse.setEmail(updatedWarehouse.getEmail());
            existingWarehouse.setWarehouseStatus(updatedWarehouse.getWarehouseStatus());
            existingWarehouse.setWarehouseOperatingHours(updatedWarehouse.getWarehouseOperatingHours());

            Warehouse updatedWarehouseEntity = warehouseService.saveWarehouse(existingWarehouse);

            return Mono.just(ResponseEntity.ok(updatedWarehouseEntity));
        } else {
            return Mono.just(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body("Warehouse not found with ID: " + warehouseId));
        }
    }

    // Delete a Warehouse
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteWarehouse(@PathVariable String id) {
        String deletedId = warehouseService.deleteWarehouse(id);

        if (deletedId != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK)
                    .body("Warehouse with ID: " + deletedId + " has been deleted."));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Warehouse not found with ID: " + id));
        }
    }
}
