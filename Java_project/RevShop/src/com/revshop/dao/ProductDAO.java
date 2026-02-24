package com.revshop.dao;

import com.revshop.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    void save(Product product);
    void update(Product product);
    void delete(int productId);

    Optional<Product> findById(int productId);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> searchByKeyword(String keyword);
    List<Product> findBySellerId(int sellerId);

    void updateStock(int productId, int newStock);
}
