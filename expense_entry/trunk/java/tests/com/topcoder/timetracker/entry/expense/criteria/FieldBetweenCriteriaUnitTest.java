/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.criteria;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.expense.UnitTestHelper;

/**
 * <p>
 * Tests functionality and error cases of <code>FieldBetweenCriteria</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class FieldBetweenCriteriaUnitTest extends TestCase {
    /**
     * Represents the field which will be used to create the new <code>FieldBetweenCriteria</code> instance for
     * testing.
     */
    private static final String FIELD = "field";

    /**
     * Represents the from value which will be used to create the new <code>FieldBetweenCriteria</code> instance for
     * testing.
     */
    private static final String FROM_VALUE = "fromValue";

    /**
     * Represents the to value which will be used to create the new <code>FieldBetweenCriteria</code> instance for
     * testing.
     */
    private static final String TO_VALUE = "toValue";

    /** Represents the <code>FieldBetweenCriteria</code> instance which will be used for testing. */
    private FieldBetweenCriteria criteria = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        criteria = new FieldBetweenCriteria(FIELD, FROM_VALUE, TO_VALUE);
    }

    /**
     * <p>
     * Tests constructor <code>FieldBetweenCriteria(String, Object, Object)</code> when field is <code>null </code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testFieldBetweenCriteria_NullField() {
        try {
            new FieldBetweenCriteria(null, FROM_VALUE, TO_VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldBetweenCriteria(String, Object, Object)</code> when field is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldBetweenCriteria_EmptyField() {
        try {
            new FieldBetweenCriteria(" ", FROM_VALUE, TO_VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldBetweenCriteria(String, Object, Object)</code> when fromValue is <code>null
     * </code>. No exception will be expected.
     * </p>
     */
    public void testFieldBetweenCriteria_NullFromValue() {
        criteria = new FieldBetweenCriteria(FIELD, null, TO_VALUE);
        assertEquals("The field value should be correct.", FIELD,
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "field"));
        assertNull("The from value should be correct.",
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "fromValue"));
        assertEquals("The to value should be correct.", TO_VALUE,
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "toValue"));
    }

    /**
     * <p>
     * Tests constructor <code>FieldBetweenCriteria(String, Object, Object)</code> when toValue is <code>null </code>.
     * No exception will be expected.
     * </p>
     */
    public void testFieldBetweenCriteria_NullToValue() {
        criteria = new FieldBetweenCriteria(FIELD, FROM_VALUE, null);
        assertEquals("The field value should be correct.", FIELD,
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "field"));
        assertEquals("The from value should be correct.", FROM_VALUE,
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "fromValue"));
        assertNull("The to value should be correct.",
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "toValue"));
    }

    /**
     * <p>
     * Tests constructor <code>FieldBetweenCriteria(String, Object, Object)</code> when both the fromValue and toValue
     * are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testFieldBetweenCriteria_NullFromValueNullToValue() {
        try {
            new FieldBetweenCriteria(FIELD, null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>FieldBetweenCriteria(String, Object, Object)</code>.
     * </p>
     */
    public void testFieldBetweenCriteria_Accuracy() {
        assertEquals("The field value should be correct.", FIELD,
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "field"));
        assertEquals("The from value should be correct.", FROM_VALUE,
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "fromValue"));
        assertEquals("The to value should be correct.", TO_VALUE,
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "toValue"));
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies <code>FieldBetweenCriteria</code> subclasses <code>Criteria</code>.
     * </p>
     */
    public void testFieldBetweenCriteria_InheritanceAccuracy() {
        assertTrue("FieldBetweenCriteria does not subclass Criteria.", this.criteria instanceof Criteria);
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
     * Tests the accuracy of method <code>getFromValue()</code>.
     * </p>
     */
    public void testGetFromValue_Accuracy() {
        assertEquals("The from value should be correct.", FROM_VALUE, this.criteria.getFromValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getToValue()</code>.
     * </p>
     */
    public void testGetToValue_Accuracy() {
        assertEquals("The to value should be correct.", TO_VALUE, this.criteria.getToValue());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getWhereClause()</code>. Both the from value and to value exist.
     * </p>
     */
    public void testGetWhereClause_Accuracy1() {
        assertEquals("The whereClause should be correct.", FIELD + " between ? and ?", this.criteria.getWhereClause());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getWhereClause()</code>. Only the from value exists.
     * </p>
     */
    public void testGetWhereClause_Accuracy2() {
        criteria = new FieldBetweenCriteria(FIELD, FROM_VALUE, null);
        assertEquals("The whereClause should be correct.", FIELD + " >= ?", this.criteria.getWhereClause());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getWhereClause()</code>. Only the to value exists.
     * </p>
     */
    public void testGetWhereClause_Accuracy3() {
        criteria = new FieldBetweenCriteria(FIELD, null, TO_VALUE);
        assertEquals("The whereClause should be correct.", FIELD + " <= ?", this.criteria.getWhereClause());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getParameters()</code>. Both the from value and to value exist.
     * </p>
     */
    public void testGetParameters_Accuracy1() {
        assertEquals("The parameters should be correct.", 2, this.criteria.getParameters().length);
        assertEquals("The parameters should be correct.", FROM_VALUE, this.criteria.getParameters()[0]);
        assertEquals("The parameters should be correct.", TO_VALUE, this.criteria.getParameters()[1]);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getParameters()</code>. Only the from value exists.
     * </p>
     */
    public void testGetParameters_Accuracy2() {
        criteria = new FieldBetweenCriteria(FIELD, FROM_VALUE, null);
        assertEquals("The parameters should be correct.", 1, this.criteria.getParameters().length);
        assertEquals("The parameters should be correct.", FROM_VALUE, this.criteria.getParameters()[0]);
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getParameters()</code>. Only the to value exists.
     * </p>
     */
    public void testGetParameters_Accuracy3() {
        criteria = new FieldBetweenCriteria(FIELD, null, TO_VALUE);
        assertEquals("The parameters should be correct.", 1, this.criteria.getParameters().length);
        assertEquals("The parameters should be correct.", TO_VALUE, this.criteria.getParameters()[0]);
    }

    /**
     * <p>
     * Tests the method of <code>getAmountBetweenCriteria(BigDecimal, BigDecimal)</code> when fromValue is
     * <code>null </code>. No exception will be expected.
     * </p>
     */
    public void testGetAmountBetweenCriteria_NullFromValue() {
        BigDecimal toValue = new BigDecimal("1");
        criteria = FieldBetweenCriteria.getAmountBetweenCriteria(null, toValue);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.AMOUNT_FIELD, criteria.getField());
        assertNull("The from value should be correct.", criteria.getFromValue());
        assertEquals("The to value should be correct.", toValue, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getAmountBetweenCriteria(BigDecimal, BigDecimal)</code> when toValue is <code>null
     * </code>. No exception will be expected.
     * </p>
     */
    public void testGetAmountBetweenCriteria_NullToValue() {
        BigDecimal fromValue = new BigDecimal("1");
        criteria = FieldBetweenCriteria.getAmountBetweenCriteria(fromValue, null);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.AMOUNT_FIELD, criteria.getField());
        assertEquals("The from value should be correct.", fromValue, criteria.getFromValue());
        assertNull("The to value should be correct.", criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getAmountBetweenCriteria(BigDecimal, BigDecimal)</code> when both the fromValue and
     * toValue are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAmountBetweenCriteria_NullFromValueNullToValue() {
        try {
            FieldBetweenCriteria.getAmountBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getAmountBetweenCriteria(BigDecimal, BigDecimal)</code>.
     * </p>
     */
    public void testGetAmountBetweenCriteria_Accuracy() {
        BigDecimal fromValue = new BigDecimal("1");
        BigDecimal toValue = new BigDecimal("2");
        criteria = FieldBetweenCriteria.getAmountBetweenCriteria(fromValue, toValue);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.AMOUNT_FIELD, criteria.getField());
        assertEquals("The from value should be correct.", fromValue, criteria.getFromValue());
        assertEquals("The to value should be correct.", toValue, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getCreationDateBetweenCriteria(Date, Date)</code> when fromDate is <code>null </code>.
     * No exception will be expected.
     * </p>
     */
    public void testGetCreationDateBetweenCriteria_NullFromDate() {
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getCreationDateBetweenCriteria(null, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.CREATION_DATE_FIELD,
                criteria.getField());
        assertNull("The from value should be correct.", criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getCreationDateBetweenCriteria(Date, Date)</code> when toDate is <code>null </code>.
     * No exception will be expected.
     * </p>
     */
    public void testGetCreationDateBetweenCriteria_NullToDate() {
        Date fromDate = new Date();
        criteria = FieldBetweenCriteria.getCreationDateBetweenCriteria(fromDate, null);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.CREATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertNull("The to value should be correct.", criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getCreationDateBetweenCriteria(Date, Date)</code> when both the fromDate and toDate
     * are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetCreationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getCreationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCreationDateBetweenCriteria(Date, Date)</code>.
     * </p>
     */
    public void testGetCreationDateBetweenCriteria_Accuracy() {
        Date fromDate = new Date();
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getCreationDateBetweenCriteria(fromDate, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.CREATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationDateBetweenCriteria(Date, Date)</code>
     * when fromDate is <code>null</code>. No exception will be expected.
     * </p>
     */
    public void testGetExpenseStatusCreationDateBetweenCriteria_NullFromDate() {
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseStatusCreationDateBetweenCriteria(null, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_STATUS_CREATION_DATE_FIELD,
                criteria.getField());
        assertNull("The from value should be correct.", criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationDateBetweenCriteria(Date, Date)</code>
     * when toDate is <code>null</code>. No exception will be expected.
     * </p>
     */
    public void testGetExpenseStatusCreationDateBetweenCriteria_NullToDate() {
        Date fromDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseStatusCreationDateBetweenCriteria(fromDate, null);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_STATUS_CREATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertNull("The to value should be correct.", criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationDateBetweenCriteria(Date, Date)</code> when both
     * the fromDate and toDate are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusCreationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getExpenseStatusCreationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getExpenseStatusCreationDateBetweenCriteria(Date, Date)</code>.
     * </p>
     */
    public void testGetExpenseStatusCreationDateBetweenCriteria_Accuracy() {
        Date fromDate = new Date();
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseStatusCreationDateBetweenCriteria(fromDate, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_STATUS_CREATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusModificationDateBetweenCriteria(Date, Date)</code>
     * when fromDate is <code>null </code>.
     * No exception will be expected.
     * </p>
     */
    public void testGetExpenseStatusModificationDateBetweenCriteria_NullFromDate() {
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseStatusModificationDateBetweenCriteria(null, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_STATUS_MODIFICATION_DATE_FIELD,
                criteria.getField());
        assertNull("The from value should be correct.", criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusModificationDateBetweenCriteria(Date, Date)</code>
     * when toDate is <code>null </code>.
     * No exception will be expected.
     * </p>
     */
    public void testGetExpenseStatusModificationDateBetweenCriteria_NullToDate() {
        Date fromDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseStatusModificationDateBetweenCriteria(fromDate, null);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_STATUS_MODIFICATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertNull("The to value should be correct.", criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusModificationDateBetweenCriteria(Date, Date)</code>
     * when both the fromDate and toDate
     * are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseStatusModificationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getExpenseStatusModificationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getExpenseStatusModificationDateBetweenCriteria(Date, Date)</code>.
     * </p>
     */
    public void testGetExpenseStatusModificationDateBetweenCriteria_Accuracy() {
        Date fromDate = new Date();
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseStatusModificationDateBetweenCriteria(fromDate, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_STATUS_MODIFICATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationDateBetweenCriteria(Date, Date)</code>
     * when fromDate is <code>null</code>. No exception will be expected.
     * </p>
     */
    public void testGetExpenseTypeCreationDateBetweenCriteria_NullFromDate() {
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseTypeCreationDateBetweenCriteria(null, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_TYPE_CREATION_DATE_FIELD,
                criteria.getField());
        assertNull("The from value should be correct.", criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationDateBetweenCriteria(Date, Date)</code>
     * when toDate is <code>null</code>. No exception will be expected.
     * </p>
     */
    public void testGetExpenseTypeCreationDateBetweenCriteria_NullToDate() {
        Date fromDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseTypeCreationDateBetweenCriteria(fromDate, null);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_TYPE_CREATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertNull("The to value should be correct.", criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseStatusCreationDateBetweenCriteria(Date, Date)</code>
     * when both the fromDate and toDate are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeCreationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getExpenseTypeCreationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getExpenseTypeCreationDateBetweenCriteria(Date, Date)</code>.
     * </p>
     */
    public void testGetExpenseTypeCreationDateBetweenCriteria_Accuracy() {
        Date fromDate = new Date();
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseTypeCreationDateBetweenCriteria(fromDate, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_TYPE_CREATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseTypeModificationDateBetweenCriteria(Date, Date)</code>
     * when fromDate is <code>null </code>.
     * No exception will be expected.
     * </p>
     */
    public void testGetExpenseTypeModificationDateBetweenCriteria_NullFromDate() {
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseTypeModificationDateBetweenCriteria(null, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_TYPE_MODIFICATION_DATE_FIELD,
                criteria.getField());
        assertNull("The from value should be correct.", criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseTypeModificationDateBetweenCriteria(Date, Date)</code>
     * when toDate is <code>null </code>.
     * No exception will be expected.
     * </p>
     */
    public void testGetExpenseTypeModificationDateBetweenCriteria_NullToDate() {
        Date fromDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseTypeModificationDateBetweenCriteria(fromDate, null);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_TYPE_MODIFICATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertNull("The to value should be correct.", criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getExpenseTypeModificationDateBetweenCriteria(Date, Date)</code>
     * when both the fromDate and toDate
     * are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetExpenseTypeModificationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getExpenseTypeModificationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getExpenseTypeModificationDateBetweenCriteria(Date, Date)</code>.
     * </p>
     */
    public void testGetExpenseTypeModificationDateBetweenCriteria_Accuracy() {
        Date fromDate = new Date();
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getExpenseTypeModificationDateBetweenCriteria(fromDate, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.EXPENSE_TYPE_MODIFICATION_DATE_FIELD,
                criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getModificationDateBetweenCriteria(Date, Date)</code> when fromDate is <code>null
     * </code>. No exception will be expected.
     * </p>
     */
    public void testGetModificationDateBetweenCriteria_NullFromDate() {
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getModificationDateBetweenCriteria(null, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.MODIFICATION_DATE_FIELD,
            criteria.getField());
        assertNull("The from value should be correct.", criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getModificationDateBetweenCriteria(Date, Date)</code> when toDate is <code>null
     * </code>. No exception will be expected.
     * </p>
     */
    public void testGetModificationDateBetweenCriteria_NullToDate() {
        Date fromDate = new Date();
        criteria = FieldBetweenCriteria.getModificationDateBetweenCriteria(fromDate, null);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.MODIFICATION_DATE_FIELD,
            criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertNull("The to value should be correct.", criteria.getToValue());
    }

    /**
     * <p>
     * Tests the method of <code>getModificationDateBetweenCriteria(Date, Date)</code> when both the fromDate and
     * toDate are<code>null </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetModificationDateBetweenCriteria_NullFromDateNullToDate() {
        try {
            FieldBetweenCriteria.getModificationDateBetweenCriteria(null, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getModificationDateBetweenCriteria(Date, Date)</code>.
     * </p>
     */
    public void testGetModificationDateBetweenCriteria_Accuracy() {
        Date fromDate = new Date();
        Date toDate = new Date();
        criteria = FieldBetweenCriteria.getModificationDateBetweenCriteria(fromDate, toDate);
        assertEquals("The field value should be correct.", FieldBetweenCriteria.MODIFICATION_DATE_FIELD,
            criteria.getField());
        assertEquals("The from value should be correct.", fromDate, criteria.getFromValue());
        assertEquals("The to value should be correct.", toDate, criteria.getToValue());
    }
}
