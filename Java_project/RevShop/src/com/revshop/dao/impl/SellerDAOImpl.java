package com.revshop.dao.impl;

import com.revshop.dao.SellerDAO;
import com.revshop.model.Seller;
import com.revshop.util.DatabaseConnection;

import java.sql.*;
import java.util.Optional;

public class SellerDAOImpl implements SellerDAO {

    @Override
    public void save(Seller seller) {
        String sql = "INSERT INTO SELLERS (USER_ID, BUSINESS_NAME, GST_NUMBER) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seller.getUserId());
            ps.setString(2, seller.getBusinessName());
            ps.setString(3, seller.getGstNumber());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving seller", e);
        }
    }

    @Override
    public Optional<Seller> findByUserId(int userId) {

        String sql = "SELECT * FROM SELLERS WHERE USER_ID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Seller seller = new Seller();
                seller.setSellerId(rs.getInt("SELLER_ID"));
                seller.setUserId(rs.getInt("USER_ID"));
                seller.setBusinessName(rs.getString("BUSINESS_NAME"));
                seller.setGstNumber(rs.getString("GST_NUMBER"));
                return Optional.of(seller);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching seller", e);
        }

        return Optional.empty();
    }


}
