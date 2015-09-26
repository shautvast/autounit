package nl.jssl.autounit.classanalyser;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.junit.Test;

import nl.jssl.autounit.testclasses.BooleanArguments;
import nl.jssl.autounit.testclasses.ByteArguments;
import nl.jssl.autounit.testclasses.FloatArguments;
import nl.jssl.autounit.testclasses.IntArguments;

public class PrimtiveTypeTests {
	@Test
	public void testGetPublicMethods() throws NoSuchMethodException, SecurityException {
		List<Method> publicMethods = new ClassAnalyser(IntArguments.class).getPublicMethods();

		assertEquals(2, publicMethods.size());
	}

	@Test
	public void testIntegerArgument() {
		Iterator<MethodCallResults> methodCallResults = new TreeSet<>(
				new ClassAnalyser(IntArguments.class).analyse().getMethodCallResults()).iterator();

		assertEquals("public java.lang.String evenOrUneven(int arg1,int arg2)",
				methodCallResults.next().getMethodSignature());
		assertEquals("public int randomOther(int arg1)", methodCallResults.next().getMethodSignature());
	}

	@Test
	public void testBooleanArgument() {
		MethodCallResults mcr = new ClassAnalyser(BooleanArguments.class).analyse().getMethodCallResults().get(0);
		assertEquals(mcr.getMethodSignature(), "public java.lang.String getText(boolean arg1,boolean arg2)");
	}

	@Test
	public void testByteArgument() {
		MethodCallResults mcr = new ClassAnalyser(ByteArguments.class).analyse().getMethodCallResults().get(0);
		assertEquals(mcr.getMethodSignature(), "public int getDouble(byte arg1)");
	}

	@Test
	public void testFloatArgument() {
		MethodCallResults mcr = new ClassAnalyser(FloatArguments.class).analyse().getMethodCallResults().get(0);
		assertEquals(mcr.getMethodSignature(), "public int round(float arg1)");
	}

}
