package nl.jssl.autounit;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.jssl.autounit.inputs.ArgumentsForSingleCall;
import nl.jssl.autounit.inputs.CombinedInputSetFactory;
import nl.jssl.autounit.utils.Permuter.Tuple;

public class Recorder {
	private Class<?> testTarget;

	public Recorder(Class<?> testTarget) {
		this.testTarget = testTarget;
	}

	public Map<String, MethodCallResults> record() {
		Map<String, MethodCallResults> classresults = new HashMap<String, MethodCallResults>();
		for (Method m : getPublicMethods()) {
			MethodCallResults methodresults = recordMethod(m);
			classresults.put(getMethodSignature(m), methodresults);
		}
		return classresults;
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

	private MethodCallResults recordMethod(Method m) {
		List<Tuple> inputSet = new CombinedInputSetFactory().getInputs(testTarget, m);
		MethodcallExecutor methodcallExecutor = new MethodcallExecutor(testTarget, m);
		methodcallExecutor.execute(inputSet);
		return methodcallExecutor.getResult();
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
