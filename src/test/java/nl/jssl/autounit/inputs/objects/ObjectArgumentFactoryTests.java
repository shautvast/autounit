package nl.jssl.autounit.inputs.objects;

import static nl.jssl.autounit.inputs.objects.ObjectArgumentFactory.returnsVoid;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;

public class ObjectArgumentFactoryTests {
	@Test
	public void testVoidReturn() throws NoSuchMethodException, SecurityException {
		Method testVoidReturnMethod = ObjectArgumentFactoryTests.class.getMethod("testVoidReturn", new Class<?>[] {});
		assertEquals(false, returnsVoid(testVoidReturnMethod));

		Method getBarMethod = ObjectArgumentFactoryTests.class.getMethod("getBar", new Class<?>[] {});
		assertEquals(true, returnsVoid(getBarMethod));
	}

	public String getBar() {
		return "foo";
	}
}
