package tests;

import ecs.World;
import ecs.Entity;
import tests.components.NameComponent;

public class EntityFactory {
	public World world;
	
	public EntityFactory(World world) {
		this.world = world;
	}
	
	//Create an entity with NameComponent
	public Entity createNamedEntity(String name) {
		NameComponent nameComponent = new NameComponent();
		nameComponent.name = name;
		
		Entity entity = new Entity();
		entity.addComponent(nameComponent);
		world.addEntity(entity);
		
		return entity;
	} 
}
