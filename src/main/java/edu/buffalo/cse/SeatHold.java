package edu.buffalo.cse;

import java.time.Instant;

public class SeatHold {
    Instant heldAt;

    public SeatHold(){
        this.heldAt = Instant.now();
    }
}
