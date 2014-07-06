package nl.jssl.autounit.inputs.objects;

import java.util.Map;
import java.util.Set;

import nl.jssl.autounit.AutoTester;
import nl.jssl.autounit.MethodCallResults;
import nl.jssl.autounit.testclasses.SomeBean;

import org.junit.Assert;
import org.junit.Test;

public class JavaBeanTests {
	@Test
	public void testJavaBeanArgument() {
		Map<String, MethodCallResults> results = new AutoTester().record(SomeBean.class);
		Set<String> keys = results.keySet();
		Assert.assertTrue(keys.contains("public void setFoo(java.lang.String arg1)"));
		MethodCallResults mcr = results.values().iterator().next();
		System.out.println(mcr.getCoverageResult().getLineCounter().getMissedCount() + " missed lines");
		System.out.println(mcr.getReport());
	}
}
