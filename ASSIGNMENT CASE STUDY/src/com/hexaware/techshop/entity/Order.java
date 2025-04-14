package com.hexaware.techshop.entity;

import java.time.LocalDate;

public class Order {
    private int orderID;
    private Customer customer;
    private LocalDate orderDate;
    private double totalAmount;

    public Order(int orderID, Customer customer, LocalDate orderDate, double totalAmount) {
        this.orderID = orderID;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer cust) {
        this.customer = cust;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate date) {
        this.orderDate = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double amt) {
        if (amt < 0)
            throw new IllegalArgumentException("Amount cannot be negative");
        this.totalAmount = amt;
    }

    public String getOrderDetails() {
        return "OrderID: " + orderID + ", Date: " + orderDate + ", Total: " + totalAmount + ", Customer: [" + customer.getCustomerDetails() + "]";
    }
}