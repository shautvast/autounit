package nl.jssl.autounit.inputs.primtives;

import nl.jssl.autounit.inputs.ArgumentsForSingleParameter;

public class BooleanArgumentFactory implements ArgumentFactory<Boolean> {

	public ArgumentsForSingleParameter<Boolean> getInputs(Class<?> testTarget) {
		ArgumentsForSingleParameter<Boolean> inputs = new ArgumentsForSingleParameter<Boolean>();
		inputs.add(Boolean.FALSE);
		inputs.add(Boolean.TRUE);
		return inputs;
	}

}
