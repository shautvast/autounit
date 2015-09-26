package nl.jssl.autounit.classanalyser;

import java.util.List;

public class ClassResults {
	private final Class<?> type;
	private final List<MethodExecutionResults> methodCallResults;

	public ClassResults(Class<?> type, List<MethodExecutionResults> methodCallResults) {
		super();
		this.type = type;
		this.methodCallResults = methodCallResults;
	}

	public List<MethodExecutionResults> getMethodCallResults() {
		return methodCallResults;
	}

	public Class<?> getType() {
		return type;
	}
}
