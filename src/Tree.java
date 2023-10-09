import processing.core.PImage;

import java.util.List;

public class Tree extends Health{
    private final String TREE_KEY = "tree";

    public Tree(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health, int healthLimit){
        super(id, position, images, animationPeriod, actionPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        if(!transform(world, scheduler, imageStore)){
            scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        if (this.getHealth() <= 0){
            NewEntity stump = new Stump(world.getStumpKey() + "_" + this.getId(), this.getPosition(), imageStore.getImageList(world.getStumpKey()));
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            world.addEntity(stump);
            return true;
        }
        return false;
    }
}
