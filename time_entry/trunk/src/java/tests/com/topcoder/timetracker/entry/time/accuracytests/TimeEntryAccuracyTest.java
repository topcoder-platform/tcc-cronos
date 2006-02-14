package com.topcoder.timetracker.entry.time.accuracytests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.TimeEntry;

/**
 * Unit test for <code>TimeEntry</code>
 * @author fuyun
 * @version 1.0
 */
public class TimeEntryAccuracyTest extends TestCase {
    
    /**
     * the <code>TimeEntry</code> instance used in test.
     */
    private TimeEntry timeEntry = null;
    
    /**
     * the modify user string used for test.
     */
    private final String MODIFY_USER = "modify user";
    
    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() throws Exception {
        timeEntry = new TimeEntry();
    }

    /**
     * <p>
     * Test the method getCreationDate().
     * </p>
     */
    public void testGetCreationDate() {
        assertNull("the default creation date should be null.", timeEntry.getCreationDate());
    }

    /**
     * <p>
     * Test method <code>setCreationDate</code>.
     * </p>
     */
    public void testSetCreationDate1() {
        Date date = new Date();

        timeEntry.setCreationDate(date);

        assertEquals("creation date is not properly set.", date, timeEntry.getCreationDate());
    }

    /**
     * <p>
     * Test method <code>setCreationDate</code>. the <code>null</code> argument is acceptable.
     * </p>
     */
    public void testSetCreationDate2() {
        timeEntry.setCreationDate(null);

        assertNull("creation date is not properly set.", timeEntry.getCreationDate());
    }

    /**
     * <p>
     * Test method <code>getModificationUser</code>.
     * </p>
     */
    public void testGetModificationUser() {
        assertNull("the default modification should be null.", timeEntry.getModificationUser());
    }

    /**
     * <p>
     * Test method <code>setModificationUser</code>.
     * </p>
     */
    public void testSetModificationUser1() {
        timeEntry.setModificationUser(MODIFY_USER);

        assertEquals("The modification user is not properly set.", MODIFY_USER, timeEntry.getModificationUser());
    }

    /**
     * <p>
     * Test method <code>setModificationUser</code>. the <code>null</code> is acceptable.
     * </p>
     */
    public void testSetModificationUser2() {
        timeEntry.setModificationUser(null);

        assertNull("The modification user can be null.", timeEntry.getModificationUser());
    }

    /**
     * <p>
     * Test method <code>getModificationDate</code>.
     * </p>
     */
    public void testGetModificationDate() {
        assertNull("the default modification date should be null.", timeEntry.getModificationDate());
    }

    /**
     * <p>
     * Test method <code>setModificationDate</code>.
     * </p>
     */
    public void testSetModificationDate1() {
        Date date = new Date();

        timeEntry.setModificationDate(date);

        assertEquals("The modification date is not properly set.", date, timeEntry.getModificationDate());
    }

    /**
     * <p>
     * Test method <code>setModificationDate</code>. the <code>null</code> argument is acceptable.
     * </p>
     */
    public void testSetModificationDate2() {
        timeEntry.setModificationDate(null);

        assertNull("The modification date can be null.", timeEntry.getModificationDate());
    }

}
