package com.hexaware.techshop.dao;

import java.util.List;

import com.hexaware.techshop.entity.*;

public interface OrderDAO {
    void placeOrder(Order order) throws Exception;
    Order getOrderById(int orderId) throws Exception;
    List<Order> getOrdersByCustomer(int customerId) throws Exception;
    void updateOrderStatus(int orderId, String newStatus) throws Exception;
    void cancelOrder(int orderId) throws Exception;
}
