package tawseel.com.tajertawseel.activities;

/**
 * Created by Monis on 7/15/2016.
 */

public class ProductData {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    private String image;
    private long price;
}
