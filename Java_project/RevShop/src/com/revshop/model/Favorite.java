package com.revshop.model;

public class Favorite {

    private int favId;
    private int userId;
    private int productId;

    public Favorite() {}

    public Favorite(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public int getFavId() { return favId; }
    public void setFavId(int favId) { this.favId = favId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
}

