package ly.generalassemb.energize.data;

/**
 * Created by darrankelinske on 6/28/16.
 */
public class Drink {

    private int id;
    private String name;
    private String description;
    private double size;
    private double price;
    private String imageLocation;
    private int manufacturerId;

    public Drink(int id, String name, String description, double size, double price, String imageLocation,
                 int manufacturerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.size = size;
        this.price = price;
        this.imageLocation = imageLocation;
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImageLocation() {
        return imageLocation;
    }
}
