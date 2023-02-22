package ecs;

import ecs.debug.WorldDebugger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import ecs.debug.$Entity;

public class World {
	//These modes are used for internal updates on ECS system 
	public static final int MODE_ADD = 0;                       //When add a new Entity
	public static final int MODE_REMOVE = 1;                    //When remove an Entity
	public static final int MODE_UPDATE = 2;                    //When update the component list of an Entity
	
    private List<System> systems = new ArrayList<>();           //List of systems for process entities
	private List<Entity> entities = new ArrayList<>();          //List of actived entities, used for update new added systems
	private List<Entity> entitiesToRemove = new ArrayList<>();  //List of entities to remove before the next update
	private int current_max_id = 0;                             //Current id, increased by one for each new entity added in the World.
	private WorldDebugger debugger = new WorldDebugger();

	public List<Entity> getEntities() {
		return entities;
	}
	
	//Alias to quick add entities
	public <T extends Entity> T $(T entity) {
		addEntity(entity);
		return entity;
	}

	//Alias to quick add systems
	public <T extends System> T $(T system) {
		addSystem(system);
		return system;
	}

	//Alias to quick get systems
	public <T extends System> T $(Class<T> systemType) {
		return getSystem(systemType);
	}
	
	//Search for a system by the given class
	public <T extends System> T getSystem(Class<T> systemType) {
		for (int i = 0; i < systems.size(); i++) {
			if (systems.get(i).getClass().getName().hashCode() == systemType.getName().hashCode()) {
				return (T) systems.get(i);
			}
		}
		return null;
	}
	
    //Fill the given system with entities it can process, and insert it into the World
	public void addSystem(System system) {
		for (Entity entity : entities) {
			if (system.canProcess(entity)) {
				system.entities.add(entity);
			}
		}
		systems.add(system);
		debugger.systems.put(system, 0L);
	}

	//Remove a system from the world
	public void removeSystem(System system) {
		systems.remove(system);
	}

	//Remove a system from the world that is instance of the given class
	public <T> void removeSystemByType(Class<T> systemClass) {
		for (int i = 0; i < systems.size(); i++) {
			if (systems.get(i).getClass() instanceof T) {
				systems.remove(i);
				break;
			}
		}
	}
	
	//Get an entity by the ID, no longer recommended, because it generate iterations in the system
	public Entity getEntityById(int entityId) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).getId() == entityId) {
				return entities.get(i);
			}
		}
		return null;
	}

	//Add a entity to the world after setup it
	public void addEntity(Entity entity) {
		entity.setWorld(this);
		entity.setId(++current_max_id);
		entities.add(entity);
		entity.setActived(true);
		onUpdateEntities(entity, MODE_ADD); //Call to the system that an new entity have added, for insert it in the required systems
	}
	
	//Add multiple entities to the world
	public void addEntities(Entity... entities) {
		for (Entity entity : entities) {
			addEntity(entity);
		}
	}

	//Put an entity in the remove list
	public void removeEntity(Entity entity) {
		entitiesToRemove.add(entity);
		entity.setActived(false);
	}

	//Call the system for each entities modification (ADD, UPDATE, and REMOVE)
	public void onUpdateEntities(Entity e, int mode) { 
		for (System system : systems) {
			if (system.canProcess(e)) {
				if (mode == MODE_ADD) {
					system.entities.add(e);
				} else if (mode == MODE_REMOVE) {
					system.entities.remove(e);
				} else if (mode == MODE_UPDATE) {
					system.entities.remove(e);
					if (system.canProcess(e)) {
						system.entities.add(e);														
					}
				}
			}
		}
	}
	
	//Called for update the ECS, it will call process() for each entity in each systems entity lists
	public void update(float delta) {
		debugger.iterations = 0;
		for (System system : systems) {
			debugger.start();
			system.start();
			for (Entity entity : system.entities) {
				system.process(this, entity, delta);
				debugger.iterations++;
			}
			system.end();
			debugger.end(system);
		}
		
		for (Entity entity : entitiesToRemove) {
			entities.remove(entity);
			onUpdateEntities(entity, MODE_REMOVE);
		}
		
		entitiesToRemove.clear();
	}
	
	//Returns the last iteration count (Number of time that process() is called)
//	public int getIterationCount() {
//		return iterationCount;
//	}
	
	//Number of entities in the world
	public int getEntitiesCount() {
		return entities.size();
	}
	
	//Number of systems in the world
	public int getSystemCount() {
		return systems.size();
	}
	
	public <T extends Map.Entry<System, Long>> String log() {
		String log = "WORLD DEBUGER\n";
		log += "Entity count: "+getEntitiesCount()+"\n";
		log += "Iterations: "+debugger.iterations+"\n";
		log += "Systems duration, in nanos:\n";
		
		List<T> list = new ArrayList<>(debugger.systems.entrySet());
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T a, T b) {
				return (int)(b.getValue() - a.getValue());
			}
		});
		
		for (T entry : list) {
			log += "- "+entry.getKey().getClass().getName() + ": " + entry.getValue()+"\n";
		}
		
		for (Entity entity : entities) {
			if (entity instanceof $Entity) {
				log += (($Entity)entity).log();
			}
		}
		
		return log;
	}
}
