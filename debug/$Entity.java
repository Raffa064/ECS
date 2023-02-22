package ecs.debug;

import ecs.Component;
import ecs.Entity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class $Entity extends Entity {
	public HashMap<Component, Long> components = new HashMap<>();
	
	@Override
	public <T extends Component> T getComponent(Class<T> componentClass) {
		T component = super.getComponent(componentClass);
		components.put(component, components.getOrDefault(component, 0L) + 1);
		return component;
	}
	
	public <T extends Map.Entry<Component, Long>> String log() {
		String separator = "-----------------------------------------------\n";
		String log = separator;
		log += "Entity class: "+getClass().getName()+"\n";
		log += "Entity id: "+getId()+"\n";
		log += "Component use counter:\n";
		
		List<T> list = new ArrayList<>(components.entrySet());
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T a, T b) {
				return (int)(b.getValue() - a.getValue());
			}
		});

		for (T entry : list) {
			log += "- "+entry.getKey().getClass().getName() + ": " + entry.getValue()+"\n";
		}
		
		return log;
	}
}
