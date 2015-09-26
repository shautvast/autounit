package nl.jssl.autounit.inspect;

import java.util.List;

import org.junit.Test;

import javassist.NotFoundException;
import nl.jssl.autounit.testclasses.IntArguments;
import nl.jssl.autounit.util.ConstantpoolReader;

public class ConstantpoolReaderTest {
	@Test
	public void test() throws NotFoundException {
		ConstantpoolReader reader = new ConstantpoolReader(IntArguments.class);
		List<String> strings = reader.scanStrings();
		for (String string : strings) {
			System.out.println(string);
		}
	}
}
