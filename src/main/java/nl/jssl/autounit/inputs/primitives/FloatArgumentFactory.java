package nl.jssl.autounit.inputs.primitives;

import nl.jssl.autounit.inputs.ArgumentsForSingleParameter;


public class FloatArgumentFactory implements ArgumentFactory<Float> {

	public ArgumentsForSingleParameter<Float> getInputs(Class<?> testTarget) {
		ArgumentsForSingleParameter<Float> inputs = new ArgumentsForSingleParameter<Float>();
		for (int i = 0; i < 1000; i++) {
			inputs.add((float) ((2 * Math.random() - 2) * Float.MAX_VALUE));
		}
		inputs.add(Float.MIN_VALUE);
		inputs.add(-2F);
		inputs.add(-1F);
		inputs.add(0F);
		inputs.add(1F);
		inputs.add(2F);
		inputs.add(Float.MAX_VALUE);
		return inputs;
	}
}
