package com.revshop.dao;

import com.revshop.model.User;
import java.util.Optional;

public interface UserDAO {

    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(int userId);
    void updatePassword(int userId, String newPassword);
}

