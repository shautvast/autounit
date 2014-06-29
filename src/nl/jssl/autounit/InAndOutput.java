package nl.jssl.autounit;

public class InAndOutput {
	private final Object input;
	private final Object output;

	public InAndOutput(Object input, Object output) {
		this.input = input;
		this.output = output;
	}

	public Object getInput() {
		return input;
	}

	public Object getOutput() {
		return output;
	}
}
