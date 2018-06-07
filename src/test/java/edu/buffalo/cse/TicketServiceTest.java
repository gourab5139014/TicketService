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
    public void testHoldSeats1Customer()
    {
    	TicketServiceImpl ts = new TicketServiceImpl("testHoldSeats Venue",5,5,2);
    	SeatHold sh = ts.findAndHoldSeats(2, "testHoldSeats Customer");
    	assertEquals(0, sh.getRowIndex());
    	assertEquals(0, sh.getColumnIndex());
    }
    
    @Test
    public void testHoldSeatsAllSeatsHeldDiffCustomers()
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


}
