package nl.jssl.autounit;

import javassist.ClassPool;
import javassist.NotFoundException;

/**
 * 
 */
public class Configuration {
	public static ClassPool getClassPool() {
		ClassPool classPool = new ClassPool();
		try {
			classPool.appendClassPath(getClasspathSettingOrEclipseDefault());
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
		return classPool;
	}

	private static String getClasspathSettingOrEclipseDefault() {
		return System.getProperty("autounit.cp", "bin");
	}
}
