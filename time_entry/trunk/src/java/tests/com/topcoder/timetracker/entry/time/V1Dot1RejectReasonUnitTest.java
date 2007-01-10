/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for RejectReason.
 * </p>
 *
 * <p>
 * This class is pretty simple. The test cases simply verifies the get and set process.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class V1Dot1RejectReasonUnitTest extends TestCase {
    /**
     * <p>
     * RejectReason instance for testing.
     * </p>
     */
    private RejectReason rejectReason = null;

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     */
    protected void setUp() {
        this.rejectReason = new RejectReason();
    }

    /**
     * <p>
     * Tests the constructor - RejectReason().
     * </p>
     */
    public void testTimeStatus() {
        assertNotNull("The constructor does not work well", this.rejectReason);
    }
}
