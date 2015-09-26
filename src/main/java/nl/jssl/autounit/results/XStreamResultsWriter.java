package nl.jssl.autounit.results;

import java.io.PrintStream;

import com.thoughtworks.xstream.XStream;

import nl.jssl.autounit.classanalyser.ClassResults;
import nl.jssl.autounit.classanalyser.InAndOutput;
import nl.jssl.autounit.classanalyser.MethodCallResults;
import nl.jssl.autounit.util.Pair;
import nl.jssl.autounit.util.XStreamArgumentsConverter;

public class XStreamResultsWriter extends ResultsWriter {
	private XStream xstream = new XStream();

	public XStreamResultsWriter(PrintStream out) {
		super(out);
		setup();
	}

	@Override
	public void write(ClassResults results) {
		xstream.toXML(results, out);
	}

	private void setup() {
		xstream.alias("case", InAndOutput.class);
		xstream.alias("results", MethodCallResults.class);
		xstream.alias("args", Pair.class);
		xstream.registerConverter(new XStreamArgumentsConverter());
	}
}
