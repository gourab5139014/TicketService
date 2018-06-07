package edu.buffalo.cse;

import java.time.Instant;
import java.util.Random;

public class SeatHold {
    private Instant heldAt; // Not to be set from outside the class

    private String customerEmail;
    private long expireAfterSeconds;
    private int rowIndex;
    private int columnIndex;
    private int numberOfSeats;
    private int seatHoldId;

    public SeatHold(long expireAfterSeconds, String customerEmail){
        this.expireAfterSeconds = expireAfterSeconds;
        this.customerEmail = customerEmail;
        this.heldAt = Instant.now();
        this.seatHoldId = this.generateSeatHoldId(customerEmail);
    }

    private int generateSeatHoldId(String customerEmail) {
        Random r = new Random();
        return r.nextInt(customerEmail.length());
    }

    public String release(){
        return null;
    }

    public void reserve(){

    }

    public Boolean isActive(){
        return Instant.now().minusSeconds(expireAfterSeconds).isBefore(this.heldAt) || Instant.now().minusSeconds(expireAfterSeconds).equals(this.heldAt);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getSeatHoldId() {
        return seatHoldId;
    }

    public void setSeatHoldId(int seatHoldId) {
        this.seatHoldId = seatHoldId;
    }
}
