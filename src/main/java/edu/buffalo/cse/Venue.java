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
        this.rows = rows;
        this.columns = columns;
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
        int seatsAvailable = 0;
        for(int i=0; i<rows;i++) {
            for (int j = 0; j < columns; j++) {
                if(this.seats[i][j].isFree())
                    seatsAvailable++;
            }
        }
        this.availableSeats = seatsAvailable;
        return this.availableSeats;
    }

    public Boolean hasSeats(int requestedSeats){
        return this.getAvailableSeats() > 0;
    }

    public Boolean reserveSeats(int seatHoldId, String customerEmail) throws Exception {
        Boolean done = false;
        this.cleanupExpiredSeatHolds();
        if(this.seatHolds.containsKey(customerEmail)){
            List<SeatHold> customerSeatHolds = this.seatHolds.get(customerEmail);
            List<SeatHold> removeSeatHolds = new ArrayList<>();
            for(SeatHold csh:customerSeatHolds){
                if(csh.getSeatHoldId() == seatHoldId && csh.isActive()){
                    for(int k = 0; k < csh.getNumberOfSeats(); k++){
                        seats[csh.getRowIndex()][csh.getColumnIndex()+k].setStatusBooked();
                    }
                    done = true;
                    removeSeatHolds.add(csh);
                    break;
                }
            }
            customerSeatHolds.removeAll(removeSeatHolds);

        }else{
            throw new Exception(customerEmail + " doesn't have a seathold already in  Venue "+name); //TODO Make a NoCustomerExists Exception
        }
        if(!done)
        	throw new Exception("Invalid seatHold Id "+seatHoldId); //TODO Make a custom exception InvalidSeatHold Id
        return done;
    }

    public SeatHold holdSeats(int numSeats, String customerEmail) throws Exception {
        SeatHold sh;
        this.cleanupExpiredSeatHolds(); //TODO Whacky idea!!!
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
                    System.err.println("Started looking for seats at ("+i+","+j+")");
                    found = this.hasRemainingSeatsFreeInRow(i, j, numSeats-1); // Are next numSeats FREE ?

                }
            }
        }
        //Prepare a SeatHold object to return
        if(found){
            sh = new SeatHold(secondsToHoldExpiry, customerEmail);
            sh.setRowIndex(i-1);
            sh.setColumnIndex(j-1);
            sh.setNumberOfSeats(numSeats);
            // Change the status of seats[][]
            for(int k = 0; k < numSeats; k++){
                seats[i-1][j-1+k].setStatusHold();
            }
        } else{
            throw new Exception("No continuous seats found"); //TODO Custom exception for NoContinousSeatsFound
        }
        return sh;
    }

    private Boolean hasRemainingSeatsFreeInRow(int m, int n, int remainingSeats) {
        Boolean r = true;
        int count = remainingSeats;
        for(int j = n+1 ; j < columns && count > 0; j++)
        {
            r = r && this.seats[m][j].isFree();
            System.err.println("Checking " + count +" remaining seat  at ("+m+","+j+") = "+this.seats[m][j].isFree());
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
        System.err.println("GC for seats of "+customerEmail);
        if(this.seatHolds.containsKey(customerEmail)){
            List<SeatHold> customerSeatHolds = this.seatHolds.get(customerEmail);
            List<SeatHold> expiredSeatHolds = new ArrayList<>(); //Cleanup for existing seatholds for the customer
            for(SeatHold csh: customerSeatHolds){
                if(!csh.isActive()) {
                    expiredSeatHolds.add(csh);
                    for(int j=0; j<csh.getNumberOfSeats();j++){
                        seats[csh.getRowIndex()][csh.getColumnIndex()+j].setStatusFree();
                    }
                }
            }
            customerSeatHolds.removeAll(expiredSeatHolds);
            if(customerSeatHolds.size()==0){ //Customer has no more seatholds
                this.seatHolds.remove(customerEmail);
            }else {
                this.seatHolds.put(customerEmail, customerSeatHolds);
            }
        }else{
            throw new Exception(customerEmail + " doesn't have a seathold already in  Venue "+name); //TODO Make a NoCustomerExists Exception
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< rows; i++){
            StringBuilder r = new StringBuilder();
            for(int j=0;j<columns;j++){
                if(seats[i][j].isFree())
                    r.append("F");
                else if(seats[i][j].isHold())
                    r.append("H");
                else
                    r.append("B");
            }
            r.append(System.getProperty("line.separator"));
            sb.append(r);
        }
        return sb.toString();
    }
}
