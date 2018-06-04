package edu.buffalo.cse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SeatHoldTest {
    @BeforeClass
    public static void setup(){

    }

    @AfterClass
    public static void teardown(){

    }

    @Test
    public void testSeatHoldExpired(){
        long secondsToLive = 3;
        SeatHold sh = new SeatHold(secondsToLive);
        synchronized (this) {
            try {
                Thread.sleep((secondsToLive + 1) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertTrue(sh.isActive() == false);
        }
    }

    @Test
    public void testSeatHoldNotExpired(){
        long secondsToLive = 3;
        SeatHold sh = new SeatHold(secondsToLive);
        synchronized (this){
            try {
                Thread.sleep((secondsToLive-1)*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertTrue(sh.isActive());
        }
    }
}
