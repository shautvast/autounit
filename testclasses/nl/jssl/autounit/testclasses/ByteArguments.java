package nl.jssl.autounit.testclasses;

public class ByteArguments {
	public int getDouble(byte value) {
		if (value == 0) {
			return 11;
		} else {
			return value * 2;
		}
	}
}
