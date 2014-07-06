package nl.jssl.autounit.inputs.primtives;

import nl.jssl.autounit.inputs.ArgumentsForSingleParameter;

public interface ArgumentFactory<T> {
	ArgumentsForSingleParameter<T> getInputs(Class<?> testTarget);
}
