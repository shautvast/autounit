package nl.jssl.autounit.results;

import java.io.PrintStream;

import nl.jssl.autounit.classanalyser.ClassResults;

public abstract class ResultsWriter {
	protected PrintStream out;

	public ResultsWriter(PrintStream out) {
		super();
		this.out = out;
	}

	public abstract void write(ClassResults classResults);
}
