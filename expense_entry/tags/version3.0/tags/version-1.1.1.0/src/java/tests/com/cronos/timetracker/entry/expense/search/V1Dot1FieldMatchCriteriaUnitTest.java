/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.search;

import com.cronos.timetracker.entry.expense.V1Dot1TestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>FieldMatchCriteria</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1FieldMatchCriteriaUnitTest extends TestCase {
    /**
     * Represents the field which will be used to create the new <code>FieldMatchCriteria</code> instance for testing.
     */
    private static final String FIELD = "field";

    /**
     * Represents the value which will be used to create the new <code>FieldMatchCriteria</code> instance for testing.
     */
    private static final String VALUE = "value";

    /** Represents the <code>FieldMatchCriteria</code> instance which will be used for testing. */
    private FieldMatchCriteria criteria = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        criteria = new FieldMatchCriteria(FIELD, VALUE);
    }

    /**
     * <p>
     * Tests constructor <code>FieldMatchCriteria(String, Object)</code> when field is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldMatchCriteria_NullField() {
        try {
            new FieldMatchCriteria(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldMatchCriteria(String, Object)</code> when field is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldMatchCriteria_EmptyField() {
        try {
            new FieldMatchCriteria(" ", VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldMatchCriteria(String, Object)</code> when value is <code>null </code>. No
     * exceptions will be expected.
     * </p>
     */
    public void testFieldMatchCriteria_NullValue() {
        new FieldMatchCriteria(FIELD, null);
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>FieldMatchCriteria(String, Object)</code>.
     * </p>
     */
    public void testFieldMatchCriteria_Accuracy() {
        assertEquals("The field value should be correct.", FIELD,
            V1Dot1TestHelper.getPrivateField(criteria.getClass(), criteria, "field"));
        assertEquals("The value should be correct.", VALUE,
            V1Dot1TestHelper.getPrivateField(criteria.getClass(), criteria, "value"));
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies <code>FieldMatchCriteria</code> subclasses <code>Criteria</code>.
     * </p>
     */
    public void testFieldMatchCriteria_InheritanceAccuracy() {
        assertTrue("FieldMatchCriteria does not subclass Criteria.", this.criteria instanceof Criteria);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getField()</code>.
     * </p>
     */
    public void testGetField_Accuracy() {
        assertEquals("The field value should be correct.", FIELD, this.criteria.getField());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getValue()</code>.
     * </p>
     */
    public void testGetValue_Accuracy() {
        assertEquals("The field value should be correct.", VALUE, this.criteria.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getWhereClause()</code>.
     * </p>
     */
    public void testGetWhereClause_Accuracy() {
        assertEquals("The whereClause should be correct.", FIELD + "=?", this.criteria.getWhereClause());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getParameters()</code>.
     * </p>
     */
    public void testGetParameters_Accuracy() {
        assertEquals("The parameters should be correct.", 1, this.criteria.getParameters().length);
        assertEquals("The parameters should be correct.", VALUE, this.criteria.getParameters()[0]);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getExpenseTypeMatchCriteria(int)</code>.
     * </p>
     */
    public void testGetExpenseTypeMatchCriteria_Accuracy() {
        criteria = FieldMatchCriteria.getExpenseTypeMatchCriteria(1);
        assertEquals("The field value should be correct.", FieldMatchCriteria.EXPENSE_TYPE_FIELD, criteria.getField());
        assertEquals("The value value should be correct.", new Integer(1), criteria.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getExpenseStatusMatchCriteria(int)</code>.
     * </p>
     */
    public void testGetExpenseStatusMatchCriteria_Accuracy() {
        criteria = FieldMatchCriteria.getExpenseStatusMatchCriteria(1);
        assertEquals("The field value should be correct.", FieldMatchCriteria.EXPENSE_STATUS_FIELD,
                criteria.getField());
        assertEquals("The value value should be correct.", new Integer(1), criteria.getValue());
    }

    /**
     * <p>
     * Tests the method of <code>getCreationUserMatchCriteria(String)</code> when user is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetCreationUserMatchCriteria_NullUser() {
        try {
            FieldMatchCriteria.getCreationUserMatchCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getCreationUserMatchCriteria(String)</code> when user is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetCreationUserMatchCriteria_EmptyUser() {
        try {
            FieldMatchCriteria.getCreationUserMatchCriteria(" ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCreationUserMatchCriteria(String)</code>.
     * </p>
     */
    public void testGetCreationUserMatchCriteria_Accuracy() {
        String user = "user";
        criteria = FieldMatchCriteria.getCreationUserMatchCriteria(user);
        assertEquals("The field value should be correct.", FieldMatchCriteria.CREATION_USER_FIELD, criteria.getField());
        assertEquals("The value value should be correct.", user, criteria.getValue());
    }

    /**
     * <p>
     * Tests the method of <code>getModificationUserMatchCriteria(String)</code> when user is <code>null</code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetModificationUserMatchCriteria_NullUser() {
        try {
            FieldMatchCriteria.getModificationUserMatchCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getModificationUserMatchCriteria(String)</code> when user is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetModificationUserMatchCriteria_EmptyUser() {
        try {
            FieldMatchCriteria.getModificationUserMatchCriteria(" ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getModificationUserMatchCriteria(String)</code>.
     * </p>
     */
    public void testGetModificationUserMatchCriteria_Accuracy() {
        String user = "user";
        criteria = FieldMatchCriteria.getModificationUserMatchCriteria(user);
        assertEquals("The field value should be correct.", FieldMatchCriteria.MODIFICATION_USER_FIELD,
            criteria.getField());
        assertEquals("The value value should be correct.", user, criteria.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getBillableMatchCriteria(String)</code>.
     * </p>
     */
    public void testGetBillableMatchCriteria_Accuracy1() {
        criteria = FieldMatchCriteria.getBillableMatchCriteria(true);
        assertEquals("The field value should be correct.", FieldMatchCriteria.BILLABLE_FIELD, criteria.getField());
        assertEquals("The value value should be correct.", new Short((short) 1), criteria.getValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getBillableMatchCriteria(String)</code>.
     * </p>
     */
    public void testGetBillableMatchCriteria_Accuracy2() {
        criteria = FieldMatchCriteria.getBillableMatchCriteria(false);
        assertEquals("The field value should be correct.", FieldMatchCriteria.BILLABLE_FIELD, criteria.getField());
        assertEquals("The value value should be correct.", new Short((short) 0), criteria.getValue());
    }
}
