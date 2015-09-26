package nl.jssl.autounit.inputs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.jssl.autounit.inputs.objects.ObjectArgumentFactory;
import nl.jssl.autounit.inputs.objects.StringArgumentFactory;
import nl.jssl.autounit.inputs.primitives.ArgumentFactory;
import nl.jssl.autounit.inputs.primitives.BooleanArgumentFactory;
import nl.jssl.autounit.inputs.primitives.ByteArgumentFactory;
import nl.jssl.autounit.inputs.primitives.DoubleArgumentFactory;
import nl.jssl.autounit.inputs.primitives.FloatArgumentFactory;
import nl.jssl.autounit.inputs.primitives.IntegerArgumentFactory;
import nl.jssl.autounit.util.LinkedList;
import nl.jssl.autounit.util.Permuter;

public class MethodcallArgumentsFactory {
	private final Map<Class<?>, ArgumentFactory<?>> primitivesFactories;
	private final Class<?> testTarget;

	public MethodcallArgumentsFactory(Class<?> testTarget) {
		this.testTarget = testTarget;
		primitivesFactories = new HashMap<Class<?>, ArgumentFactory<?>>();
		populateFactories();
	}

	public List<LinkedList> getInputs(Method method) {
		return combine(getArgumentsForAllParameters(method));
	}

	private List<LinkedList> combine(List<List<?>> inputSetsForAllArguments) {
		int nrOfParameters = inputSetsForAllArguments.size();
		if (nrOfParameters == 0) {
			return Collections.emptyList();
		} else if (nrOfParameters == 1) {
			// simple case
			return makeArgumentsForSingleParameterCall(inputSetsForAllArguments);
		} else {
			return Permuter.permute(inputSetsForAllArguments);
		}
	}

	private List<LinkedList> makeArgumentsForSingleParameterCall(List<List<?>> generatedInputSetsForAllArguments) {
		List<LinkedList> allPossibleArguments = new ArrayList<>();

		List<?> generatedInputs = generatedInputSetsForAllArguments.iterator().next();

		for (Object variable : generatedInputs) {
			LinkedList argument = new LinkedList(variable);
			allPossibleArguments.add(argument);
		}
		return allPossibleArguments;
	}

	List<List<?>> getArgumentsForAllParameters(Method method) {
		List<List<?>> singleInputSets = new ArrayList<List<?>>();
		for (Class<?> parametertype : method.getParameterTypes()) {
			List<?> inputs = tryPrimitives(parametertype);

			if (inputs == null) {
				inputs = new ObjectArgumentFactory().getObjectArgument(parametertype);
			}
			if (inputs != null) {
				singleInputSets.add(inputs);
			}
		}
		return singleInputSets;
	}

	private ArgumentsForSingleParameter<?> tryPrimitives(Class<?> parametertype) {
		ArgumentFactory<?> inputsFactory = primitivesFactories.get(parametertype);
		if (inputsFactory != null) {
			return inputsFactory.getInputs(testTarget);
		} else {
			return null;
		}
	}

	private void populateFactories() {
		primitivesFactories.put(int.class, new IntegerArgumentFactory());
		primitivesFactories.put(Integer.class, new IntegerArgumentFactory());
		primitivesFactories.put(double.class, new DoubleArgumentFactory());
		primitivesFactories.put(Double.class, new DoubleArgumentFactory());
		primitivesFactories.put(float.class, new FloatArgumentFactory());
		primitivesFactories.put(Float.class, new FloatArgumentFactory());
		primitivesFactories.put(byte.class, new ByteArgumentFactory());
		primitivesFactories.put(Byte.class, new ByteArgumentFactory());
		primitivesFactories.put(Boolean.class, new BooleanArgumentFactory());
		primitivesFactories.put(boolean.class, new BooleanArgumentFactory());
		primitivesFactories.put(String.class, new StringArgumentFactory());
	}
}
