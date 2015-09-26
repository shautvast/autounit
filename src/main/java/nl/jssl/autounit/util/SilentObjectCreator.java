package nl.jssl.autounit.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nl.jssl.autounit.classanalyser.AnalysisFailed;
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
			return tryCreateInstance(clazz, parent);
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException
				| InvocationTargetException e) {
			throw new AnalysisFailed("Cannot create object", e);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T tryCreateInstance(Class<T> clazz, Class<? super T> parent)
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

		ReflectionFactory reflectionFactory = ReflectionFactory.getReflectionFactory();
		Constructor<?> objDef = parent.getDeclaredConstructor();
		Constructor<T> intConstr = (Constructor<T>) reflectionFactory.newConstructorForSerialization(clazz, objDef);
		return clazz.cast(intConstr.newInstance());
	}
}