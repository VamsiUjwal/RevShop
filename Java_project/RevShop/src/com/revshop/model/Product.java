package com.revshop.model;

public class Product {

    private int productId;
    private int sellerId;
    private String name;
    private String category;
    private String description;
    private double mrp;
    private double discountPrice;
    private int stock;
    private int stockThreshold;

    public Product() {}

    public Product(int sellerId, String name, String category,
                   String description, double mrp,
                   double discountPrice, int stock, int stockThreshold) {
        this.sellerId = sellerId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.mrp = mrp;
        this.discountPrice = discountPrice;
        this.stock = stock;
        this.stockThreshold = stockThreshold;
    }

    // Getters & Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getMrp() { return mrp; }
    public void setMrp(double mrp) { this.mrp = mrp; }

    public double getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(double discountPrice) { this.discountPrice = discountPrice; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getStockThreshold() { return stockThreshold; }
    public void setStockThreshold(int stockThreshold) { this.stockThreshold = stockThreshold; }
}

