package nl.jssl.autounit.inputs.primtives;

import nl.jssl.autounit.inputs.ArgumentsForSingleParameter;

public class ByteArgumentFactory implements ArgumentFactory<Byte> {

	public ArgumentsForSingleParameter<Byte> getInputs(Class<?> testTarget) {
		ArgumentsForSingleParameter<Byte> inputs = new ArgumentsForSingleParameter<Byte>();
		for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
			inputs.add(i);
		}
		return inputs;
	}

}
