package nl.jssl.autounit;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Player {
    final private Object testinstance;

    private Player(Object testinstance) {
        super();
        this.testinstance = testinstance;
    }

    public void playback(Map<String, List<MethodCallResults>> recordedResults) {
        for (Entry<String, List<MethodCallResults>> entry: recordedResults.entrySet()) {
            getInputs(entry.getValue());
            // new Executor(testinstance,getMethod(entry.getKey()))
        }

    }

    private void getInputs(List<MethodCallResults> value) {
        // TODO Auto-generated method stub

    }

    // private Method getMethod(String key) {
    // // TODO Auto-generated method stub
    //
    // }
}