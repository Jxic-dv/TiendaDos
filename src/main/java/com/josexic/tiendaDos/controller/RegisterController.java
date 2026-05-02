package com.josexic.tiendaDos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    // Inyectamos el manager de memoria en lugar de tu base de datos
    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register/save")
    public String registerAdmin(@RequestParam String username, @RequestParam String password) {

        // 1. Verificamos si el admin ya existe en la memoria
        if (userDetailsManager.userExists(username)) {
            return "redirect:/login?error_exists";
        }

        // 2. Creamos el nuevo usuario de Spring Security (Fíjate que le puse rol ADMIN)
        UserDetails newAdmin = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("ADMIN") // Puedes cambiarlo a "USER" si prefieres, pero asumo que son admins
                .build();

        // 3. Lo guardamos en memoria. ¡No toca tu base de datos!
        userDetailsManager.createUser(newAdmin);

        return "redirect:/login?success";
    }
}
