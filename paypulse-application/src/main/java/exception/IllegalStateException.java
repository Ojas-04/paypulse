package exception;

import lombok.Getter;

/**
 * Custom exception to indicate an illegal state in the application.
 */
@Getter
public class IllegalStateException extends RuntimeException {

    private String object;
    private String errorCode;

    public IllegalStateException() {super();}

    public IllegalStateException(String message) {super(message);}

    public IllegalStateException(String message, String object) {
        super(message);
        this.object = object;
    }

    public IllegalStateException(String message, String object, String errorCode) {
        super(message);
        this.object = object;
        this.errorCode = errorCode;
    }

}
