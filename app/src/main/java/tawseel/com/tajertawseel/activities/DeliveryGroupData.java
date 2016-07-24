package tawseel.com.tajertawseel.activities;

/**
 * Created by AbdulMoeed on 7/22/2016.
 */
public class DeliveryGroupData {
    private String name ,noOfOrders,grpID,ItemPrice,PriceRange;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoOfOrders() {
        return noOfOrders;
    }

    public void setNoOfOrders(String noOfOrders) {
        this.noOfOrders = noOfOrders;
    }

    public String getGrpID() {
        return grpID;
    }

    public void setGrpID(String grpID) {
        this.grpID = grpID;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getPriceRange() {
        return PriceRange;
    }

    public void setPriceRange(String priceRange) {
        PriceRange = priceRange;
    }
}
