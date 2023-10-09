import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Dude extends EntityActivate implements Move, Transform{
    private final String DUDE_KEY = "dude";
    protected int resourceLimit;
    protected int resourceCount;

    public Dude(String id, Point position, List<PImage> images, double animationPeriod,
                int resourceLimit, int resourceCount, double actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public int getResourceLimit(){
        return this.resourceLimit;
    }

    public int getResourceCount(){
        return this.resourceCount;
    }

    public Point nextPosition(WorldModel world, Point destPos){
        PathingStrategy strategy = new AStarPathing();
        List<Point> next = strategy.computePath(this.getPosition(), destPos,
                canPassThrough(world), withinReach(), strategy.CARDINAL_NEIGHBORS);
        if (next.size() == 0){
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
}
