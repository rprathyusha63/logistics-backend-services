package org.miraclesoft.controller;

import org.miraclesoft.domain.Order;
import org.miraclesoft.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/v1/orders")
public class TestController
{
    @Autowired
    private final OrderService orderService;
    private Logger logger = LoggerFactory.getLogger(TestController.class);
    public TestController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Get all Orders
    @GetMapping
    public Flux<ResponseEntity<Order>> getAllOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("authentication {}",authentication);
        Flux<ResponseEntity<Order>> orders = Flux.fromIterable(orderService.getAllOrders())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
        logger.info("Security {} ", SecurityContextHolder.getContext().getAuthentication());
        return orders;
    }

    // Get Order by ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Order>> getOrder(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        return Mono.just(order)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    // Create an Order
    @PostMapping
    public Mono<ResponseEntity<Order>> createOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        Order savedOrder = orderService.saveOrder(order);
        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(savedOrder));
    }

    // Update an Order
    @PutMapping("/{id}")
    public Mono<ResponseEntity<?>> updateOrder(@PathVariable String id, @RequestBody Order updatedOrder) {
        Order existingOrder = orderService.getOrderById(id);
        if (existingOrder != null) {
            // Update the properties of existingOrder with the values from updatedOrder
            existingOrder.setWarehouseId(updatedOrder.getWarehouseId());
            existingOrder.setVendorId(updatedOrder.getVendorId());
            existingOrder.setProductId(updatedOrder.getProductId());
            existingOrder.setAccessorialId(updatedOrder.getAccessorialId());
            existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
            existingOrder.setCondition(updatedOrder.getCondition());
            existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setReceivedOn(updatedOrder.getReceivedOn());
            existingOrder.setProcessedOn(updatedOrder.getProcessedOn());
            existingOrder.setShippedOn(updatedOrder.getShippedOn());
            existingOrder.setPaymentMode(updatedOrder.getPaymentMode());
            existingOrder.setBillingAddress(updatedOrder.getBillingAddress());
            existingOrder.setPaymentConfirmationNumber(updatedOrder.getPaymentConfirmationNumber());

            Order updatedOrderEntity = orderService.saveOrder(existingOrder);

            return Mono.just(ResponseEntity.ok(updatedOrderEntity));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found with ID: " + id));
        }
    }

    // Delete an Order
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteOrder(@PathVariable String id) {
        String deletedId = orderService.deleteOrder(id);

        if (deletedId != null) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK)
                    .body("Order with ID: " + deletedId + " has been deleted."));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order not found with ID: " + id));
        }
    }
}
