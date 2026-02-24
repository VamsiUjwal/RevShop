package com.revshop.service.impl;

import com.revshop.dao.*;
import com.revshop.dao.impl.*;
import com.revshop.model.*;
import com.revshop.service.AuthService;
import com.revshop.util.PasswordUtil;

public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO = new UserDAOImpl();
    private final SellerDAO sellerDAO = new SellerDAOImpl();

    @Override
    public void registerBuyer(User user) {

        if (userDAO.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        // HASH PASSWORD BEFORE SAVING
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        user.setRole("BUYER");

        userDAO.save(user);
    }


    @Override
    public void registerSeller(User user, String businessName, String gst) {
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        user.setRole("SELLER");
        userDAO.save(user);

        User saved = userDAO.findByEmail(user.getEmail()).orElseThrow();
        sellerDAO.save(new Seller(saved.getUserId(), businessName, gst));
    }

    @Override
    public User login(String email, String password) {

        User user = userDAO.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String hashedInput = PasswordUtil.hashPassword(password);

        if (!user.getPassword().equals(hashedInput)) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }


    @Override
    public void changePassword(int userId, String oldPassword, String newPassword) {
        User user = userDAO.findById(userId).orElseThrow();

        if (!user.getPassword().equals(PasswordUtil.hashPassword(oldPassword))) {
            throw new RuntimeException("Old password incorrect");
        }

        userDAO.updatePassword(userId, PasswordUtil.hashPassword(newPassword));
    }

    @Override
    public void forgotPassword(String email, String securityAnswer, String newPassword) {
        User user = userDAO.findByEmail(email).orElseThrow();

        if (!user.getSecurityAnswer().equalsIgnoreCase(securityAnswer)) {
            throw new RuntimeException("Security answer incorrect");
        }

        userDAO.updatePassword(user.getUserId(),
                PasswordUtil.hashPassword(newPassword));
    }
}

