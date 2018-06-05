package edu.buffalo.cse;

public class Seat {

    private enum SeatStatus{
        FREE, HOLD, BOOKED
    }

    private SeatStatus status;
    private String customerEmail;

    public Seat(){
        this.status = SeatStatus.FREE;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatusHold() {
        this.status = SeatStatus.HOLD;
    }

    public void setStatusFree() {
        this.status = SeatStatus.FREE;
    }

    public void setStatusBooked() {
        this.status = SeatStatus.BOOKED;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public Boolean isFree(){
        return this.status == SeatStatus.FREE;
    }
    public Boolean isHold(){
        return this.status == SeatStatus.HOLD;
    }
    public Boolean isBooked(){
        return this.status == SeatStatus.BOOKED;
    }
}
