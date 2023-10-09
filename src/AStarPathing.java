

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AStarPathing implements PathingStrategy {
    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        HashMap<Point, Node> openMap = new HashMap<>();
        HashMap<Point, Node> closedMap = new HashMap<>();
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        List<Point> path = new LinkedList<>();

        Node current = new Node(start, 0, heuristic(start, end), null);
        openList.add(current);
        openMap.put(current.getLocation(),current);

        while(openList.size() > 0){
            Node curr1 = openList.poll();
            closedMap.put(curr1.getLocation(), curr1);
            openMap.remove(curr1);
            if (withinReach.test(curr1.getLocation(), end)){
                return pathform(path, curr1);
            }

            List<Point> neighbor = potentialNeighbors.apply(curr1.getLocation()).filter(canPassThrough)
                    .filter(p -> !(p.equals(start) && p.equals(end))).filter(p -> !closedMap.containsKey(p)).toList();

            for (Point p : neighbor){
                int g = curr1.getG() + neighbor(curr1.getLocation(), p);
                int h = (Math.abs(end.getX() - p.getX()) + Math.abs(end.getY() - p.getY()));
                Node node2 = new Node(p, g, h, curr1);
                if (!openMap.containsKey(p)) {
                    openList.add(node2);
                    openMap.put(p, node2);
                }else if (openMap.get(p).getG() > g){
                    openList.remove(openMap.get(p));
                    openMap.replace(p, node2);
                    openList.add(node2);
                }
            }
        }
        return path;
    }

    public int neighbor(Point current, Point end){
        return heuristic(current, end);
    }

    public int heuristic(Point current, Point target){
        return current.distanceSquared(target);
    }

    public List<Point> pathform(List<Point> points, Node node){
        points.add(node.getLocation());
        if(node.getPrevious() == null){
            Collections.reverse(points);
            points.remove(0);
            return points;
        }else {
            return pathform(points, node.getPrevious());
        }
    }

}
