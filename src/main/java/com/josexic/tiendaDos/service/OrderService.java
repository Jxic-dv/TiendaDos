package com.josexic.tiendaDos.service;

import com.josexic.tiendaDos.entity.Order;
import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();


    Order getOrderById(Integer id);

    Order saveOrder(Order order);

    Order updateOrder(Integer id, Order order);

    void deleteOrder(Integer id);
}