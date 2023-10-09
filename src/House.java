import processing.core.PImage;

import java.util.List;

public class House extends NewEntity{
    private final String HOUSE_KEY = "house";

    public House(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
}
