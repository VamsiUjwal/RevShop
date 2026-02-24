package com.revshop.service.impl;

import com.revshop.dao.ProductDAO;
import com.revshop.dao.impl.ProductDAOImpl;
import com.revshop.model.Product;
import com.revshop.service.ProductService;
import java.util.List;


public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public void addProduct(Product product) {
        productDAO.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    @Override
    public void deleteProduct(int productId) {
        productDAO.delete(productId);
    }

    @Override
    public List<Product> browseAll() {
        return productDAO.findAll();
    }

    @Override
    public List<Product> filterByCategory(String category) {
        return productDAO.findByCategory(category);
    }

    @Override
    public List<Product> search(String keyword) {
        return productDAO.searchByKeyword(keyword);
    }

    @Override
    public Product getById(int productId) {
        return productDAO.findById(productId).orElseThrow();
    }
    @Override
    public List<Product> getProductsBySellerId(int sellerId) {
        return productDAO.findBySellerId(sellerId);
    }
}
