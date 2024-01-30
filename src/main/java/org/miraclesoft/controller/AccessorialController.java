package org.miraclesoft.controller;

import org.miraclesoft.domain.Accessorials;
import org.miraclesoft.service.AccessorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/accessorials")
public class AccessorialController {
    private final AccessorialService accessorialService;

    @Autowired
    public AccessorialController(AccessorialService accessorialService) {
        this.accessorialService = accessorialService;
    }

    @GetMapping
    public Flux<ResponseEntity<Accessorials>> getAllAccessorials() {
        return Flux.fromIterable(accessorialService.getAllAccessorials()) // Convert List to Flux
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{accessorialId}")
    public Mono<ResponseEntity<?>> getAccessorialById(@PathVariable String accessorialId) {
        Accessorials accessorial = accessorialService.getAccessorialById( accessorialId);
        if (accessorial != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(accessorial));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Accessorial not found with ID " + accessorialId));
        }
    }

    @PostMapping
    public Mono<ResponseEntity<Accessorials>> createAccessorial(@RequestBody Accessorials accessorial) {
        accessorial.setCreatedOn(new Timestamp(new Date().getTime()));
        accessorial.setAccessorialId(UUID.randomUUID().toString());
        Accessorials savedService = accessorialService.saveAccessorial(accessorial);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(savedService));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<?>> updateAccessorial(@PathVariable String id, @RequestBody Accessorials updatedAccessorial) {
        Accessorials existingAccessorial = accessorialService.getAccessorialById(id);
        if (existingAccessorial != null) {
            existingAccessorial.setAccessorialType(updatedAccessorial.getAccessorialType());
            existingAccessorial.setDescription(updatedAccessorial.getDescription());

            Accessorials updatedServiceEntity = accessorialService.saveAccessorial(existingAccessorial);

            return Mono.just(ResponseEntity.ok(updatedServiceEntity));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Accessorial not found with ID: " + id));
        }
    }

    // Delete a Service
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteService(@PathVariable String id) {
        String deletedId = accessorialService.deleteAccessorial(id);

        if (deletedId != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK)
                    .body("Accessorial with ID: " + deletedId + " has been deleted."));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Accessorial not found with ID: " + id));
        }
    }
}
