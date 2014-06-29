package nl.jssl.autounit.inputs;


public class LongInputsFactory implements InputsFactory<Long> {

	public SingleTypeInputs<Long> getInputs(Class<?> testTarget) {
		SingleTypeInputs<Long> inputs = new SingleTypeInputs<Long>();
		for (int i = 0; i < 1000; i++) {
			inputs.add((long) ((2 * Math.random() - 2) * Long.MAX_VALUE));
		}
		inputs.add(Long.MIN_VALUE);
		inputs.add(-2L);
		inputs.add(-1L);
		inputs.add(0L);
		inputs.add(1L);
		inputs.add(2L);
		inputs.add(Long.MAX_VALUE);
		return inputs;
	}

}
