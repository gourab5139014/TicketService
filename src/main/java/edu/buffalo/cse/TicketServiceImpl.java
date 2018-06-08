package edu.buffalo.cse;

import edu.buffalo.cse.exceptions.InvalidCustomerException;
import edu.buffalo.cse.exceptions.InvalidSeatholdException;
import edu.buffalo.cse.exceptions.NoContinuousSeatsAvailableException;
import edu.buffalo.cse.exceptions.VenueFullException;
import org.omg.CORBA.DynAnyPackage.Invalid;

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
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) throws InvalidCustomerException, VenueFullException, NoContinuousSeatsAvailableException {
        //TODO Introduce exceptions about Validation of customerEmail
        SeatHold sh = null;
        sh = v.holdSeats(numSeats, customerEmail);
        return sh;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) throws InvalidSeatholdException, InvalidCustomerException {

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
