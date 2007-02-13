/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.search;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>TimeEntryCriteria</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @author arylio
 *
 * @version 2.0
 * @since 1.1
 */
public class V1Dot1TimeEntryCriteriaUnitTest extends TestCase {
    /**
     * <p>
     * Tests the static final field DESCRIPTION.
     * </p>
     */
    public void testDESCRIPTION() {
        assertEquals("The static final field should be correct.", "time_entry.description",
            TimeEntryCriteria.DESCRIPTION.getName());
    }

    /**
     * <p>
     * Tests the static final field TIME_STATUS_ID.
     * </p>
     */
    public void testTIME_STATUS_ID() {
        assertEquals("The static final field should be correct.", "time_entry.time_status_id",
            TimeEntryCriteria.TIME_STATUS_ID.getName());
    }

    /**
     * <p>
     * Tests the static final field CREATION_USER.
     * </p>
     */
    public void testCREATION_USER() {
        assertEquals("The static final field should be correct.", "time_entry.creation_user",
            TimeEntryCriteria.CREATION_USER.getName());
    }

    /**
     * <p>
     * Tests the static final field MODIFICATION_USER.
     * </p>
     */
    public void testMODIFICATION_USER() {
        assertEquals("The static final field should be correct.", "time_entry.modification_user",
            TimeEntryCriteria.MODIFICATION_USER.getName());
    }

    /**
     * <p>
     * Tests the static final field BILLABLE_FLAG.
     * </p>
     */
    public void testBILLABLE_FLAG() {
        assertEquals("The static final field should be correct.", "time_entry.billable",
            TimeEntryCriteria.BILLABLE_FLAG.getName());
    }

    /**
     * <p>
     * Tests the static final field REJECT_REASON_ID.
     * </p>
     */
    public void testREJECT_REASON_ID() {
        assertEquals("The static final field should be correct.", "time_reject_reason.reject_reason_id",
            TimeEntryCriteria.REJECT_REASON_ID.getName());
    }

    /**
     * <p>
     * Tests the static final field HOURS.
     * </p>
     */
    public void testHOURS() {
        assertEquals("The static final field should be correct.",
                "time_entry.hours", TimeEntryCriteria.HOURS.getName());
    }

    /**
     * <p>
     * Tests the static final field DATE.
     * </p>
     */
    public void testDATE() {
        assertEquals("The static final field should be correct.", "time_entry.entry_date",
                TimeEntryCriteria.DATE.getName());
    }

    /**
     * <p>
     * Tests the static final field CREATION_DATE.
     * </p>
     */
    public void testCREATION_DATE() {
        assertEquals("The static final field should be correct.", "time_entry.creation_date",
            TimeEntryCriteria.CREATION_DATE.getName());
    }

    /**
     * <p>
     * Tests the static final field MODIFICATION_DATE.
     * </p>
     */
    public void testMODIFICATION_DATE() {
        assertEquals("The static final field should be correct.", "time_entry.modification_date",
            TimeEntryCriteria.MODIFICATION_DATE.getName());
    }
}
