package nl.jssl.autounit.classanalyser;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import nl.jssl.autounit.inputs.MethodcallArgumentsFactory;
import nl.jssl.autounit.util.LinkedList;

/**
 * Analyses the class you want to create tests for.
 */
public class ClassAnalyser<T> {
	private Class<T> testTarget;

	public ClassAnalyser(Class<T> testTarget) {
		this.testTarget = testTarget;
	}

	public ClassResults analyseAndGetResults() {
		List<MethodExecutionResults> classresults = new ArrayList<>();

		for (Method m : getPublicMethods()) {
			classresults.add(analyseMethod(m));
		}

		return new ClassResults(testTarget, classresults);
	}

	private MethodExecutionResults analyseMethod(Method method) {
		List<LinkedList> inputSet = new MethodcallArgumentsFactory(testTarget).getInputs(method);

		MethodcallExecutor<T> methodcallExecutor = new MethodcallExecutor<>(testTarget, method);

		return methodcallExecutor.executeAndGetResults(inputSet);
	}

	List<Method> getPublicMethods() {
		List<Method> publicMethods = new ArrayList<Method>();

		for (Method m : testTarget.getDeclaredMethods()) {
			if (Modifier.isPublic(m.getModifiers())) {
				publicMethods.add(m);
			}
		}
		return publicMethods;
	}

}
