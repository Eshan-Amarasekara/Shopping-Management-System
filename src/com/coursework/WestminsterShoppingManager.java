package com.coursework;
import javax.swing.text.View;
import java.util.*;
import java.io.*;

class WestminsterShoppingManager implements ShoppingManager {
    private static final int Max_Products = 50;
    public static List<Product> productList;
    private int numObject;

    public WestminsterShoppingManager(int listLength) {
        this.numObject = listLength;
        this.productList = new ArrayList<Product>();
        loadProductsFromFile(); // Load products from file when the application starts
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public void displayConsole() {
        boolean exit= false;
        try{
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print the list of products");
        System.out.println("4. Save products to file");
        System.out.println("5. Open GUI for Customers");
        System.out.println("6. Exit");
        System.out.print("Option: ");

        Scanner scanner = new Scanner(System.in);
        int choice= scanner.nextInt();

        switch (choice) {
            case 1:


                System.out.println("Select product type: ");
                System.out.println("1. Electronics");
                System.out.println("2. Clothing");
                int productTypeChoice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                System.out.println("Enter product ID: ");
                String productId = scanner.nextLine();

                System.out.println("Enter product name: ");
                String productName = scanner.nextLine();

                System.out.println("Enter available items: ");
                int availableItems = scanner.nextInt();

                System.out.println("Enter price: ");
                double price = scanner.nextDouble();

                switch (productTypeChoice) {
                    case 1:
                        // Add Electronics
                        System.out.println("Enter brand: ");
                        String brand = scanner.next();

                        System.out.println("Enter warranty period (in months): ");
                        int warrantyPeriod = scanner.nextInt();

                        Electronics e = new Electronics(productId, productName, availableItems,price, brand,warrantyPeriod);
                        this.addProduct(e);
                        break;

                    case 2:
                        // Add Clothing
                        System.out.println("Enter size: ");
                        String size = scanner.next();

                        System.out.println("Colour: ");
                        String colour = scanner.next();

                        Clothing c = new Clothing(productId, productName, availableItems,price, size,colour);
                        this.addProduct(c);
                        break;

                    default:
                        System.out.println("Invalid choice. Product not added.");
                }
                System.out.println("Product added successfully!");
                break;
            case 2:
                deleteProduct();
                break;
            case 3:
                printProductList();
                break;
            case 4:
                saveProductsToFile();
                break;
            case 5:
                Login login = new Login();
                login.setVisible(true);
                ViewProduct viewProduct = new ViewProduct(this);
                viewProduct.setVisible(true);
                break;

            case 6:

                System.exit(0);
                exit= true;
                saveProductsToFile();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a valid option.");
    } catch (Exception e) {
        System.out.println("An error occurred: " + e.getMessage());
    }
}


    public void addProduct(Product product) {
        if (productList.size() <= Max_Products) {
            productList.add(product);
        } else {
            System.out.println("Maximum number of products reached. Cannot add more.");
        }
    }


    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the product ID to delete:");
            String productIdToDelete = scanner.next();
            boolean productFound = false;

            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                if (product.getProductId().equals(productIdToDelete)) {
                    productList.remove(i); // Remove the product from the list
                    productFound = true;
                    break;
                }
            }

            if (productFound) {
                System.out.println("Product with ID " + productIdToDelete + " deleted successfully.");
            } else {
                System.out.println("Product with ID " + productIdToDelete + " not found.");
            }

            System.out.println("Total number of products left: " + productList.size());
    }

    public void printProductList() {
        // Sort the product list alphabetically by product ID
        Collections.sort(productList, Comparator.comparing(Product::getProductId));

        // Display information for each product in the system
        for (int i=0; i<productList.size(); i++) {
            if (productList.get(i).getType().equals("Electronics")) {
                System.out.println("Type: Electronics");
                System.out.println(productList.get(i).toString());
            } else if (productList.get(i).getType().equals("Clothing")) {
                System.out.println("Type: Clothing");
                System.out.println(productList.get(i).toString());
            }
            System.out.println();
        }
    }

    public void saveProductsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"))) {
            for (Product product : productList) {
                writer.write(product.toString()); // Assuming your Product class has a toString() method
                writer.newLine();
            }
            System.out.println("Product details saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Load the list of products from a file
    public void loadProductsFromFile() {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("products.txt"));
            String line = "";
            ArrayList<String> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            System.out.println(list);

        } catch (FileNotFoundException e) {
            System.out.println("No saved data found.");
        } catch (IOException e) {
            System.out.println("Error loading product list from file: " + e.getMessage());
            e.printStackTrace();
        }

    }






    public static void main(String[] args){
        ShoppingManager shoppingManager = new WestminsterShoppingManager(10);
        boolean exit = false;

        while (!exit) {
            shoppingManager.displayConsole();
        }
    }
}