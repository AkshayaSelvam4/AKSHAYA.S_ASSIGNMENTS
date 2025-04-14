package com.hexaware.techshop.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.techshop.dao.*;
import com.hexaware.techshop.entity.*;
import com.hexaware.techshop.util.*;

public class InventoryDAOImpl implements InventoryDAO {

  

    @Override
    public void addInventory(Inventory inventory) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO Inventory (ProductID, QuantityInStock, LastStockUpdate) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, inventory.getProduct().getProductID());
            ps.setInt(2, inventory.getQuantityInStock());
            ps.setDate(3, Date.valueOf(inventory.getLastStockUpdate()));
            ps.executeUpdate();
        }
    }

    @Override
    public void updateInventory(Inventory inventory) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "UPDATE Inventory SET QuantityInStock=?, LastStockUpdate=? WHERE ProductID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, inventory.getQuantityInStock());
            ps.setDate(2, Date.valueOf(inventory.getLastStockUpdate()));
            ps.setInt(3, inventory.getProduct().getProductID());
            ps.executeUpdate();
        }
    }

    @Override
    public Inventory getInventoryByProductId(int productId) throws Exception {
        Inventory inventory = null;
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT i.*, p.ProductName, p.Description, p.Price FROM Inventory i JOIN Products p ON i.ProductID = p.ProductID WHERE i.ProductID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getDouble("Price")
                );
                inventory = new Inventory(
                        rs.getInt("InventoryID"),
                        product,
                        rs.getInt("QuantityInStock"),
                        rs.getDate("LastStockUpdate").toLocalDate()
                );
            }
        }
        return inventory;
    }

    @Override
    public List<Inventory> getAllInventory() throws Exception {
        List<Inventory> list = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT i.*, p.ProductName, p.Description, p.Price FROM Inventory i JOIN Products p ON i.ProductID = p.ProductID";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getDouble("Price")
                );
                Inventory inventory = new Inventory(
                        rs.getInt("InventoryID"),
                        product,
                        rs.getInt("QuantityInStock"),
                        rs.getDate("LastStockUpdate").toLocalDate()
                );
                list.add(inventory);
            }
        }
        return list;
    }

    @Override
    public boolean isProductAvailable(int productId, int requiredQty) throws Exception {
        Inventory inv = getInventoryByProductId(productId);
        return inv != null && inv.getQuantityInStock() >= requiredQty;
    }

    @Override
    public void adjustStock(int productId, int qtyChange) throws Exception {
        Inventory inv = getInventoryByProductId(productId);
        if (inv == null) {
            throw new Exception("Inventory not found for product ID: " + productId);
        }
        int updatedQty = inv.getQuantityInStock() + qtyChange;
        if (updatedQty < 0) {
            throw new Exception("Insufficient stock after adjustment.");
        }

        inv.updateStockQuantity(updatedQty);
        inv.getProduct().setProductID(productId); // In case it's not set
        updateInventory(inv);
    }
}
