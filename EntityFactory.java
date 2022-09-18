package ecs;

//Now this class are default implemented, with an mechanism to extends other "sub factorys"
import java.lang.reflect.Field;

public class EntityFactory extends MultipleExtends {
	public World world;

	public EntityFactory(World world) {
		this.world = world;
	}

	@Override
	public void extend(Class... classesToExtends) {
		super.extend(classesToExtends);
		
		int extendedClassesSize = extendedClasses.size();
		for (int i = 0; i < classesToExtends.length; i++) {
			try {
				Object obj = extendedClasses.get(extendedClassesSize - i - 1);
				Field worldField = obj.getClass().getField("world");
				worldField.set(obj, world);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
