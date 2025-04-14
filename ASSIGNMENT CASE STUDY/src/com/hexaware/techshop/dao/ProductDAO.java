package com.hexaware.techshop.dao;

import java.util.List;

import com.hexaware.techshop.entity.*;

public interface ProductDAO {
    void addProduct(Product product) throws Exception;
    void updateProduct(Product product) throws Exception;
    Product getProductById(int productId) throws Exception;
    List<Product> getAllProducts() throws Exception;
    void deleteProduct(int productId) throws Exception;
}