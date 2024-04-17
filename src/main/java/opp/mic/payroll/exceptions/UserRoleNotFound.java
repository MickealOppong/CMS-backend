package opp.mic.payroll.exceptions;

public class UserRoleNotFound extends RuntimeException{
    public UserRoleNotFound(String message) {
        super(message);
    }

    public UserRoleNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
