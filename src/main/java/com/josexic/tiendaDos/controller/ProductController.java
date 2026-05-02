package com.josexic.tiendaDos.controller;

import com.josexic.tiendaDos.entity.Product;
import com.josexic.tiendaDos.service.ProductService;
import com.josexic.tiendaDos.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product") // Cambiado a singular para que coincida con tus redirects
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", new Product());
        }
        return "product"; // Ahora busca src/main/resources/templates/product.html
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/product"; // Redirige a la lista en singular
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("editMode", true);
        return "product"; // Retorna la misma vista en singular
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/product"; // Redirige a la lista en singular
    }
}