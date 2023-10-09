import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeNotFull extends Dude{
    public DudeNotFull(String id, Point position, List<PImage> images, double animationPeriod,
                     int resourceLimit, int resourceCount, double actionPeriod){
        super(id, position, images, animationPeriod, resourceLimit, resourceCount, actionPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Optional<NewEntity> target = world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (target.isEmpty() || !this.moveTo(world, target.get(), scheduler) || !this.transform(world, scheduler, imageStore)){
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public boolean moveTo(WorldModel world, NewEntity target, EventScheduler scheduler){
        if (Point.adjacent(this.getPosition(), target.getPosition())){
            this.resourceCount += 1;
            ((Health)target).setHealth(((Health) target).getHealth()-1);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.getPosition().equals(nextPos)){
                Optional<NewEntity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(target);
                }
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            DudeFull dudeFull = new DudeFull(this.getId(), this.getPosition(), this.getImages(), this.getAnimationPeriod(),
                    this.getResourceLimit(), this.getResourceCount(), this.getActionPeriod());
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            world.addEntity(dudeFull);
            dudeFull.scheduleActions(scheduler, world, imageStore);
            return true;
        }

        return false;
    }
}
