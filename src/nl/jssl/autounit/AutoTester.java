package nl.jssl.autounit;

import java.io.FileInputStream;
import java.io.IOException;
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

	public Map<String, MethodCallResults> record(Class instance) {
		Map<String, MethodCallResults> results = new Recorder(instance).record();
		resultsWriter.write(instance.getName(), results);
		return results;
	}

	// public void assertUnchangedBehaviour(Object instance) {
	// Map<String, MethodCallResults> recordedResults =
	// load(getClassname(instance));
	// for (Entry<String, MethodCallResults> entry : recordedResults.entrySet())
	// {
	//
	// }
	// }

	private Map<String, MethodCallResults> load(String classname) {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(classname + ".dat"));
			Map<String, MethodCallResults> results = (Map<String, MethodCallResults>) objectInputStream.readObject();
			objectInputStream.close();
			return results;
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
