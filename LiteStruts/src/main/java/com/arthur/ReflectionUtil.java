package com.arthur;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtil {

	public static List<Method> getSetterMethods(Class<?> clz) {
		return getMethods(clz, "set");
	}

	public static void setParameters(Object o, Map<String, String> params) {
		
		List<Method> methods = getSetterMethods(o.getClass());
		
		for(String name : params.keySet()) {
			String methodName = "set" + name;
			for(Method m : methods) {
				if (m.getName().equalsIgnoreCase(methodName)) {
					try {
						m.invoke(o, params.get(name));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static List<Method> getGetterMethods(Class<?> clz) {
		return getMethods(clz, "get");
	}
	
	private static List<Method> getMethods(Class<?> clz, String startWithName){
		List<Method> methods = new ArrayList<Method>();
		
		for(Method m : clz.getDeclaredMethods()) {
			if(m.getName().startsWith(startWithName)) {
				methods.add(m);
			}
		}
		
		return methods;
	}

	public static Map<String, Object> getParamterMap(Object o) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		List<Method> methods = getGetterMethods(o.getClass());
		
		for(Method m : methods) {
			String methodName = m.getName();
			String name = methodName.replaceFirst("get", "").toLowerCase();
			try {
				Object value = m.invoke(o);
				params.put(name, value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return params;
	}
	

}
