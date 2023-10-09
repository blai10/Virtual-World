import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DudeFull extends Dude{
    public DudeFull(String id, Point position, List<PImage> images, double animationPeriod,
                 int resourceLimit, int resourceCount, double actionPeriod){
        super(id, position, images, animationPeriod, resourceLimit, resourceCount, actionPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Optional<NewEntity> fullTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && this.moveTo(world, fullTarget.get(), scheduler)){
            this.transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public boolean moveTo(WorldModel world, NewEntity target, EventScheduler scheduler){

        if (Point.adjacent(this.getPosition(), target.getPosition())){
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.getPosition().equals(nextPos)){
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        DudeNotFull dudeNotFull = new DudeNotFull(this.getId(), this.getPosition(), this.getImages(), this.getAnimationPeriod(),
                this.getResourceLimit(), this.getResourceCount(), this.getActionPeriod());
        world.removeEntity( this);
        world.addEntity(dudeNotFull);
        dudeNotFull.scheduleActions(scheduler, world, imageStore);
        return true;
    }
}
