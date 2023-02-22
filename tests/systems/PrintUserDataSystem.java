package tests.systems;

import ecs.Entity;
import ecs.World;
import tests.components.NameComponent;
import ecs.tests.UserEntity;

public class PrintUserDataSystem extends ecs.System {
	@Override
	public boolean canProcess(Entity entity) {
		return entity instanceof UserEntity; //Accept all user entities, this prevent to use getComponent()
	}

	@Override
	public void start() {
	}

	@Override
	public void process(World world, Entity entity, float delta) {
		UserEntity user = (UserEntity) entity;
		System.out.println("User "+user.getName()+" is "+user.getAge()+" years old.");
	}

	@Override
	public void end() {
	}
}
