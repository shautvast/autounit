package nl.jssl.autounit.inputs;

import nl.jssl.autounit.Configuration;
import nl.jssl.autounit.utils.ConstantpoolReader;

public class StringInputsFactory implements InputsFactory<String> {
	private static ConstantpoolReader constantpoolReader = new ConstantpoolReader(Configuration.getClassPool());

	@Override
	public SingleTypeInputs<String> getInputs(Class<?> testTarget) {
		SingleTypeInputs<String> inputs = new SingleTypeInputs<String>();
		inputs.add(null);
		inputs.add("some");
		inputs.addAll(constantpoolReader.scanStrings(testTarget));
		return inputs;
	}

}
