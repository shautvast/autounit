package nl.jssl.autounit;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import nl.jssl.autounit.utils.SilentObjectCreator;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.LoggerRuntime;
import org.jacoco.core.runtime.RuntimeData;
import org.jacoco.examples.CoreTutorial.MemoryClassLoader;

public class CoverageAnalyser {
	private IRuntime runtime;
	private RuntimeData data = new RuntimeData();

	public <T> T instrument(Class<T> testTarget) {
		try {
			String targetName = testTarget.getName();

			runtime = new LoggerRuntime();

			Instrumenter instr = new Instrumenter(runtime);
			byte[] instrumented = instr.instrument(getTargetClass(targetName), targetName);

			data = new RuntimeData();
			runtime.startup(data);

			MemoryClassLoader memoryClassLoader = new MemoryClassLoader();
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
			ExecutionDataStore executionData = new ExecutionDataStore();
			SessionInfoStore sessionInfos = new SessionInfoStore();
			data.collect(executionData, sessionInfos, false);
			runtime.shutdown();

			CoverageBuilder coverageBuilder = new CoverageBuilder();
			Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
			String targetName = instrumentedTestTarget.getClass().getName();
			analyzer.analyzeClass(getTargetClass(targetName), targetName);

			return new InvocationResult(coverageBuilder.getClasses().iterator().next(), output);
		} catch (Exception e) {
			throw new ExecutionException(e);
		}
	}

	private <T> Method getInstrumentedMethod(T testTarget, Method method) throws NoSuchMethodException {
		return testTarget.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
	}

	private <T> Object invoke(T testTarget, Object[] inputs, Method newInstanceMethod) throws IllegalAccessException {
		Object output;
		try {
			output = newInstanceMethod.invoke(testTarget, inputs);
		} catch (InvocationTargetException i) {
			StackTraceElement stacktraceElement = i.getTargetException().getStackTrace()[0];
			String info = stacktraceElement.getClassName() + ":" + stacktraceElement.getLineNumber();
			output = i.getTargetException().getClass().getName() + "(" + i.getTargetException().getMessage() + ") at "
					+ info;
		}
		return output;
	}

	private InputStream getTargetClass(String name) {
		String resource = '/' + name.replace('.', '/') + ".class";
		return getClass().getResourceAsStream(resource);
	}

}

class InvocationResult {
	public final IClassCoverage coverage;
	public final Object output;

	public InvocationResult(IClassCoverage coverage, Object output) {
		super();
		this.coverage = coverage;
		this.output = output;
	}

}
