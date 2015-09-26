package nl.jssl.autounit.classanalyser;

import nl.jssl.autounit.util.Pair;

public class InAndOutput {
	private final Pair input;
	private final Object output;

	public InAndOutput(Pair input, Object output) {
		this.input = input;
		this.output = output;
	}

	public Pair getInput() {
		return input;
	}

	public Object getOutput() {
		return output;
	}
}
