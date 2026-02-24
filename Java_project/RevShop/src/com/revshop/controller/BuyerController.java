package com.revshop.controller;

import com.revshop.model.*;
import com.revshop.service.*;
import com.revshop.service.impl.*;


import java.util.List;
import java.util.Scanner;

public class BuyerController {

    private final BuyerService buyerService = new BuyerServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final NotificationService notificationService = new NotificationServiceImpl();
    private final Scanner sc = new Scanner(System.in);

    public void buyerMenu(User user) {

        while (true) {

            System.out.println("\n👉 Please select an option from the menu below.");
            System.out.println("==================================================");
            System.out.println("1. Browse Products");
            System.out.println("2. Add To Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove From Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Payment");
            System.out.println("7. Order History");
            System.out.println("8. View Notifications");
            System.out.println("9. Add Review");
            System.out.println("10. View Invoice");
            System.out.println("11. Logout");
            System.out.println("==================================================");

            System.out.print("Enter your choice (1-11): ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                continue;
            }

            switch (choice) {

                case 1:
                    browse();
                    break;

                case 2:
                    addToCart(user);
                    break;

                case 3:
                    viewCart(user);
                    break;

                case 4:
                    removeFromCart(user);   // ✅ Correct mapping
                    break;

                case 5:
                    checkoutFlow(user);     // ✅ Only summary
                    break;

                case 6:
                    processPayment(user);   // ✅ Payment here
                    break;

                case 7:
                    orderHistory(user);
                    break;

                case 8:
                    viewNotifications(user);
                    break;

                case 9:
                    addReviewFlow(user);
                    break;

                case 10:
                    viewInvoice(user);
                    break;

                case 11:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select 1-9.");
            }
        }
    }




    private void browse() {
        List<Product> products = productService.browseAll();
        products.forEach(p ->
                System.out.println(p.getProductId() + " - " + p.getName()
                        + " - ₹" + p.getDiscountPrice()));
    }

    private void addToCart(User user) {

        try {

            System.out.print("Product ID: ");
            int pid = Integer.parseInt(sc.nextLine());

            System.out.print("Quantity: ");
            int qty = Integer.parseInt(sc.nextLine());

            buyerService.addToCart(new CartItem(user.getUserId(), pid, qty));

            System.out.println("\n✅ Product added to cart successfully!");

        } catch (NumberFormatException e) {

            System.out.println("Invalid input. Please enter numeric values.");

        } catch (Exception e) {

            System.out.println("Error adding product to cart: " + e.getMessage());
        }
    }

    private void viewCart(User user) {

        List<CartItem> cart = buyerService.viewCart(user.getUserId());

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\n========= YOUR CART =========");

        double total = 0;

        for (CartItem item : cart) {

            Product p = productService.getById(item.getProductId());

            double price = p.getDiscountPrice();
            double subtotal = price * item.getQuantity();
            total += subtotal;

            System.out.println("----------------------------------");
            System.out.println("Product ID  : " + p.getProductId());
            System.out.println("Name        : " + p.getName());
            System.out.println("Price       : ₹" + price);
            System.out.println("Quantity    : " + item.getQuantity());
            System.out.println("Subtotal    : ₹" + subtotal);
        }

        System.out.println("----------------------------------");
        System.out.println("Total Amount: ₹" + total);
        System.out.println("==================================\n");
    }
    private void checkoutFlow(User user) {

        List<CartItem> cart = buyerService.viewCart(user.getUserId());

        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Cannot checkout.");
            return;
        }

        System.out.println("\n========= ORDER SUMMARY =========");

        double total = 0;

        for (CartItem item : cart) {

            Product p = productService.getById(item.getProductId());
            double price = p.getDiscountPrice();
            double subtotal = price * item.getQuantity();
            total += subtotal;

            System.out.println("----------------------------------");
            System.out.println("Product : " + p.getName());
            System.out.println("Price   : ₹" + price);
            System.out.println("Qty     : " + item.getQuantity());
            System.out.println("Subtotal: ₹" + subtotal);
        }

