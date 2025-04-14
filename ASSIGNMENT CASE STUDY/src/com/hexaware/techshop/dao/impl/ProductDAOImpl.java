package com.hexaware.techshop.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.techshop.dao.*;
import com.hexaware.techshop.entity.*;
import com.hexaware.techshop.util.*;

public class ProductDAOImpl implements ProductDAO {

 

    @Override
    public void addProduct(Product product) throws Exception {
        if (product.getPrice() < 0)
            throw new IllegalArgumentException("Price cannot be negative.");

        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO Products (ProductName, Description, Price) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.executeUpdate();
        }
    }

    @Override
    public void updateProduct(Product product) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "UPDATE Products SET ProductName=?, Description=?, Price=? WHERE ProductID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getProductID());
            ps.executeUpdate();
        }
    }

    @Override
    public Product getProductById(int productId) throws Exception {
        Product product = null;
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Products WHERE ProductID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
                );
            }
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Products";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
                );
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public void deleteProduct(int productId) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "DELETE FROM Products WHERE ProductID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.executeUpdate();
        }
    }
}

