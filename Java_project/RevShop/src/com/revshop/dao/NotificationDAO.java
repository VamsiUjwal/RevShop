package com.revshop.dao;

import com.revshop.model.Notification;
import java.util.List;

public interface NotificationDAO {

    void save(Notification notification);
    List<Notification> getUserNotifications(int userId);
}
