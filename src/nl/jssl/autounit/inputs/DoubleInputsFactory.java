package nl.jssl.autounit.inputs;


public class DoubleInputsFactory implements InputsFactory<Double> {

	public SingleTypeInputs<Double> getInputs(Class<?> testTarget) {
		SingleTypeInputs<Double> inputs = new SingleTypeInputs<Double>();
		for (int i = 0; i < 1000; i++) {
			inputs.add(((2 * Math.random() - 2) * Double.MAX_VALUE));
		}
		inputs.add(Double.MIN_VALUE);
		inputs.add(-2D);
		inputs.add(-1D);
		inputs.add(0D);
		inputs.add(1D);
		inputs.add(2D);
		inputs.add(Double.MAX_VALUE);
		return inputs;
	}

}
