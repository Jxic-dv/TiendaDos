package com.josexic.tiendaDos.service;

import com.josexic.tiendaDos.entity.Order;
import com.josexic.tiendaDos.exception.ResourceNotFoundException;
import com.josexic.tiendaDos.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));
    }

    @Override
    public Order saveOrder(Order order) {
        if (order.getOrderDate() == null || order.getOrderDate().isEmpty()) {
            order.setOrderDate(java.time.LocalDateTime.now().toString());
        }
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Integer id, Order order) {
        Order existingOrder = getOrderById(id);

        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setOrderTotal(order.getOrderTotal());
        existingOrder.setShippingAddress(order.getShippingAddress());
        existingOrder.setUser(order.getUser());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido no encontrado con ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}