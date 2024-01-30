package org.miraclesoft.service;
import org.miraclesoft.domain.Order;
import org.miraclesoft.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        savedOrder.setWarehouse(null);
        savedOrder.setVendor(null);
        savedOrder.setProduct(null);
        savedOrder.setAccessorials(null);
        return savedOrder;
    }

    public String deleteOrder(String id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return id;
        } else {
            return null;
        }
    }
}
