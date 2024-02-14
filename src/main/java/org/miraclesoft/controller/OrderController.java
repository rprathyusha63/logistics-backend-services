package org.miraclesoft.controller;
import org.miraclesoft.domain.Order;
import org.miraclesoft.domain.VendorProduct;
import org.miraclesoft.domain.VendorProductId;
import org.miraclesoft.service.OrderService;
import org.miraclesoft.service.VendorProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final VendorProductService vendorProductService;


    @Autowired
    public OrderController(OrderService orderService, VendorProductService vendorProductService) {
        this.orderService = orderService;
        this.vendorProductService=vendorProductService;
    }

    // Get all Orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if(orders.size()!=0){
            return new ResponseEntity<>(orders,HttpStatus.OK);
        }
        else {
            ResponseEntity res = new ResponseEntity(HttpStatus.NOT_FOUND);
            return res;
        }
    }

    @GetMapping("/today")
    public ResponseEntity<List<Order>> getAllOrdersToday() {
        List<Order> orders = orderService.getAllOrdersToday();
        if(orders.size()!=0){
            return new ResponseEntity<>(orders,HttpStatus.OK);
        }
        else {
           ResponseEntity res = new ResponseEntity(HttpStatus.NOT_FOUND);
           return res;
        }
    }
    @GetMapping("/warehouse/{id}")
    public ResponseEntity<List<Order>> getOrdersByWarehouse(@PathVariable("id") String warehouseId) {
        List<Order> orders = orderService.getOrdersByWarehouse(warehouseId);
        if(orders.size()!=0){
            return new ResponseEntity<>(orders,HttpStatus.OK);
        }
        else {
            ResponseEntity res = new ResponseEntity(HttpStatus.NOT_FOUND);
            return res;
        }
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
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        Calendar c= Calendar.getInstance();
        order.setOrderId("MLOG"+c.get(Calendar.YEAR)+Integer.toString(n));
        Timestamp receievedOn = new Timestamp(Calendar.getInstance().getTime().getTime());
        order.setReceivedOn(receievedOn);
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
            if(existingOrder.getStatus().toString().equals("PENDING") || existingOrder.getStatus().toString().equals("PROCESSING")){
                if(updatedOrder.getStatus().toString().equals("SHIPPED")){
                    VendorProduct existing = vendorProductService.getById(new VendorProductId(updatedOrder.getVendorId(), updatedOrder.getProductId()));
                    if (existing != null) {
                        existing.setQuantity(existing.getQuantity()-1);
                        VendorProduct updatedVendorProduct = vendorProductService.saveVendorProduct(existing);
                    }
                }

            }
            existingOrder.setStatus(updatedOrder.getStatus());

            existingOrder.setReceivedOn(updatedOrder.getReceivedOn());
            if(updatedOrder.getStatus().toString().equals("SHIPPED")){
                existingOrder.setShippedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
                if(existingOrder.getProcessedOn()==null){
                    existingOrder.setProcessedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
                }
            }
            if(updatedOrder.getStatus().toString().equals("PROCESSING")){
                existingOrder.setProcessedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
            }
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