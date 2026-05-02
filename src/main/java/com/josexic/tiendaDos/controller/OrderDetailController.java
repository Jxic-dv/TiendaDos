package com.josexic.tiendaDos.controller;

import com.josexic.tiendaDos.entity.OrderDetail;
import com.josexic.tiendaDos.service.OrderDetailService;
import com.josexic.tiendaDos.service.OrderService;
import com.josexic.tiendaDos.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderdetail")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderDetailController(OrderDetailService orderDetailService, OrderService orderService, ProductService productService) {
        this.orderDetailService = orderDetailService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping
    public String listOrderDetails(Model model) {
        model.addAttribute("orderDetails", orderDetailService.getAllOrderDetails());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("orderDetail", new OrderDetail());
        return "orderdetail";
    }

    @PostMapping("/save")
    public String saveOrderDetail(@ModelAttribute("orderDetail") OrderDetail orderDetail) {
        orderDetailService.saveOrderDetail(orderDetail);
        return "redirect:/orderdetail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("orderDetail", orderDetailService.getOrderDetailById(id));
        model.addAttribute("orderDetails", orderDetailService.getAllOrderDetails());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("editMode", true);
        return "orderdetail";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderDetail(@PathVariable Integer id) {
        orderDetailService.deleteOrderDetail(id);
        return "redirect:/orderdetail";
    }
}