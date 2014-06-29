package nl.jssl.autounit.utils;

import nl.jssl.autounit.inputs.ArgumentsForSingleCall;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ArgumentsConverter implements Converter {

	public boolean canConvert(Class clazz) {
		return (clazz == ArgumentsForSingleCall.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		ArgumentsForSingleCall person = (ArgumentsForSingleCall) value;
		int index = 1;
		for (Object arg : person) {
			writer.startNode("arg" + index);
			writer.setValue(arg.toString());
			writer.endNode();
		}

	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return null;
	}

}
