package com.revshop.service.impl;

import com.revshop.dao.NotificationDAO;
import com.revshop.dao.impl.NotificationDAOImpl;
import com.revshop.model.Notification;
import com.revshop.service.NotificationService;

import java.util.List;

public class NotificationServiceImpl implements NotificationService {

    private final NotificationDAO notificationDAO = new NotificationDAOImpl();

    @Override
    public void notifyUser(int userId, String message) {
        notificationDAO.save(new Notification(userId, message, "UNREAD"));
        System.out.println("[NOTIFICATION] " + message);
    }

    @Override
    public List<Notification> getUserNotifications(int userId) {
        return notificationDAO.getUserNotifications(userId);
    }
}

