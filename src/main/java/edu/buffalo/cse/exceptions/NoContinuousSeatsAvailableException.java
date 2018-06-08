package edu.buffalo.cse.exceptions;

public class NoContinuousSeatsAvailableException extends Exception {
    private static final String DEFAULT_MESSAGE = "Seats can not be allocated together!";

    public NoContinuousSeatsAvailableException() {
        super(DEFAULT_MESSAGE);
    }

    public NoContinuousSeatsAvailableException(String message) {
        super(message);
    }
}
