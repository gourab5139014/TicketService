package edu.buffalo.cse;

import java.time.Instant;

public class SeatHold {
    Instant heldAt;
    String customerEmail;
    long expireAfterSeconds;
    int rowIndex;
    int columnIndex;
    int numberofSeats;

    public SeatHold(long expireAfterSeconds){
        this.expireAfterSeconds = expireAfterSeconds;
        this.heldAt = Instant.now();
    }

    public String release(){
        return null;
    }

    public void reserve(){

    }

    public Boolean isActive(){
        return Instant.now().minusSeconds(expireAfterSeconds).isBefore(this.heldAt) || Instant.now().minusSeconds(expireAfterSeconds).equals(this.heldAt);
    }
}
