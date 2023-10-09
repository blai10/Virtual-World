import processing.core.PImage;

import java.util.List;

public class Hole extends NewEntity{
    private final String HOLE_KEY = "hole";

    public Hole(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
}
