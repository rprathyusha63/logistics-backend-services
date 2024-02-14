package org.miraclesoft.service;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.miraclesoft.domain.Order;
import org.miraclesoft.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllOrdersSortByDate();
    }

    public List<Order> getAllOrdersToday() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        Timestamp t = new Timestamp(now.getTime().getTime());
        return orderRepository.findAllOrdersToday(t);
    }
    public List<Order> getOrdersByWarehouse(String warehouseId) {
        return orderRepository.findOrdersByWarehouse(warehouseId);
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
