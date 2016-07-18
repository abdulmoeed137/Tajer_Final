package tawseel.com.tajertawseel.activities;

import java.util.ArrayList;

/**
 * Created by Monis on 7/15/2016.
 */

public class ProductLayoutData {

    public ArrayList<ProductData> getItems() {
        return items;
    }

    public void setItems(ArrayList<ProductData> items) {
        this.items = items;
        for (int i=0;i<items.size();i++)
            total+=items.get(i).getPrice();
    }

    private ArrayList<ProductData> items;

    public ProductLayoutData()
    {
        items=new ArrayList<ProductData>();
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public long getTotal() {
        return total;
    }

    public long getDelivery_charges() {
        return delivery_charges;
    }

    public void setDelivery_charges(long delivery_charges) {
        this.delivery_charges = delivery_charges;
    }

    private String pay_method;
    private long total,delivery_charges;
}
