package com.coursework;

public class Clothing extends Product{
    private String size;    //Clothing item size
    private String colour;  //Clothing item colour

    //Constructor including all variables
    public Clothing(String productId, String productName, int itemCount, double price, String size, String colour) {
        super(productId, productName, itemCount, price);
        this.size = size;
        this.colour = colour;
    }

    //Constructor excluding itemCount
    public Clothing(String productId, String productName, double price, String size, String colour) {
        super(productId, productName, price);
        this.size = size;
        this.colour = colour;
    }

    public Clothing(String size, String colour) {
        this.size = size;
        this.colour = colour;
    }

    //Default Constructor
    public Clothing(){

    }

    //Getters
    public String getSize() {
        return size;
    }
    public String getColour() {
        return colour;
    }

    public String getType(){
        return "Clothing";
    }
    //Setters
    public void setSize(String size) {
        this.size = size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    //toString method to display details of the clothing
    @Override
    public String toString() {
        return "\n Product Id: " + getProductId() +
                ", Product Name: " + getProductName() +
                ", Product Type: Clothing" +
                ", Price: " + getPrice() +
                ", Size: '" + size +
                ", Colour : " + colour +
                ", Available Items: " + getItemCount()
                ;
    }
}
