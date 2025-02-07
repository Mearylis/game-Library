package com.gamelibrary.repositories;

import com.gamelibrary.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProductById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void deleteProduct(int id) {
        products.removeIf(p -> p.getId() == id);
    }
}