package nl.jssl.autounit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import nl.jssl.autounit.inputs.ArgumentsForSingleCall;
import nl.jssl.autounit.utils.ArgumentsConverter;

import com.thoughtworks.xstream.XStream;

public class ResultsWriter {
	XStream xstream = new XStream();

	public ResultsWriter() {
		setup();
	}

	private void setup() {
		xstream.alias("case", InAndOutput.class);
		xstream.alias("results", MethodCallResults.class);
		xstream.alias("args", ArgumentsForSingleCall.class);
		xstream.registerConverter(new ArgumentsConverter());
	}

	public void write(String classname, Map<String, MethodCallResults> results) {
		try {
			xstream.toXML(results, new FileOutputStream(classname + ".xml"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

	}
}
