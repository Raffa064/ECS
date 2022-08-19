package ecs;

import java.util.List;
import java.util.ArrayList;

public abstract class System {
	public List<Entity> entities = new ArrayList<>();
	public abstract boolean canProcess(Entity entity);
	public abstract void start();
	public abstract void process(World world, Entity entity, float delta);
	public abstract void end();
}
