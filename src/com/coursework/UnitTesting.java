package com.coursework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitTesting {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Electronics("1", "Laptop", 10, 1000, "Dell", 36));
        products.add(new Clothing("2", "Shirt", 20, 50, "L", "Red"));

        WestminsterShoppingManager wsm = new WestminsterShoppingManager(10);
        wsm.productList = products;

        assertEquals(products, wsm.getAllProducts());
    }

    @Test
    void addProduct() {
        WestminsterShoppingManager wsm = new WestminsterShoppingManager(10);

        wsm.addProduct(new Electronics("1", "Laptop", 10, 1000, "Dell", 36));
        wsm.addProduct(new Clothing("2", "Shirt", 20, 50, "L", "Red"));

        assertEquals(2, wsm.productList.size());
    }

    @Test
    void deleteProduct() {
        // Create a list of products
        List<Product> products = new ArrayList<>();
        products.add(new Electronics("1", "Laptop", 10, 1000, "Dell", 36));
        products.add(new Clothing("2", "Shirt", 20, 50, "L", "Red"));

        // Create an instance of WestminsterShoppingManager and set the productList
        WestminsterShoppingManager wsm = new WestminsterShoppingManager(10);
        wsm.productList = new ArrayList<>(products);

        // Call the deleteProduct method
        wsm.deleteProduct();

        // Assert that the productList has the expected size after deletion
        assertEquals(1, wsm.productList.size());

        // Assert that the remaining product is the expected one
        Product remainingProduct = wsm.productList.get(0);
        assertEquals("2", remainingProduct.getProductId());
        assertEquals("Shirt", remainingProduct.getProductName());
        assertEquals(20, remainingProduct.getItemCount());
        assertEquals(50, remainingProduct.getPrice());
        assertEquals("L", ((Clothing) remainingProduct).getSize());
        assertEquals("Red", ((Clothing) remainingProduct).getColour());
    }

    @Test
    void printProductList() {
        List<Product> products = new ArrayList<>();
        products.add(new Electronics("1", "Laptop", 10, 1000, "Dell", 36));
        products.add(new Clothing("2", "Shirt", 20, 50, "L", "Red"));

        WestminsterShoppingManager wsm = new WestminsterShoppingManager(10);
        wsm.productList = products;

        System.setOut(new PrintStream(new ByteArrayOutputStream())); // Reset the output stream
        wsm.printProductList();

        assertTrue(outContent.toString().contains("Type: Electronics"));
        assertTrue(outContent.toString().contains("Type: Clothing"));
    }

}