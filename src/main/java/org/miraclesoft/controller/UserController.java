package org.miraclesoft.controller;
import org.miraclesoft.domain.Order;
import org.miraclesoft.domain.jwt.UserAuth;
import org.miraclesoft.service.OrderService;
import org.miraclesoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all Users
    @GetMapping
    public Flux<ResponseEntity<UserAuth>> getAllUsers() {
        return Flux.fromIterable(userService.getAllUsers())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Get User by ID
    @GetMapping("/{email}")
    public Mono<ResponseEntity<UserAuth>> getUserbyEmail(@PathVariable String email) {
        UserAuth user = userService.getUserByEmail(email);
        return Mono.just(user)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Delete a User
    @DeleteMapping("/{email}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable String email) {
        String deletedId = userService.deleteUser(email);

        if (deletedId != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK)
                    .body("User with ID: " + deletedId + " has been deleted."));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + email));
        }
    }
}