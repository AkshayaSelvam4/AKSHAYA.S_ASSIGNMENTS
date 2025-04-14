package com.hexaware.techshop.entity;

import java.time.LocalDate;

public class Inventory {
    private int inventoryID;
    private Product product;
    private int quantityInStock;
    private LocalDate lastStockUpdate;

    public Inventory(int inventoryID, Product product, int quantityInStock, LocalDate lastStockUpdate) {
        this.inventoryID = inventoryID;
        this.product = product;
        this.quantityInStock = quantityInStock;
        this.lastStockUpdate = lastStockUpdate;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public LocalDate getLastStockUpdate() {
        return lastStockUpdate;
    }

    public void addToInventory(int qty) {
        if (qty < 0) throw new IllegalArgumentException("Invalid quantity to add");
        quantityInStock += qty;
    }

    public void removeFromInventory(int qty) {
        if (qty > quantityInStock) throw new IllegalArgumentException("Insufficient stock");
        quantityInStock -= qty;
    }

    public void updateStockQuantity(int newQty) {
        if (newQty < 0) throw new IllegalArgumentException("Stock can't be negative");
        quantityInStock = newQty;
    }

    public boolean isProductAvailable(int requiredQty) {
        return quantityInStock >= requiredQty;
    }

    public double getInventoryValue() {
        return quantityInStock * product.getPrice();
    }
}