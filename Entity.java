package ecs;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private BinaryTree<Component> components = new BinaryTree<>();
	private World world;
	private int id;

	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}
	
	public boolean contains(Class<? extends Component>... componentClasses) {
		for (Class<? extends Component> c : componentClasses) {
			if (getComponent(c) == null) {
				return false;
			}
		}
		return true;
	}
	
	public <T extends Component> T getComponent(Class<T> componentClass) {
		return (T) components.search(componentClass.getName().hashCode());
	}
	
	public void addComponent(Component component) {
		component.entity = this;
		components.put(component.getClass().getName().hashCode(), component);
	}

	public void update() {
		world.onUpdateEntities(this, World.MODE_UPDATE);
	}
	
	public void removeComponent(Component component) {
		components.remove(component.getClass().getName().hashCode());
		world.onUpdateEntities(this, World.MODE_UPDATE);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
