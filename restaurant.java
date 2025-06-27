/*
Name: Sharon Basovich
Title: OOP Assignment #2
Date: May 23, 2025
Course Code: ICS4U1c
Checked by: Yichen Xiao
*/

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class restaurant {
    static Scanner in = new Scanner(System.in);

    /*
     * Dependencies: restaurant.java, food.java, order.java
     * Overview: Each food item is a class
     * Each food item extend the food class, which contains all the necessary
     * requirements for the food
     */

    // initialize arraylists to track the menu items and the orders
    static ArrayList<food> items = new ArrayList<food>();
    static ArrayList<order> orders = new ArrayList<order>();

    public static void main(String[] args) {

        // create a few menu items to test
        items.add(new food(5.00, "pizza", true, 350, "dough, tomato sauce, cheese", true, false, false));
        items.add(new food(9.25, "burger", false, 700, "lettuce, tomatoes, pickles, angus beef, buns", false, false,
                false));
        items.add(new food(7.50, "salad", true, 280, "tomatoes, cucumbers, peppers, lettuce, dressing, onions", true,
                true, true));

        // main loop, user can continually select which option they want until they exit
        // the program
        while (true) {

            // prompt for what the user wants to do continually until value is 0-5
            System.out.println("1: Place an order");
            System.out.println("2: Cook an order");
            System.out.println("3: View a menu item");
            System.out.println("4: Add a menu item");
            System.out.println("5: Remove a menu item");
            int option;
            while (true) {
                System.out.println(
                        "Enter a number 1-5 corresponding to the option you would like to select or 0 to exit:");
                option = getInt();
                if (option > 5 || option < 0) {
                    System.out.println("Error, input must be number 0-5");
                } else {
                    break;
                }
            }

            // call a method based on user selections
            switch (option) {
                case 0:
                    return;
                case 1:
                    order();
                    break;
                case 2:
                    cook();
                    break;
                case 3:
                    viewItem();
                    break;
                case 4:
                    addItem();
                    break;
                case 5:
                    removeItem();
                    break;
                default:
                    break;
            }

        }
    }

    public static void cook() {
        // if there are no orders, exit
        if (orders.size() == 0) {
            System.out.println("There are currently no orders to cook");
            return;
        }

        // print out each order, including the customer name, the time of order, and the
        // urgency
        System.out.println("Here are all the current orders:");
        for (order order : orders) {
            System.out.println(
                    order.getCustomer() + "'s order at " + order.getTime() + ". Urgency: " + order.getUrgency());
        }

        // prompt the user for the order to cook, if they don't select one of the order
        // names, keep prompting until they do
        System.out.println("------------------------");
        System.out.println("Whose order would you like to cook:");
        while (true) {
            String name = in.nextLine();
            for (order order : orders) {
                if (name.equalsIgnoreCase(order.getCustomer())) {
                    // print out the information for the user
                    System.out.println("Here is the information for " + name + "'s order:");
                    System.out.println("Item: " + order.getName());
                    System.out.println("Amount ordered: " + order.getQuantity());
                    // warn if item is out of stock
                    if (!order.isInStock()) {
                        System.out.println("WARNING: Item is currently out of stock, purchase more ASAP");
                    }
                    System.out.println("Price: " + order.getPrice());
                    System.out.println("Calories: " + order.getCalories());
                    System.out.println("Ingredients: " + order.getIngredients());
                    System.out.println("Customization note: " + order.getCustomizationNote());

                    // give the user time to cook the order, and then type done to remove the order
                    // from the list, if they type something else, prompt again until they do
                    System.out.println("Once you have cooked the order, type done to remove it from the list:");
                    while (true) {
                        if (in.nextLine().equalsIgnoreCase("done")) {
                            orders.remove(order);
                            return;
                        } else {
                            System.out.println("Input must be the word done");
                        }
                    }
                }
            }
            // if the code hasn't returned yet, the name wasn't actually in the list so we
            // prompt again
            System.out.println("That was not one of the names with an order, please try again.");
        }
    }

    public static void order() {
        // ask for customer name
        System.out.println("Welcome, what's your name:");
        String name = in.nextLine();

        // until they enter a valid menu item, keep looping
        while (true) {
            // display all the menu itmes
            System.out.println("Enter the name of any menu item to order it, or press enter to exit");
            for (food food : items) {
                System.out.println("Food: " + food.getName() + " | Price: $" + food.getPrice());
            }

            // if they just pressed enter, then exit
            String option = in.nextLine();
            if (option.equals("")) {
                return;

            } else {
                // check if each food item matches the identifier
                for (food food : items) {
                    if (food.getName().equalsIgnoreCase(option)) {
                        int quantity;

                        // until they enter a number 1 or more
                        while (true) {
                            System.out.println("Good choice, how many items would you like?");
                            quantity = getInt();
                            if (quantity < 1) {
                                System.out.println("Invalid, quantity must be greater than one");
                            } else {
                                break;
                            }
                        }

                        // prompt for urgency and a note
                        System.out.println("How urgent is your order?");
                        String urgency = in.nextLine();

                        System.out.println("Would you like to leave a customization note? (Press enter to skip):");
                        String note = in.nextLine();

                        // get the current time, and convert it into a more readable format
                        LocalDateTime time = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        String formattedDate = time.format(formatter);

                        // create a new object in the orders arraylist using the information collected
                        System.out.println("Your order has been confirmed at " + formattedDate);
                        orders.add(new order(food, quantity, formattedDate, urgency, note, name));
                        return;
                    }
                }
                System.out.println("That's not the name of one of the menu items, try again");
            }
        }
    }

    public static void removeItem() {
        // if there are no items, you can't remove nothing so return
        if (items.size() == 0) {
            System.out.println("No menu items to remove");
            return;
        }

        // print the name of each item
        for (food food : items) {
            System.out.println(food.getName());
        }

        // until the user in puts a name that matches a food, keep looping
        System.out.println("Which item would you like to remove?");
        while (true) {
            String choice = in.nextLine();
            for (food food : items) {
                // if the name matches an item, remove that item from the list
                if (food.getName().equalsIgnoreCase(choice)) {
                    System.out.println(food.getName() + " successfully removed");
                    items.remove(food);
                    return;
                }
            }

            System.out.println("Error, item not found, try again");
        }
    }

    public static void viewItem() {
        // if menu has no items, exit
        if (items.size() == 0) {
            System.out.println("No menu items to view");
            return;
        }

        // prompt until user enters 1-3, representing which dietary restrictions they
        // would like to filter by
        int filter = 0;
        System.out.println(
                "Would you like to filter the list by dietary restrictions (vegetarian, vegan, or dairy-free)");
        if (getYesOrNo()) {
            System.out.println("1: Vegetarian");
            System.out.println("2: Vegan");
            System.out.println("3: Dairy-free");
            do {
                System.out.println("Enter number between 1-3");
                filter = getInt();
                if (filter > 3 || filter < 1) {
                    System.out.println("Error, value must be 1, 2, or 3");
                }
            } while (filter > 3 || filter < 1);

        }

        // print out the foods to view
        // using a switch statement, only print out specific items that match the
        // filter, if they selected one
        while (true) {
            System.out.println("Menu:");
            int counter = 1;
            for (food food : items) {
                switch (filter) {
                    case 0:
                        System.out.println(counter + ": " + food.getName());
                        counter++;
                        break;
                    case 1:
                        if (food.isVegetarian()) {
                            System.out.println(counter + ": " + food.getName());
                        }
                        counter++;
                        break;
                    case 2:
                        if (food.isVegan()) {
                            System.out.println(counter + ": " + food.getName());
                        }
                        counter++;
                        break;
                    case 3:
                        if (food.isDairyFree()) {
                            System.out.println(counter + ": " + food.getName());
                        }
                        counter++;
                        break;

                    default:
                        break;
                }
            }

            // prompt for the name of the item whose details they want to view
            System.out.println(
                    "Enter name of the item to view or nothing to exit viewer:");

            // if they enter nothing, exit the viewer
            String option = in.nextLine();
            if (option.equals("")) {
                break;
            } else {
                boolean found = false;
                for (food food : items) {
                    // print out the information for all the food
                    if (food.getName().equalsIgnoreCase(option)) {
                        found = true;
                        System.out.println("You have selected " + food.getName());
                        System.out.println("Price: " + food.getPrice());
                        System.out.println("Calories: " + food.getCalories());
                        System.out.println("Ingredients: " + food.getIngredients());
                        if (food.isInStock()) {
                            System.out.println("In stock");
                        } else {
                            System.out.println("Out of stock");
                        }
                    }
                }

                // if the item was not found, let print an error message
                if (!found) {
                    System.out.println("The item name is not in the list");
                }
            }

        }
    }

    public static void addItem() {
        // create variable for all the data
        double price;
        String name;
        boolean vegetarian = false;
        boolean dairyFree = false;
        boolean vegan = false;
        boolean inStock;
        int calories;
        String ingredients;

        // prompt for each piece of information, using helper methods to make sure the
        // values are formatted correctly

        while (true) {
            System.out.println("Enter the item name");
            name = in.nextLine().trim();
            if (name.length() == 0) {
                System.out.println("Error, name cannot be empty");
            } else {
                break;
            }
        }
        System.out.println("Enter price (in CAD):");
        price = getPrice();
        System.out.println("Enter calories:");
        calories = getInt();
        System.out.println("Enter ingredients:");
        ingredients = in.nextLine();
        System.out.println("Is the product in stock?");
        inStock = getYesOrNo();

        // if the user would like to add more information for filtering, use the more
        // detailed constructor
        System.out.println(
                "Would you like to optionally add vegetarian, dairy-free, or vegan to the item, if not it will be assumed the item is none:");
        if (getYesOrNo()) {
            System.out.println("Is the product vegetarian?");
            vegetarian = getYesOrNo();

            System.out.println("Is the product vegan?");
            vegan = getYesOrNo();

            System.out.println("Is the product dairy-free?");
            dairyFree = getYesOrNo();
            items.add(new food(price, name, inStock, calories, ingredients, vegetarian, dairyFree, vegan));
        } else {
            items.add(new food(price, name, inStock, calories, ingredients));
        }
    }

    public static boolean getYesOrNo() {
        // keep prompting until user inputs yes or no, and when they do, return true or
        // false respectively
        while (true) {
            System.out.println("Enter Yes/No:");
            String answer = in.nextLine();
            if (answer.equalsIgnoreCase("yes")) {
                return true;
            }
            if (answer.equalsIgnoreCase("no")) {
                return false;
            }

            System.out.println("Value must be yes or no, try again");
        }
    }

    public static double getPrice() {
        // keep prompting until the user inputs a price which contains two decimal
        // places (for the cents), which is then returned
        while (true) {
            try {
                boolean pass = true;
                String price = in.nextLine();
                String[] priceArr = price.split("\\.");
                if (priceArr.length == 2) {
                    if (priceArr[1].length() > 2) {
                        System.out.println("Error, price can only have two decimal places");
                        pass = false;
                    }
                } else {
                    System.out.println("Error, price must follow format XX.XX");
                    pass = false;
                }
                double n = Double.parseDouble(price);
                if (pass) {
                    return n;
                }
            } catch (Exception e) {
                System.out.println("Must be a number with up to two decimal places, try again");
            }
        }
    }

    public static int getInt() {
        while (true) {
            // try to convert the readers input into an integer
            // if it is not an integer, print an error message and try again
            // if it is an integer, return the integer
            try {
                int n = Integer.parseInt(in.nextLine());
                return n;
            } catch (Exception e) {
                System.out.println("Must be a number try again");
            }
        }
    }
}
