package nl.jssl.autounit.classanalyser;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import nl.jssl.autounit.inputs.MethodcallArgumentsFactory;
import nl.jssl.autounit.util.Pair;

public class ClassAnalyser {
	private Class<?> testTarget;

	public ClassAnalyser(Class<?> testTarget) {
		this.testTarget = testTarget;
	}

	public ClassResults analyse() {
		List<MethodCallResults> classresults = new ArrayList<>();
		for (Method m : getPublicMethods()) {
			MethodCallResults methodresults = recordMethod(m);
			classresults.add(methodresults);
		}

		return new ClassResults(testTarget, classresults);
	}

	private MethodCallResults recordMethod(Method m) {
		List<Pair> inputSet = new MethodcallArgumentsFactory().getInputs(testTarget, m);
		MethodcallExecutor methodcallExecutor = new MethodcallExecutor(testTarget, m);
		methodcallExecutor.execute(inputSet);
		MethodCallResults mcr = methodcallExecutor.getResult();
		return mcr;
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
