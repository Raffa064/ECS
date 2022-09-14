package ecs;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private BinaryTree<Component> components = new BinaryTree<>();  //Tree of components, is a best way to improve process speed
	private World world;
	private int id; //Entity id, can be used for get the entity in the world using getEntity() method 
	private boolean actived; //Is true when entity is added to the world, false when rmeoved from the world

	protected void setActived(boolean actived) {
		this.actived = actived;
	}

	public boolean isActived() {
		return actived;
	} 
	
	//Automatically called by the World when add the entity in the World entity list
	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}
	
	//Returns true when constains all given Component classes
	public boolean contains(Class<? extends Component>... componentClasses) {
		for (Class<? extends Component> c : componentClasses) {
			if (getComponent(c) == null) {
				return false;
			}
		}
		return true;
	}
	
	//Returns true when constains least one of the given Component classes
	public boolean containsLeastOne(Class<? extends Component>... componentClasses) {
		for (Class<? extends Component> c : componentClasses) {
			if (getComponent(c) != null) {
				return true;
			}
		}
		return false;
	}
	
	//Uses the given class to search for an component in the component tree using the class name hash code as search id
	public <T extends Component> T getComponent(Class<T> componentClass) {
		return (T) components.search(componentClass.getName().hashCode());
	}
	
	//This method is called internally to tell World that something has been updated in the component tree.
	public void update() {
		if (world != null) {
			world.onUpdateEntities(this, World.MODE_UPDATE);
		}
	}
	
	//Add an component to the entity, and call to the world
	public void addComponent(Component component) {
		component.entity = this;
		components.put(component.getClass().getName().hashCode(), component);
		update();
	}
	
	//Remove a component from the entity, and call to the world
	public void removeComponent(Component component) {
		components.remove(component.getClass().getName().hashCode());
		update();
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
