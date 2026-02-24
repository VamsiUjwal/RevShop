package com.revshop.service;

import com.revshop.model.Product;
import java.util.List;

public interface SellerService {

    List<Product> getSellerProducts(int sellerId);
}

