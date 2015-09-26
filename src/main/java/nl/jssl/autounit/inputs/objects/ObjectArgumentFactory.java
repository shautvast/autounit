package nl.jssl.autounit.inputs.objects;

import java.lang.reflect.Method;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import nl.jssl.autounit.inputs.ArgumentsForSingleParameter;

/**
 * Creates arguments if they are objects. Methods that return values are
 * populated like in regular mocking.
 * 
 */
public class ObjectArgumentFactory {

	public ArgumentsForSingleParameter<?> getObjectArgument(Class<?> parametertype) {
		ArgumentsForSingleParameter<Object> inputs = new ArgumentsForSingleParameter<Object>();
		Object mock = createMock(parametertype);
		inputs.add(mock);
		return inputs;
	}

	private Object createMock(Class<?> parametertype) {
		Object mock = Mockito.mock(parametertype, new DefaultAnswer());

		return mock;
	}

	static class DefaultAnswer implements Answer<Object> {

		@Override
		public Object answer(InvocationOnMock invocation) throws Throwable {
			Method method = invocation.getMethod();
			// necessary?
			if (returnsVoid(method)) {
				return Void.TYPE;
			}
			return null;

		}

	}

	static boolean returnsVoid(Method method) {
		Class<?> returnType = method.getReturnType();
		return returnType != Void.TYPE;
	}

}
