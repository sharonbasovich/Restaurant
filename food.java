public class food {
    // food class contains the name, price, calories, and ingredients of the food
    // also, it must 
    
    private double price;
    private String name;
    private boolean vegetarian = false;
    private boolean dairyFree = false;
    private boolean vegan = false;
    private boolean inStock;
    private int calories;
    private String ingredients;

    // two constructors, one for just the key information, and one with the
    // additional filters (vegan, vegetarian, and dairy-free)
    public food(double price, String name, boolean inStock, int calories, String ingredients, boolean vegetarian,
            boolean dairyFree, boolean vegan) {
        this.price = price;
        this.name = name;
        this.vegetarian = vegetarian;
        this.dairyFree = dairyFree;
        this.vegan = vegan;
        this.inStock = inStock;
        this.calories = calories;
        this.ingredients = ingredients;
    }

    public food(double price, String name, boolean inStock, int calories, String ingredients) {
        this.price = price;
        this.name = name;
        this.inStock = inStock;
        this.calories = calories;
        this.ingredients = ingredients;
    }

    // standard getters and setters
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}
