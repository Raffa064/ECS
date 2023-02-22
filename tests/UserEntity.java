package ecs.tests;

import ecs.debug.$Entity;
import ecs.tests.components.AgeComponent;
import tests.components.NameComponent;

//This entity are extending the $EntityObject, that is a debug version of the EntityObject
public class UserEntity extends $Entity {
	public NameComponent nameComponent = $(new NameComponent());
	public AgeComponent ageComponent = $(new AgeComponent());

	public void setName(String name) {
		nameComponent.name = name;
	}

	public String getName() {
		return nameComponent.name;
	}
	
	public void setAge(int age) {
		ageComponent.age = age;
	}
	
	public int getAge() {
		return ageComponent.age;
	}
}
