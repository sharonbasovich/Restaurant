import java.time.*;

public class order extends food {
    private int quantity;
    private String time;
    private String urgency;
    private String customizationNote;
    private String customer;

    // constructor
    // orders are childs of food, as they contain all the information of the food
    // plus extra information like the customer and quantity
    public order(food item, int quantity, String time, String urgency, String customizationNote, String customer) {
        super(quantity, item.getName(), item.isInStock(), item.getCalories(), item.getIngredients(),
                item.isVegetarian(), item.isDairyFree(), item.isVegan());
        this.quantity = quantity;
        this.time = time;
        this.urgency = urgency;
        this.customizationNote = customizationNote;
        this.customer = customer;
    }

    // standard getters and setters
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getCustomizationNote() {
        return customizationNote;
    }

    public void setCustomizationNote(String customizationNote) {
        this.customizationNote = customizationNote;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

}
