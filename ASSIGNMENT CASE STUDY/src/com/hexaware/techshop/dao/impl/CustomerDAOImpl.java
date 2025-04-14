package com.hexaware.techshop.dao.impl;

import java.sql.*;
import java.util.*;

import com.hexaware.techshop.dao.CustomerDAO;
import com.hexaware.techshop.entity.Customer;
import com.hexaware.techshop.exception.InvalidDataException;
import com.hexaware.techshop.util.DBConnUtil;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public void registerCustomer(Customer customer) throws Exception {
        if (!customer.getEmail().matches("^(.+)@(.+)$")) {
            throw new InvalidDataException("Invalid email format.");
        }

        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO Customers (FirstName, LastName, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());
            ps.executeUpdate();
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "UPDATE Customers SET FirstName=?, LastName=?, Email=?, Phone=?, Address=? WHERE CustomerID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());
            ps.setInt(6, customer.getCustomerID());
            ps.executeUpdate();
        }
    }

    @Override
    public Customer getCustomerById(int customerId) throws Exception {
        Customer customer = null;
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Customers WHERE CustomerID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Address")
                );
            }
        }
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM Customers";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new Customer(
                    rs.getInt("CustomerID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Email"),
                    rs.getString("Phone"),
                    rs.getString("Address")
                ));
            }
        }
        return list;
    }

    @Override
    public void deleteCustomer(int customerId) throws Exception {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "DELETE FROM Customers WHERE CustomerID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        }
    }

    @Override
    public int getTotalOrdersByCustomer(int customerId) throws Exception {
        int total = 0;
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT COUNT(*) AS Total FROM Orders WHERE CustomerID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
            }
        } 
        return total;
    }
}

