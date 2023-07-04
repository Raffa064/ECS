package ecs;

public abstract class MergedSystem extends System {
    private System[] systems;

    public MergedSystem(System... systems) {
        this.systems = systems;
    }

    @Override
    public abstract boolean canProcess(Entity entity);
    
    public boolean acceptedByAll(Entity entity) {
        boolean accepted = true;
        for (System system : systems) {
            if (!system.canProcess(entity)) {
                accepted = false;
                break;
            }
        }
        return accepted;
    }

    @Override
    public void start() {
        for (System system : systems) system.start();
    }

    @Override
    public void process(World world, Entity entity, float delta) {
        for (System system : systems) system.process(world, entity, delta);
    }

    @Override
    public void end() {
        for (System system : systems) system.end();
    }
}
