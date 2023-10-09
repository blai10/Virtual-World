public class ActivityAction implements Action {
    private EntityActivate entity;
    private WorldModel world;
    private ImageStore imageStore;

    public ActivityAction(EntityActivate entity, WorldModel world, ImageStore imageStore){
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler){
        this.entity.executeActivity(this.world, this.imageStore, scheduler);
    }

    public static Action createActivityAction(EntityActivate entity, WorldModel world, ImageStore imageStore){
        return new ActivityAction(entity, world, imageStore);
    }

    public EntityAnimate getEntity() {
        return this.entity;
    }

    public WorldModel getWorld(){
        return this.world;
    }

    public ImageStore getImageStore(){
        return this.imageStore;
    }
}
