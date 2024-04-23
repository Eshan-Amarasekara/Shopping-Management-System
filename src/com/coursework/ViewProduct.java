package com.coursework;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class ViewProduct extends JFrame implements ActionListener {

    private int itemCount;
    private int categoryCount;
    private Map<String, Integer> categoryItemCount;

    //Buttons
    private JButton addBtn;
    private JButton cartBtn;

    //ComboBox for drop down list
    private JComboBox<String> categoryMenu;

    private WestminsterShoppingManager shoppingManager;

    //Tables
    private DefaultTableModel tableModel;
    private JTable productsTable;
    private JTable shoppingCartTable;

    //Labels
    private JLabel dropDownLabel;
    private JLabel selectedProductsLabel;
    private JLabel productID;
    private JLabel categoryLbl;
    private JLabel nameLbl;
    private JLabel sizeLbl;
    private JLabel colourLbl;
    private JLabel itemCountLbl;
    private JLabel brandlbl;
    private JLabel WarrantyLbl;
    private JLabel subTotalLbl;
    private JLabel discount10Lbl;
    private JLabel discount20Lbl;
    private JLabel totalLbl;

    private JFrame shoppingFrame;

    private JPanel shoppingPanel;

    //Method to update table
    private void updateTable() {
        tableModel.setRowCount(0);
        for (Product product : shoppingManager.productList) {
            if (categoryMenu.getSelectedItem().equals("All") ||
            (categoryMenu.getSelectedItem().equals("Electronics") && product instanceof Electronics)||
            (categoryMenu.getSelectedItem().equals("Clothing") && product instanceof Clothing)) {
                Object[] rowData = {
                        product.getProductId(), product.getProductName(),
                        product instanceof Electronics ?  "Electronics": "Clothing" ,
                        product.getPrice(), getProductInfo(product)
                };
                tableModel.addRow(rowData);
            }
        }
    }

    //Get product info
    private Product getProductInfo(String info) {
        String[] tokens = info.split(", ");
        String type = tokens[0];

        if (type.contains("Brand")) {
            String brand = tokens[0].substring(tokens[0].indexOf(":") + 1).trim();
            String warranty = tokens[1].substring(tokens[1].indexOf(":") + 1).trim();
            return new Electronics("", "", 0, 0, brand, Integer.parseInt(warranty.split(" ")[0]));
        } else if (type.contains("Size")) {
            String size = tokens[0].substring(tokens[0].indexOf(":") + 1).trim();
            String colour = tokens[1].substring(tokens[1].indexOf(":") + 1).trim();
            return new Clothing("", "", 0, 0, size, colour);
        } else {
            return null;
        }
    }

    //Method to get product information for Product class
    private String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            return "Brand: " + ((Electronics) product).getBrand() +
                    ", Warranty: " + ((Electronics) product).getWarrantyPeriod() + "- months ";

        } else if (product instanceof Clothing) {
            return "Size: " + ((Clothing) product).getSize() +
                    ", Colour: " + ((Clothing) product).getColour();

        } else {
            return "No Information";
        }
    }


    //Main GUI Method
    ViewProduct(WestminsterShoppingManager shoppingManager) {
        itemCount = 0;
        categoryCount = 0;
        categoryItemCount = new HashMap<>();
        this.shoppingManager = shoppingManager;
        this.setPreferredSize(new Dimension(540, 680));
        String[] menu = {"All", "Electronics", "Clothing"};

        dropDownLabel = new JLabel("Select Product Category");
        categoryMenu = new JComboBox<>(menu);
        categoryMenu.addActionListener(this);

        addBtn = new JButton("Add to Shopping Cart");
        addBtn.setFocusable(false);
        addBtn.addActionListener(this);

        selectedProductsLabel = new JLabel(" Selected Products  -  Details");
        selectedProductsLabel.setFont(new Font("Roboto", Font.BOLD, 16));

        cartBtn = new JButton("Shopping Cart");
        cartBtn.setFocusable(false);
        cartBtn.addActionListener(this);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price");
        tableModel.addColumn("Additional Info");


        productsTable = new JTable(tableModel);
        productsTable.setRowHeight(30);


        productsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row == 0) {
                    // Set background color for the first row
                    cellComponent.setBackground(Color.YELLOW); // Change Color.YELLOW to the color of your choice
                } else {
                    // Use the default background color for other rows
                    cellComponent.setBackground(table.getBackground());
                }

                return cellComponent;
            }
        });
        updateTable();
        criticalStockLevel();

        shoppingPanel = new JPanel();
        shoppingPanel.setPreferredSize(new Dimension(540, 480));
        shoppingPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        shoppingPanel.setVisible(false);
        //Adding the labels
        productID = new JLabel(" Product ID: ");
        categoryLbl = new JLabel(" Category: ");
        nameLbl = new JLabel(" Product Name: ");
        sizeLbl = new JLabel(" Size: ");
        colourLbl = new JLabel(" Colour: ");
        itemCountLbl = new JLabel();
        brandlbl = new JLabel(" Brand: ");
        WarrantyLbl = new JLabel(" Warranty: ");
        subTotalLbl = new JLabel(" Sub Total :");
        discount10Lbl = new JLabel(" 10% Discount for first purchase: ");
        discount20Lbl = new JLabel(" 20% Discount for three same category purchase: ");
        totalLbl = new JLabel(" Total: ");
        totalLbl.setFont(new Font("Roboto", Font.BOLD, 16));
        totalLbl.setForeground(Color.green);
        productsTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productsTable.getSelectedRow();
            if (selectedRow != -1) {
                Object category = tableModel.getValueAt(selectedRow, 2);

                Object productId = tableModel.getValueAt(selectedRow, 0);
                Object productName = tableModel.getValueAt(selectedRow, 1);
                Product product = getProductInfo(tableModel.getValueAt(selectedRow, 4).toString());


                productID.setText(" Product ID: " + productId.toString());
                nameLbl.setText(" Product Name: " + productName.toString());
                categoryLbl.setText(" Category: " + category.toString());

                if(product instanceof Clothing) {
                    sizeLbl.setText("   Size :"+((Clothing) product).getSize());
                    colourLbl.setText("   Color :"+((Clothing) product).getColour());
                } else if(product instanceof Electronics) {
                    brandlbl.setText("   Brand :"+((Electronics) product).getBrand());
                    WarrantyLbl.setText("   Warranty :"+((Electronics) product).getWarrantyPeriod()+" years");

                }

                if (category.toString().equals("Clothing")) {
                    sizeLbl.setVisible(true);
                    colourLbl.setVisible(true);
                    itemCountLbl.setVisible(true);

                    brandlbl.setVisible(false);
                    WarrantyLbl.setVisible(false);
                } else if (category.toString().equals("Electronics")) {
                    brandlbl.setVisible(true);
                    WarrantyLbl.setVisible(true);

                    sizeLbl.setVisible(false);
                    colourLbl.setVisible(false);
                    itemCountLbl.setVisible(false);
                }
            }


        });


        //Mouse listener to capture mouse clicks on products
        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedRow = productsTable.getSelectedRow();
                    if (selectedRow != -1) {
                        Object productId = tableModel.getValueAt(selectedRow, 0);
                        Object productName = tableModel.getValueAt(selectedRow, 1);
                        productID.setText(" Product ID: " + productId.toString());
                        nameLbl.setText(" Product Name: " + productName.toString());
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(productsTable);

        this.setTitle("Westminster Shopping Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setSize(2,6);
        northPanel.add(dropDownLabel, BorderLayout.WEST);
        categoryMenu.setSize(new Dimension(8,2));
        northPanel.add(categoryMenu);
        northPanel.add(cartBtn, BorderLayout.LINE_END);
        this.add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(selectedProductsLabel, BorderLayout.NORTH);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));

        labelsPanel.add(productID);
        labelsPanel.add(categoryLbl);
        labelsPanel.add(nameLbl);
        labelsPanel.add(sizeLbl);
        labelsPanel.add(colourLbl);
        labelsPanel.add(itemCountLbl);
        labelsPanel.add(brandlbl);
        labelsPanel.add(WarrantyLbl);

        southPanel.add(labelsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addBtn);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(southPanel, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    //Method to indicate when stock is below 3
    private void criticalStockLevel() {
        productsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int availableItemsColumnIndex = 4; // Assuming available items info is in the 5th column

                Object availableItemsInfo = table.getValueAt(row, availableItemsColumnIndex);
                String availableItemsString = availableItemsInfo.toString().replaceAll("\\D+", ""); // Extracting only numbers
                int availableItems = 0;
                try {
                    availableItems = Integer.parseInt(availableItemsString);
                } catch (NumberFormatException e) {

                    System.err.println("Error : "+ e.getMessage());
                }

                if (availableItems < 3) {
                    cellComponent.setBackground(Color.RED);
                } else {
                    cellComponent.setBackground(table.getBackground());
                }
                return cellComponent;
            }
        });
    }

    //actionPerformed Method for actions like buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            addToShoppingCart();
        }
        if (e.getSource() == cartBtn) {
            openShoppingCartWindow();
        }

        if (e.getSource() ==categoryMenu) {
            String selectedCategory = (String) categoryMenu.getSelectedItem();
            if (!selectedCategory.equals("All") && !selectedCategory.equals("Clothing") &&
                    !selectedCategory.equals("Electronics")) {
                System.out.println(selectedCategory);
            }
            updateTable();
        }
    }

    //Method to add products to shopping cart
    private void addToShoppingCart() {
        if (shoppingCartTable == null) {
            initializeShoppingCart();
        }
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow != -1) {
            Object productId = tableModel.getValueAt(selectedRow, 0);
            Object productName = tableModel.getValueAt(selectedRow, 1);
            Object price = tableModel.getValueAt(selectedRow, 3);

            DefaultTableModel shoppingCartTableModel = (DefaultTableModel) shoppingCartTable.getModel();
            boolean itemAlreadyExists = false;

            for (int i = 0; i < shoppingCartTableModel.getRowCount(); i++) {
                String cartProductId = shoppingCartTableModel.getValueAt(i, 0).toString().split(",")[0].replace("ID: ", "").trim();
                if (cartProductId.equals(productId.toString())) {
                    // If the product already exists in the cart, update the quantity and price
                    int currentQuantity = (int) shoppingCartTableModel.getValueAt(i, 1);
                    shoppingCartTableModel.setValueAt(currentQuantity + 1, i, 1);
                    double totalPrice = (currentQuantity + 1) * Double.parseDouble(price.toString());
                    shoppingCartTableModel.setValueAt(totalPrice, i, 2);
                    itemAlreadyExists = true;
                    break;
                }
            }

            if (!itemAlreadyExists) {
                // If the product is not in the cart, add it with quantity 1
                Object[] rowData = {"ID: " + productId.toString() + ", Name: " + productName.toString(), 1, price};
                shoppingCartTableModel.addRow(rowData);
            }
            itemCount++;

            String category = (String) tableModel.getValueAt(selectedRow, 2);
            categoryCount = categoryItemCount.getOrDefault(category, 0) + 1;
            categoryItemCount.put(category, categoryCount);

            updateCart();
        }
    }

    //Method to update shopping cart
    private void updateCart() {
        DefaultTableModel shoppingCartTableModel = (DefaultTableModel) shoppingCartTable.getModel();

        double subTotal = 0;
        int itemCount = shoppingCartTableModel.getRowCount();

        for (int i = 0; i < itemCount; i++) {
            double price = (double) shoppingCartTableModel.getValueAt(i, 2);
            int quantity = (int) shoppingCartTableModel.getValueAt(i, 1);
            subTotal += price * quantity;
        }

        double discountTen = itemCount == 1 ? subTotal * 0.1 : 0;
        double discountTwenty = categoryCount >= 3 ? subTotal * 0.2 : 0;
        double total = subTotal - discountTen - discountTwenty;

        subTotalLbl.setText("Sub Total: " + String.format("%.2f", subTotal)+"/-");
        discount10Lbl.setText("10% Discount for first purchase: " + String.format("%.2f", discountTen)+"/-");
        discount20Lbl.setText("20% Discount for three same category purchase: " + String.format("%.2f", discountTwenty)+"/-");
        totalLbl.setText("Total: " + String.format("%.2f", total)+" /-");
    }

    private void openShoppingCartWindow() {
        if (shoppingFrame == null) {
            initializeShoppingCart();
        }

        shoppingFrame.setVisible(true);
    }

    private void initializeShoppingCart() {
        shoppingFrame = new JFrame("Shopping Cart");
        shoppingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingFrame.setSize(500, 500);
        shoppingFrame.setLayout(new BorderLayout());

        JPanel shoppingPanel = new JPanel();
        shoppingPanel.setLayout(new BorderLayout());

        DefaultTableModel shoppingCartTableModel = new DefaultTableModel();
        shoppingCartTableModel.addColumn("Product");
        shoppingCartTableModel.addColumn("Item Quantity");
        shoppingCartTableModel.addColumn("Price");

        shoppingCartTable = new JTable(shoppingCartTableModel);
        JScrollPane shoppingCartScrollPane = new JScrollPane(shoppingCartTable);
        shoppingCartTable.setRowHeight(30);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(4, 1));
        labelsPanel.add(subTotalLbl);
        labelsPanel.add(discount10Lbl);
        labelsPanel.add(discount20Lbl);
        labelsPanel.add(totalLbl);
        shoppingPanel.add(shoppingCartScrollPane, BorderLayout.CENTER);
        shoppingPanel.add(labelsPanel, BorderLayout.SOUTH);
        shoppingFrame.add(shoppingPanel);
    }
}