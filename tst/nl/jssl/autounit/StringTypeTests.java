package nl.jssl.autounit;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Set;

import nl.jssl.autounit.testclasses.StringArguments;

import org.junit.Test;

public class StringTypeTests {

	@Test
	public void testStringArgumentShouldBeTjeempie() {
		Map<String, MethodCallResults> results = new AutoTester().record(StringArguments.class);
		Set<String> keys = results.keySet();
		String methodSignature = keys.iterator().next();
		assertEquals("public boolean getBar(java.lang.String arg1)", methodSignature);
		MethodCallResults mcr = results.values().iterator().next();
		System.out.println(mcr.getCoverageResult().getLineCounter().getMissedCount() + " missed lines");
		System.out.println(mcr.getReport());
	}
	// @Test
	// public void testObjectArgument() throws NoSuchMethodException,
	// SecurityException {
	// IntArguments t = new IntArguments();
	// Map<String, MethodCallResults> results = new
	// AutoTester().record(MethodSignatureMaker.class);
	// Set<String> keys = results.keySet();
	// assertTrue(keys.contains("public java.lang.String getMethodSignature(java.lang.reflect.Method arg1)"));
	// MethodCallResults mcr = results.values().iterator().next();
	// System.out.println(mcr.getCoverageResult().getLineCounter().getMissedCount()
	// + " missed lines");
	// System.out.println(mcr.getReport());
	// }
}
