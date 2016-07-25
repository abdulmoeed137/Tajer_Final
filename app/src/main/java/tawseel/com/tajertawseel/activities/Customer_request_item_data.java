package tawseel.com.tajertawseel.activities;

/**
 * Created by Monis on 7/14/2016.
 */

public class Customer_request_item_data {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_of_items() {
        return no_of_items;
    }

    public void setNo_of_items(String no_of_items) {
        this.no_of_items = no_of_items;
    }

    private String number;
    private String email;
    private String no_of_items;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    private String orderID;
}


