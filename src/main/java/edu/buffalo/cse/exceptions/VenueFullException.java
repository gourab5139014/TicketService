package edu.buffalo.cse.exceptions;

public class VenueFullException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not enough seats available in venue!";

    public VenueFullException() {
        super(DEFAULT_MESSAGE);
    }

    public VenueFullException(String message) {
        super(message);
    }
}
