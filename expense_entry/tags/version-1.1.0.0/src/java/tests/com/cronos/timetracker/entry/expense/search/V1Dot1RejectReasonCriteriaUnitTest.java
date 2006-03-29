/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.search;

import junit.framework.TestCase;

import com.cronos.timetracker.entry.expense.V1Dot1TestHelper;


/**
 * <p>
 * Tests functionality and error cases of <code>RejectReasonCriteria</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1RejectReasonCriteriaUnitTest extends TestCase {
    /**
     * Represents the rejectReasonId which will be used to create the new <code>RejectReasonCriteria</code> instance
     * for testing.
     */
    private static final int ID = 1;

    /** Represents the <code>RejectReasonCriteria</code> instance which will be used for testing. */
    private RejectReasonCriteria criteria = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        criteria = new RejectReasonCriteria(ID);
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>RejectReasonCriteria(int)</code>.
     * </p>
     */
    public void testRejectReasonCriteria_Accuracy() {
        assertEquals("The rejectReasonId value should be correct.", "" + ID,
            V1Dot1TestHelper.getPrivateField(criteria.getClass(), criteria, "rejectReasonId").toString());
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies <code>RejectReasonCriteria</code> subclasses <code>Criteria</code>.
     * </p>
     */
    public void testRejectReasonCriteria_InheritanceAccuracy() {
        assertTrue("RejectReasonCriteria does not subclass Criteria.", this.criteria instanceof Criteria);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getRejectReasonId()</code>.
     * </p>
     */
    public void testGetRejectReasonId_Accuracy() {
        assertEquals("The rejectReasonId value should be correct.", ID, this.criteria.getRejectReasonId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getWhereClause()</code>.
     * </p>
     */
    public void testGetWhereClause_Accuracy() {
        assertEquals("The whereClause should be correct.",
            "? IN (SELECT reject_reason_id FROM exp_reject_reason WHERE "
            + "exp_reject_reason.ExpenseEntriesId = ExpenseEntries.ExpenseEntriesId)", this.criteria.getWhereClause());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getParameters()</code>.
     * </p>
     */
    public void testGetParameters_Accuracy() {
        assertEquals("The parameters should be correct.", 1, this.criteria.getParameters().length);
        assertEquals("The parameters should be correct.", new Integer(ID), this.criteria.getParameters()[0]);
    }
}
