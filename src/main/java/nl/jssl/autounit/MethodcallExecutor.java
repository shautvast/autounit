package nl.jssl.autounit;

import java.lang.reflect.Method;
import java.util.List;

import nl.jssl.autounit.utils.Permuter.Tuple;

/**
 * voert 1 methode uit met wisselende input parameters en bewaart het resultaat.
 * 
 */
public class MethodcallExecutor {
	private Object instrumentedTestTarget;
	private Method m;
	CoverageAnalyser coverageAnalyser = new CoverageAnalyser();
	private MethodCallResults result;

	public MethodcallExecutor(Class<?> testClass, Method m) {
		super();
		this.instrumentedTestTarget = coverageAnalyser.instrument(testClass);
		this.m = m;
		this.result = new MethodCallResults(instrumentedTestTarget, m);
	}

	public MethodCallResults execute(List<Tuple> inputs) {
		int missedLines = Integer.MAX_VALUE;
		InvocationResult lastInvocationResult = null, previous = null;
		for (Tuple input : inputs) {
			previous = lastInvocationResult;
			lastInvocationResult = analyseMethodCall(m, input);
			int missedCount = lastInvocationResult.coverage.getLineCounter().getMissedCount();
			if (missedCount < missedLines) { // coverage increases, this is an
												// interesting input
				missedLines = missedCount;
				result.addResult(input, lastInvocationResult.output);
			}
			if (missedCount == 0) { // coverage = 100%: done
				break;
			}
		}
		if (lastInvocationResult != null) {
			if (previous == null) {
				result.setCoverageResult(lastInvocationResult.coverage);
			} else {
				result.setCoverageResult(previous.coverage);
			}
		}
		return result;
	}

	private InvocationResult analyseMethodCall(Method m, Tuple input) {
		Object[] argumentarray = input.toArray();
		for (int i = 0; i < argumentarray.length; i++) {
			if (argumentarray[i].toString().equals("autounit:[NULL]")) {
				argumentarray[i] = null;
			}
		}
		return coverageAnalyser.analyse(instrumentedTestTarget, m, argumentarray);
	}

	public MethodCallResults getResult() {
		return result;
	}
}
