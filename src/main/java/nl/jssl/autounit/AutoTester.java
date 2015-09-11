package nl.jssl.autounit;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * record: records in and outputs
 * 
 * assertUnchangedBehaviour: raises an error when recorded inputs do no lead to
 * recorded outputs
 */
public class AutoTester {
	ResultsWriter resultsWriter = new ResultsWriter();

	public Map<String, MethodCallResults> record(Class<?> instance) {
		Map<String, MethodCallResults> results = new Recorder(instance).record();
		resultsWriter.write(instance.getName(), results);
		return results;
	}

	private Map<String, MethodCallResults> load(String classname) {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(classname + ".dat"));
			Map<String, MethodCallResults> results = (Map<String, MethodCallResults>) objectInputStream.readObject();
			objectInputStream.close();
			return results;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
