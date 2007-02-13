/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.failuretests;
import com.cronos.timetracker.entry.expense.search.FieldLikeCriteria;

import junit.framework.TestCase;
/**
 * Tests for FieldLikeCriteria class.
 * 
 * @author qiucx0161
 * @version 1.0
 */
public class TestFieldLikeCriteria extends TestCase {

    /**
     * Tests FieldLikeCriteria(String field, String pattern) method with null Field
     * IllegalArgumentException should be thrown.
     */
    public void testFieldLikeCriteriaNullField() {
        try {
            new FieldLikeCriteria(null, "pattern");
            fail("testFieldLikeCriteriaNullField is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testFieldLikeCriteriaNullField is failure.");
        }
    }
    
    /**
     * Tests FieldLikeCriteria(String field, String pattern) method with empty Field
     * IllegalArgumentException should be thrown.
     */
    public void testFieldLikeCriteriaEmptyField() {
        try {
            new FieldLikeCriteria(" ", "pattern");
            fail("testFieldLikeCriteriaEmptyField is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testFieldLikeCriteriaEmptyField is failure.");
        }
    }

    /**
     * Tests FieldLikeCriteria(String field, String pattern) method with null Pattern
     * IllegalArgumentException should be thrown.
     */
    public void testFieldLikeCriteriaNullPattern() {
        try {
            new FieldLikeCriteria("field", null);
            fail("testFieldLikeCriteriaNullPattern is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testFieldLikeCriteriaNullPattern is failure.");
        }
    }
    
    /**
     * Tests FieldLikeCriteria(String field, String pattern) method with empty Pattern
     * IllegalArgumentException should be thrown.
     */
    public void testFieldLikeCriteriaEmptyPattern() {
        try {
            new FieldLikeCriteria("field", " ");
            fail("testFieldLikeCriteriaEmptyPattern is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testFieldLikeCriteriaEmptyPattern is failure.");
        }
    }

    /**
     * Tests getDescriptionContainsCriteria(String value) method with empty Value
     * IllegalArgumentException should be thrown.
     */
    public void testGetDescriptionContainsCriteriaEmptyValue() {
        try {
            FieldLikeCriteria.getDescriptionContainsCriteria("");
            fail("testGetDescriptionContainsCriteriaEmptyValue is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetDescriptionContainsCriteriaEmptyValue is failure.");
        }
    }

    /**
     * Tests getDescriptionContainsCriteria(String value) method with null Value
     * IllegalArgumentException should be thrown.
     */
    public void testGetDescriptionContainsCriteriaNullValue() {
        try {
            FieldLikeCriteria.getDescriptionContainsCriteria(null);
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetDescriptionContainsCriteriaNullValue is failure.");
        }
    }
}
