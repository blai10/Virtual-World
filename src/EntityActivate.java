import processing.core.PImage;

import java.util.List;

public abstract class EntityActivate extends EntityAnimate{
    private double actionPeriod;

    public EntityActivate(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod){
        super(id, position, images, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), getAnimationPeriod());
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public double getActionPeriod(){
        return this.actionPeriod;
    }
}
