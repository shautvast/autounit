package nl.jssl.autounit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import nl.jssl.autounit.classanalyser.ClassAnalyser;
import nl.jssl.autounit.classanalyser.ClassResults;
import nl.jssl.autounit.results.JUnitSourceWriter;

/**
 * Creates a Junit source file
 *
 */
public class JUnitTestCreator<T> {

	private static final String SOURCEDIRECTORY = "src/outcome/java/";
	private final Class<T> classUnderTest;
	private final File packageDirectory;

	public JUnitTestCreator(Class<T> type) {
		this.classUnderTest = type;
		packageDirectory = createPackageDirectory();
	}

	public void create() {
		try {
			writeSourceFile();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeSourceFile() throws FileNotFoundException {
		ClassResults results = new ClassAnalyser<>(classUnderTest).analyseAndGetResults();
		getSourceWriter(packageDirectory).write(results);
	}

	private JUnitSourceWriter getSourceWriter(File packageDirectory) throws FileNotFoundException {
		return new JUnitSourceWriter(new PrintStream(new FileOutputStream(getSourceFile())));
	}

	private File createPackageDirectory() {
		File packageDirectory = new File(
				SOURCEDIRECTORY + classUnderTest.getPackage().getName().replaceAll("\\.", "/"));
		packageDirectory.mkdirs();
		return packageDirectory;
	}

	private File getSourceFile() {
		return new File(packageDirectory, classUnderTest.getSimpleName() + "Tests.java");
	}

}
