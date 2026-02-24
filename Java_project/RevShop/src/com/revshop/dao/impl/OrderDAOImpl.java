package com.revshop.dao.impl;


import com.revshop.dao.OrderDAO;
import com.revshop.model.Order;
import com.revshop.model.OrderItem;
import com.revshop.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public int createOrder(Connection conn, Order order) {
        String sql = "INSERT INTO ORDERS (USER_ID, TOTAL_AMOUNT, STATUS) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, new String[]{"ORDER_ID"})) {

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating order", e);
        }
        return 0;
    }

    @Override
    public void addOrderItem(Connection conn, OrderItem item) {
        String sql = "INSERT INTO ORDER_ITEMS (ORDER_ID, PRODUCT_ID, QUANTITY, PRICE) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding order item", e);
        }
    }

    @Override
    public List<Order> getOrdersByUser(int userId) {

        String sql = "SELECT * FROM ORDERS WHERE USER_ID = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("ORDER_ID"));
                order.setUserId(rs.getInt("USER_ID"));
                order.setTotalAmount(rs.getDouble("TOTAL_AMOUNT"));
                order.setStatus(rs.getString("STATUS"));
                order.setOrderDate(rs.getDate("ORDER_DATE"));

                orders.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching orders", e);
        }

        return orders;
    }
    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {

        String sql = "SELECT * FROM ORDER_ITEMS WHERE ORDER_ID = ?";
        List<OrderItem> items = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                OrderItem item = new OrderItem();
                item.setOrderId(rs.getInt("ORDER_ID"));
                item.setProductId(rs.getInt("PRODUCT_ID"));
                item.setQuantity(rs.getInt("QUANTITY"));
                item.setPrice(rs.getDouble("PRICE"));

                items.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching invoice items", e);
        }

        return items;
    }

}
