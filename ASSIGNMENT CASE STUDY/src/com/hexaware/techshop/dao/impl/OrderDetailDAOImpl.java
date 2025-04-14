package com.hexaware.techshop.dao.impl;

import java.sql.*;
import java.util.*;

import com.hexaware.techshop.dao.*;
import com.hexaware.techshop.entity.*;
import com.hexaware.techshop.util.DBConnUtil;

public class OrderDetailDAOImpl implements OrderDetailDAO {
   

    @Override
    public void addOrderDetail(OrderDetail detail) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, detail.getOrder().getOrderID());
            ps.setInt(2, detail.getProduct().getProductID());
            ps.setInt(3, detail.getQuantity());
            ps.executeUpdate();
        }
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) throws Exception {
        List<OrderDetail> list = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM OrderDetails od JOIN Products p ON od.ProductID = p.ProductID WHERE od.OrderID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("ProductName"),
                    rs.getString("Description"),
                    rs.getDouble("Price")
                );
                Order order = new Order(orderId, null, null, 0); // We'll assign Customer/Date later if needed
                OrderDetail detail = new OrderDetail(
                    rs.getInt("OrderDetailID"),
                    order,
                    product,
                    rs.getInt("Quantity")
                );
                list.add(detail);
            }
        }
        return list;
    }

    @Override
    public void updateOrderDetail(OrderDetail detail) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "UPDATE OrderDetails SET Quantity=? WHERE OrderDetailID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, detail.getQuantity());
            ps.setInt(2, detail.getOrderDetailID());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteOrderDetailsByOrderId(int orderId) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "DELETE FROM OrderDetails WHERE OrderID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.executeUpdate();
        }
    }
}

