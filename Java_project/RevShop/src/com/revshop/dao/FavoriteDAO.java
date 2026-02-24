package com.revshop.dao;

import java.util.List;

public interface FavoriteDAO {

    void addFavorite(int userId, int productId);
    void removeFavorite(int userId, int productId);
    List<Integer> getFavorites(int userId);
}

