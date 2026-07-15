package com.finance.domain;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private List<Category> categories = new ArrayList<>();

    public Category createCategory(String name, String type) {
        Category category = new Category(name, type);
        categories.add(category);
        System.out.println("Добавлена новая категория: " + category);
        return category;
    }

    public Category findCategoryByName(String name) {
        return categories.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Category> getAllCategories() {
        return categories;
    }
}
