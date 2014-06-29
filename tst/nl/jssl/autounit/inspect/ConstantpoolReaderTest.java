package nl.jssl.autounit.inspect;

import java.util.List;

import javassist.ClassPool;
import javassist.NotFoundException;
import nl.jssl.autounit.testclasses.IntArguments;
import nl.jssl.autounit.utils.ConstantpoolReader;

import org.junit.Test;

public class ConstantpoolReaderTest {
	@Test
	public void test() throws NotFoundException {
		ClassPool classPool = new ClassPool();
		classPool.appendClassPath("c:\\workspaces\\autounit\\autounit\\bin");
		ConstantpoolReader reader = new ConstantpoolReader(classPool);
		List<String> strings = reader.scanStrings(IntArguments.class);
		for (String string : strings) {
			System.out.println(string);
		}
	}
}
