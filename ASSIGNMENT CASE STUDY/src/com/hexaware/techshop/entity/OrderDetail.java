package com.hexaware.techshop.entity;

public class OrderDetail {
    private int orderDetailID;
    private Order order;
    private Product product;
    private int quantity;

    public OrderDetail(int orderDetailID, Order order, Product product, int quantity) {
        this.orderDetailID = orderDetailID;
        this.order = order;
        this.product = product;
        setQuantity(quantity);
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product prod) {
        this.product = prod;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int qty) {
        if (qty <= 0)
            throw new IllegalArgumentException("Quantity must be > 0");
        this.quantity = qty;
    }

    public double calculateSubtotal() {
        return product.getPrice() * quantity;
    }

    public String getOrderDetailInfo() {
        return "OrderDetailID: " + orderDetailID + ", Product: " + product.getProductName() + ", Quantity: " + quantity + ", Subtotal: " + calculateSubtotal();
    }
}