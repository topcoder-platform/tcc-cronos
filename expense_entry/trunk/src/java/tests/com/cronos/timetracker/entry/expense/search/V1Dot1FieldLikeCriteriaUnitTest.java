/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.search;

import junit.framework.TestCase;

import com.cronos.timetracker.entry.expense.V1Dot1TestHelper;


/**
 * <p>
 * Tests functionality and error cases of <code>FieldLikeCriteria</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1FieldLikeCriteriaUnitTest extends TestCase {
    /** Represents the field which will be used to create the new <code>FieldLikeCriteria</code>
     * instance for testing. */
    private static final String FIELD = "field";

    /**
     * Represents the pattern which will be used to create the new <code>FieldLikeCriteria</code> instance for testing.
     */
    private static final String PATTERN = "pattern";

    /** Represents the <code>FieldLikeCriteria</code> instance which will be used for testing. */
    private FieldLikeCriteria criteria = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        criteria = new FieldLikeCriteria(FIELD, PATTERN);
    }

    /**
     * <p>
     * Tests constructor <code>FieldLikeCriteria(String, String)</code> when field is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldLikeCriteria_NullField() {
        try {
            new FieldLikeCriteria(null, PATTERN);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldLikeCriteria(String, String)</code> when pattern is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldLikeCriteria_NullPattern() {
        try {
            new FieldLikeCriteria(FIELD, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldLikeCriteria(String, String)</code> when field is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldLikeCriteria_EmptyField() {
        try {
            new FieldLikeCriteria(" ", PATTERN);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>FieldLikeCriteria(String, String)</code> when pattern is empty. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testFieldLikeCriteria_EmptyPattern() {
        try {
            new FieldLikeCriteria(FIELD, " ");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>FieldLikeCriteria(String, String)</code>.
     * </p>
     */
    public void testFieldLikeCriteria_Accuracy() {
        assertEquals("The field value should be correct.", FIELD,
            V1Dot1TestHelper.getPrivateField(criteria.getClass(), criteria, "field"));
        assertEquals("The pattern value should be correct.", PATTERN,
            V1Dot1TestHelper.getPrivateField(criteria.getClass(), criteria, "pattern"));
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies <code>FieldLikeCriteria</code> subclasses <code>Criteria</code>.
     * </p>
     */
    public void testFieldLikeCriteria_InheritanceAccuracy() {
        assertTrue("FieldLikeCriteria does not subclass Criteria.", this.criteria instanceof Criteria);
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
     * Tests the accuracy of method <code>getPattern()</code>.
     * </p>
     */
    public void testGetPattern_Accuracy() {
        assertEquals("The pattern value should be correct.", PATTERN, this.criteria.getPattern());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getWhereClause()</code>.
     * </p>
     */
    public void testGetWhereClause_Accuracy() {
        assertEquals("The whereClause should be correct.", FIELD + " like ?", this.criteria.getWhereClause());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getParameters()</code>.
     * </p>
     */
    public void testGetParameters_Accuracy() {
        assertEquals("The parameters should be correct.", 1, this.criteria.getParameters().length);
        assertEquals("The parameters should be correct.", PATTERN, this.criteria.getParameters()[0]);
    }

    /**
     * <p>
     * Tests the method <code>getDescriptionContainsCriteria(String)</code> when value is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetDescriptionContainsCriteria_NullValue() {
        try {
            FieldLikeCriteria.getDescriptionContainsCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>getDescriptionContainsCriteria(String)</code> when value is length of zero. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetDescriptionContainsCriteria_ZeroLengthValue() {
        try {
            FieldLikeCriteria.getDescriptionContainsCriteria("");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method <code>getDescriptionContainsCriteria(String)</code> when value is all spaces. No exceptions are
     * expected.
     * </p>
     */
    public void testGetDescriptionContainsCriteria_AllSpacesValue() {
        FieldLikeCriteria.getDescriptionContainsCriteria("  ");
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getDescriptionContainsCriteria(String)</code>.
     * </p>
     */
    public void testGetDescriptionContainsCriteria_Accuracy() {
        criteria = FieldLikeCriteria.getDescriptionContainsCriteria("value");
        assertEquals("The field value should be correct.", FieldLikeCriteria.DESCRIPTION_FIELD, criteria.getField());
        assertEquals("The pattern value should be correct.", "%value%", criteria.getPattern());
    }
}
