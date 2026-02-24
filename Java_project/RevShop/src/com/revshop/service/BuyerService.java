package com.revshop.service;

import com.revshop.model.CartItem;
import com.revshop.model.Order;
import com.revshop.model.OrderItem;

import java.util.List;

public interface BuyerService {

    void addToCart(CartItem item);
    void removeFromCart(int userId, int productId);
    List<CartItem> viewCart(int userId);

    void checkout(int userId);

    List<Order> orderHistory(int userId);
    List<OrderItem> getOrderItems(int orderId);
    void addReview(int productId, int userId, int rating, String comment);

    void addFavorite(int userId, int productId);
    void removeFavorite(int userId, int productId);
}

