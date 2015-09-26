package nl.jssl.autounit.classanalyser;

import java.lang.reflect.Method;
import java.util.List;

import nl.jssl.autounit.util.LinkedList;

/**
 * Executes 1 method using alternating input parameters and yields execution
 * results.
 * 
 */
public class MethodcallExecutor<T> {
	private T instrumentedTestTarget;
	private Method methodUnderTest;
	private MethodExecutionResults result;
	private final CoverageAnalyser<T> coverageAnalyser;

	public MethodcallExecutor(Class<T> testClass, Method methodUnderTest) {
		coverageAnalyser = new CoverageAnalyser<T>(testClass);
		this.instrumentedTestTarget = coverageAnalyser.instrument();

		this.methodUnderTest = methodUnderTest;
		this.result = new MethodExecutionResults(instrumentedTestTarget, methodUnderTest);
	}

	public MethodExecutionResults executeAndGetResults(List<LinkedList> inputs) {
		InvocationResult lastInvocationResult = null, previous = null;

		int missedLines = Integer.MAX_VALUE;
		for (LinkedList input : inputs) {
			previous = lastInvocationResult;

			lastInvocationResult = analyseMethodCall(methodUnderTest, input);

			int missedCount = lastInvocationResult.getCoverage().getLineCounter().getMissedCount();

			missedLines = addInOutputCombinationWhenCoverageIncreases(missedLines, lastInvocationResult, input,
					missedCount);

			if (fullyCoveraged(missedCount)) {
				break;
			}
		}
		if (lastInvocationResult != null) {
			if (previous == null) {
				result.setCoverageResult(lastInvocationResult.getCoverage());
			} else {
				result.setCoverageResult(previous.getCoverage());
			}
		}
		return result;
	}

	private int addInOutputCombinationWhenCoverageIncreases(int missedLines, InvocationResult lastInvocationResult,
			LinkedList input, int missedCount) {
		if (coverageHasIncreased(missedLines, missedCount)) {
			missedLines = missedCount;
			addInterestingInAndOutput(input, lastInvocationResult);
		}
		return missedLines;
	}

	private void addInterestingInAndOutput(LinkedList input, InvocationResult lastInvocationResult) {
		result.addResult(input, lastInvocationResult.getOutput());
	}

	private boolean fullyCoveraged(int missedCount) {
		return missedCount == 0;
	}

	private boolean coverageHasIncreased(int missedLines, int missedCount) {
		return missedCount < missedLines;
	}

	private InvocationResult analyseMethodCall(Method methodUnderTest, LinkedList input) {
		Object[] inputs = replaceNullIndicatorWithNull(input.toArray());

		return coverageAnalyser.analyse(instrumentedTestTarget, methodUnderTest, inputs);
	}

	private Object[] replaceNullIndicatorWithNull(Object[] argumentarray) {
		for (int i = 0; i < argumentarray.length; i++) {
			if (argumentarray[i].toString().equals("autounit:[NULL]")) {
				argumentarray[i] = null;
			}
		}
		return argumentarray;
	}

}
