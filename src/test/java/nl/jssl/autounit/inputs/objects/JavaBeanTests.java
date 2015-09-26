package nl.jssl.autounit.inputs.objects;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.TreeSet;

import org.junit.Test;

import nl.jssl.autounit.JUnitTestCreator;
import nl.jssl.autounit.classanalyser.ClassAnalyser;
import nl.jssl.autounit.classanalyser.MethodExecutionResults;
import nl.jssl.autounit.testclasses.SomeBean;

public class JavaBeanTests {
	@Test
	public void testJavaBeanArgument() {
		Iterator<MethodExecutionResults> results = getSortedAnalysisResults().iterator();

		assertEquals("public int getBar()", results.next().getMethodSignature());
		assertEquals("public java.lang.String getFoo()", results.next().getMethodSignature());
		assertEquals("public void setBar(int arg1)", results.next().getMethodSignature());
		assertEquals("public void setFoo(java.lang.String arg1)", results.next().getMethodSignature());
	}

	private TreeSet<MethodExecutionResults> getSortedAnalysisResults() {
		return new TreeSet<>(new ClassAnalyser<>(SomeBean.class).analyseAndGetResults().getMethodCallResults());
	}

	@Test
	public void getUnittest() {
		new JUnitTestCreator<>(SomeBean.class).create();
	}
}
