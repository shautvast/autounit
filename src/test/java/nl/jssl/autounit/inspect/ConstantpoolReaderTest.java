package nl.jssl.autounit.inspect;

import java.util.List;

import org.junit.Test;

import javassist.ClassPool;
import javassist.NotFoundException;
import nl.jssl.autounit.testclasses.IntArguments;
import nl.jssl.autounit.util.ConstantpoolReader;

public class ConstantpoolReaderTest {
	@Test
	public void test() throws NotFoundException {
		ClassPool classPool = new ClassPool();
		classPool.appendClassPath("bin");
		ConstantpoolReader reader = new ConstantpoolReader(classPool);
		List<String> strings = reader.scanStrings(IntArguments.class);
		for (String string : strings) {
			System.out.println(string);
		}
	}
}
