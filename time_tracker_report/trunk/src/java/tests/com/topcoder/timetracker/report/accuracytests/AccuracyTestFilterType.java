/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.FilterType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Accuracy test for <code>FilterType</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestFilterType extends TestCase {
    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestFilterType.class);
    }

    /**
     * Tests getType().
     */
    public void testGetType() {
        assertTrue("EQUALITY is not valid.", FilterType.EQUALITY.getType().equals("EQUALITY"));
        assertTrue("RANGE is not valid.", FilterType.RANGE.getType().equals("RANGE"));
    }

    /**
     * Tests toString().
     */
    public void testToString() {
        assertTrue("EQUALITY is not valid.", FilterType.EQUALITY.toString().equals("EQUALITY"));
        assertTrue("RANGE is not valid.", FilterType.RANGE.toString().equals("RANGE"));
    }
}
