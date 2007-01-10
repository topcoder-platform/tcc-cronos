/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.search;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test for TimeStatusCriteria class.
 * </p>
 *
 * @author arylio
 * @version 2.0
 */
public class V2TimeStatusCriteriaUnitTest extends TestCase {
    /**
     * <p>
     * Tests the static final field DESCRIPTION.
     * </p>
     */
    public void testDESCRIPTION() {
        assertEquals("The static final field should be correct.", "time_status.description",
                TimeStatusCriteria.DESCRIPTION.getName());
    }

    /**
     * <p>
     * Tests the static final field CREATION_DATE.
     * </p>
     */
    public void testCREATION_DATE() {
        assertEquals("The static final field should be correct.", "time_status.creation_date",
                TimeStatusCriteria.CREATION_DATE.getName());
    }

    /**
     * <p>
     * Tests the static final field CREATION_DATE.
     * </p>
     */
    public void testMODIFICATION_DATE() {
        assertEquals("The static final field should be correct.", "time_status.modification_date",
                TimeStatusCriteria.MODIFICATION_DATE.getName());
    }

    /**
     * <p>
     * Tests the static final field CREATION_USER.
     * </p>
     */
    public void testCREATION_USER() {
        assertEquals("The static final field should be correct.", "time_status.creation_user",
                TimeStatusCriteria.CREATION_USER.getName());
    }

    /**
     * <p>
     * Tests the static final field MODIFICATION_USER.
     * </p>
     */
    public void testMODIFICATION_USER() {
        assertEquals("The static final field should be correct.", "time_status.modification_user",
                TimeStatusCriteria.MODIFICATION_USER.getName());
    }
}
