package edu.buffalo.cse.exceptions;

public class InvalidCustomerException extends Exception{
//    private static final long serialVersionUID = -7986915990118714483L;

    private static final String DEFAULT_MESSAGE = "Customer is not valid!";

    public InvalidCustomerException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCustomerException(String message) {
        super(message);
    }
}
