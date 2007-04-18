/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.criteria;

import com.topcoder.timetracker.entry.expense.UnitTestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>CompositeCriteria</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class CompositeCriteriaUnitTest extends TestCase {
    /**
     * Represents the criteria instances which will be used to create the new <code>CompositeCriteria</code> instance
     * for testing.
     */
    private Criteria[] criterias = null;

    /** Represents the <code>CompositeCriteria</code> instance which will be used for testing. */
    private CompositeCriteria criteria = null;

    /**
     * <p>
     * Sets up the test environment. The test instance is created.
     * </p>
     */
    protected void setUp() {
        criterias = new Criteria[5];
        criterias[0] = FieldMatchCriteria.getBillableMatchCriteria(true);
        criterias[1] = FieldMatchCriteria.getCreationUserMatchCriteria("creationUser");
        criterias[2] = FieldMatchCriteria.getModificationUserMatchCriteria("modificationUser");
        criterias[3] = FieldMatchCriteria.getExpenseStatusMatchCriteria(1);
        criterias[4] = FieldMatchCriteria.getExpenseTypeMatchCriteria(2);
        criteria = new CompositeCriteria(CompositeCriteria.AND_COMPOSITION, criterias);
    }

    /**
     * <p>
     * Tests constructor <code>CompositeCriteria(String, Criteria[])</code> when compositionKeyword is <code>null
     * </code>. Expect IllegalArgumentException.
     * </p>
     */
    public void testCompositeCriteria_NullCompositionKeyword() {
        try {
            new CompositeCriteria(null, criterias);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>CompositeCriteria(String, Criteria[])</code> when criteria is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testCompositeCriteria_NullCriteria() {
        try {
            new CompositeCriteria(CompositeCriteria.AND_COMPOSITION, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>CompositeCriteria(String, Criteria[])</code> when criteria collection's size is less
     * than 2. Expect IllegalArgumentException.
     * </p>
     */
    public void testCompositeCriteria_CriteriaSizeLessThanTwo() {
        try {
            new CompositeCriteria(CompositeCriteria.AND_COMPOSITION, new Criteria[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests constructor <code>CompositeCriteria(String, Criteria[])</code> when criteria collection contains <code>
     * null</code> element. Expect IllegalArgumentException.
     * </p>
     */
    public void testCompositeCriteria_CriteriaContainsNullElement() {
        try {
            this.criterias[0] = null;
            new CompositeCriteria(CompositeCriteria.AND_COMPOSITION, criterias);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>CompositeCriteria(String, Criteria[])</code>.
     * </p>
     */
    public void testCompositeCriteria_Accuracy() {
        assertEquals("The compositionKeyword value should be correct.", CompositeCriteria.AND_COMPOSITION,
            UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "compositionKeyword"));
        UnitTestHelper.assertArrayEquals(this.criterias,
            (Object[]) UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "criteria"));
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies <code>CompositeCriteria</code> subclasses <code>Criteria</code>.
     * </p>
     */
    public void testCompositeCriteria_InheritanceAccuracy() {
        assertTrue("CompositeCriteria does not subclass Criteria.", this.criteria instanceof Criteria);
    }

    /**
     * <p>
     * Tests the accuracy of constructor <code>CompositeCriteria(String, Criteria[])</code>. The criteria should be a
     * shallow copy.
     * </p>
     */
    public void testCompositeCriteria_ShallowCopyAccuracy() {
        Criteria[] copy = UnitTestHelper.copyCriteriaCollection(this.criterias);

        // change it outside
        this.criterias[0] = null;

        // check it
        UnitTestHelper.assertArrayEquals(copy,
            (Object[]) UnitTestHelper.getPrivateField(criteria.getClass(), criteria, "criteria"));
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCompositionKeyword()</code>.
     * </p>
     */
    public void testGetCompositionKeyword_Accuracy() {
        assertEquals("The compositionKeyword value should be correct.", CompositeCriteria.AND_COMPOSITION,
            this.criteria.getCompositionKeyword());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>.
     * </p>
     */
    public void testGetCriteria_Accuracy() {
        UnitTestHelper.assertArrayEquals(this.criterias, this.criteria.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getCriteria()</code>. The return value should be a shallow copy.
     * </p>
     */
    public void testGetCriteria_ShallowCopyAccuracy() {
        this.criterias = this.criteria.getCriteria();

        Criteria[] save = UnitTestHelper.copyCriteriaCollection(this.criterias);

        // change outside
        this.criterias[0] = null;

        // check
        UnitTestHelper.assertArrayEquals(save, this.criteria.getCriteria());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getWhereClause()</code>.
     * </p>
     */
    public void testGetWhereClause_Accuracy() {
        StringBuffer expected = new StringBuffer();

        for (int i = 0; i < this.criterias.length; i++) {
            if (i != 0) {
                expected.append(CompositeCriteria.AND_COMPOSITION);
            }

            expected.append("(").append(criterias[i].getWhereClause()).append(")");
        }

        assertEquals("The whereClause should be correct.", expected.toString(), this.criteria.getWhereClause());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getParameters()</code>.
     * </p>
     */
    public void testGetParameters_Accuracy() {
        assertEquals("The parameters should be correct.", criterias.length, criteria.getParameters().length);

        for (int i = 0; i < criterias.length; ++i) {
            assertEquals("The parameters should be correct.", criterias[i].getParameters()[0],
                this.criteria.getParameters()[i]);
        }
    }

    /**
     * <p>
     * Tests the method of <code>getAndCompositeCriteria(Criteria, Criteria)</code> when left is <code> null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_NullLeft() {
        try {
            CompositeCriteria.getAndCompositeCriteria(null, criterias[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getAndCompositeCriteria(Criteria, Criteria)</code> when right is <code> null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_NullRight() {
        try {
            CompositeCriteria.getAndCompositeCriteria(criterias[0], null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getAndCompositeCriteria(Criteria, Criteria)</code>.
     * </p>
     */
    public void testGetAndCompositeCriteria_CriteriaCriteriaAccuracy() {
        criteria = CompositeCriteria.getAndCompositeCriteria(criterias[0], criterias[1]);
        assertEquals("The compositionKeyword value should be correct.", CompositeCriteria.AND_COMPOSITION,
            criteria.getCompositionKeyword());
        UnitTestHelper.assertArrayEquals(new Criteria[] {criterias[0], criterias[1]}, criteria.getCriteria());
    }

    /**
     * <p>
     * Tests the method of <code>getAndCompositeCriteria(Criteria[])</code> when criteria is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_NullCriteria() {
        try {
            CompositeCriteria.getAndCompositeCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of  <code>getAndCompositeCriteria(Criteria[])</code> when criteria collection's size is less
     * than 2. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_CriteriaSizeLessThanTwo() {
        try {
            CompositeCriteria.getAndCompositeCriteria(new Criteria[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of  <code>getAndCompositeCriteria(Criteria[])</code> when criteria collection contains <code>
     * null</code> element. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetAndCompositeCriteria_CriteriaContainsNullElement() {
        try {
            this.criterias[0] = null;
            CompositeCriteria.getAndCompositeCriteria(criterias);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getAndCompositeCriteria(Criteria[])</code>.
     * </p>
     */
    public void testGetAndCompositeCriteria_CriteriaAccuracy() {
        criteria = CompositeCriteria.getAndCompositeCriteria(criterias);
        assertEquals("The compositionKeyword value should be correct.", CompositeCriteria.AND_COMPOSITION,
            criteria.getCompositionKeyword());
        UnitTestHelper.assertArrayEquals(this.criterias, criteria.getCriteria());
    }

    /**
     * <p>
     * Tests the method of <code>getOrCompositeCriteria(Criteria, Criteria)</code> when left is <code> null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_NullLeft() {
        try {
            CompositeCriteria.getOrCompositeCriteria(null, criterias[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of <code>getOrCompositeCriteria(Criteria, Criteria)</code> when right is <code> null</code>.
     * Expect IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_NullRight() {
        try {
            CompositeCriteria.getOrCompositeCriteria(criterias[0], null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOrCompositeCriteria(Criteria, Criteria)</code>.
     * </p>
     */
    public void testGetOrCompositeCriteria_CriteriaCriteriaAccuracy() {
        criteria = CompositeCriteria.getOrCompositeCriteria(criterias[0], criterias[1]);
        assertEquals("The compositionKeyword value should be correct.", CompositeCriteria.OR_COMPOSITION,
            criteria.getCompositionKeyword());
        UnitTestHelper.assertArrayEquals(new Criteria[] {criterias[0], criterias[1]}, criteria.getCriteria());
    }

    /**
     * <p>
     * Tests the method of <code>getOrCompositeCriteria(Criteria[])</code> when criteria is <code>null </code>. Expect
     * IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_NullCriteria() {
        try {
            CompositeCriteria.getOrCompositeCriteria(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of  <code>getOrCompositeCriteria(Criteria[])</code> when criteria collection's size is less
     * than 2. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_CriteriaSizeLessThanTwo() {
        try {
            CompositeCriteria.getOrCompositeCriteria(new Criteria[0]);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the method of  <code>getOrCompositeCriteria(Criteria[])</code> when criteria collection contains <code>
     * null</code> element. Expect IllegalArgumentException.
     * </p>
     */
    public void testGetOrCompositeCriteria_CriteriaContainsNullElement() {
        try {
            this.criterias[0] = null;
            CompositeCriteria.getOrCompositeCriteria(criterias);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getOrCompositeCriteria(Criteria[])</code>.
     * </p>
     */
    public void testGetOrCompositeCriteria_CriteriaAccuracy() {
        criteria = CompositeCriteria.getOrCompositeCriteria(criterias);
        assertEquals("The compositionKeyword value should be correct.", CompositeCriteria.OR_COMPOSITION,
            criteria.getCompositionKeyword());
        UnitTestHelper.assertArrayEquals(this.criterias, criteria.getCriteria());
    }
}
