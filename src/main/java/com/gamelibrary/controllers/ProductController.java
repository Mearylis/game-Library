package com.gamelibrary.controllers;

import com.gamelibrary.models.Product;
import com.gamelibrary.services.ProductService;

import java.util.List;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void addProduct(int id, String name, String description, int categoryId, double price) {
        productService.addProduct(id, name, description, categoryId, price);
    }

    public Product getProductById(int id) {
        return productService.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public void deleteProduct(int id) {
        productService.deleteProduct(id);
    }
}