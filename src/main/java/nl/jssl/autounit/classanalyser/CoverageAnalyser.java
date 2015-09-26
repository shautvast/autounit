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

public class CoverageAnalyser<T> {
	private final IRuntime runtime;
	private final RuntimeData data;
	private final Class<T> testTarget;

	public CoverageAnalyser(Class<T> testTarget) {
		this.testTarget = testTarget;
		data = new RuntimeData();
		runtime = new LoggerRuntime();
	}

	public T instrument() {
		try {
			runtime.startup(data);

			return getInstrumentedObjectInstance();
		} catch (Exception e) {
			throw new AnalysisFailed(e);
		}
	}

	public InvocationResult analyse(T instrumentedTestTarget, Method method, Object[] inputs) {
		try {
			Object output = invokeMethod(instrumentedTestTarget, inputs,
					getInstrumentedMethod(instrumentedTestTarget, method));

			// jacoco stuff
			ExecutionDataStore executionData = executionData();

			runtime.shutdown();

			CoverageBuilder coverageBuilder = coverageBuilder(instrumentedTestTarget, executionData);

			return new InvocationResult(coverageBuilder.getClasses().iterator().next(), output);
		} catch (Exception e) {
			throw new AnalysisFailed(e);
		}
	}

	@SuppressWarnings("unchecked")
	private T getInstrumentedObjectInstance() throws IOException, ClassNotFoundException {
		MemoryClassloader memoryClassLoader = getMemoryClassLoaderForClass(getInstrumentedByteCode());
		Class<T> targetClass = (Class<T>) memoryClassLoader.loadClass(testTarget.getName());

		return SilentObjectCreator.create(targetClass);
	}

	private byte[] getInstrumentedByteCode() throws IOException {
		return new Instrumenter(runtime).instrument(getTargetClass(), testTarget.getName());
	}

	private MemoryClassloader getMemoryClassLoaderForClass(byte[] instrumentedByteCode) {
		MemoryClassloader memoryClassLoader = new MemoryClassloader();
		memoryClassLoader.addDefinition(testTarget.getName(), instrumentedByteCode);
		return memoryClassLoader;
	}

	private ExecutionDataStore executionData() {
		ExecutionDataStore executionData = new ExecutionDataStore();
		SessionInfoStore sessionInfos = new SessionInfoStore();
		data.collect(executionData, sessionInfos, false);
		return executionData;
	}

	private CoverageBuilder coverageBuilder(T instrumentedTestTarget, ExecutionDataStore executionData)
			throws IOException {
		CoverageBuilder coverageBuilder = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
		String targetName = instrumentedTestTarget.getClass().getName();
		analyzer.analyzeClass(getTargetClass(), targetName);
		return coverageBuilder;
	}

	private Method getInstrumentedMethod(T testTarget, Method method) throws NoSuchMethodException {
		return testTarget.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
	}

	private Object invokeMethod(T testTarget, Object[] inputs, Method newInstanceMethod) throws IllegalAccessException {
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

	private InputStream getTargetClass() {
		String resource = '/' + testTarget.getName().replace('.', '/') + ".class";
		return getClass().getResourceAsStream(resource);
	}

}
