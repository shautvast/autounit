package nl.jssl.autounit.inputs.objects;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;

public class ObjectArgumentFactoryTests {
	@Test
	public void testVoidReturn() throws NoSuchMethodException, SecurityException {
		ObjectArgumentFactory o = new ObjectArgumentFactory();
		Method testVoidReturnMethod = ObjectArgumentFactoryTests.class.getMethod("testVoidReturn", new Class<?>[] {});
		assertEquals(false, o.returnsVoid(testVoidReturnMethod));

		Method getBarMethod = ObjectArgumentFactoryTests.class.getMethod("getBar", new Class<?>[] {});
		assertEquals(true, o.returnsVoid(getBarMethod));
	}

	public String getBar() {
		return "foo";
	}
}
