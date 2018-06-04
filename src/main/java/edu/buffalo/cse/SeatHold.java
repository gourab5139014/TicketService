package edu.buffalo.cse;

import java.time.Instant;

public class SeatHold {
    Instant heldAt;
    String customerEmail;
    int rowIndex;
    int columnIndex;
    int numberofSeats;

    public SeatHold(){
        this.heldAt = Instant.now();
    }

    public String release(){
        return null;
    }

    public void reserve(){
        
    }
}
