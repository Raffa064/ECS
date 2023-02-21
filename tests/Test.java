package tests;

import ecs.Entity;
import ecs.World;
import ecs.tests.NamedEntityObject;
import tests.systems.PrintNameSystem;

public class Test {
	public static void main(String[] args) {
		new Test().test();
	}
	
	public void test() {
		//Initialize world and entity factory
		World world = new World();
		EntityFactory factory = new EntityFactory(world);
		
		//Add systems
		world.addSystem(new PrintNameSystem());
		
		//Create (and add) entities
		factory.createNamedEntity("Raffa064");
		factory.createNamedEntity("Raffa");
		factory.createNamedEntity("Rafael");
		
		//The same thing but with EntityObject
		NamedEntityObject raff4 = new NamedEntityObject();
		raff4.setName("Raff4");
		world.addEntity(raff4);

		//Another example, but with alias:
		NamedEntityObject r4ff4 = world.$(new NamedEntityObject());
		r4ff4.setName("R4ff4");
		
		//Update systems
		world.update(0);
	}
}
