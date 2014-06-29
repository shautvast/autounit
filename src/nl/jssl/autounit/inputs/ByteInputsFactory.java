package nl.jssl.autounit.inputs;

public class ByteInputsFactory implements InputsFactory<Byte> {

	public SingleTypeInputs<Byte> getInputs(Class<?> testTarget) {
		SingleTypeInputs<Byte> inputs = new SingleTypeInputs<Byte>();
		for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
			inputs.add(i);
		}
		return inputs;
	}

}
