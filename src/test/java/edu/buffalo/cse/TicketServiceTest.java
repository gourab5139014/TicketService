package edu.buffalo.cse;

import static org.junit.Assert.assertTrue;

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


}
