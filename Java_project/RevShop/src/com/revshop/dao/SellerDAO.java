package com.revshop.dao;

import com.revshop.model.Seller;
import java.util.Optional;

public interface SellerDAO {

    void save(Seller seller);
    Optional<Seller> findByUserId(int userId);
}

