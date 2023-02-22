package tests.systems;

import ecs.Entity;
import ecs.World;
import tests.components.NameComponent;

/*
	This example is to test the debugger only.
*/

public class PrintInverseNameSystem extends ecs.System {
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
		String inverseName = "";
		for (int i = nameComponent.name.length()-1; i >= 0; i--) {
			inverseName += nameComponent.name.charAt(i);	
		}
		System.out.println(inverseName); //Prints the name of the entity, bud inversed
		heavyProcess();
	}

	// make heavy processing to test the debuger log. 100.000.000 iterations p/entity
	private void heavyProcess() {
		int m = 0;
		for (int i = 0; i  < 100; i++) {
			for (int j = 0; j  < 100; j++) {
				for (int k = 0; k  < 100; k++) {
					for (int l = 0; l < 100; l++) {
						m += i + j * k - l;
					}
				}
			}	
		}
	}

	@Override
	public void end() {
	}
}
