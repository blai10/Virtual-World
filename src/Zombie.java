import processing.core.PImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Zombie extends EntityActivate implements Move {
    private final String ZOMBIE_KEY = "zombie";

    public Zombie(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod) {
        super(id, position, images, animationPeriod, actionPeriod);
    }


    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy strategy = new AStarPathing();
        List<Point> next = strategy.computePath(this.getPosition(), destPos,
                canPassThrough(world), withinReach(), strategy.CARDINAL_NEIGHBORS);
        if (next.size() == 0) {
            return this.getPosition();
        }
        return next.get(0);
    }

    public Predicate<Point> canPassThrough(WorldModel world) {
        return p -> (!world.isOccupied(p) || (world.getOccupancyCell(p) instanceof Stump));
    }

    public BiPredicate<Point, Point> withinReach() {
        return (p1, p2) -> Point.neighbors(p1, p2);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<NewEntity> target = world.findNearest(this.getPosition(),
                new ArrayList<>(Arrays.asList(Fairy.class)));

        if (target.isPresent() && this.moveTo(world, target.get(), scheduler)) {
            this.transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean moveTo(WorldModel world, NewEntity target, EventScheduler scheduler) {
        if (Point.neighbors(this.getPosition(), target.getPosition())) {
            if (target instanceof Fairy) {
                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);
                return true;
            }
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());
            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Optional<NewEntity> target = world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Fairy.class)));
        if (target.isPresent() && target.get() instanceof Fairy) {
            DudeNotFull dude = new DudeNotFull(this.getId(), this.getPosition(), this.getImages(), this.getAnimationPeriod(),
                    2, 0, this.getActionPeriod());
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;
    }
}