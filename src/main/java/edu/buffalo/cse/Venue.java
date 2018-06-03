package edu.buffalo.cse;



public class Venue {
    private enum SeatStatus{
        FREE, HOLD, BOOKED
    }
    private String name;
    private int availableSeats;
    private int rows;
    private int columns;

    private SeatStatus[][] seats;

    public Venue(String name, int rows, int columns) {
        this.name = name;
        this.availableSeats = rows * columns;
        this.seats = new SeatStatus[rows][columns];
        for(int i=0; i<rows;i++) {
            for (int j = 0; j < columns; j++) {
                this.seats[i][j] = SeatStatus.FREE;
            }
        }
    }

    public int getAvailableSeats() {
        return this.availableSeats;
    }

    public Boolean hasSeats(int requestedSeats){
        Boolean ifHasSeats = true;
        // Implement method here
        return ifHasSeats;
    }

    public Boolean reserveSeats(int seatHoldId, String customerEmail) {
        throw new UnsupportedOperationException("reserveSeats Method not implemented yet");
    }

    public SeatHold holdSeats(int numSeats, String customerEmail) {
        throw new UnsupportedOperationException("holdSeats Method not implemented yet");
    }
}
