package nl.jssl.autounit.inputs;

public interface InputsFactory<T> {
	SingleTypeInputs<T> getInputs(Class<?> testTarget);
}
