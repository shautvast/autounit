package nl.jssl.autounit.testclasses;

import java.lang.reflect.Modifier;

public class MethodSignatureMaker {
	public String getMethodSignature(MyMethod m) {
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
}
