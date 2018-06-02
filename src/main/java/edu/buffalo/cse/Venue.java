package edu.buffalo.cse;

public class Venue {
    private String name;
    private int availableSeats;
    private enum SeatStatus{
        FREE, HOLD, BOOKED
    }
    private SeatStatus[][] seats;
}

