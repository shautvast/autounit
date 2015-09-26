package nl.jssl.autounit.classanalyser;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import nl.jssl.autounit.testclasses.StringArguments;

public class StringTypeTests {

	@Test
	public void testStringArgumentShouldBeTjeempie() {
		List<MethodCallResults> results = new ClassAnalyser(StringArguments.class).analyse().getMethodCallResults();
		MethodCallResults mcr = results.get(0);
		String methodSignature = mcr.getMethodSignature();
		assertEquals("public boolean getBar(java.lang.String arg1)", methodSignature);
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
	// assertTrue(keys.contains("public java.lang.String
	// getMethodSignature(java.lang.reflect.Method arg1)"));
	// MethodCallResults mcr = results.values().iterator().next();
	// System.out.println(mcr.getCoverageResult().getLineCounter().getMissedCount()
	// + " missed lines");
	// System.out.println(mcr.getReport());
	// }
}
