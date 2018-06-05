package edu.buffalo.cse;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Venue {
    private String name;
    private int availableSeats;
    private long secondsToHoldExpiry; // This depends on traffic for ticket booking for a venue
    private int rows;
    private int columns;

    private Seat[][] seats;
    private HashMap<String, List<SeatHold>> seatHolds; // <CustomerEmail, List of SeatHolds>

    public Venue(String name, int rows, int columns, int secondsToHoldExpiry) {
        this.name = name;
        this.availableSeats = rows * columns;
        this.seats = new Seat[rows][columns];
        for(int i=0; i<rows;i++) {
            for (int j = 0; j < columns; j++) {
                this.seats[i][j] = new Seat();
            }
        }
        this.seatHolds = new LinkedHashMap<>();
        this.secondsToHoldExpiry = secondsToHoldExpiry;
    }

    public int getAvailableSeats() {
        return this.availableSeats;
    }

    public Boolean hasSeats(int requestedSeats){
        return this.getAvailableSeats() > 0;
    }

    public Boolean reserveSeats(int seatHoldId, String customerEmail) {
        throw new UnsupportedOperationException("reserveSeats Method not implemented yet");
    }

    public SeatHold holdSeats(int numSeats, String customerEmail) throws Exception {
        SeatHold sh;
        if(this.availableSeats >= numSeats){
            sh = this.holdContinuousSeats(numSeats, this.secondsToHoldExpiry, customerEmail);

            List<SeatHold> customerSeatHolds = null; //Add held seats against customer in hash
            if(this.seatHolds.containsKey(customerEmail)){
                this.cleanupExpiredSeatHolds(customerEmail);
                customerSeatHolds = this.seatHolds.get(customerEmail);
            } else{
                customerSeatHolds = new ArrayList<>();
            }
            customerSeatHolds.add(sh);
            this.seatHolds.put(customerEmail, customerSeatHolds);

        } else{
            throw new Exception(numSeats + " seat can not be allocated in Venue "+name); //TODO Make a custom exception
        }
        return sh;
    }

    private SeatHold holdContinuousSeats(int numSeats, long secondsToHoldExpiry, String customerEmail) throws Exception {
        // Iterate through the array. Prune iteration based on numSeats requested seats
        SeatHold sh = null;
        boolean found = false;
        int i=0,j=0;
        for(i = 0; i < rows && !found; i++){
            for(j = 0; j < columns - numSeats + 1 && !found; j++){
                if(seats[i][j].isFree()){// Find a starting point to start the search for numSeats continuous seats
                    found = this.hasRemainingSeatsFreeInRow(i, j, numSeats-1); // Are next numSeats FREE ?
                }
            }
        }
        //Prepare a SeatHold object to return
        if(found){
            sh = new SeatHold(secondsToHoldExpiry, customerEmail);
            sh.setRowIndex(i);
            sh.setColumnIndex(j);
            sh.setNumberOfSeats(numSeats);
            // Change the status of seats[][]
            for(int k = 0; k < numSeats; k++){
                seats[i][j+k].setStatusHold();
            }
        } else{
            throw new Exception("No continuous seats found"); //TODO Custom exception for NoContinousSeatsFound
        }
        return sh;
    }

    private Boolean hasRemainingSeatsFreeInRow(int m, int n, int remainingSeats) {
        Boolean r = false;
        int count = remainingSeats;
        for(int j = n+1 ; j < columns && count > 0; j++)
        {
            r = r && this.seats[m][j].isFree();
            count--;
        }
        return r;
    }

    public void cleanupExpiredSeatHolds() throws Exception
    {
        //TODO Make a NoCustomerExists Exception
        for(String customerEmail : this.seatHolds.keySet()){
            this.cleanupExpiredSeatHolds(customerEmail);
        }
    }

    public void cleanupExpiredSeatHolds(String customerEmail) throws Exception {
        if(this.seatHolds.containsKey(customerEmail)){
            List<SeatHold> customerSeatHolds = this.seatHolds.get(customerEmail);
            List<SeatHold> expiredSeatHolds = new ArrayList<>(); //Cleanup for existing seatholds for the customer
            for(SeatHold csh: customerSeatHolds){
                if(!csh.isActive()) {
                    expiredSeatHolds.add(csh);
                }
            }
            customerSeatHolds.removeAll(expiredSeatHolds);
            this.seatHolds.put(customerEmail, customerSeatHolds);
        }else{
            throw new Exception(customerEmail + " doesn't have a seathold already in  Venue "+name); //TODO Make a NoCustomerExists Exception
        }

    }
}
