/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.report.BaseTestCase;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;

/**
 * <p>
 * This class provides tests for <code>FixedBillingEntryReport</code> class. It tests:
 * <ol>
 * <li>FixedBillingEntryReport() constructor</li>
 * <li>getter and setter of fixedBillingEntry</li>
 * <li>getter and setter of fixedBillingStatus</li>
 * </ol>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestFixedBillingEntryReport extends BaseTestCase {

    /**
     * <p>
     * Create a new <code>FixedBillingEntry</code> object.
     * </p>
     *
     * @return a new created <code>FixedBillingEntry</code> object.
     *
     * @throws Exception to JUnit
     */
    private FixedBillingEntry createFixedBillingEntry() throws Exception {
        FixedBillingEntry entry = new FixedBillingEntry();
        entry.setId(1);
        entry.setDescription("entry desc");
        entry.setFixedBillingStatus(this.createFixedBillingStatus());
        return entry;
    }

    /**
     * <p>
     * Create a new <code>FixedBillingStatus</code> object.
     * </p>
     *
     * @return a new created <code>FixedBillingStatus</code> object.
     *
     * @throws Exception to JUnit
     */
    private FixedBillingStatus createFixedBillingStatus() throws Exception {
        FixedBillingStatus status = new FixedBillingStatus();
        status.setId(101);
        status.setDescription("status desc");
        return status;
    }

    /**
     * <p>
     * Assert a <code>FixedBillingEntry</code> object to be same as the time it was created.
     * </p>
     *
     * @param entry the entry to test
     * @throws Exception to JUnit
     */
    private void assertExpectedFixedBillingEntry(FixedBillingEntry entry) throws Exception {
        assertNotNull("The entry should not be null.", entry);
        assertEquals("The entry.id should be 1.", 1, entry.getId());
        assertEquals("The entry.description should be 'entry desc'.", "entry desc", entry.getDescription());
        this.assertExpectedFixedBillingStatus(entry.getFixedBillingStatus());
    }

    /**
     * <p>
     * Assert a <code>FixedBillingStatus</code> object to be same as the time it was created.
     * </p>
     *
     * @param status the status to test
     * @throws Exception to JUnit
     */
    private void assertExpectedFixedBillingStatus(FixedBillingStatus status) throws Exception {
        assertNotNull("The entry should not be null.", status);
        assertEquals("The entry.id should be 101.", 101, status.getId());
        assertEquals("The entry.description should be 'status desc'.", "status desc", status.getDescription());
    }

    /**
     * <p>
     * Test of <code>FixedBillingEntryReport()</code> constructor.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testFixedBillingEntryReport_Ctor() throws Exception {
        FixedBillingEntryReport report = new FixedBillingEntryReport();
        assertTrue("FixedBillingEntryReport should extend ReportEntryBean", report instanceof ReportEntryBean);
    }

    /**
     * <p>
     * Validate the getter/setter methods of fixedBillingEntry.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void assertFixedBillingEntry_getter_setter() throws Exception {
        FixedBillingEntryReport report = new FixedBillingEntryReport();

        FixedBillingEntry entry = this.createFixedBillingEntry();
        report.setFixedBillingEntry(entry);
        FixedBillingEntry retrievedentry = report.getFixedBillingEntry();

        assertEquals("The entry object should be stored properly.", entry, retrievedentry);
        this.assertExpectedFixedBillingEntry(retrievedentry);
    }

    /**
     * <p>
     * Test of <code>getFixedBillingEntry()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetFixedBillingEntry() throws Exception {
        this.assertFixedBillingEntry_getter_setter();
    }

    /**
     * <p>
     * Test of <code>setFixedBillingEntry()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetFixedBillingEntry() throws Exception {
        this.assertFixedBillingEntry_getter_setter();
    }

    /**
     * <p>
     * Validate the getter/setter methods of fixedBillingStatus.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void assertFixedBillingStatus_getter_setter() throws Exception {
        FixedBillingEntryReport report = new FixedBillingEntryReport();

        FixedBillingStatus status = this.createFixedBillingStatus();
        report.setFixedBillingStatus(status);
        FixedBillingStatus retrievedstatus = report.getFixedBillingStatus();

        assertEquals("The entry object should be stored properly.", status, retrievedstatus);
        this.assertExpectedFixedBillingStatus(retrievedstatus);
    }

    /**
     * <p>
     * Test of <code>getFixedBillingStatus()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetFixedBillingStatus() throws Exception {
        this.assertFixedBillingStatus_getter_setter();
    }

    /**
     * <p>
     * Test of <code>setFixedBillingStatus()</code> method.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetFixedBillingStatus() throws Exception {
        this.assertFixedBillingStatus_getter_setter();
    }
}
