package com.revshop.controller;

import com.revshop.model.Product;
import com.revshop.model.User;
import com.revshop.service.ProductService;
import com.revshop.service.impl.ProductServiceImpl;

import java.util.Scanner;

public class SellerController {

    private final ProductService productService = new ProductServiceImpl();
    private final Scanner sc = new Scanner(System.in);

    public void sellerMenu(User user) {

        while (true) {
            System.out.println("\n👉 Please select an option from the menu below.");
            System.out.println("==================================================");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Logout");
            System.out.println("==================================================");

            System.out.print("Enter your choice (1-4): ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> addProduct(user);
                case 2 -> updateProduct();
                case 3 -> deleteProduct();
                case 4 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addProduct(User user) {

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Category: ");
        String cat = sc.nextLine();

        System.out.print("Description: ");
        String desc = sc.nextLine();

        System.out.print("MRP: ");
        double mrp = Double.parseDouble(sc.nextLine());

        double dp;

        System.out.print("Apply Discount? (Y/N): ");
        String applyDiscount = sc.nextLine();

        if (applyDiscount.equalsIgnoreCase("Y")) {

            System.out.print("Enter Discount Percentage: ");
            double percent = Double.parseDouble(sc.nextLine());

            if (percent < 0 || percent > 100) {
                System.out.println("Invalid percentage. No discount applied.");
                dp = mrp;
            } else {
                dp = mrp - (mrp * percent / 100);
            }

        } else {
            dp = mrp;  // No discount
        }


        System.out.print("Stock: ");
        int stock = Integer.parseInt(sc.nextLine());

        System.out.print("Stock Threshold: ");
        int threshold = Integer.parseInt(sc.nextLine());

        productService.addProduct(
                new Product(user.getUserId(), name, cat, desc, mrp, dp, stock, threshold));
        System.out.println("Product added successfully!");
    }

    private void updateProduct() {
        System.out.print("Product ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Product p = productService.getById(id);

        System.out.print("New Stock: ");
        p.setStock(Integer.parseInt(sc.nextLine()));

        productService.updateProduct(p);
        System.out.println("Product updated successfully!");
    }

    private void deleteProduct() {
        System.out.print("Product ID: ");
        int id = Integer.parseInt(sc.nextLine());
        productService.deleteProduct(id);
        System.out.println("Product updated successfully!");

    }
    public void showSellerSummary(User user) {

        System.out.println("\n===== SELLER SUMMARY =====");
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());

        System.out.println("Manage your products and orders efficiently.");
        System.out.println("===========================\n");
    }

}

