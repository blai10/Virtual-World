import processing.core.PImage;

import java.util.List;

public class EntityAnimate extends NewEntity{
    private double animationPeriod;

    public EntityAnimate(String id, Point position, List<PImage> images, double animationPeriod){
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
    }

    public double getAnimationPeriod(){
        return this.animationPeriod;
    }

}
