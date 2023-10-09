import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Fairy extends EntityActivate implements Move{
    private final String FAIRY_KEY = "fairy";
    private final int SAPLING_HEALTH_LIMIT = 5;
    private final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000;
    private final int SAPLING_HEALTH = 0;

    public Fairy(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Optional<NewEntity> fairyTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Stump.class)));
        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();
            if (this.moveTo(world, fairyTarget.get(), scheduler)) {
                Sapling sapling = new Sapling(world.getSaplingKey() + "_" + fairyTarget.get().getId(), tgtPos,
                        imageStore.getImageList(world.getSaplingKey()), SAPLING_ACTION_ANIMATION_PERIOD,
                        SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_HEALTH, SAPLING_HEALTH_LIMIT);
                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.getActionPeriod());
    }

    @Override
    public boolean moveTo(WorldModel world, NewEntity target, EventScheduler scheduler){
        if (Point.adjacent(this.getPosition(), target.getPosition())){
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else{
            Point nextPos = nextPosition(world, target.getPosition());
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

    public Point nextPosition(WorldModel world, Point destPos){
        PathingStrategy strategy = new AStarPathing();
        List<Point> next = strategy.computePath(this.getPosition(), destPos, canPassThrough(world),
                withinReach(), strategy.CARDINAL_NEIGHBORS);
        if (next.size() == 0){
            return this.getPosition();
        }
        return next.get(0);

    }
    public Predicate<Point> canPassThrough(WorldModel world) {
        return p -> (!world.isOccupied(p) || !(world.isOccupied(p)));
    }

    public BiPredicate<Point, Point> withinReach() {
        return (p1, p2) -> Point.adjacent(p1, p2);
    }
}
