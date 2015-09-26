package nl.jssl.autounit.classanalyser;

import nl.jssl.autounit.util.LinkedList;

public class InAndOutput {
	private final LinkedList input;
	private final Object output;

	public InAndOutput(LinkedList input, Object output) {
		this.input = input;
		this.output = output;
	}

	public LinkedList getInput() {
		return input;
	}

	public Object getOutput() {
		return output;
	}
}
