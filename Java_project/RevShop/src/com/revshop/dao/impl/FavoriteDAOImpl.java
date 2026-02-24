package com.revshop.dao.impl;

import com.revshop.dao.FavoriteDAO;
import com.revshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAOImpl implements FavoriteDAO {

    @Override
    public void addFavorite(int userId, int productId) {
        String sql = "INSERT INTO FAVORITES (USER_ID, PRODUCT_ID) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding favorite", e);
        }
    }

    @Override
    public void removeFavorite(int userId, int productId) {
        String sql = "DELETE FROM FAVORITES WHERE USER_ID=? AND PRODUCT_ID=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error removing favorite", e);
        }
    }

    @Override
    public List<Integer> getFavorites(int userId) {
        String sql = "SELECT PRODUCT_ID FROM FAVORITES WHERE USER_ID=?";
        List<Integer> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("PRODUCT_ID"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching favorites", e);
        }
        return list;
    }
}

