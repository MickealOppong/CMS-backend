package opp.mic.payroll.exceptions;

public class PhotoStorageException extends RuntimeException{
    public PhotoStorageException(String message) {
        super(message);
    }

    public PhotoStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
