package com.josexic.tiendaDos.controller;

import com.josexic.tiendaDos.entity.Category;
import com.josexic.tiendaDos.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category") // Mantenemos el singular para que coincida con tu petición original
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        // "categories" (plural) para la tabla/lista
        model.addAttribute("categories", categoryService.getAllCategories());

        // "category" (singular) para el objeto del formulario (th:object)
        if (!model.containsAttribute("category")) {
            model.addAttribute("category", new Category());
        }
        model.addAttribute("editMode", false);
        return "category"; // Busca src/main/resources/templates/category.html
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("editMode", true);
        return "category";
    }

    @PostMapping("/save")
    public String saveOrUpdate(@ModelAttribute("category") Category category) {
        if (category.getCategoryId() != null && category.getCategoryId() > 0) {
            categoryService.updateCategory(category.getCategoryId(), category);
        } else {
            categoryService.saveCategory(category);
        }
        // Redirigir a la ruta base definida en @RequestMapping
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return "redirect:/category";
    }

    @GetMapping("/search")
    public String searchCategory(@RequestParam("id") Integer id, Model model) {
        try {
            Category categoryFound = categoryService.getCategoryById(id);
            // Mandamos el resultado a la lista para que la tabla lo muestre
            model.addAttribute("categories", java.util.List.of(categoryFound));
        } catch (Exception e) {
            model.addAttribute("categories", java.util.Collections.emptyList());
        }
        model.addAttribute("category", new Category());
        model.addAttribute("editMode", false);
        return "category";
    }
}