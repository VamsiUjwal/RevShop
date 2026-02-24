package com.revshop.service;

import com.revshop.model.Notification;
import java.util.List;

public interface NotificationService {

    void notifyUser(int userId, String message);
    List<Notification> getUserNotifications(int userId);
}
