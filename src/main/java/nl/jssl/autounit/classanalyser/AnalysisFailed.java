package nl.jssl.autounit.classanalyser;
@SuppressWarnings("serial")
public class AnalysisFailed extends RuntimeException {

    public AnalysisFailed() {
        super();
    }

    public AnalysisFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AnalysisFailed(String message, Throwable cause) {
        super(message, cause);
    }

    public AnalysisFailed(String message) {
        super(message);
    }

    public AnalysisFailed(Throwable cause) {
        super(cause);
    }

}
