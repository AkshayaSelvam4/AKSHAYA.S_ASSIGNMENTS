package com.hexaware.techshop.entity;

public class Product {
    private int productID;
    private String productName;
    private String description;
    private double price;

    public Product(int productID, String productName, String description, double price) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        setPrice(price);
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price must be non-negative");
        this.price = price;
    }

    public String getProductDetails() {
        return "ID: " + productID + ", Name: " + productName + ", Description: " + description + ", Price: " + price;
    }

    public void updateProductInfo(String description, double price) {
        setDescription(description);
        setPrice(price);
    }
}
