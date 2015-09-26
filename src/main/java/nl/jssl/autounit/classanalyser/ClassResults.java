package nl.jssl.autounit.classanalyser;

import java.util.List;

public class ClassResults {
	private final Class<?> type;
	private final List<MethodCallResults> methodCallResults;

	public ClassResults(Class<?> type, List<MethodCallResults> methodCallResults) {
		super();
		this.type = type;
		this.methodCallResults = methodCallResults;
	}

	public List<MethodCallResults> getMethodCallResults() {
		return methodCallResults;
	}

	public Class<?> getType() {
		return type;
	}
}
