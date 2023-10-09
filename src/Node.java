public class Node {
    private Point location;
    private int f, g, h;
    private Node previous;

    public Node(Point location, int g, int h, Node previous){
        this.location = location;
        this.g = g;
        this.h = h;
        this.f = this.g + this.h;
        this.previous = previous;
    }

    public Point getLocation() {
        return this.location;
    }

    public int getF() {
        return this.f;
    }

    public int getG() {
        return this.g;
    }

    public int getH() {
        return this.h;
    }

    public Node getPrevious() {
        return this.previous;
    }
}
