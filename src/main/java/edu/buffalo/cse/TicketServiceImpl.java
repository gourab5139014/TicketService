package edu.buffalo.cse;

import java.util.Random;

public class TicketServiceImpl implements TicketService {
    private Venue v;

    public TicketServiceImpl(){
        this("DefaultVenue", 10, 10, 10);
    }

    public TicketServiceImpl(String venueName, int rowsOfSeats, int columnsOfSeats, int holdSeconds){
        v = new Venue(venueName, rowsOfSeats, columnsOfSeats, holdSeconds);
    }

    @Override
    public int numSeatsAvailable() {
        return v.getAvailableSeats();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        //TODO Introduce exceptions about VenueNotInitialized, SoldOut, NoContinuousSeats, Validation of customerEmail
        try {
            SeatHold sh = v.holdSeats(numSeats, customerEmail);
        } catch (Exception e) {
            e.printStackTrace(); //TODO Do custom exception
        }
        return null;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail){
        //TODO Introduce exceptions HoldExpired, VenueNotInitialized, SoldOut, Validation of customerEmail
        String confirmationCode = "";
        Boolean success = v.reserveSeats(seatHoldId, customerEmail);
        if(success){
            confirmationCode = this._generateConfirmationCode(4);
        }
        return confirmationCode;
    }

    public String _generateConfirmationCode(int len){
        char[] otp = new char[len];
        String numbers = "0123456789";
        Random r = new Random();
        for(int i = 0; i < len; i++){
            otp[i] = numbers.charAt(r.nextInt(numbers.length()));
        }
        return String.valueOf(otp);
    }
}
