import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class NewEntity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    public NewEntity(String id, Point position, List<PImage> images){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    public String log(){
        return this.id.isEmpty() ? null : String.format("%s %d %d %d", this.id, this.position.getX(), this.position.getY(), this.imageIndex);
    }

    public double getNumFromRange(double max, double min){
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    public int getIntFromRange(int max, int min){
        Random rand = new Random();
        return min +rand.nextInt(max - min);
    }

    public static int clamp(int value, int low, int high){
        return Math.min(high, Math.max(value, low));
    }

    public void nextImage(){
        this.imageIndex = this.imageIndex + 1;
    }

    public PImage getCurrentImage(){
        return this.images.get(this.imageIndex % this.images.size());
    }

    public String getId(){
        return this.id;
    }

    public Point getPosition(){
        return this.position;
    }

    public List<PImage> getImages(){
        return this.images;
    }

    public Point setPosition(Point position){
        return this.position = position;
    }


}
