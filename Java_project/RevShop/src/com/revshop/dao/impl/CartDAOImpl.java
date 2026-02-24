package com.revshop.dao.impl;

import com.revshop.dao.CartDAO;
import com.revshop.model.CartItem;
import com.revshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {

    @Override
    public void addToCart(CartItem item) {
        String sql = "INSERT INTO CART (USER_ID, PRODUCT_ID, QUANTITY) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getUserId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQuantity());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding to cart", e);
        }
    }

    @Override
    public void removeFromCart(int userId, int productId) {
        String sql = "DELETE FROM CART WHERE USER_ID=? AND PRODUCT_ID=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error removing cart item", e);
        }
    }

    @Override
    public List<CartItem> getUserCart(int userId) {
        String sql = "SELECT * FROM CART WHERE USER_ID=?";
        List<CartItem> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartId(rs.getInt("CART_ID"));
                item.setUserId(rs.getInt("USER_ID"));
                item.setProductId(rs.getInt("PRODUCT_ID"));
                item.setQuantity(rs.getInt("QUANTITY"));
                list.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching cart", e);
        }
        return list;
    }

    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM CART WHERE USER_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error clearing cart", e);
        }
    }
}

