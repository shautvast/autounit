package nl.jssl.autounit.results;

import java.io.PrintStream;

import nl.jssl.autounit.classanalyser.ClassResults;
import nl.jssl.autounit.classanalyser.InAndOutput;
import nl.jssl.autounit.classanalyser.MethodCallResults;

public class JUnitSourceWriter extends ResultsWriter {

	public JUnitSourceWriter(PrintStream out) {
		super(out);
	}

	@Override
	public void write(ClassResults results) {
		writeTestClass(results);
	}

	private void writeTestClass(ClassResults results) {
		out.println("package " + results.getType().getPackage().getName() + ";");
		out.println();
		out.println("import static org.junit.Assert.assertEquals;");
		out.println("import org.junit.Test;");
		out.println();
		out.println("public class " + results.getType().getSimpleName() + "Tests {");
		Count index = new Count(1);
		for (MethodCallResults mcr : results.getMethodCallResults()) {
			for (InAndOutput inout : mcr.getContents()) {
				writeMethod(index, results, mcr, inout);
			}
		}

		out.println("}");
	}

	private void writeMethod(Count index, ClassResults results, MethodCallResults mcr, InAndOutput inout) {
		out.println("\t@Test");
		out.println("\tpublic void " + mcr.getMethodName() + index.getValue() + "(){");
		index.increment();
		out.print("\t\tassertEquals(new " + results.getType().getSimpleName() + "()." + mcr.getMethodName() + "(");
		int nrArguments = inout.getInput().depth();
		int argumentCount = 1;
		for (Object o : inout.getInput()) {
			out.print(toString(o.getClass(), o));
			if ((argumentCount++) < nrArguments) {
				out.print(",");
			}
		}
		out.print("), ");
		out.print(toString(mcr.getMethodReturnType(), inout.getOutput()));
		out.println(");");
		out.println("\t}");
	}

	private String toString(Class<?> type, Object object) {
		if (type == String.class) {
			return "\"" + object + "\"";
		} else if (type == Double.class || type == double.class) {
			return object.toString() + "D";
		} else if (type == Byte.class || type == byte.class) {
			return "(byte)" + object.toString();
		} else if (type == Integer.class || type == int.class) {
			return object.toString();
		} else if (type == Float.class || type == float.class) {
			return object.toString() + "F";
		} else if (type == Short.class || type == short.class) {
			return "(short)" + object.toString();
		} else if (type == Character.class || type == char.class) {
			return "'" + object.toString() + "'";
		} else
			return object.toString();
	}

	static class Count {
		private int value;

		public Count(int value) {
			this.value = value;
		}

		public void increment() {
			value++;
		}

		public int getValue() {
			return value;
		}
	}
}
