package ly.generalassemb.energize.data;

/**
 * Created by daz on 7/7/16.
 */
public class Manufacturer {
    private String name;
    private String location;
    private String description;

    public Manufacturer(String name, String location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
