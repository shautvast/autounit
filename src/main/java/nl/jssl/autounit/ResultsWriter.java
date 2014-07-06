package nl.jssl.autounit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import nl.jssl.autounit.utils.Permuter.Tuple;
import nl.jssl.autounit.utils.XStreamArgumentsConverter;

import com.thoughtworks.xstream.XStream;

public class ResultsWriter {
	XStream xstream = new XStream();

	public ResultsWriter() {
		setup();
	}

	private void setup() {
		xstream.alias("case", InAndOutput.class);
		xstream.alias("results", MethodCallResults.class);
		xstream.alias("args", Tuple.class);

		xstream.registerConverter(new XStreamArgumentsConverter());
	}

	public void write(String classname, Map<String, MethodCallResults> results) {
		try {
			xstream.toXML(results, new FileOutputStream(classname + ".xml"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

	}
}
