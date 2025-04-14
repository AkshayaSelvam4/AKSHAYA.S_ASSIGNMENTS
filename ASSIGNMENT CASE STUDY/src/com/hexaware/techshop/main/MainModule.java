package com.hexaware.techshop.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.hexaware.techshop.dao.*;
import com.hexaware.techshop.dao.impl.*;
import com.hexaware.techshop.entity.*;
import com.hexaware.techshop.exception.*;

public class MainModule {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerDAO customerDAO = new CustomerDAOImpl();
    private static final ProductDAO productDAO = new ProductDAOImpl();
    private static final OrderDAO orderDAO = new OrderDAOImpl();
    private static final InventoryDAO inventoryDAO = new InventoryDAOImpl();
    private static final OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear newline

            try {
                switch (choice) {
                    case 1 -> registerCustomer();
                    case 2 -> addProduct();
                    case 3 -> placeOrder();
                    case 4 -> viewCustomerOrders();
                    case 5 -> updateInventory();
                    case 6 -> listProducts();
                    case 7 -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Exiting TechShop... Goodbye!");
    }

    private static void showMenu() {
        System.out.println("\n=== TechShop Menu ===");
        System.out.println("1. Register Customer");
        System.out.println("2. Add Product");
        System.out.println("3. Place Order");
        System.out.println("4. View Customer Orders");
        System.out.println("5. Update Inventory");
        System.out.println("6. List Products");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    private static void registerCustomer() throws Exception {
        System.out.println("Enter First Name: ");
        String fname = scanner.nextLine();

        System.out.println("Enter Last Name: ");
        String lname = scanner.nextLine();

        System.out.println("Enter Email: ");
        String email = scanner.nextLine();

        if (!email.matches("^(.+)@(.+)$")) {
            throw new InvalidDataException("Invalid email format.");
        }

        System.out.println("Enter Phone: ");
        String phone = scanner.nextLine();

        System.out.println("Enter Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(0, fname, lname, email, phone, address);
        customerDAO.registerCustomer(customer);
        System.out.println("Customer registered successfully.");
    }

    private static void addProduct() throws Exception {
        System.out.println("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Description: ");
        String desc = scanner.nextLine();

        System.out.println("Enter Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Product product = new Product(0, name, desc, price);
        productDAO.addProduct(product);
        System.out.println("Product added successfully.");
    }

    private static void placeOrder() throws Exception {
        System.out.println("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            throw new IncompleteOrderException("Customer not found.");
        }

        System.out.println("Enter Product ID: ");
        int productId = scanner.nextInt();

        System.out.println("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Product product = productDAO.getProductById(productId);
        if (product == null) {
            throw new IncompleteOrderException("Product not found.");
        }

        boolean available = inventoryDAO.isProductAvailable(productId, quantity);
        if (!available) {
            throw new InsufficientStockException("Not enough stock available.");
        }

        double total = product.getPrice() * quantity;
        Order order = new Order(0, customer, LocalDate.now(), total);
        orderDAO.placeOrder(order);

        OrderDetail detail = new OrderDetail(0, order, product, quantity);
        orderDetailDAO.addOrderDetail(detail);

        inventoryDAO.adjustStock(productId, -quantity);

        System.out.println("Order placed successfully with Order ID: " + order.getOrderID());
    }

    private static void viewCustomerOrders() throws Exception {
        System.out.println("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        List<Order> orders = orderDAO.getOrdersByCustomer(customerId);
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order o : orders) {
                System.out.println(o.getOrderDetails());
            }
        }
    }

    private static void updateInventory() throws Exception {
        System.out.println("Enter Product ID: ");
        int productId = scanner.nextInt();

        System.out.println("Enter Quantity to Add: ");
        int qty = scanner.nextInt();
        scanner.nextLine();

        Inventory inventory = inventoryDAO.getInventoryByProductId(productId);
        if (inventory == null) {
            Product p = productDAO.getProductById(productId);
            if (p == null) throw new Exception("Product not found.");
            inventory = new Inventory(0, p, qty, LocalDate.now());
            inventoryDAO.addInventory(inventory);
        } else {
            inventory.addToInventory(qty);
            inventoryDAO.updateInventory(inventory);
        }

        System.out.println("Inventory updated.");
    }

    private static void listProducts() throws Exception {
        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            System.out.println(p.getProductDetails());
        }
    }
}
