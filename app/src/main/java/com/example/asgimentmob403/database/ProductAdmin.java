package com.example.asgimentmob403.database;

public class ProductAdmin {
    private String id;
    private String brand;
    private String description;
    private String productName;
    private int productPrice;
    private String urlImg;

    public ProductAdmin() {
    }

    public ProductAdmin( String brand, String description, String productName, int productPrice, String urlImg) {
        this.brand = brand;
        this.description = description;
        this.productName = productName;
        this.productPrice = productPrice;
        this.urlImg = urlImg;
    }

    public ProductAdmin(String brand, String productName) {
        this.brand = brand;
        this.productName = productName;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
