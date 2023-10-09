import processing.core.PImage;

import java.util.List;

public class Stump extends NewEntity{
    private final String STUMP_KEY = "stump";
    public Stump(String id, Point position, List<PImage> images){
        super(id, position, images);
    }
}
