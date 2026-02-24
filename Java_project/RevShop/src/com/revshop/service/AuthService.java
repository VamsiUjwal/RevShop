package com.revshop.service;

import com.revshop.model.User;

public interface AuthService {

    void registerBuyer(User user);
    void registerSeller(User user, String businessName, String gst);

    User login(String email, String password);

    void changePassword(int userId, String oldPassword, String newPassword);
    void forgotPassword(String email, String securityAnswer, String newPassword);
}
