package edu.buffalo.cse.exceptions;

public class InvalidSeatholdException extends Exception {
//    private static final long serialVersionUID = -7986915990118714483L;

    private static final String DEFAULT_MESSAGE = "SeatHoldId is not valid!";

    public InvalidSeatholdException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidSeatholdException(String message) {
        super(message);
    }
}
