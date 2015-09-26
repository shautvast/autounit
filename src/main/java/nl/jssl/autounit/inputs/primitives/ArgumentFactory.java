package nl.jssl.autounit.inputs.primitives;

import nl.jssl.autounit.inputs.ArgumentsForSingleParameter;

public interface ArgumentFactory<T> {
	ArgumentsForSingleParameter<T> getInputs(Class<?> testTarget);
}
