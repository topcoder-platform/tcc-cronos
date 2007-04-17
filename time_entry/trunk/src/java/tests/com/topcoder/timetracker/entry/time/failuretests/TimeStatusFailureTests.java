package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.timetracker.entry.time.TimeStatus;

import junit.framework.TestCase;
/**
 * <p>
 * Failure test cases for TimeStatus.
 * </p>
 *
 * @author KLW
 * @version 3.2
 */
public class TimeStatusFailureTests extends TestCase {
    /**
     * <p>
     * The TimeStatus instance for testing.
     * </p>
     */
    private TimeStatus instance;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new TimeStatus();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
    }
    /**
     * <p>
     * Tests TimeStatus#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is null and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription1() {
        try {
            instance.setDescription(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests TimeStatus#setDescription(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when description is empty and expects IllegalArgumentException.
     * </p>
     */
    public void testSetDescription2() {
        try {
            instance.setDescription(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}
