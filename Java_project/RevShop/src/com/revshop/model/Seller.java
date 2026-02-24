package com.revshop.model;

public class Seller {

    private int sellerId;
    private int userId;
    private String businessName;
    private String gstNumber;

    public Seller() {}

    public Seller(int userId, String businessName, String gstNumber) {
        this.userId = userId;
        this.businessName = businessName;
        this.gstNumber = gstNumber;
    }

    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }
}

