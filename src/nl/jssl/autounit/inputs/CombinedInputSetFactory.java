package nl.jssl.autounit.inputs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.jssl.autounit.utils.Permuter;
import nl.jssl.autounit.utils.Permuter.Tuple;

public class CombinedInputSetFactory {
	private final Map<Class<?>, InputsFactory<?>> primitivesFactories;

	public CombinedInputSetFactory() {
		primitivesFactories = new HashMap<>();
		populateFactories();
	}

	public List<Tuple> getInputs(Class<?> testTarget, Method m) {
		return combine(getInputSetsForAllArguments(testTarget, m));
	}

	private List<Tuple> combine(List<List<?>> inputSetsForAllArguments) {
		int nrOfParameters = inputSetsForAllArguments.size();
		if (nrOfParameters == 1) {
			// simple case
			return makeArgumentsForSingleParameterCall(inputSetsForAllArguments);
		} else {
			return Permuter.permute(inputSetsForAllArguments);
		}
	}

	private List<Tuple> makeArgumentsForSingleParameterCall(
			List<List<?>> generatedInputSetsForAllArguments) {
		List<Tuple> allPossibleArguments = new ArrayList<Tuple>();

		List<?> generatedInputs = generatedInputSetsForAllArguments.iterator().next();

		for (Object variable : generatedInputs) {
			Tuple argument = new Tuple(variable);
			allPossibleArguments.add(argument);
		}
		return allPossibleArguments;
	}

	private List<List<?>> getInputSetsForAllArguments(Class<?> testTarget, Method m) {
		List<List<?>> singleInputSets = new ArrayList<>();
		for (Class<?> parametertype : m.getParameterTypes()) {
			List<?> inputs = tryPrimitives(testTarget, parametertype);

			if (inputs == null) {
				inputs = new MocksFactory().getMockInputs(testTarget, parametertype);
			}
			if (inputs != null) {
				singleInputSets.add(inputs);
			}
		}
		return singleInputSets;
	}

	private SingleTypeInputs<?> tryPrimitives(Class<?> testTarget, Class<?> parametertype) {
		InputsFactory<?> inputsFactory = primitivesFactories.get(parametertype);
		if (inputsFactory != null) {
			return inputsFactory.getInputs(testTarget);
		} else {
			return null;
		}
	}

	private void populateFactories() {
		primitivesFactories.put(int.class, new IntegerInputsFactory());
		primitivesFactories.put(Integer.class, new IntegerInputsFactory());
		primitivesFactories.put(double.class, new DoubleInputsFactory());
		primitivesFactories.put(Double.class, new DoubleInputsFactory());
		primitivesFactories.put(float.class, new FloatInputsFactory());
		primitivesFactories.put(Float.class, new FloatInputsFactory());
		primitivesFactories.put(byte.class, new ByteInputsFactory());
		primitivesFactories.put(Byte.class, new ByteInputsFactory());
		primitivesFactories.put(Boolean.class, new BooleanInputsFactory());
		primitivesFactories.put(boolean.class, new BooleanInputsFactory());
		primitivesFactories.put(String.class, new StringInputsFactory());
	}
}
