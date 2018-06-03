package edu.buffalo.cse;

public class Seat {
    private enum SeatStatus{
        FREE, HOLD, BOOKED
    }

    private SeatStatus status;
    private String customerEmail;

    public Seat(){
        this.status = SeatStatus.FREE;
    }
}
