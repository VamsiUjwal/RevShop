package com.revshop.dao.impl;

import com.revshop.dao.ReviewDAO;
import com.revshop.model.Review;
import com.revshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {

    @Override
    public void addReview(Review review) {

        String sql = "INSERT INTO REVIEWS (PRODUCT_ID, USER_ID, RATING, REVIEW_COMMENT) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, review.getProductId());
            ps.setInt(2, review.getUserId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding review", e);
        }
    }

    @Override
    public List<Review> getReviewsByProduct(int productId) {

        String sql = "SELECT * FROM REVIEWS WHERE PRODUCT_ID=?";
        List<Review> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Review r = new Review();
                r.setReviewId(rs.getInt("REVIEW_ID"));
                r.setProductId(rs.getInt("PRODUCT_ID"));
                r.setUserId(rs.getInt("USER_ID"));
                r.setRating(rs.getInt("RATING"));
                r.setComment(rs.getString("REVIEW_COMMENT"));  // ✅ FIXED
                list.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching reviews", e);
        }

        return list;
    }
}