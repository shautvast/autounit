package nl.jssl.autounit.inputs;

public class BooleanInputsFactory implements InputsFactory<Boolean> {

	public SingleTypeInputs<Boolean> getInputs(Class<?> testTarget) {
		SingleTypeInputs<Boolean> inputs = new SingleTypeInputs<Boolean>();
		inputs.add(Boolean.FALSE);
		inputs.add(Boolean.TRUE);
		return inputs;
	}

}
