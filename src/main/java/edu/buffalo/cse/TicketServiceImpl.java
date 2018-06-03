package edu.buffalo.cse;

public class TicketServiceImpl implements TicketService {
    private Venue v;

    public TicketServiceImpl(){
        this("DefaultVenue", 100, 100);
    }

    public TicketServiceImpl(String venueName, int rowsOfSeats, int columnsOfSeats){
        v = new Venue(venueName, rowsOfSeats, columnsOfSeats);
    }

    @Override
    public int numSeatsAvailable() {
        return 0;
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        return null;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        return null;
    }
}
