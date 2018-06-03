package edu.buffalo.cse;

import java.time.Instant;

public class SeatHold {
    Instant heldAt;
    String customerEmail;

    public SeatHold(){
        this.heldAt = Instant.now();
    }
}
