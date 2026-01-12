package exception;

/**
 * Custom exception to indicate an illegal state in the application.
 */
public class IllegalStateException extends RuntimeException {

    private String object;

    public IllegalStateException() {super();}

    public IllegalStateException(String message) {super(message);}

    public IllegalStateException(String message, Throwable cause) {super(message, cause);}

    public IllegalStateException(Throwable cause) {super(cause);}

    public IllegalStateException(String message, Throwable cause, String object) {
        super(message, cause);
        this.object = object;
    }

    public String getObject() {
        return object;
    }
}

