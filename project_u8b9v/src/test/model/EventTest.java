package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: used from alarm system
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Transaction added to Savings Account");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Transaction added to Savings Account", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Transaction added to Savings Account", e.toString());
    }

    @Test
    public void testHashCode() {
        Event event1 = new Event("event 1");
        Event event2 = new Event("event 2");

        // Test when both events have same values for date and description
        Event event3 = new Event("event 1");

        assertEquals(event1.hashCode(), event3.hashCode());
    }

    @Test
    public void testEquals() {
        Event event1 = new Event("event 1");
        Event event2 = new Event("event 2");

        // Test when both events have same values for date and description
        Event event3 = new Event("event 1");

        assertTrue(event1.equals(event3));
        assertFalse(event1.equals(event2));
        assertFalse(event1.equals(null));
        assertFalse(event1.equals("event 1"));
    }
}