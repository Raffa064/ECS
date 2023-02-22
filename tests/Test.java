package tests;

import ecs.World;
import ecs.tests.NamedEntityObject;
import ecs.tests.UserEntity;
import ecs.tests.components.AgeComponent;
import tests.systems.PrintInverseNameSystem;
import tests.systems.PrintNameSystem;
import tests.systems.PrintUserDataSystem;

public class Test {
	public static void main(String[] args) {
		Test test = new Test();
		test.test();
	}
	
	public void test() {
		//Initialize world and entity factory
		World world = new World();
		EntityFactory factory = new EntityFactory(world);
		
		//Add system
		world.addSystem(new PrintNameSystem());
		
		//Add systems with alias
		world.$(new PrintInverseNameSystem());
		world.$(new PrintUserDataSystem());
		
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

		UserEntity userA = world.$(new UserEntity());
		userA.setName("Filipe");
		userA.setAge(37);
		
		//To test the debug, I'll call multiple times the getComponent
		UserEntity userB = world.$(new UserEntity());
		userB.setName("Rosane");
		userB.setAge(67);
		for (int i = 0; i < 10000; i++) userB.$(AgeComponent.class); //NOTE: the $(..) can be used to add and get components depending by the param type
		
		//Update systems
		world.update(0);
		
		//log
		System.out.println("\n"+world.log());
	}
}
