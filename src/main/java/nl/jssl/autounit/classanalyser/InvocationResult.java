package nl.jssl.autounit.classanalyser;

import org.jacoco.core.analysis.IClassCoverage;

class InvocationResult {
	private final IClassCoverage coverage;
	private final Object output;

	public InvocationResult(IClassCoverage coverage, Object output) {
		super();
		this.coverage = coverage;
		this.output = output;
	}

	public IClassCoverage getCoverage() {
		return coverage;
	}

	public Object getOutput() {
		return output;
	}

}