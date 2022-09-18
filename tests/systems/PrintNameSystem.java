package tests.systems;

import ecs.Entity;
import ecs.World;
import tests.components.NameComponent;

public class PrintNameSystem extends ecs.System {
	@Override
	public boolean canProcess(Entity entity) {
		return entity.contains(NameComponent.class); //Accept all entities that contains NameComponent
	}

	@Override
	public void start() {
	}

	@Override
	public void process(World world, Entity entity, float delta) {
		NameComponent nameComponent = entity.getComponent(NameComponent.class); //gets the NameComponent component
		System.out.println(nameComponent.name); //Prints the name of the entity
	}

	@Override
	public void end() {
	}
}
