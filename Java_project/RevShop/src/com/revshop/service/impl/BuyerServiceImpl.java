package com.revshop.service.impl;

import com.revshop.dao.*;
import com.revshop.dao.impl.*;
import com.revshop.model.*;
import com.revshop.service.BuyerService;
import com.revshop.service.NotificationService;
import com.revshop.util.DatabaseConnection;


import java.sql.Connection;
import java.util.List;

public class BuyerServiceImpl implements BuyerService {

    private final CartDAO cartDAO = new CartDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final ReviewDAO reviewDAO = new ReviewDAOImpl();
    private final FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
    private final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public void addToCart(CartItem item) {
        cartDAO.addToCart(item);
    }

    @Override
    public void removeFromCart(int userId, int productId) {
        cartDAO.removeFromCart(userId, productId);
    }

    @Override
    public List<CartItem> viewCart(int userId) {
        return cartDAO.getUserCart(userId);
    }

    @Override
    public void checkout(int userId) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false);

            List<CartItem> cartItems = cartDAO.getUserCart(userId);

            double total = 0;

            for (CartItem item : cartItems) {
                Product p = productDAO.findById(item.getProductId()).orElseThrow();

                if (p.getStock() < item.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for " + p.getName());
                }

                total += p.getDiscountPrice() * item.getQuantity();
            }

            // Simulated Payment
            System.out.println("Processing Payment...");
            Thread.sleep(1000);
            System.out.println("Payment Successful!");

            Order order = new Order(userId, total, "CONFIRMED");
            int orderId = orderDAO.createOrder(conn, order);

            for (CartItem item : cartItems) {

                Product p = productDAO.findById(item.getProductId()).orElseThrow();

                OrderItem orderItem = new OrderItem(orderId,
                        item.getProductId(),
                        item.getQuantity(),
                        p.getDiscountPrice());

                orderDAO.addOrderItem(conn, orderItem);

                int newStock = p.getStock() - item.getQuantity();
                productDAO.updateStock(p.getProductId(), newStock);

                if (newStock <= p.getStockThreshold()) {
                    notificationService.notifyUser(
                            p.getSellerId(),
                            "Stock low for product: " + p.getName());
                }
            }

            cartDAO.clearCart(userId);

            conn.commit();

            notificationService.notifyUser(userId,
                    "Order placed successfully! Order ID: " + orderId);

        } catch (Exception e) {
            throw new RuntimeException("Checkout failed: " + e.getMessage());
        }
    }

    @Override
    public List<Order> orderHistory(int userId) {
        return orderDAO.getOrdersByUser(userId);
    }
    @Override
    public List<OrderItem> getOrderItems(int orderId) {
        return orderDAO.getOrderItemsByOrderId(orderId);
    }

    @Override
    public void addReview(int productId, int userId, int rating, String comment) {
        reviewDAO.addReview(new Review(productId, userId, rating, comment));
    }

    @Override
    public void addFavorite(int userId, int productId) {
        favoriteDAO.addFavorite(userId, productId);
    }

    @Override
    public void removeFavorite(int userId, int productId) {
        favoriteDAO.removeFavorite(userId, productId);
    }

}

