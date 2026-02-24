package com.revshop.dao;

import com.revshop.model.Review;
import java.util.List;

public interface ReviewDAO {

    void addReview(Review review);
    List<Review> getReviewsByProduct(int productId);
}

