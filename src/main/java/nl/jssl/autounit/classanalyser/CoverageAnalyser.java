package nl.jssl.autounit.classanalyser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.LoggerRuntime;
import org.jacoco.core.runtime.RuntimeData;

import nl.jssl.autounit.util.MemoryClassloader;
import nl.jssl.autounit.util.SilentObjectCreator;

public class CoverageAnalyser {
	private IRuntime runtime;
	private RuntimeData data = new RuntimeData();

	@SuppressWarnings("unchecked")
	public <T> T instrument(Class<T> testTarget) {
		try {
			String targetName = testTarget.getName();

			runtime = new LoggerRuntime();

			Instrumenter instr = new Instrumenter(runtime);
			byte[] instrumented = instr.instrument(getTargetClass(targetName), targetName);

			data = new RuntimeData();
			runtime.startup(data);

			MemoryClassloader memoryClassLoader = new MemoryClassloader();
			memoryClassLoader.addDefinition(targetName, instrumented);
			Class<T> targetClass = (Class<T>) memoryClassLoader.loadClass(targetName);

			return SilentObjectCreator.create(targetClass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> InvocationResult analyse(T instrumentedTestTarget, Method method, Object[] inputs) {
		try {
			Method instanceMethod = getInstrumentedMethod(instrumentedTestTarget, method);
			Object output = invoke(instrumentedTestTarget, inputs, instanceMethod);

			// jacoco stuff
			ExecutionDataStore executionData = executionData();
			
			runtime.shutdown();

			CoverageBuilder coverageBuilder = coverageBuilder(instrumentedTestTarget, executionData);

			return new InvocationResult(coverageBuilder.getClasses().iterator().next(), output);
		} catch (Exception e) {
			throw new AnalysisFailed(e);
		}
	}

	private ExecutionDataStore executionData() {
		ExecutionDataStore executionData = new ExecutionDataStore();
		SessionInfoStore sessionInfos = new SessionInfoStore();
		data.collect(executionData, sessionInfos, false);
		return executionData;
	}

	private <T> CoverageBuilder coverageBuilder(T instrumentedTestTarget, ExecutionDataStore executionData)
			throws IOException {
		CoverageBuilder coverageBuilder = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
		String targetName = instrumentedTestTarget.getClass().getName();
		analyzer.analyzeClass(getTargetClass(targetName), targetName);
		return coverageBuilder;
	}

	private <T> Method getInstrumentedMethod(T testTarget, Method method) throws NoSuchMethodException {
		return testTarget.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
	}

	private <T> Object invoke(T testTarget, Object[] inputs, Method newInstanceMethod) throws IllegalAccessException {
		Object output;
		try {
			output = newInstanceMethod.invoke(testTarget, inputs);
		} catch (InvocationTargetException i) {
			output = createOutputFromException(i);
		}
		return output;
	}

	private Object createOutputFromException(InvocationTargetException i) {
		Object output;
		StackTraceElement stacktraceElement = i.getTargetException().getStackTrace()[0];
		String info = stacktraceElement.getClassName() + ":" + stacktraceElement.getLineNumber();
		output = i.getTargetException().getClass().getName() + "(" + i.getTargetException().getMessage() + ") at "
				+ info;
		return output;
	}

	private InputStream getTargetClass(String name) {
		String resource = '/' + name.replace('.', '/') + ".class";
		return getClass().getResourceAsStream(resource);
	}

}
