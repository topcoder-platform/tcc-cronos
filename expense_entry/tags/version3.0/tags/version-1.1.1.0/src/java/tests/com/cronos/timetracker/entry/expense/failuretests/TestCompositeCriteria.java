/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.failuretests;
import com.cronos.timetracker.entry.expense.search.CompositeCriteria;
import com.cronos.timetracker.entry.expense.search.Criteria;
import com.cronos.timetracker.entry.expense.search.FieldLikeCriteria;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;

import junit.framework.TestCase;
/**
 * Tests for CompositeCriteria class.
 * 
 * @author qiucx0161
 * @version 1.0
 */
public class TestCompositeCriteria extends TestCase {
    
    /**
     * Criteria[] instance used for test.
     */
    private Criteria[] criteria = null;
    
    /**
     * Criteria instance used for test.
     */
    private Criteria like = null;
    private Criteria match = null;
    
    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        like = new FieldLikeCriteria("field", "pattern");
        match = new FieldMatchCriteria("field", "obj");
        criteria = new Criteria[]{like, match};
    }

    /**
     * Tests CompositeCriteria(String compositionKeyword, Criteria[] criteria) method with null CompositionKeyword
     * IllegalArgumentException should be thrown.
     */
    public void testCompositeCriteriaNullCompositionKeyword() {
        try {
            new CompositeCriteria(null, criteria);
            fail("testCompositeCriteriaNullCompositionKeyword is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testCompositeCriteriaNullCompositionKeyword is failure.");
        }
    }

    /**
     * Tests CompositeCriteria(String compositionKeyword, Criteria[] criteria) method with null Criteria[]
     * IllegalArgumentException should be thrown.
     */
    public void testCompositeCriteriaNullCriteria() {
        try {
            new CompositeCriteria("Or", null);
            fail("testCompositeCriteriaNullCriteria is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testCompositeCriteriaNullCriteria is failure.");
        }
    }

    /**
     * Tests GetAndCompositeCriteria(Criteria left, Criteria right) method with null Left
     * IllegalArgumentException should be thrown.
     */
    public void testGetAndCompositeCriteriaNullLeft() {
        try {
            CompositeCriteria.getAndCompositeCriteria(null, match);
            fail("testGetAndCompositeCriteriaNullLeft is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetAndCompositeCriteriaNullLeft is failure.");
        }
    }

    /**
     * Tests GetAndCompositeCriteria(Criteria left, Criteria right) method with null Right
     * IllegalArgumentException should be thrown.
     */
    public void testGetAndCompositeCriteriaNullRight() {
        try {
            CompositeCriteria.getAndCompositeCriteria(match, null);
            fail("testGetAndCompositeCriteriaNullRight is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetAndCompositeCriteriaNullRight is failure.");
        }
    }
    /**
     * Tests GetAndCompositeCriteria(Criteria[] criteria) method with null Criteria
     * IllegalArgumentException should be thrown.
     */
    public void testGetAndCompositeCriteriaNullCriteria() {
        Criteria[] c = new Criteria[]{null, match};
        try {
            CompositeCriteria.getAndCompositeCriteria(c);
            fail("testGetAndCompositeCriteriaNullCriteria is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetAndCompositeCriteriaNullCriteria is failure.");
        }
    }
    /**
     * Tests GetAndCompositeCriteria(Criteria[] criteria) method with invalid Criteria number
     * IllegalArgumentException should be thrown.
     */
    public void testGetAndCompositeCriteriaLessCriteria() {
        Criteria[] c = new Criteria[]{match};
        try {
            CompositeCriteria.getAndCompositeCriteria(c);
            fail("testGetAndCompositeCriteriaLessCriteria is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetAndCompositeCriteriaLessCriteria is failure.");
        }
    }
    
    /**
     * Tests GetOrCompositeCriteria(Criteria left, Criteria right) method with null Left
     * IllegalArgumentException should be thrown.
     */
    public void testGetOrCompositeCriteriaNullLeft() {
        try {
            CompositeCriteria.getOrCompositeCriteria(null, match);
            fail("testGetOrCompositeCriteriaNullLeft is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetOrCompositeCriteriaNullLeft is failure.");
        }
    }

    /**
     * Tests GetOrCompositeCriteria(Criteria left, Criteria right) method with null Right
     * IllegalArgumentException should be thrown.
     */
    public void testGetOrCompositeCriteriaNullRight() {
        try {
            CompositeCriteria.getOrCompositeCriteria(match, null);
            fail("testGetOrCompositeCriteriaNullRight is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetOrCompositeCriteriaNullRight is failure.");
        }
    }
    /**
     * Tests GetOrCompositeCriteria(Criteria[] criteria) method with null Criteria
     * IllegalArgumentException should be thrown.
     */
    public void testGetOrCompositeCriteriaNullCriteria() {
        Criteria[] c = new Criteria[]{null, match};
        try {
            CompositeCriteria.getOrCompositeCriteria(c);
            fail("testGetOrCompositeCriteriaNullCriteria is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetOrCompositeCriteriaNullCriteria is failure.");
        }
    }
    
    /**
     * Tests GetOrCompositeCriteria(Criteria[] criteria) method with invalid Criteria number
     * IllegalArgumentException should be thrown.
     */
    public void testGetOrCompositeCriteriaLessCriteria() {
        Criteria[] c = new Criteria[]{match};
        try {
            CompositeCriteria.getOrCompositeCriteria(c);
            fail("testGetOrCompositeCriteriaLessCriteria is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetOrCompositeCriteriaLessCriteria is failure.");
        }
    }
}
