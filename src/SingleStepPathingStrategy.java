import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SingleStepPathingStrategy implements PathingStrategy {
    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        return potentialNeighbors.apply(start).filter(canPassThrough).filter(pt ->
                !pt.equals(start) && !pt.equals(end) && Math.abs(end.x - pt.x) <= Math.abs(end.x - start.x)
                        && Math.abs(end.y - pt.y) <= Math.abs(end.y - start.y)).limit(1).collect(Collectors.toList());
    }
}
