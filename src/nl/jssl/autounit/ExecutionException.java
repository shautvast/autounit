package nl.jssl.autounit;
@SuppressWarnings("serial")
public class ExecutionException extends RuntimeException {

    public ExecutionException() {
        super();
    }

    public ExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutionException(String message) {
        super(message);
    }

    public ExecutionException(Throwable cause) {
        super(cause);
    }

}
