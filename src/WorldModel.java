import processing.core.PImage;

import java.util.*;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel {


    private final int PROPERTY_KEY = 0;
    private final int PROPERTY_ID = 1;
    private final int PROPERTY_COL = 2;
    private final int PROPERTY_ROW = 3;
    private final int ENTITY_NUM_PROPERTIES = 4;


    private final String STUMP_KEY = "stump";
    private final int STUMP_NUM_PROPERTIES = 0;


    private final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private final int SAPLING_HEALTH_LIMIT = 5;
    private final String SAPLING_KEY = "sapling";
    private final int SAPLING_HEALTH = 0;
    private final int SAPLING_NUM_PROPERTIES = 1;


    private final String OBSTACLE_KEY = "obstacle";
    private final int OBSTACLE_ANIMATION_PERIOD = 0;
    private final int OBSTACLE_NUM_PROPERTIES = 1;


    private final String DUDE_KEY = "dude";
    private final int DUDE_ACTION_PERIOD = 0;
    private final int DUDE_ANIMATION_PERIOD = 1;
    private final int DUDE_LIMIT = 2;
    private final int DUDE_NUM_PROPERTIES = 3;


    private final String HOUSE_KEY = "house";
    private final int HOUSE_NUM_PROPERTIES = 0;


    private final String FAIRY_KEY = "fairy";
    private final int FAIRY_ANIMATION_PERIOD = 0;
    private final int FAIRY_ACTION_PERIOD = 1;
    private final int FAIRY_NUM_PROPERTIES = 2;

    private final String SKELETON_KEY = "skeleton";
    private final int SKELETON_ANIMATION_PERIOD= 1;
    private final int SKELETON_ACTION_PERIOD = 1;
    private final int SKELETON_NUM_PROPERTIES = 2;

    private static final String HOLE_KEY = "hole";
    private static final int HOLE_NUM_PROPERTIES = 0;

    private static final String ZOMBIE_KEY = "zombie";
    private final int ZOMBIE_NUM_PROPERTIES = 3;
    private final int ZOMBIE_ACTION_PERIOD = 0;
    private final int ZOMBIE_ANIMATION_PERIOD = 1;
    private final int ZOMBIE_LIMIT = 2;

    private final String TREE_KEY = "tree";
    private final int TREE_ANIMATION_PERIOD = 0;
    private final int TREE_ACTION_PERIOD = 1;
    private final int TREE_HEALTH = 2;
    private final int TREE_NUM_PROPERTIES = 3;
    private int numRows;
    private int numCols;
    private Background[][] background;
    private NewEntity[][] occupancy;
    public Set<NewEntity> entities;

    public WorldModel() {
    }

    public void parseSapling(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            NewEntity entity = new Sapling(id, pt, imageStore.getImageList(SAPLING_KEY), SAPLING_ACTION_ANIMATION_PERIOD,
                    SAPLING_ACTION_ANIMATION_PERIOD, health, SAPLING_HEALTH_LIMIT);
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", SAPLING_KEY, SAPLING_NUM_PROPERTIES));
        }
    }

    public void parseDude(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            NewEntity entity = new DudeNotFull(id, pt, imageStore.getImageList(DUDE_KEY),
                    Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), Integer.parseInt(properties[DUDE_LIMIT]),
                    0, Double.parseDouble(properties[DUDE_ACTION_PERIOD]));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }

    public void parseFairy(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == FAIRY_NUM_PROPERTIES) {
            NewEntity entity = new Fairy(id, pt, imageStore.getImageList(FAIRY_KEY), Double.parseDouble(properties[FAIRY_ANIMATION_PERIOD]),
                    Double.parseDouble(properties[FAIRY_ACTION_PERIOD]));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", FAIRY_KEY, FAIRY_NUM_PROPERTIES));
        }
    }

    public void parseTree(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == TREE_NUM_PROPERTIES) {
            NewEntity entity = new Tree(id, pt, imageStore.getImageList(TREE_KEY), Double.parseDouble(properties[TREE_ANIMATION_PERIOD]),
                    Double.parseDouble(properties[TREE_ACTION_PERIOD]), Integer.parseInt(properties[TREE_HEALTH]), 0);
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", TREE_KEY, TREE_NUM_PROPERTIES));
        }
    }

    public void parseObstacle(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            NewEntity entity = new Obstacle(id, pt, imageStore.getImageList(OBSTACLE_KEY), Double.parseDouble(properties[OBSTACLE_ANIMATION_PERIOD]));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", OBSTACLE_KEY, OBSTACLE_NUM_PROPERTIES));
        }
    }

    public void parseHouse(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            NewEntity entity = new House(id, pt, imageStore.getImageList(HOUSE_KEY));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOUSE_KEY, HOUSE_NUM_PROPERTIES));
        }
    }
    public void parseHole(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOLE_NUM_PROPERTIES) {
            NewEntity entity = new Hole(id, pt, imageStore.getImageList(HOLE_KEY));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOLE_KEY, HOLE_NUM_PROPERTIES));
        }
    }

    public void parseZombie(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == ZOMBIE_NUM_PROPERTIES) {
            NewEntity entity = new DudeNotFull(id, pt, imageStore.getImageList(ZOMBIE_KEY),
                    Double.parseDouble(properties[ZOMBIE_ANIMATION_PERIOD]), Integer.parseInt(properties[ZOMBIE_LIMIT]),
                    0, Double.parseDouble(properties[ZOMBIE_ACTION_PERIOD]));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", ZOMBIE_KEY, ZOMBIE_NUM_PROPERTIES));
        }
    }

    public void parseSkeleton(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == SKELETON_NUM_PROPERTIES) {
            NewEntity entity = new Fairy(id, pt, imageStore.getImageList(SKELETON_KEY), Double.parseDouble(properties[SKELETON_ANIMATION_PERIOD]),
                    Double.parseDouble(properties[SKELETON_ACTION_PERIOD]));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", SKELETON_KEY, SKELETON_NUM_PROPERTIES));
        }
    }


    public void parseStump(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == STUMP_NUM_PROPERTIES) {
            NewEntity entity = new Stump(id, pt, imageStore.getImageList(STUMP_KEY));
            tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", STUMP_KEY, STUMP_NUM_PROPERTIES));
        }
    }

    public void tryAddEntity(NewEntity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < this.numRows && pos.getX() >= 0 && pos.getX() < this.numCols;
    }

    public boolean isOccupied(Point pos) {

        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    public Optional<NewEntity> nearestEntity(List<NewEntity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            NewEntity nearest = entities.get(0);
            int nearestDistance = nearest.getPosition().distanceSquared(pos);

            for (NewEntity other : entities) {
                int otherDistance = other.getPosition().distanceSquared(pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public Optional<NewEntity> findNearest(Point pos, List<Class<?>> kinds) {
        List<NewEntity> ofType = new LinkedList<>();
        for (Class<?> kind : kinds) {
            for (NewEntity entity : this.entities) {
                if (kind.isInstance(entity)) {
                    ofType.add(entity);
                }
            }
        }

        return nearestEntity(ofType, pos);
    }

    /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */
    public void addEntity(NewEntity entity) {
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }

    public void moveEntity(NewEntity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(NewEntity entity) {
        removeEntityAt(entity.getPosition());
    }

    public void removeEntityAt(Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            NewEntity entity = getOccupancyCell(pos);
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }


    public Optional<NewEntity> getOccupant(Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }

    public NewEntity getOccupancyCell(Point pos) {

        return this.occupancy[pos.getY()][pos.getX()];
    }

    public void setOccupancyCell( Point pos, NewEntity entity) {
        this.occupancy[pos.y][pos.x] = entity;
    }


    public void load(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        parseSaveFile(saveFile, imageStore, defaultBackground);
        if(this.background == null){
            this.background = new Background[this.numRows][this.numCols];
            for (Background[] row : this.background)
                Arrays.fill(row, defaultBackground);
        }
        if(this.occupancy == null){
            this.occupancy = new NewEntity[this.numRows][this.numCols];
            this.entities = new HashSet<>();
        }
    }
    public void parseSaveFile(Scanner saveFile, ImageStore imageStore, Background defaultBackground){
        String lastHeader = "";
        int headerLine = 0;
        int lineCounter = 0;
        while(saveFile.hasNextLine()){
            lineCounter++;
            String line = saveFile.nextLine().strip();
            if(line.endsWith(":")){
                headerLine = lineCounter;
                lastHeader = line;
                switch (line){
                    case "Backgrounds:" -> this.background = new Background[this.numRows][this.numCols];
                    case "Entities:" -> {
                        this.occupancy = new NewEntity[this.numRows][this.numCols];
                        this.entities = new HashSet<>();
                    }
                }
            }else{
                switch (lastHeader){
                    case "Rows:" -> this.numRows = Integer.parseInt(line);
                    case "Cols:" -> this.numCols = Integer.parseInt(line);
                    case "Backgrounds:" -> this.parseBackgroundRow(line, lineCounter-headerLine-1, imageStore);
                    case "Entities:" -> this.parseEntity(line, imageStore);
                }
            }
        }
    }
    public void parseBackgroundRow(String line, int row, ImageStore imageStore) {
        String[] cells = line.split(" ");
        if(row < this.numRows){
            int rows = Math.min(cells.length, this.numCols);
            for (int col = 0; col < rows; col++){
                this.background[row][col] = new Background(cells[col], imageStore.getImageList(cells[col]));
            }
        }
    }

    public void parseEntity(String line, ImageStore imageStore) {
        String[] properties = line.split(" ", ENTITY_NUM_PROPERTIES + 1);
        if (properties.length >= ENTITY_NUM_PROPERTIES) {
            String key = properties[PROPERTY_KEY];
            String id = properties[PROPERTY_ID];
            Point pt = new Point(Integer.parseInt(properties[PROPERTY_COL]), Integer.parseInt(properties[PROPERTY_ROW]));

            properties = properties.length == ENTITY_NUM_PROPERTIES ?
                    new String[0] : properties[ENTITY_NUM_PROPERTIES].split(" ");

            switch (key) {
                case OBSTACLE_KEY -> parseObstacle(properties, pt, id, imageStore);
                case DUDE_KEY -> parseDude(properties, pt, id, imageStore);
                case FAIRY_KEY -> parseFairy(properties, pt, id, imageStore);
                case HOUSE_KEY -> parseHouse(properties, pt, id, imageStore);
                case TREE_KEY -> parseTree(properties, pt, id, imageStore);
                case SAPLING_KEY -> parseSapling(properties, pt, id, imageStore);
                case STUMP_KEY -> parseStump(properties, pt, id, imageStore);
                case HOLE_KEY -> parseHole(properties, pt, id, imageStore);
                case ZOMBIE_KEY -> parseZombie(properties, pt, id, imageStore);
                case SKELETON_KEY -> parseSkeleton(properties, pt, id, imageStore);
                default -> throw new IllegalArgumentException("Entity key is unknown");
            }
        }else{
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public Background getBackgroundCell(Point pos) {
        return this.background[pos.getY()][pos.getX()];
    }

    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.getY()][pos.getX()] = background;
    }

    public Optional<PImage> getBackgroundImage(Point pos) {
        if (this.withinBounds(pos)) {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        } else {
            return Optional.empty();
        }
    }

    public int getNumRows(){
        return numRows;
    }

    public int getNumCols(){
        return numCols;
    }

    public Set<NewEntity> getEntities(){
        return entities;
    };

    public String getSaplingKey(){
        return SAPLING_KEY;
    }

    public String getTreeKey(){
        return TREE_KEY;
    }

    public String getStumpKey(){
        return STUMP_KEY;
    }

    public String getZombieKey() {return ZOMBIE_KEY; }


    /**
     * Helper method for testing. Don't move or modify this method.
     */
    public List<String> log(){
        List<String> list = new ArrayList<>();
        for (NewEntity entity : entities) {
            String log = entity.log();
            if(log != null) list.add(log);
        }
        return list;
    }


}
