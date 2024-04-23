package com.coursework;
import java.util.StringTokenizer;
public abstract class Product {

    private String productId;   //Product ID
    private String productName; //Product Name
    private int itemCount;      //Number of available items
    private double price;       //Price of item

    //Constructor including all variables
    public Product(String productId, String productName, int itemCount, double price) {
        this.productId = productId;
        this.productName = productName;
        this.itemCount = itemCount;
        this.price = price;
    }

    //Constructor excluding itemCount
    public Product(String productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        itemCount=1;
    }

    //Default Constructor
    public Product() {
    }

    //Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getType();

    //Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //toString method to display details of the class objects
    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", itemCount=" + itemCount +
                ", price=" + price +
                '}';
    }

}
