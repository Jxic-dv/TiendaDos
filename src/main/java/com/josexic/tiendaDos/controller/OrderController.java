package com.josexic.tiendaDos.controller;

import com.josexic.tiendaDos.entity.Order;
import com.josexic.tiendaDos.service.OrderService;
import com.josexic.tiendaDos.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService; // Necesario para llenar el combo de clientes

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String listOrders(Model model) {
        // "orders" para la lista de tarjetas/tabla
        model.addAttribute("orders", orderService.getAllOrders());

        // "users" para el select del formulario
        model.addAttribute("users", userService.getAllUsers());

        // "order" para el objeto del formulario (th:object)
        if (!model.containsAttribute("order")) {
            model.addAttribute("order", new Order());
        }
        model.addAttribute("editMode", false);
        return "order"; // Busca src/main/resources/templates/order.html
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("editMode", true);
        return "order";
    }

    @PostMapping("/save")
    public String saveOrUpdate(@ModelAttribute("order") Order order) {
        // La lógica de actualización o guardado
        if (order.getOrderId() != null && order.getOrderId() > 0) {
            // Asumiendo que tienes un método update en tu Service
            orderService.saveOrder(order);
        } else {
            // Seteamos la fecha solo si es nuevo, si es que no lo haces en el Service
            if (order.getOrderDate() == null) {
                order.setOrderDate(java.time.LocalDateTime.now().toString());
            }
            orderService.saveOrder(order);
        }
        return "redirect:/order";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return "redirect:/order";
    }

    @GetMapping("/search")
    public String searchOrder(@RequestParam("id") Integer id, Model model) {
        try {
            Order orderFound = orderService.getOrderById(id);
            model.addAttribute("orders", java.util.List.of(orderFound));
        } catch (Exception e) {
            model.addAttribute("orders", java.util.Collections.emptyList());
        }
        model.addAttribute("order", new Order());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("editMode", false);
        return "order";
    }
}