package nl.jssl.autounit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import nl.jssl.autounit.classanalyser.ClassAnalyser;
import nl.jssl.autounit.results.JUnitSourceWriter;

/**
 * Creates a Junit source file
 *
 */
public class JUnitTestCreator {

	private static final String SOURCEDIRECTORY = "src/outcome/java/";

	public void assembleJUnitTest(Class<?> type) {
		try {
			tryAssembleJUnitTest(type);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void tryAssembleJUnitTest(Class<?> type) throws FileNotFoundException {
		writeSourceFile(createPackageDirectory(type), type);
	}

	private void writeSourceFile(File packageDirectory, Class<?> classUnderTest) throws FileNotFoundException {
		resultsWriter(packageDirectory, classUnderTest).write(new ClassAnalyser(classUnderTest).analyse());
	}

	private JUnitSourceWriter resultsWriter(File packageDirectory, Class<?> type) throws FileNotFoundException {
		return new JUnitSourceWriter(
				new PrintStream(new FileOutputStream(toSourceFile(packageDirectory, type))));
	}

	private File createPackageDirectory(Class<?> type) {
		File packageDirectory = toPackageDirectory(type);
		packageDirectory.mkdirs();
		return packageDirectory;
	}

	private File toSourceFile(File packageDirectory, Class<?> type) {
		return new File(packageDirectory, type.getName().replaceAll("\\.", "/") + "Tests.java");
	}

	private File toPackageDirectory(Class<?> type) {
		return new File(SOURCEDIRECTORY + type.getPackage().getName().replaceAll("\\.", "/"));
	}

}
