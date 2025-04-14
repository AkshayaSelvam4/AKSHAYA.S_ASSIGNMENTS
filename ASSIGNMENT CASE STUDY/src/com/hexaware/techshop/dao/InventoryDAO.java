package com.hexaware.techshop.dao;

import java.util.List;

import com.hexaware.techshop.entity.*;

public interface InventoryDAO {
    void addInventory(Inventory inventory) throws Exception;
    void updateInventory(Inventory inventory) throws Exception;
    Inventory getInventoryByProductId(int productId) throws Exception;
    List<Inventory> getAllInventory() throws Exception;
    boolean isProductAvailable(int productId, int requiredQty) throws Exception;
    void adjustStock(int productId, int qtyChange) throws Exception;
}
