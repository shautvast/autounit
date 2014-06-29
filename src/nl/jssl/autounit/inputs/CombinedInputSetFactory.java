package nl.jssl.autounit.inputs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.jssl.autounit.utils.Permutator;

public class CombinedInputSetFactory {
	private final Map<Class<?>, InputsFactory<?>> primitivesFactories;

	public CombinedInputSetFactory() {
		primitivesFactories = new HashMap<>();
		populateFactories();
	}

	public List<ArgumentsForSingleCall> getInputs(Class<?> testTarget, Method m) {
		return combine(getInputSetsForAllArguments(testTarget, m));
	}

	private List<ArgumentsForSingleCall> combine(List<SingleTypeInputs<?>> inputSetsForAllArguments) {
		int nrOfParameters = inputSetsForAllArguments.size();
		if (nrOfParameters == 1) {
			// simple case
			return makeArgumentsForSingleParameterCall(inputSetsForAllArguments);
		} else {
			List<ArgumentsForSingleCall> allPossibleArguments = new ArrayList<>();

			for (SingleTypeInputs<?> inputs : inputSetsForAllArguments) {
				// make list of all permutations of first argument values
				List<?> permutatedInputs = Permutator.permute(inputs);
				int index = 0;
				for (Object variable : permutatedInputs) {
					// all lists ("columns") are combined into "rows"
					if (index >= allPossibleArguments.size()) {
						ArgumentsForSingleCall a = new ArgumentsForSingleCall();
						a.add(variable);
						allPossibleArguments.add(a);
					} else {
						ArgumentsForSingleCall argumentsForSingleCall = allPossibleArguments.get(index);
						argumentsForSingleCall.add(variable);
					}
					index++;
				}
			}
			// the row view
			return allPossibleArguments;
		}

	}

	private List<ArgumentsForSingleCall> makeArgumentsForSingleParameterCall(
			List<SingleTypeInputs<?>> generatedInputSetsForAllArguments) {
		List<ArgumentsForSingleCall> allPossibleArguments = new ArrayList<ArgumentsForSingleCall>();

		SingleTypeInputs<?> generatedInputs = generatedInputSetsForAllArguments.iterator().next();

		for (Object variable : generatedInputs) {
			ArgumentsForSingleCall argument = new ArgumentsForSingleCall();
			argument.add(variable);
			allPossibleArguments.add(argument);
		}
		return allPossibleArguments;
	}

	private List<SingleTypeInputs<?>> getInputSetsForAllArguments(Class<?> testTarget, Method m) {
		List<SingleTypeInputs<?>> singleInputSets = new ArrayList<>();
		for (Class<?> parametertype : m.getParameterTypes()) {
			SingleTypeInputs<?> inputs = tryPrimitives(testTarget, parametertype);

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
