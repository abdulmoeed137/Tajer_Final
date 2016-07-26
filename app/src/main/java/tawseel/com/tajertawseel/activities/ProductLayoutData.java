package tawseel.com.tajertawseel.activities;

import java.util.ArrayList;

/**
 * Created by Monis on 7/15/2016.
 */

public class ProductLayoutData {

    public ArrayList<PostGroupListData> getItems() {
        return items;
    }

    public void setItems(ArrayList<PostGroupListData> items) {
        this.items = items;
        for (int i=0;i<items.size();i++)
            total+=Long.parseLong(items.get(i).getPrice());
    }

    private ArrayList<PostGroupListData> items;

    public ProductLayoutData()
    {
        items=new ArrayList<PostGroupListData>();
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

    private String pay_method="";
    private long total,delivery_charges;
}
