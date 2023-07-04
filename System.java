package ecs;

import java.util.List;
import java.util.ArrayList;

public abstract class System {
	public List<Entity> entities = new ArrayList<>();
    private Entity current;
    
    //Alias to quick get component from current entity
    public <T extends Component> T $(Class<T> componentClass) {
        return current.$(componentClass);
    }
    
    //Alias to quick get component from any entity
    public <T extends Component> T $$(Entity entity, Class<T> componentClass) {
        return entity.$(componentClass);
    }
    
    public final void _process(World world, Entity entity, float delta) {
        current = entity;
        process(world, entity, delta);
        current = null;
    }
    
	public abstract boolean canProcess(Entity entity);
	public abstract void start();
	public abstract void process(World world, Entity entity, float delta);
	public abstract void end();
}
