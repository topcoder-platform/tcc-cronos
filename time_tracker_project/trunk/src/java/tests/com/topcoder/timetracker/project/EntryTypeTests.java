/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for EntryType.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class EntryTypeTests extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(EntryTypeTests.class);
    }

    /**
     * <p>
     * Tests EntryType#getName() for accuracy.
     * </p>
     *
     * <p>
     * It verifies EntryType#getName() is correct.
     * </p>
     */
    public void testGetName() {
        assertEquals("Failed to get the name correctly.", "time_entry", EntryType.TIME_ENTRY.getName());
        assertEquals("Failed to get the name correctly.", "fixed_billing", EntryType.FIXED_BILLING_ENTRY.getName());
        assertEquals("Failed to get the name correctly.", "expense_entry", EntryType.EXPENSE_ENTRY.getName());
    }

    /**
     * <p>
     * Tests EntryType#toString() for accuracy.
     * </p>
     *
     * <p>
     * It verifies EntryType#toString() is correct.
     * </p>
     */
    public void testToString() {
        assertEquals("Failed to string correctly.", "time_entry", EntryType.TIME_ENTRY.toString());
        assertEquals("Failed to string correctly.", "fixed_billing", EntryType.FIXED_BILLING_ENTRY.toString());
        assertEquals("Failed to string correctly.", "expense_entry", EntryType.EXPENSE_ENTRY.toString());
    }

}