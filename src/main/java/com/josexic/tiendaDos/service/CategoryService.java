package com.josexic.tiendaDos.service;

import com.josexic.tiendaDos.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Integer id);
    Category saveCategory(Category category);
    Category updateCategory(Integer id, Category category);
    void deleteCategory(Integer id);
}