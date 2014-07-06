package nl.jssl.autounit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jacoco.core.analysis.IClassCoverage;

public class MethodCallResults {
	private transient Object testinstance;
	private transient Method executedMethod;
	private List<InAndOutput> contents = new ArrayList<InAndOutput>();
	private transient IClassCoverage coverageResult;

	public MethodCallResults(Object testClass, Method m) {
		super();
		this.testinstance = testClass;
		this.executedMethod = m;
	}

	public void addResult(Object input, Object output) {
		contents.add(new InAndOutput(input, output));
	}

	public List<InAndOutput> getContents() {
		return contents;
	}

	public Object getTestinstance() {
		return testinstance;
	}

	public Method getExecutedMethod() {
		return executedMethod;
	}

	@Override
	public String toString() {
		return getReport();
	}

	public void setCoverageResult(IClassCoverage coverageResult) {
		this.coverageResult = coverageResult;
	}

	public IClassCoverage getCoverageResult() {
		return coverageResult;
	}

	public String getReport() {
		StringBuilder s = new StringBuilder();
		for (InAndOutput inAndOutput : contents) {
			s.append(inAndOutput.getInput() + " => " + inAndOutput.getOutput() + "\n");
		}
		return s.toString();
	}
}
