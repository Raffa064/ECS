package ecs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.InvocationTargetException;

public class MultipleExtends {
	protected List<Object> extendedClasses = new ArrayList<>();
	
	public void extend(Class... classesToExtends) {
		try {
			for (Class clazz : classesToExtends) {
				Object obj = clazz.getConstructor().newInstance();
				extendedClasses.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object $(String methodName, Object... params) {
		try {
			for (Object obj : extendedClasses) {
				Method method = getMethod(obj.getClass(), methodName, params);
				if (method != null) {
					method.setAccessible(true);
					return method.invoke(obj, params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	//This method allows to get a method from a class using params that extends the requested types (enables the hyerarchy of param types)
	private Method getMethod(Class clazz, String methodName, Object... params) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				boolean ok = true;
				int index = 0;
				for (Class paramClass : method.getParameterTypes()) {
					try {
						paramClass.asSubclass(params[index].getClass());
					} catch(Exception e) {
						ok = false;
						break;
					}
					index++;
				}
				if (ok) return method;
			}
		}
		return null;
	}
}
