package edu.buffalo.cse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void testHoldSeats1GroupAndSeatsAvailable() throws Exception {
        Venue v = new Venue("TestVenue",5,5,10);
        System.err.println(v);
        SeatHold sh = v.holdSeats(2,"TestCustomer1");
        System.err.println(v);
        assertTrue(sh.getRowIndex() == 0);
        assertTrue(sh.getColumnIndex() == 0);
        assertTrue(sh.getNumberOfSeats() == 2);
        assertEquals(23, v.getAvailableSeats());
    }

    @Test
    public void testHoldSeats2GroupsOneRow() throws Exception {
        int group1seats = 2;
        int group2seats = 3;
        Venue v = new Venue("TestVenue",5,5,10);
        System.err.println(v);
        SeatHold sh1 = v.holdSeats(group1seats,"TestCustomer1");
        SeatHold sh2 = v.holdSeats(group2seats, "TestCustomer2");
        System.err.println(v);
        assertTrue(sh1.getRowIndex() == 0);
        assertTrue(sh1.getColumnIndex() == 0);
        assertTrue(sh1.getNumberOfSeats() == group1seats);
        assertTrue(sh2.getRowIndex() == 0);
        assertTrue(sh2.getColumnIndex() == 2);
        assertTrue(sh2.getNumberOfSeats() == group2seats);
    }

    @Test
    public void testHoldSeats2GroupsAdjacentRows() throws Exception {
        int group1seats = 3;
        int group2seats = 3;
        Venue v = new Venue("TestVenue",5,5,10);
        System.err.println(v);
        SeatHold sh1 = v.holdSeats(group1seats,"TestCustomer1");
        SeatHold sh2 = v.holdSeats(group2seats, "TestCustomer2");
        System.err.println(v);
        assertTrue(sh1.getRowIndex() == 0);
        assertTrue(sh1.getColumnIndex() == 0);
        assertTrue(sh1.getNumberOfSeats() == group1seats);
        assertTrue(sh2.getRowIndex() == 1);
        assertTrue(sh2.getColumnIndex() == 0);
        assertTrue(sh2.getNumberOfSeats() == group2seats);
    }

    @Test(expected = Exception.class) //TODO Use custom exception
    public void testHoldSeats1GroupTooBigForRow() throws Exception {
        Venue v = new Venue("TestVenue",5,5,10);
        SeatHold sh = v.holdSeats(6,"TestCustomer1");
    }

    @Test(expected = Exception.class) //TODO Use custom exception
    public void testHoldSeats1GroupTooBigForVenue() throws Exception {
        Venue v = new Venue("TestVenue",5,5,10);
        SeatHold sh = v.holdSeats(26,"TestCustomer1");
    }

    @Test(expected = Exception.class) //TODO Use custom exception
    public void testHoldSeats1GroupCantFitInBetween() throws Exception {
        Venue v = new Venue("TestVenue",5,5,10);
        v.holdSeats(2,"TestCustomer");
        v.holdSeats(4,"TestCustomer");
        v.holdSeats(5,"TestCustomer");
        v.holdSeats(4,"TestCustomer");
        v.holdSeats(4,"TestCustomer");
        v.holdSeats(4,"TestCustomer");
    }
    
    @Test
    public void testHoldSeatsManyGroupsExpired() throws Exception {
        int secondsToLive = 1;
		Venue v = new Venue("TestVenue",5,5,secondsToLive );
        assertEquals(25, v.getAvailableSeats());
        v.holdSeats(2,"TestCustomer");
        assertEquals(23, v.getAvailableSeats());
        v.holdSeats(4,"TestCustomer");
        assertEquals(19, v.getAvailableSeats());
        v.holdSeats(5,"TestCustomer");
        assertEquals(14, v.getAvailableSeats());
        v.holdSeats(4,"TestCustomer");
        assertEquals(10, v.getAvailableSeats());
        v.holdSeats(4,"TestCustomer");
        assertEquals(6, v.getAvailableSeats());
        System.err.println(v);
        synchronized (this) {
            try {
                System.err.println("Sleeping for "+(secondsToLive + 1)+" secs");
                Thread.sleep((secondsToLive + 1) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SeatHold sh = v.holdSeats(4,"TestCustomer");
        System.err.println(v);
        assertTrue(sh.getRowIndex()==0);
        assertTrue(sh.getColumnIndex()==0);
        assertTrue(sh.getNumberOfSeats()==4);
        assertEquals(21, v.getAvailableSeats());
    }

    @Test
    public void testHoldSeatsManyGroupsExpiredAndAvailableSeats() throws Exception {
        int secondsToLive = 1;
        Venue v = new Venue("TestVenue",5,5,secondsToLive );
        v.holdSeats(2,"TestCustomer");
        v.holdSeats(4,"TestCustomer");
        v.holdSeats(5,"TestCustomer");
        v.holdSeats(4,"TestCustomer");
        v.holdSeats(4,"TestCustomer");
        synchronized (this) {
            try {
                Thread.sleep((secondsToLive + 1) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.err.println(v);
        SeatHold sh = v.holdSeats(4,"TestCustomer");
        assertTrue(sh.getRowIndex()==0);
        assertTrue(sh.getColumnIndex()==0);
        assertTrue(sh.getNumberOfSeats()==4);
    }

    @Test
    public void testHoldAndReserve1Group() throws Exception {
        Venue v = new Venue("TestVenue",5,5,5);
        SeatHold sh = v.holdSeats(2, "TestCustomer");
        Boolean reserveStatus = v.reserveSeats(sh.getSeatHoldId(), "TestCustomer");
        assertTrue(reserveStatus);
    }

    @Test(expected = Exception.class)
    public void testHoldAndReserve1GroupExpired() throws Exception {
        int secondsToLive = 1;
        Venue v = new Venue("TestVenue",5,5,secondsToLive);
        SeatHold sh = v.holdSeats(2, "TestCustomer");
        System.err.println(v);
        synchronized (this) {
            try {
                Thread.sleep((secondsToLive + 1) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Boolean reserveStatus = v.reserveSeats(sh.getSeatHoldId(), "TestCustomer");
        System.err.println(v);
    }
}
