# Restaurant Management System

A Java GUI application for managing restaurant orders, menu items, and cooking operations.

## Features

- **Place Orders**: Add new orders with customer details, food items, quantity, urgency, and customization notes
- **Menu Management**: View, add, and remove menu items with filtering options (vegetarian, vegan, dairy-free)
- **Order Tracking**: View all current orders with details
- **Cooking Interface**: Select and mark orders as cooked

## How to Run

### Prerequisites

- Java 8 or higher installed on your system

### Running the Application

**Run the GUI application:**
   Download RestaurantApp.jar from the releases tab and run it




### Alternative: Run the original terminal version

```bash
java restaurant
```

## File Structure

- `RestaurantApp.java` - Main GUI application
- `restaurant.java` - Original terminal-based application
- `food.java` - Food item class
- `order.java` - Order class that extends food

## GUI Features

The GUI application provides a modern interface with four main tabs:

1. **Place Order**: Form to create new orders
2. **Menu**: View and manage menu items with filtering
3. **Orders**: View all current orders
4. **Cook**: Select orders to mark as cooked

The application includes:

- Form validation
- Real-time updates
- System message logging
- Confirmation dialogs
- Swing interface with system look and feel
