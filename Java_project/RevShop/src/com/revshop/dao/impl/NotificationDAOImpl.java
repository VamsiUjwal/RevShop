package com.revshop.dao.impl;

import com.revshop.dao.NotificationDAO;
import com.revshop.model.Notification;
import com.revshop.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO {

    @Override
    public void save(Notification notification) {
        String sql = "INSERT INTO NOTIFICATIONS (USER_ID, MESSAGE, STATUS) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getMessage());
            ps.setString(3, notification.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving notification", e);
        }
    }

    @Override
    public List<Notification> getUserNotifications(int userId) {
        String sql = "SELECT * FROM NOTIFICATIONS WHERE USER_ID=?";
        List<Notification> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setNotifId(rs.getInt("NOTIF_ID"));
                n.setUserId(rs.getInt("USER_ID"));
                n.setMessage(rs.getString("MESSAGE"));
                n.setStatus(rs.getString("STATUS"));
                n.setCreatedAt(rs.getDate("CREATED_AT"));
                list.add(n);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching notifications", e);
        }
        return list;
    }
}

