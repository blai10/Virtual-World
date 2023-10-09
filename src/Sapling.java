import processing.core.PImage;

import java.util.List;

public class Sapling extends Health{
    private final String SAPLING_KEY = "sapling";
    private final int TREE_HEALTH_MAX = 3;
    private final int TREE_HEALTH_MIN = 1;
    private final double TREE_ACTION_MIN = 1.000;
    private final double TREE_ACTION_MAX = 1.400;
    private final double TREE_ANIMATION_MIN = 0.050;
    private final double TREE_ANIMATION_MAX = 0.600;
    public Sapling(String id, Point position, List<PImage> images, double animationPeriod,
                   double actionPeriod, int health, int healthLimit){
        super(id, position, images, animationPeriod, actionPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        this.health += 1;
        if (!transform(world, scheduler, imageStore)){
            scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        if (this.getHealth() <= 0) {
            Stump stump = new Stump(world.getStumpKey() + "_" + this.getId(), this.getPosition(), imageStore.getImageList(world.getStumpKey()));
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            world.addEntity(stump);
            return true;
        } else if (this.getHealth() >= this.getHealthLimit()){
            Tree tree = new Tree(world.getTreeKey() + "_" + this.getId(), this.getPosition(), imageStore.getImageList(world.getTreeKey()),
                    getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
                    getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), 0);
            world.removeEntity( this);
            scheduler.unscheduleAllEvents(this);
            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;
    }
}
