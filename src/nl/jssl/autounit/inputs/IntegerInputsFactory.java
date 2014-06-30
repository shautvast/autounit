package nl.jssl.autounit.inputs;

public class IntegerInputsFactory implements InputsFactory<Integer> {

	public SingleTypeInputs<Integer> getInputs(Class<?> testTarget) {
		SingleTypeInputs<Integer> inputs = new SingleTypeInputs<Integer>();
//		for (int i = 0; i < 2; i++) {
//			inputs.add((int) ((2 * Math.random() - 2) * Integer.MAX_VALUE));
//		}
		inputs.add(Integer.MIN_VALUE);
		inputs.add(-1);
		inputs.add(0);
		inputs.add(1);
		inputs.add(Integer.MAX_VALUE);
		return inputs;
	}

}
