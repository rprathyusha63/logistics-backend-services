package org.miraclesoft.controller;

import org.miraclesoft.domain.AccessorialRateId;
import org.miraclesoft.domain.AccessorialRates;
import org.miraclesoft.service.AccessorialRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/accessorial-rates")
public class AccessorialRatesController {
    private final AccessorialRatesService accessorialRatesService;

    @Autowired
    public AccessorialRatesController(AccessorialRatesService accessorialRatesService) {
        this.accessorialRatesService = accessorialRatesService;
    }

    @GetMapping
    public Flux<ResponseEntity<AccessorialRates>> getAllAccessorialRates() {
        return Flux.fromIterable(accessorialRatesService.getAllAccessorialRates()) // Convert List to Flux
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{accessorialId}/{warehouseId}")
    public Mono<ResponseEntity<?>> getAccessorialRateById(@PathVariable String accessorialId,
                                                      @PathVariable String warehouseId) {
        AccessorialRates accessorialRate = accessorialRatesService.getAccessorialRateById( new AccessorialRateId(accessorialId,warehouseId));
        if (accessorialRate != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(accessorialRate));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("AccessorialRate for accessorial ID: " +
                    accessorialId + " and warehouse Id: "+ warehouseId+" was not found."));
        }
    }

    @PostMapping
    public Mono<ResponseEntity<AccessorialRates>> createAccessorialRate(@RequestBody AccessorialRates accessorialRate) {
        AccessorialRates savedAccessorialRate = accessorialRatesService.saveAccessorialRate(accessorialRate);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(savedAccessorialRate));
    }

    @PutMapping()
    public Mono<ResponseEntity<?>> updateAccessorialRate(@RequestBody AccessorialRates accessorialRate) {
        AccessorialRates existingAccessorialRate = accessorialRatesService.getAccessorialRateById(accessorialRate.getId());
        if (existingAccessorialRate != null) {
            existingAccessorialRate.setAccessorialRate(accessorialRate.getAccessorialRate());
            AccessorialRates updatedAccessorialRate = accessorialRatesService.saveAccessorialRate(existingAccessorialRate);

            return Mono.just(ResponseEntity.ok(updatedAccessorialRate));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Accessorial Rate not found for Accessorial ID: "
                    + accessorialRate.getId().getAccessorial_id() + " and warehouse Id: "+accessorialRate.getId().getWarehouse_id()));
        }
    }

    // Delete a Service
    @DeleteMapping("/{accessorialId}/{warehouseId}")
    public Mono<ResponseEntity<String>> deleteService(@PathVariable String accessorialId,
                                                      @PathVariable String warehouseId) {
        AccessorialRateId deletedId = accessorialRatesService.deleteAccessorialRate(new AccessorialRateId(accessorialId,warehouseId));

        if (deletedId != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK)
                    .body("AccessorialRate for accessorial ID: " +
                            accessorialId + " and warehouse Id: "+ warehouseId+" has been deleted."));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("AccessorialRate for accessorial ID: " +
                            accessorialId + " and warehouse Id: "+ warehouseId+" was not found."));
        }
    }
}
