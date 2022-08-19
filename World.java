package ecs;

import java.util.List;
import java.util.ArrayList;

public class World {
	public static final int MODE_ADD = 0;
	public static final int MODE_REMOVE = 1;
	public static final int MODE_UPDATE = 2;
	
    private List<System> systems = new ArrayList<>();
	private List<Entity> entities = new ArrayList<>();
	private List<Entity> entitiesToRemove = new ArrayList<>();
	private int current_max_id = 0;
	
	public World() {
	}

	public void setLogs(String logs) {
	}

	public List<Entity> getEntities() {
		return entities;
	}
	
	public <T> System getSystem(Class<T> systemType) {
		for (int i = 0; i < systems.size(); i++) {
			if (systems.get(i).getClass() instanceof T) {
				return systems.get(i);
			}
		}
		return null;
	}

	public void addSystem(System system) {
		for (Entity entity : entities) {
			if (system.canProcess(entity)) {
				system.entities.add(entity);
			}
		}
		systems.add(system);
	}

	public void removeSystem(System system) {
		systems.remove(system);
	}

	public <T> void removeSystemByType(Class<T> systemClass) {
		for (int i = 0; i < systems.size(); i++) {
			if (systems.get(i).getClass() instanceof T) {
				systems.remove(i);
				break;
			}
		}
	}
	
	public Entity getEntityById(int entityId) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getId() == entityId) {
				return entities.get(i);
			}
		}
		return null;
	}

	public void addEntity(Entity entity) {
		entity.setWorld(this);
		entity.setId(++current_max_id);
		entities.add(entity);
		onUpdateEntities(entity, MODE_ADD);
	}

	public void removeEntity(Entity entity) {
		entitiesToRemove.add(entity);
	}

	public void onUpdateEntities(Entity e, int mode) { 
		for (System system : systems) {
			if (system.canProcess(e)) {
				if (mode == MODE_ADD) {
					java.lang.System.out.println("adding an entity to a system");
					system.entities.add(e);
				} else if (mode == MODE_REMOVE) {
					java.lang.System.out.println("removing an entity to a system");
					system.entities.remove(e);
				} else if (mode == MODE_UPDATE) {
					java.lang.System.out.println("updating an entity on a system");
					system.entities.remove(e);
					if (system.canProcess(e)) {
						system.entities.add(e);														
					}
				}
			}
		}
	}
	
	public void update(float delta) {
		for (System system : systems) {
			system.start();
			for (Entity entity : system.entities) {
				system.process(this, entity, delta);
			}
			system.end();
		}
		for (Entity entity : entitiesToRemove) {
			entities.remove(entity);
			onUpdateEntities(entity, MODE_REMOVE);
		}
		entitiesToRemove.clear();
	}
}
