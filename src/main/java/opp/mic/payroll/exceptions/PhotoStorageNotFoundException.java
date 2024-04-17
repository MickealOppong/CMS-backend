package opp.mic.payroll.exceptions;

public class PhotoStorageNotFoundException extends RuntimeException{
    public PhotoStorageNotFoundException(String message) {
        super(message);
    }

    public PhotoStorageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
