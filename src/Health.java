import processing.core.PImage;

import java.util.List;

public abstract class Health extends EntityActivate implements Transform{
    protected int health;
    private int healthLimit;

    public Health(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health, int healthLimit){
        super(id, position, images, animationPeriod, actionPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public int getHealth(){
        return this.health;
    }

    public int setHealth(int health){
        return this.health = health;
    }

    public int getHealthLimit(){
        return this.healthLimit;
    }
}
