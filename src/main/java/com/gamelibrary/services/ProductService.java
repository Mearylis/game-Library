package com.gamelibrary.services;

import com.gamelibrary.models.Product;
import com.gamelibrary.repositories.ProductRepository;

import java.time.LocalDate;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(int id, String name, String description, int categoryId, double price) {
        Product product = new Product(id, name, description, categoryId, price, LocalDate.now());
        productRepository.addProduct(product);
    }

    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
    }
}