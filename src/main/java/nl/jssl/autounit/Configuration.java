package nl.jssl.autounit;

import javassist.ClassPool;
import javassist.NotFoundException;

/**
 * TODO make configurable
 * 
 */
public class Configuration {
	public static ClassPool getClassPool() {
		ClassPool classPool = new ClassPool();
		try {
			classPool.appendClassPath("c:\\workspaces\\autounit\\autounit\\bin");
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
		return classPool;
	}
}
