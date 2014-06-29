package nl.jssl.autounit.inputs;


public class FloatInputsFactory implements InputsFactory<Float> {

	public SingleTypeInputs<Float> getInputs(Class<?> testTarget) {
		SingleTypeInputs<Float> inputs = new SingleTypeInputs<Float>();
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
