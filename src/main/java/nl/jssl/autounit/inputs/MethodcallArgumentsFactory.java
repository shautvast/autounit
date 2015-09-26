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
import nl.jssl.autounit.util.Pair;
import nl.jssl.autounit.util.Permuter;

public class MethodcallArgumentsFactory {
	private final Map<Class<?>, ArgumentFactory<?>> primitivesFactories;

	public MethodcallArgumentsFactory() {
		primitivesFactories = new HashMap<Class<?>, ArgumentFactory<?>>();
		populateFactories();
	}

	public List<Pair> getInputs(Class<?> testTarget, Method m) {
		return combine(getArgumentsForAllParameters(testTarget, m));
	}

	private List<Pair> combine(List<List<?>> inputSetsForAllArguments) {
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

	private List<Pair> makeArgumentsForSingleParameterCall(List<List<?>> generatedInputSetsForAllArguments) {
		List<Pair> allPossibleArguments = new ArrayList<>();

		List<?> generatedInputs = generatedInputSetsForAllArguments.iterator().next();

		for (Object variable : generatedInputs) {
			Pair argument = new Pair(variable);
			allPossibleArguments.add(argument);
		}
		return allPossibleArguments;
	}

	List<List<?>> getArgumentsForAllParameters(Class<?> testTarget, Method m) {
		List<List<?>> singleInputSets = new ArrayList<List<?>>();
		for (Class<?> parametertype : m.getParameterTypes()) {
			List<?> inputs = tryPrimitives(testTarget, parametertype);

			if (inputs == null) {
				inputs = new ObjectArgumentFactory().getObjectArgument(testTarget, parametertype);
			}
			if (inputs != null) {
				singleInputSets.add(inputs);
			}
		}
		return singleInputSets;
	}

	private ArgumentsForSingleParameter<?> tryPrimitives(Class<?> testTarget, Class<?> parametertype) {
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
