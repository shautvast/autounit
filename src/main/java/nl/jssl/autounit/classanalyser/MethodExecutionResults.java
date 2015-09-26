package nl.jssl.autounit.classanalyser;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.jacoco.core.analysis.IClassCoverage;

import nl.jssl.autounit.util.LinkedList;

public class MethodExecutionResults implements Comparable<MethodExecutionResults> {
	private transient Object testinstance;
	private transient Method executedMethod;
	private List<InAndOutput> contents = new ArrayList<InAndOutput>();
	private transient IClassCoverage coverageResult;

	public MethodExecutionResults(Object testClass, Method m) {
		super();
		this.testinstance = testClass;
		this.executedMethod = m;
	}

	public void addResult(LinkedList input, Object output) {
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

	public String getMethodSignature() {
		return getMethodSignature(executedMethod);
	}

	public String getMethodName() {
		return executedMethod.getName();
	}

	private String getMethodSignature(Method m) {
		String signature = Modifier.toString(m.getModifiers()) + " " + m.getReturnType().getName() + " " + m.getName()
				+ "(";
		int index = 1;
		Class<?>[] parameterTypes = m.getParameterTypes();
		for (Class<?> type : parameterTypes) {
			signature += type.getName() + " arg" + (index++);
			if (index <= parameterTypes.length) {
				signature += ",";
			}
		}
		signature += ")";
		return signature;
	}

	public Class<?> getMethodReturnType() {
		return executedMethod.getReturnType();
	}

	@Override
	public int compareTo(MethodExecutionResults o) {
		return getMethodName().compareTo(o.getMethodName());
	}
}
