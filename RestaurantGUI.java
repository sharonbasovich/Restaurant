import javax.swing.*;
import javax.swing.border.*;
import javax.swing.UIManager;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class RestaurantGUI extends JFrame {
    private static ArrayList<food> items = new ArrayList<food>();
    private static ArrayList<order> orders = new ArrayList<order>();
    
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JTextArea outputArea;
    
    // Order panel components
    private JTextField customerField;
    private JComboBox<String> foodComboBox;
    private JSpinner quantitySpinner;
    private JTextField urgencyField;
    private JTextArea customizationArea;
    
    // Menu panel components
    private JList<String> menuList;
    private DefaultListModel<String> menuListModel;
    private JComboBox<String> filterComboBox;
    
    // Orders panel components
    private JList<String> ordersList;
    private DefaultListModel<String> ordersListModel;
    
    public RestaurantGUI() {
        initializeData();
        setupGUI();
    }
    
    private void initializeData() {
        // Create sample menu items
        items.add(new food(5.00, "Pizza", true, 350, "Dough, tomato sauce, cheese", true, false, false));
        items.add(new food(9.25, "Burger", false, 700, "Lettuce, tomatoes, pickles, angus beef, buns", false, false, false));
        items.add(new food(7.50, "Salad", true, 280, "Tomatoes, cucumbers, peppers, lettuce, dressing, onions", true, true, true));
    }
    
    private void setupGUI() {
        setTitle("Restaurant Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Create tabs
        createOrderTab();
        createMenuTab();
        createOrdersTab();
        createCookTab();
        
        // Create output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setRows(5);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("System Messages"));
        mainPanel.add(outputScrollPane, BorderLayout.SOUTH);
        
        log("Restaurant Management System started successfully!");
    }
    
    private void createOrderTab() {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Place New Order"));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Customer name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 1;
        customerField = new JTextField(20);
        formPanel.add(customerField, gbc);
        
        // Food selection
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Food Item:"), gbc);
        gbc.gridx = 1;
        foodComboBox = new JComboBox<>();
        for (food item : items) {
            foodComboBox.addItem(item.getName() + " - $" + item.getPrice());
        }
        formPanel.add(foodComboBox, gbc);
        
        // Quantity
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        formPanel.add(quantitySpinner, gbc);
        
        // Urgency
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Urgency:"), gbc);
        gbc.gridx = 1;
        urgencyField = new JTextField(20);
        formPanel.add(urgencyField, gbc);
        
        // Customization
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Customization:"), gbc);
        gbc.gridx = 1;
        customizationArea = new JTextArea(3, 20);
        JScrollPane noteScrollPane = new JScrollPane(customizationArea);
        formPanel.add(noteScrollPane, gbc);
        
        // Place order button
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(e -> placeOrder());
        formPanel.add(placeOrderButton, gbc);
        
        orderPanel.add(formPanel, BorderLayout.CENTER);
        tabbedPane.addTab("Place Order", orderPanel);
    }
    
    private void createMenuTab() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu Management"));
        
        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter by:"));
        filterComboBox = new JComboBox<>(new String[]{"All Items", "Vegetarian", "Vegan", "Dairy-Free"});
        filterComboBox.addActionListener(e -> updateMenuList());
        filterPanel.add(filterComboBox);
        
        // Add/Remove buttons
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> showAddItemDialog());
        filterPanel.add(addItemButton);
        
        JButton removeItemButton = new JButton("Remove Item");
        removeItemButton.addActionListener(e -> removeSelectedItem());
        filterPanel.add(removeItemButton);
        
        menuPanel.add(filterPanel, BorderLayout.NORTH);
        
        // Menu list
        menuListModel = new DefaultListModel<>();
        menuList = new JList<>(menuListModel);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.addListSelectionListener(e -> showItemDetails());
        
        JScrollPane menuScrollPane = new JScrollPane(menuList);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("Menu Items"));
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);
        
        // Item details panel
        JTextArea itemDetailsArea = new JTextArea();
        itemDetailsArea.setEditable(false);
        itemDetailsArea.setRows(8);
        JScrollPane detailsScrollPane = new JScrollPane(itemDetailsArea);
        detailsScrollPane.setBorder(BorderFactory.createTitledBorder("Item Details"));
        menuPanel.add(detailsScrollPane, BorderLayout.SOUTH);
        
        // Store reference to details area
        menuPanel.putClientProperty("detailsArea", itemDetailsArea);
        
        updateMenuList();
        tabbedPane.addTab("Menu", menuPanel);
    }
    
    private void createOrdersTab() {
        JPanel ordersPanel = new JPanel(new BorderLayout());
        ordersPanel.setBorder(BorderFactory.createTitledBorder("Current Orders"));
        
        ordersListModel = new DefaultListModel<>();
        ordersList = new JList<>(ordersListModel);
        ordersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane ordersScrollPane = new JScrollPane(ordersList);
        ordersPanel.add(ordersScrollPane, BorderLayout.CENTER);
        
        updateOrdersList();
        tabbedPane.addTab("Orders", ordersPanel);
    }
    
    private void createCookTab() {
        JPanel cookPanel = new JPanel(new BorderLayout());
        cookPanel.setBorder(BorderFactory.createTitledBorder("Cook Orders"));
        
        // Orders list for cooking
        JList<String> cookOrdersList = new JList<>();
        DefaultListModel<String> cookOrdersModel = new DefaultListModel<>();
        cookOrdersList.setModel(cookOrdersModel);
        cookOrdersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane cookScrollPane = new JScrollPane(cookOrdersList);
        cookPanel.add(cookScrollPane, BorderLayout.CENTER);
        
        // Cook button
        JButton cookButton = new JButton("Cook Selected Order");
        cookButton.addActionListener(e -> cookSelectedOrder(cookOrdersList));
        cookPanel.add(cookButton, BorderLayout.SOUTH);
        
        // Store reference to model
        cookPanel.putClientProperty("ordersModel", cookOrdersModel);
        
        updateCookOrdersList(cookOrdersModel);
        tabbedPane.addTab("Cook", cookPanel);
    }
    
    private void placeOrder() {
        String customerName = customerField.getText().trim();
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a customer name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String selectedFood = (String) foodComboBox.getSelectedItem();
        if (selectedFood == null) {
            JOptionPane.showMessageDialog(this, "Please select a food item.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Extract food name from combo box item
        String foodName = selectedFood.split(" - ")[0];
        
        int quantity = (Integer) quantitySpinner.getValue();
        String urgency = urgencyField.getText().trim();
        String customization = customizationArea.getText().trim();
        
        // Find the selected food item
        food selectedFoodItem = null;
        for (food item : items) {
            if (item.getName().equals(foodName)) {
                selectedFoodItem = item;
                break;
            }
        }
        
        if (selectedFoodItem != null) {
            // Get current time
            LocalDateTime time = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = time.format(formatter);
            
            // Create order
            order newOrder = new order(selectedFoodItem, quantity, formattedDate, urgency, customization, customerName);
            orders.add(newOrder);
            
            // Clear form
            customerField.setText("");
            foodComboBox.setSelectedIndex(0);
            quantitySpinner.setValue(1);
            urgencyField.setText("");
            customizationArea.setText("");
            
            // Update lists
            updateOrdersList();
            updateCookOrdersList();
            
            log("Order placed for " + customerName + ": " + quantity + "x " + foodName);
        }
    }
    
    private void updateFoodComboBox() {
        foodComboBox.removeAllItems();
        for (food item : items) {
            foodComboBox.addItem(item.getName() + " - $" + item.getPrice());
        }
    }
    
    private void updateMenuList() {
        menuListModel.clear();
        String filter = (String) filterComboBox.getSelectedItem();
        
        for (food item : items) {
            boolean shouldAdd = false;
            switch (filter) {
                case "All Items":
                    shouldAdd = true;
                    break;
                case "Vegetarian":
                    shouldAdd = item.isVegetarian();
                    break;
                case "Vegan":
                    shouldAdd = item.isVegan();
                    break;
                case "Dairy-Free":
                    shouldAdd = item.isDairyFree();
                    break;
            }
            
            if (shouldAdd) {
                menuListModel.addElement(item.getName());
            }
        }
    }
    
    private void showItemDetails() {
        String selectedItem = menuList.getSelectedValue();
        if (selectedItem != null) {
            for (food item : items) {
                if (item.getName().equals(selectedItem)) {
                    JTextArea detailsArea = (JTextArea) ((JPanel) tabbedPane.getComponentAt(1)).getClientProperty("detailsArea");
                    detailsArea.setText(String.format(
                        "Name: %s\n" +
                        "Price: $%.2f\n" +
                        "Calories: %d\n" +
                        "Ingredients: %s\n" +
                        "In Stock: %s\n" +
                        "Vegetarian: %s\n" +
                        "Vegan: %s\n" +
                        "Dairy-Free: %s",
                        item.getName(),
                        item.getPrice(),
                        item.getCalories(),
                        item.getIngredients(),
                        item.isInStock() ? "Yes" : "No",
                        item.isVegetarian() ? "Yes" : "No",
                        item.isVegan() ? "Yes" : "No",
                        item.isDairyFree() ? "Yes" : "No"
                    ));
                    break;
                }
            }
        }
    }
    
    private void updateOrdersList() {
        ordersListModel.clear();
        for (order order : orders) {
            ordersListModel.addElement(order.getCustomer() + " - " + order.getName() + " (Qty: " + order.getQuantity() + ", Urgency: " + order.getUrgency() + ")");
        }
    }
    
    private void updateCookOrdersList() {
        updateCookOrdersList(null);
    }
    
    private void updateCookOrdersList(DefaultListModel<String> model) {
        if (model == null) {
            JPanel cookPanel = (JPanel) tabbedPane.getComponentAt(3);
            model = (DefaultListModel<String>) cookPanel.getClientProperty("ordersModel");
        }
        
        if (model != null) {
            model.clear();
            for (order order : orders) {
                model.addElement(order.getCustomer() + " - " + order.getName() + " (Qty: " + order.getQuantity() + ")");
            }
        }
    }
    
    private void cookSelectedOrder(JList<String> cookOrdersList) {
        int selectedIndex = cookOrdersList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to cook.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        order selectedOrder = orders.get(selectedIndex);
        
        int result = JOptionPane.showConfirmDialog(this,
            "Cook order for " + selectedOrder.getCustomer() + "?\nItem: " + selectedOrder.getName() + "\nQuantity: " + selectedOrder.getQuantity(),
            "Confirm Cooking", JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            orders.remove(selectedIndex);
            updateOrdersList();
            updateCookOrdersList();
            log("Order cooked and removed for " + selectedOrder.getCustomer());
        }
    }
    
    private void showAddItemDialog() {
        JDialog dialog = new JDialog(this, "Add New Menu Item", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Form fields
        JTextField nameField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField caloriesField = new JTextField(20);
        JTextArea ingredientsArea = new JTextArea(3, 20);
        JCheckBox inStockCheckBox = new JCheckBox("In Stock", true);
        JCheckBox vegetarianCheckBox = new JCheckBox("Vegetarian");
        JCheckBox veganCheckBox = new JCheckBox("Vegan");
        JCheckBox dairyFreeCheckBox = new JCheckBox("Dairy-Free");
        
        // Add components to form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Price ($):"), gbc);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Calories:"), gbc);
        gbc.gridx = 1;
        formPanel.add(caloriesField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Ingredients:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(ingredientsArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(inStockCheckBox, gbc);
        
        gbc.gridy = 5;
        formPanel.add(vegetarianCheckBox, gbc);
        
        gbc.gridy = 6;
        formPanel.add(veganCheckBox, gbc);
        
        gbc.gridy = 7;
        formPanel.add(dairyFreeCheckBox, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Item");
        JButton cancelButton = new JButton("Cancel");
        
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText());
                int calories = Integer.parseInt(caloriesField.getText());
                String ingredients = ingredientsArea.getText().trim();
                boolean inStock = inStockCheckBox.isSelected();
                boolean vegetarian = vegetarianCheckBox.isSelected();
                boolean vegan = veganCheckBox.isSelected();
                boolean dairyFree = dairyFreeCheckBox.isSelected();
                
                if (name.isEmpty() || ingredients.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Name and ingredients cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                food newItem = new food(price, name, inStock, calories, ingredients, vegetarian, dairyFree, vegan);
                items.add(newItem);
                
                updateFoodComboBox();
                updateMenuList();
                log("Added new menu item: " + name);
                dialog.dispose();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for price and calories.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    private void removeSelectedItem() {
        String selectedItem = menuList.getSelectedValue();
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to remove '" + selectedItem + "' from the menu?",
            "Confirm Removal", JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            items.removeIf(item -> item.getName().equals(selectedItem));
            updateFoodComboBox();
            updateMenuList();
            log("Removed menu item: " + selectedItem);
        }
    }
    
    private void log(String message) {
        outputArea.append(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " - " + message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new RestaurantGUI().setVisible(true);
        });
    }
} 