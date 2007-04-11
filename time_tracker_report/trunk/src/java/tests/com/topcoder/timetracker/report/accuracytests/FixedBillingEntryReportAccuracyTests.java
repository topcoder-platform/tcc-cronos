/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.report.informix.FixedBillingEntryReport;

import junit.framework.TestCase;

/**
 * The test of FixedBillingEntryReport.
 *
 * @author brain_cn
 * @version 1.0
 */
public class FixedBillingEntryReportAccuracyTests extends TestCase {
    /** The tset FixedBillingEntryReport for testing. */
    private FixedBillingEntryReport instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        instance = new FixedBillingEntryReport();
    }

    /**
     * Test method for 'FixedBillingEntryReport()'
     */
    public void testFixedBillingEntryReport() {
        assertNotNull("the instance is created", instance);
    }

    /**
     * Test method for 'getFixedBillingEntry()'
     */
    public void testGetFixedBillingEntry() {
        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        instance.setFixedBillingEntry(fixedBillingEntry);
        assertEquals("incorrect fixedBillingEntry", fixedBillingEntry, instance.getFixedBillingEntry());
    }

    /**
     * Test method for 'setFixedBillingEntry(FixedBillingEntry)'
     */
    public void testSetFixedBillingEntry() {
        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        instance.setFixedBillingEntry(fixedBillingEntry);
        assertEquals("incorrect fixedBillingEntry", fixedBillingEntry, instance.getFixedBillingEntry());

        FixedBillingEntry fixedBillingEntry1 = new FixedBillingEntry();
        instance.setFixedBillingEntry(fixedBillingEntry1);
        assertEquals("incorrect fixedBillingEntry", fixedBillingEntry1, instance.getFixedBillingEntry());
    }

    /**
     * Test method for 'getFixedBillingStatus()'
     */
    public void testGetFixedBillingStatus() {
        FixedBillingStatus fixedBillingStatus = new FixedBillingStatus();
        instance.setFixedBillingStatus(fixedBillingStatus);
        assertEquals("incorrect fixedBillingStatus", fixedBillingStatus, instance.getFixedBillingStatus());
    }

    /**
     * Test method for 'setFixedBillingStatus(FixedBillingStatus)'
     */
    public void testSetFixedBillingStatus() {
        FixedBillingStatus fixedBillingStatus = new FixedBillingStatus();
        instance.setFixedBillingStatus(fixedBillingStatus);
        assertEquals("incorrect fixedBillingStatus", fixedBillingStatus, instance.getFixedBillingStatus());

        FixedBillingStatus fixedBillingStatus1 = new FixedBillingStatus();
        instance.setFixedBillingStatus(fixedBillingStatus1);
        assertEquals("incorrect fixedBillingStatus", fixedBillingStatus1, instance.getFixedBillingStatus());
    }

}
