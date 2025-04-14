package com.hexaware.techshop.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.techshop.dao.*;
import com.hexaware.techshop.entity.*;
import com.hexaware.techshop.util.*;

public class OrderDAOImpl implements OrderDAO {

   

    @Override
    public void placeOrder(Order order) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO Orders (CustomerID, OrderDate, TotalAmount) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getCustomer().getCustomerID());
            ps.setDate(2, Date.valueOf(order.getOrderDate()));
            ps.setDouble(3, order.getTotalAmount());
            ps.executeUpdate();

            // Optional: retrieve generated OrderID
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderID(rs.getInt(1));
            }
        }
    }

    @Override
    public Order getOrderById(int orderId) throws Exception {
        Order order = null;
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Orders o JOIN Customers c ON o.CustomerID = c.CustomerID WHERE o.OrderID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("CustomerID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Address")
                );
                order = new Order(
                        rs.getInt("OrderID"),
                        customer,
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getDouble("TotalAmount")
                );
            }
        }
        return order;
    }

    @Override
    public List<Order> getOrdersByCustomer(int customerId) throws Exception {
        List<Order> list = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Orders WHERE CustomerID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        null, // Customer is optional here
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getDouble("TotalAmount")
                );
                list.add(order);
            }
        }
        return list;
    }

    @Override
    public void updateOrderStatus(int orderId, String newStatus) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "UPDATE Orders SET Status=? WHERE OrderID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }
    }

    @Override
    public void cancelOrder(int orderId) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            conn.setAutoCommit(false);

            // 1. Delete OrderDetails
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM OrderDetails WHERE OrderID=?");
            ps1.setInt(1, orderId);
            ps1.executeUpdate();

            // 2. Delete the Order
            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM Orders WHERE OrderID=?");
            ps2.setInt(1, orderId);
            ps2.executeUpdate();

            conn.commit();
        } catch (Exception ex) {
            throw new Exception("Order cancelation failed: " + ex.getMessage());
        }
    }
}

