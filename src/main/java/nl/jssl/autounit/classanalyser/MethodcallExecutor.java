package nl.jssl.autounit.classanalyser;

import java.lang.reflect.Method;
import java.util.List;

import nl.jssl.autounit.util.Pair;

/**
 * voert 1 methode uit met wisselende input parameters en bewaart het resultaat.
 * 
 */
public class MethodcallExecutor {
	private Object instrumentedTestTarget;
	private Method methodUnderTest;
	CoverageAnalyser coverageAnalyser = new CoverageAnalyser();
	private MethodCallResults result;

	public MethodcallExecutor(Class<?> testClass, Method methodUnderTest) {
		super();
		this.instrumentedTestTarget = coverageAnalyser.instrument(testClass);
		this.methodUnderTest = methodUnderTest;
		this.result = new MethodCallResults(instrumentedTestTarget, methodUnderTest);
	}

	public MethodCallResults execute(List<Pair> inputs) {
		InvocationResult lastInvocationResult = null, previous = null;

		int missedLines = Integer.MAX_VALUE;
		for (Pair input : inputs) {
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
			Pair input, int missedCount) {
		if (coverageHasIncreased(missedLines, missedCount)) {
			missedLines = missedCount;
			addInterestingInAndOutput(input, lastInvocationResult);
		}
		return missedLines;
	}

	private void addInterestingInAndOutput(Pair input, InvocationResult lastInvocationResult) {
		result.addResult(input, lastInvocationResult.getOutput());
	}

	private boolean fullyCoveraged(int missedCount) {
		return missedCount == 0;
	}

	private boolean coverageHasIncreased(int missedLines, int missedCount) {
		return missedCount < missedLines;
	}

	private InvocationResult analyseMethodCall(Method methodUnderTest, Pair input) {
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

	public MethodCallResults getResult() {
		return result;
	}
}
