package com.josexic.tiendaDos.service;

import com.josexic.tiendaDos.entity.OrderDetail;
import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetails();
    OrderDetail getOrderDetailById(Integer id);
    OrderDetail saveOrderDetail(OrderDetail orderDetail);
    OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetail);
    void deleteOrderDetail(Integer id);
}