package edu.buffalo.cse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TicketServiceTest {

    @BeforeClass
    public static void setup(){

    }

    @AfterClass
    public static void teardown(){

    }

    @Test
    public void testConfirmationCodeLength(){
        int confirmationCodeLength = 5;
        TicketService ts = new TicketServiceImpl();
        String cc = ((TicketServiceImpl) ts)._generateConfirmationCode(confirmationCodeLength);
        assertTrue(cc.length() == confirmationCodeLength);
    }
    
    @Test
    public void testHoldSeats1Customer() throws Exception
    {
    	TicketServiceImpl ts = new TicketServiceImpl("testHoldSeats Venue",5,5,2);
    	SeatHold sh = ts.findAndHoldSeats(2, "testHoldSeats Customer");
    	assertEquals(0, sh.getRowIndex());
    	assertEquals(0, sh.getColumnIndex());
    }
    
    @Test
    public void testHoldSeatsAllSeatsHeldDiffCustomers() throws Exception
    {
    	TicketServiceImpl ts = new TicketServiceImpl("testHoldSeats Venue",5,5,2);
    	List<SeatHold> shl = new ArrayList<>();
    	for(int i = 0; i<5;i++){
    		shl.add(ts.findAndHoldSeats(5, "TestCustomer"+i));
    	}
    	for(int i = 0; i<shl.size();i++){
    		SeatHold sh = shl.get(i);
    		assertEquals(i, sh.getRowIndex());
        	assertEquals(0, sh.getColumnIndex());
    	}
    }
    
    @Test(expected = Exception.class)
    public void testHoldSeatsBookedException() throws Exception
    {
    	TicketServiceImpl ts = new TicketServiceImpl("testHoldSeats Venue",5,5,2);
    	List<SeatHold> shl = new ArrayList<>();
    	for(int i = 0; i<16;i++){
    		shl.add(ts.findAndHoldSeats(5, "TestCustomer"+i));
    	}
    }
    
    @Test
    public void testHoldReserveSeats1Group1Customer() throws Exception
    {
    	TicketServiceImpl ts = new TicketServiceImpl("testHoldReserveSeats1Customer Venue",5,5,2);
    	SeatHold sh = ts.findAndHoldSeats(2, "testHoldReserveSeats1Customer Customer");
    	String confirmationCode = ts.reserveSeats(sh.getSeatHoldId(), "testHoldReserveSeats1Customer Customer");
    	assertEquals(0, sh.getRowIndex());
    	assertEquals(0, sh.getColumnIndex());
    	assertTrue(confirmationCode.length() > 1);
    }
    
    @Test(expected = Exception.class)
    public void testHoldReserveSeats1Group1CustomerExpired() throws Exception
    {
    	int timeToLive = 1;
    	TicketServiceImpl ts = new TicketServiceImpl("testHoldReserveSeats1Customer Venue",5,5,timeToLive);
    	SeatHold sh = ts.findAndHoldSeats(2, "testHoldReserveSeats1Customer Customer");
    	synchronized (this) {
            try {
                Thread.sleep((timeToLive + 1) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    	String confirmationCode = ts.reserveSeats(sh.getSeatHoldId(), "testHoldReserveSeats1Customer Customer");
    	assertEquals(0, sh.getRowIndex());
    	assertEquals(0, sh.getColumnIndex());
    	assertTrue(confirmationCode.length() > 1);
    }
    
    @Test(expected = Exception.class) //TODO Custom InvalidSeatHoldId Exception
    public void testHoldReserveSeats1Group1CustomerInvalidSeatHold() throws Exception
    {
    	TicketServiceImpl ts = new TicketServiceImpl("testHoldReserveSeats1Customer Venue",5,5,2);
    	SeatHold sh = ts.findAndHoldSeats(2, "testHoldReserveSeats1Customer Customer");
    	System.err.println(sh.getSeatHoldId());
    	String confirmationCode = ts.reserveSeats(1234, "testHoldReserveSeats1Customer Customer");
//    	assertEquals(0, sh.getRowIndex());
//    	assertEquals(0, sh.getColumnIndex());
//    	assertTrue(confirmationCode.length() > 1);
    }
    
    @Test(expected = Exception.class) //TODO Custom InvalidCustomer Exception
    public void testHoldReserveSeats1Group1CustomerInvalidCustomer() throws Exception
    {
    	TicketServiceImpl ts = new TicketServiceImpl("testHoldReserveSeats1Customer Venue",5,5,2);
    	SeatHold sh = ts.findAndHoldSeats(2, "testHoldReserveSeats1Customer Customer");
    	String confirmationCode = ts.reserveSeats(sh.getSeatHoldId(), "InvalidCustomerId");
//    	assertEquals(0, sh.getRowIndex());
//    	assertEquals(0, sh.getColumnIndex());
//    	assertTrue(confirmationCode.length() > 1);
    }
    
    @Test
    public void testHoldReserveSeatsManyGroups1Customer() throws Exception
    {
    	TicketServiceImpl ts = new TicketServiceImpl("testHoldReserveSeats1Customer Venue",5,5,2);
    	SeatHold sh1 = ts.findAndHoldSeats(2, "testHoldReserveSeats1Customer Customer");
    	SeatHold sh2 = ts.findAndHoldSeats(2, "testHoldReserveSeats1Customer Customer");
    	SeatHold sh3 = ts.findAndHoldSeats(2, "testHoldReserveSeats1Customer Customer");
    	
    	String confirmationCode1 = ts.reserveSeats(sh1.getSeatHoldId(), "testHoldReserveSeats1Customer Customer");
    	String confirmationCode2 = ts.reserveSeats(sh2.getSeatHoldId(), "testHoldReserveSeats1Customer Customer");
    	String confirmationCode3 = ts.reserveSeats(sh3.getSeatHoldId(), "testHoldReserveSeats1Customer Customer");
    	
    	assertTrue(confirmationCode1.length() > 1);
    	assertTrue(confirmationCode2.length() > 1);
    	assertTrue(confirmationCode3.length() > 1);
    }
    
}
