package com.josexic.tiendaDos.service;

import com.josexic.tiendaDos.entity.OrderDetail;
import com.josexic.tiendaDos.exception.ResourceNotFoundException;
import com.josexic.tiendaDos.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public OrderDetail getOrderDetailById(Integer id) {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + id));
    }

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        if (orderDetail.getQuantity() != null && orderDetail.getUnitPrice() != null) {
            orderDetail.setSubtotal(orderDetail.getQuantity() * orderDetail.getUnitPrice());
        }
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(Integer id, OrderDetail orderDetail) {
        OrderDetail existingDetail = getOrderDetailById(id);

        existingDetail.setQuantity(orderDetail.getQuantity());
        existingDetail.setUnitPrice(orderDetail.getUnitPrice());
        existingDetail.setOrder(orderDetail.getOrder());
        existingDetail.setProduct(orderDetail.getProduct());

        if (existingDetail.getQuantity() != null && existingDetail.getUnitPrice() != null) {
            existingDetail.setSubtotal(existingDetail.getQuantity() * existingDetail.getUnitPrice());
        }

        return orderDetailRepository.save(existingDetail);
    }

    @Override
    public void deleteOrderDetail(Integer id) {
        if (!orderDetailRepository.existsById(id)) {
            throw new ResourceNotFoundException("Detalle no encontrado con ID: " + id);
        }
        orderDetailRepository.deleteById(id);
    }
}