        System.out.println("----------------------------------");
        System.out.println("Grand Total: ₹" + total);
        System.out.println("==================================");


    }




    private void orderHistory(User user) {

        List<Order> orders = buyerService.orderHistory(user.getUserId());

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n========= ORDER HISTORY =========");

        for (Order o : orders) {
            System.out.println("----------------------------------");
            System.out.println("Order ID    : " + o.getOrderId());
            System.out.println("Date        : " + o.getOrderDate());
            System.out.println("Total Amount: ₹" + o.getTotalAmount());
            System.out.println("Status      : " + o.getStatus());
        }

        System.out.println("==================================\n");
    }


    private void viewNotifications(User user) {
        notificationService.getUserNotifications(user.getUserId())
                .forEach(n -> System.out.println(n.getMessage()));
    }
    public void showBuyerSummary(User user) {

        System.out.println("\n===== BUYER SUMMARY =====");
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());

        try {
            int cartSize = buyerService.viewCart(user.getUserId()).size();
            int orderCount = buyerService.orderHistory(user.getUserId()).size();
            int notificationCount =
                    notificationService.getUserNotifications(user.getUserId()).size();

            System.out.println("Cart Items: " + cartSize);
            System.out.println("Total Orders: " + orderCount);
            System.out.println("Notifications: " + notificationCount);

        } catch (Exception e) {
            System.out.println("Summary data unavailable.");
        }

        System.out.println("=========================\n");
    }
    private void showCheckoutSummary(User user) {

        List<CartItem> cart = buyerService.viewCart(user.getUserId());

        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n========= ORDER SUMMARY =========");

        double total = 0;

        for (CartItem item : cart) {

            Product product = productService.getById(item.getProductId());

            double price = product.getDiscountPrice();
            double subtotal = price * item.getQuantity();
            total += subtotal;

            System.out.println("----------------------------------");
            System.out.println("Product : " + product.getName());
            System.out.println("Price   : ₹" + price);
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Subtotal: ₹" + subtotal);
        }

        System.out.println("----------------------------------");
        System.out.println("Total Amount: ₹" + total);
        System.out.println("==================================");
    }

    private void processPayment(User user) {

        List<CartItem> cart = buyerService.viewCart(user.getUserId());

        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Nothing to pay.");
            return;
        }

        System.out.println("\nSelect Payment Method:");
        System.out.println("1. UPI");
        System.out.println("2. Credit/Debit Card");
        System.out.println("3. Net Banking");

        System.out.print("Enter option: ");

        try {
            Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid payment method.");
            return;
        }

        System.out.println("Processing Payment...");
        System.out.println("Payment Successful!");

        buyerService.checkout(user.getUserId());   // 🔹 Create order here

        System.out.println("Order placed successfully!");
    }
    private void removeFromCart(User user) {

        List<CartItem> cart = buyerService.viewCart(user.getUserId());

        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Nothing to remove.");
            return;
        }

        System.out.println("\n========= YOUR CART =========");

        for (CartItem item : cart) {

            Product p = productService.getById(item.getProductId());

            System.out.println("Product ID: " + p.getProductId() +
                    " | Name: " + p.getName() +
                    " | Qty: " + item.getQuantity());
        }

        System.out.println("----------------------------------");
        System.out.print("Enter Product ID to remove: ");

        try {
            int pid = Integer.parseInt(sc.nextLine());

            buyerService.removeFromCart(user.getUserId(), pid);

            System.out.println("Product removed from cart successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        } catch (Exception e) {
            System.out.println("Error removing product: " + e.getMessage());
        }
    }
    private void addReviewFlow(User user) {

        System.out.print("Enter Product ID: ");
        int productId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Rating (1-5): ");
        int rating = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Comment: ");
        String comment = sc.nextLine();

        buyerService.addReview(productId, user.getUserId(), rating, comment);

        System.out.println("Review added successfully!");
    }
    private void viewInvoice(User user) {

        System.out.print("Enter Order ID: ");
        int orderId = Integer.parseInt(sc.nextLine());

        List<OrderItem> items = buyerService.getOrderItems(orderId);

        if (items.isEmpty()) {
            System.out.println("Invalid Order ID or no items found.");
            return;
        }

        System.out.println("\n=========== INVOICE ===========");
        System.out.println("Order ID: " + orderId);
        System.out.println("--------------------------------");

        double total = 0;

        for (OrderItem item : items) {

            Product p = productService.getById(item.getProductId());

            double subtotal = item.getPrice() * item.getQuantity();
            total += subtotal;

            System.out.println("Product : " + p.getName());
            System.out.println("Price   : ₹" + item.getPrice());
            System.out.println("Qty     : " + item.getQuantity());
            System.out.println("Subtotal: ₹" + subtotal);
            System.out.println("--------------------------------");
        }

        System.out.println("Grand Total: ₹" + total);
        System.out.println("================================\n");
    }
}

