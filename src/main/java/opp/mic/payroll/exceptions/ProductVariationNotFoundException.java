package opp.mic.payroll.exceptions;

public class ProductVariationNotFoundException extends RuntimeException {
    public ProductVariationNotFoundException(String message) {
        super(message);
    }

    public ProductVariationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
