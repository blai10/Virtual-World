import processing.core.PImage;

import java.util.List;

public class Obstacle extends EntityAnimate{
    private final String OBSTACLE_KEY = "obstacle";

    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod){
        super(id, position, images, animationPeriod);
    }
}
