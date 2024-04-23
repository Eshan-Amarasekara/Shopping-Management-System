package com.coursework;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Electronics extends Product {
    private String brand;       //Brand of electronic product
    private int warrantyPeriod; //Warranty Period in days


    //Constructor including all variables
    public Electronics(String productId, String productName, int itemCount, double price, String brand, int warrantyPeriod) {
        super(productId, productName, itemCount, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //Constructor excluding itemCount
    public Electronics(String productId, String productName, double price, String brand, int warrantyPeriod) {
        super(productId, productName, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public Electronics(String brand, int warrantyPeriod) {
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //Default Constructor
    public Electronics() {
    }

    //Getters
    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public String getType() {
        return "Electronics";
    }

    //Setters
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    //toString method to display details of the elrctronics
    @Override
    public String toString() {
        return "\n Product Id: " + getProductId() +
                ", Product Name: " + getProductName() +
                ", Product Type: Electronics"+
                ", Price: " + getPrice()+
                ", Brand: '" + brand +
                ", Warranty Period: " + warrantyPeriod +
                ", Available Items: " + getItemCount()
                ;
    }

}



