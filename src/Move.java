public interface Move {
    boolean moveTo(WorldModel world, NewEntity target, EventScheduler scheduler);
    Point nextPosition(WorldModel world, Point destPos);
}
