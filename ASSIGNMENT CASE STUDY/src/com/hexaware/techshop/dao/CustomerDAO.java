package com.hexaware.techshop.dao;

import java.util.List;

import com.hexaware.techshop.entity.*;

public interface CustomerDAO {
    void registerCustomer(Customer customer) throws Exception;
    void updateCustomer(Customer customer) throws Exception;
    Customer getCustomerById(int customerId) throws Exception;
    List<Customer> getAllCustomers() throws Exception;
    void deleteCustomer(int customerId) throws Exception;
    int getTotalOrdersByCustomer(int customerId) throws Exception;
}