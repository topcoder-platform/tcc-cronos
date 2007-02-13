/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.search;

import com.topcoder.timetracker.entry.expense.V1Dot1TestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>NotCriteria</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1NotCriteriaUnitTest extends TestCase {
    /**
     * Represents the contained criteria which will be used to create the <code>NotCriteria</code> instance used for
     * testing.
     */
    private Criteria innerCriteria = null;

    /** Represents the <code>NotCriteria</code> instance which will be used for testing. */
    private NotCriteria criteria = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        innerCriteria = new FieldBetweenCriteria("field", "from", "to");
        criteria = new NotCriteria(innerCriteria);
    }

    /**
     * <p>
     * Tests constructor <code>NotCriteria(Criteria)</code> when criteria is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testNotCriteria_NullCriteria() {
        try {
            new NotCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>NotCriteria(Criteria)</code>.
     * </p>
     */
    public void testNotCriteria_Accuracy() {
        assertEquals("The inner criteria value should be correct.", this.innerCriteria,
            V1Dot1TestHelper.getPrivateField(criteria.getClass(), criteria, "criteria"));
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies <code>NotCriteria</code> subclasses <code>Criteria</code>.
     * </p>
     */
    public void testNotCriteria_InheritanceAccuracy() {
        assertTrue("NotCriteria does not subclass Criteria.", this.criteria instanceof Criteria);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testGetCriteria_Accuracy() {
        assertEquals("The inner criteria should be correct.", innerCriteria, this.criteria.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getWhereClause()</code>.
     * </p>
     */
    public void testGetWhereClause_Accuracy() {
        assertEquals("The whereClause should be correct.", "NOT (" + innerCriteria.getWhereClause() + ")",
            this.criteria.getWhereClause());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getParameters()</code>.
     * </p>
     */
    public void testGetParameters_Accuracy() {
        assertEquals("The parameters should be correct.", innerCriteria.getParameters(), criteria.getParameters());
    }
}
