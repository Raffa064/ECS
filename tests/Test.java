package tests;

import ecs.World;
import tests.systems.PrintNameSystem;

public class Test {
	public static void main(String[] args) {
		//Initialize world and entity factory
		World world = new World();
		EntityFactory factory = new EntityFactory(world);
		
		//Add systems
		world.addSystem(new PrintNameSystem());
		
		//Create (and add) entities
		factory.createNamedEntity("Raffa064");
		factory.createNamedEntity("Raffa");
		factory.createNamedEntity("Rafael");
		
		//Update systems
		world.update(0);
	}
}
