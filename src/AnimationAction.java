public class AnimationAction implements Action{
    private EntityAnimate entity;
    private int repeatCount;

    public AnimationAction (EntityAnimate entity, int repeatCount){
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler){
        this.entity.nextImage();

        if (this.repeatCount != 1){
            scheduler.scheduleEvent(this.entity,
                    createAnimationAction(this.entity, Math.max(this.repeatCount -1, 0)),
                            this.entity.getAnimationPeriod());
        }
    }

    public static Action createAnimationAction(EntityAnimate entity, int repeatCount){
        return new AnimationAction(entity, repeatCount);
    }
}
