package com.revshop.dao;

import com.revshop.model.CartItem;
import java.util.List;

public interface CartDAO {

    void addToCart(CartItem item);
    void removeFromCart(int userId, int productId);
    List<CartItem> getUserCart(int userId);
    void clearCart(int userId);
}
