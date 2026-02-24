package com.revshop.service;

import com.revshop.model.Product;
import java.util.List;


public interface ProductService {

    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int productId);

    List<Product> browseAll();
    List<Product> filterByCategory(String category);
    List<Product> search(String keyword);
    Product getById(int productId);
    List<Product> getProductsBySellerId(int sellerId);
}
