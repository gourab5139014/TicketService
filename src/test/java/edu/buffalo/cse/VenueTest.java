package edu.buffalo.cse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VenueTest {
    @BeforeClass
    public static void setup(){

    }

    @AfterClass
    public static void teardown(){

    }

    @Test
    public void testAvailableSeats(){
        Venue v = new Venue("TestVenue",10,10,10);
        assertTrue(v.getAvailableSeats() == 100);
    }

    @Test
    public void testHoldSeats1Group() throws Exception {
        Venue v = new Venue("TestVenue",5,5,10);
        System.err.println(v);
        SeatHold sh = v.holdSeats(2,"TestCustomer1");
        System.err.println(v);
        assertTrue(sh.getRowIndex() == 0);
        assertTrue(sh.getColumnIndex() == 0);
        assertTrue(sh.getNumberOfSeats() == 2);
    }

}
