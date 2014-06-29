package nl.jssl.autounit.inputs;

import java.lang.reflect.Method;

import org.mockito.Mockito;

public class MocksFactory {
	CombinedInputSetFactory inputSetFactory = new CombinedInputSetFactory();

	public SingleTypeInputs<?> getMockInputs(Class<?> testTarget, Class<?> parametertype) {
		SingleTypeInputs<Object> inputs = new SingleTypeInputs<>();
		setExpectations(parametertype);
		// inputs.add(mock);
		return inputs;
	}

	private void setExpectations(Class<?> parametertype) {
		Method[] declaredMethods = parametertype.getDeclaredMethods();
		for (Method m : declaredMethods) {
			// Set<SingleTypeInputs<?>> inputs =
			// inputSetFactory.getInputs(parametertype, m);
		}
		Object mock = Mockito.mock(parametertype);
	}
}
