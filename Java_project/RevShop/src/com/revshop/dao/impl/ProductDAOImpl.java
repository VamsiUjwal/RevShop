package com.revshop.dao.impl;

import com.revshop.dao.ProductDAO;
import com.revshop.model.Product;
import com.revshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO PRODUCTS (SELLER_ID, NAME, CATEGORY, DESCRIPTION, MRP, DISCOUNT_PRICE, STOCK, STOCK_THRESHOLD) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, product.getSellerId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getCategory());
            ps.setString(4, product.getDescription());
            ps.setDouble(5, product.getMrp());
            ps.setDouble(6, product.getDiscountPrice());
            ps.setInt(7, product.getStock());
            ps.setInt(8, product.getStockThreshold());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving product", e);
        }
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE PRODUCTS SET NAME=?, CATEGORY=?, DESCRIPTION=?, MRP=?, DISCOUNT_PRICE=?, STOCK=?, STOCK_THRESHOLD=? WHERE PRODUCT_ID=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getMrp());
            ps.setDouble(5, product.getDiscountPrice());
            ps.setInt(6, product.getStock());
            ps.setInt(7, product.getStockThreshold());
            ps.setInt(8, product.getProductId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating product", e);
        }
    }

    @Override
    public void delete(int productId) {

        try (Connection conn = DatabaseConnection.getConnection()) {

            conn.setAutoCommit(false);

            // 1️⃣ Delete from ORDER_ITEMS
            try (PreparedStatement ps1 =
                         conn.prepareStatement("DELETE FROM ORDER_ITEMS WHERE PRODUCT_ID=?")) {
                ps1.setInt(1, productId);
                ps1.executeUpdate();
            }

            // 2️⃣ Delete from CART
            try (PreparedStatement ps2 =
                         conn.prepareStatement("DELETE FROM CART WHERE PRODUCT_ID=?")) {
                ps2.setInt(1, productId);
                ps2.executeUpdate();
            }

            // 3️⃣ Delete from REVIEWS
            try (PreparedStatement ps3 =
                         conn.prepareStatement("DELETE FROM REVIEWS WHERE PRODUCT_ID=?")) {
                ps3.setInt(1, productId);
                ps3.executeUpdate();
            }

            // 4️⃣ Delete from FAVORITES
            try (PreparedStatement ps4 =
                         conn.prepareStatement("DELETE FROM FAVORITES WHERE PRODUCT_ID=?")) {
                ps4.setInt(1, productId);
                ps4.executeUpdate();
            }

            // 5️⃣ Finally delete product
            try (PreparedStatement ps5 =
                         conn.prepareStatement("DELETE FROM PRODUCTS WHERE PRODUCT_ID=?")) {
                ps5.setInt(1, productId);
                ps5.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product", e);
        }
    }

    @Override
    public Optional<Product> findById(int productId) {
        String sql = "SELECT * FROM PRODUCTS WHERE PRODUCT_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching product", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM PRODUCTS";
        List<Product> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching products", e);
        }
        return list;
    }

    @Override
    public List<Product> findByCategory(String category) {
        String sql = "SELECT * FROM PRODUCTS WHERE CATEGORY=?";
        List<Product> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error filtering products", e);
        }
        return list;
    }

    @Override
    public List<Product> searchByKeyword(String keyword) {
        String sql = "SELECT * FROM PRODUCTS WHERE LOWER(NAME) LIKE ? OR LOWER(DESCRIPTION) LIKE ?";
        List<Product> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String key = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, key);
            ps.setString(2, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching products", e);
        }
        return list;
    }

    @Override
    public void updateStock(int productId, int newStock) {
        String sql = "UPDATE PRODUCTS SET STOCK=? WHERE PRODUCT_ID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newStock);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating stock", e);
        }
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("PRODUCT_ID"));
        p.setSellerId(rs.getInt("SELLER_ID"));
        p.setName(rs.getString("NAME"));
        p.setCategory(rs.getString("CATEGORY"));
        p.setDescription(rs.getString("DESCRIPTION"));
        p.setMrp(rs.getDouble("MRP"));
        p.setDiscountPrice(rs.getDouble("DISCOUNT_PRICE"));
        p.setStock(rs.getInt("STOCK"));
        p.setStockThreshold(rs.getInt("STOCK_THRESHOLD"));
        return p;
    }
    @Override
    public List<Product> findBySellerId(int sellerId) {

        String sql = "SELECT * FROM PRODUCTS WHERE SELLER_ID = ?";

        List<Product> products = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product p = new Product();

                p.setProductId(rs.getInt("PRODUCT_ID"));
                p.setName(rs.getString("NAME"));
                p.setCategory(rs.getString("CATEGORY"));
                p.setDescription(rs.getString("DESCRIPTION"));
                p.setMrp(rs.getDouble("MRP"));
                p.setDiscountPrice(rs.getDouble("DISCOUNT_PRICE"));
                p.setStock(rs.getInt("STOCK"));
                p.setStockThreshold(rs.getInt("STOCK_THRESHOLD"));

                products.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching seller products", e);
        }

        return products;
    }
}

