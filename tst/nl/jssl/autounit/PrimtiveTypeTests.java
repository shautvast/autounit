package nl.jssl.autounit;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.jssl.autounit.testclasses.BooleanArguments;
import nl.jssl.autounit.testclasses.ByteArguments;
import nl.jssl.autounit.testclasses.FloatArguments;
import nl.jssl.autounit.testclasses.IntArguments;

import org.junit.Test;

public class PrimtiveTypeTests {
	@Test
	public void testGetPublicMethods() throws NoSuchMethodException, SecurityException {
		Method evenOrUneven = IntArguments.class.getMethod("evenOrUneven", int.class);
		List<Method> publicMethods = new Recorder(IntArguments.class).getPublicMethods();

		assertEquals(2, publicMethods.size());
		assertEquals(evenOrUneven, publicMethods.get(0));

	}

	@Test
	public void testIntegerArgument() {
		Map<String, MethodCallResults> results = new AutoTester().record(IntArguments.class);
		Set<String> keys = results.keySet();
		assertTrue(keys.contains("public java.lang.String evenOrUneven(int arg1)"));
		MethodCallResults mcr = results.values().iterator().next();
		System.out.println(mcr.getCoverageResult().getLineCounter().getMissedCount() + " missed lines");
		System.out.println(mcr.getReport());
	}

	@Test
	public void testBooleanArgument() {
		Map<String, MethodCallResults> results = new AutoTester().record(BooleanArguments.class);
		Set<String> keys = results.keySet();
		assertTrue(keys.contains("public java.lang.String getText(boolean arg1,boolean arg2)"));
		MethodCallResults mcr = results.values().iterator().next();
		System.out.println(mcr.getCoverageResult().getLineCounter().getMissedCount() + " missed lines");
		System.out.println(mcr.getReport());
	}

	@Test
	public void testByteArgument() {
		Map<String, MethodCallResults> results = new AutoTester().record(ByteArguments.class);
		Set<String> keys = results.keySet();
		assertTrue(keys.contains("public int getDouble(byte arg1)"));
		MethodCallResults mcr = results.values().iterator().next();
		System.out.println(mcr.getCoverageResult().getLineCounter().getMissedCount() + " missed lines");
		System.out.println(mcr.getReport());
	}

	@Test
	public void testFloatArgument() {
		Map<String, MethodCallResults> results = new AutoTester().record(FloatArguments.class);
		Set<String> keys = results.keySet();
		assertTrue(keys.contains("public int round(float arg1)"));
		MethodCallResults mcr = results.values().iterator().next();
		System.out.println(mcr.getCoverageResult().getLineCounter().getMissedCount() + " missed lines");
		System.out.println(mcr.getReport());
	}

}
