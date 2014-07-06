package nl.jssl.autounit.utils;

import nl.jssl.autounit.utils.Permuter.Tuple;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XStreamArgumentsConverter implements Converter {

	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class clazz) {
		return (clazz == Tuple.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Tuple arguments = (Tuple) value;
		int index = 1;
		for (Object arg : arguments) {
			writer.startNode("arg" + index);
			writer.setValue(arg.toString());
			writer.endNode();
		}

	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return null;
	}

}
