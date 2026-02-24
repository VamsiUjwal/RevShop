package com.revshop.model;

import java.util.Date;

public class Notification {

    private int notifId;
    private int userId;
    private String message;
    private String status;
    private Date createdAt;

    public Notification() {}

    public Notification(int userId, String message, String status) {
        this.userId = userId;
        this.message = message;
        this.status = status;
    }

    public int getNotifId() { return notifId; }
    public void setNotifId(int notifId) { this.notifId = notifId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
