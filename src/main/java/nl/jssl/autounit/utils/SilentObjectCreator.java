package nl.jssl.autounit.utils;

import java.lang.reflect.Constructor;

import sun.reflect.ReflectionFactory;

/**
 * Creates Objects from any class.
 * 
 * Kindly copied from http://www.javaspecialists.eu/archive/Issue175.html
 */
public class SilentObjectCreator {
	public static <T> T create(Class<T> clazz) {
		return create(clazz, Object.class);
	}

	public static <T> T create(Class<T> clazz, Class<? super T> parent) {
		try {
			ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
			Constructor<?> objDef = parent.getDeclaredConstructor();
			Constructor<T> intConstr = (Constructor<T>) rf.newConstructorForSerialization(clazz, objDef);
			return clazz.cast(intConstr.newInstance());
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot create object", e);
		}
	}
}