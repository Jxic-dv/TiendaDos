package com.josexic.tiendaDos.controller;

import com.josexic.tiendaDos.entity.User;
import com.josexic.tiendaDos.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // LISTAR Y PREPARAR FORMULARIO (Todo en uno)
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        // Mandamos un objeto vacío para el formulario de "Nuevo Usuario"
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
            model.addAttribute("editMode", false);
        }
        return "users";
    }

    // EDITAR: Carga los datos en el mismo users.html
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("editMode", true);
        return "users";
    }

    @PostMapping("/save")
    public String saveOrUpdate(@ModelAttribute("user") User user) {
        if (user.getUserId() != null && user.getUserId() > 0) {
            userService.updateUser(user.getUserId(), user);
        } else {
            userService.saveUser(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
    @GetMapping("/search")
    public String searchUser(@RequestParam("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("users", java.util.List.of(user)); // Solo mostramos el encontrado
        } else {
            model.addAttribute("users", java.util.Collections.emptyList());
        }
        model.addAttribute("user", new User());
        model.addAttribute("editMode", false);
        return "users";
    }
}
