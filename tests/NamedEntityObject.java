package ecs.tests;

import ecs.Entity;
import tests.components.NameComponent;

public class NamedEntityObject extends Entity {
	public NameComponent nameComponent = $(new NameComponent());

	public void setName(String name) {
		nameComponent.name = name;
	}

	public String getName() {
		return nameComponent.name;
	}
}
