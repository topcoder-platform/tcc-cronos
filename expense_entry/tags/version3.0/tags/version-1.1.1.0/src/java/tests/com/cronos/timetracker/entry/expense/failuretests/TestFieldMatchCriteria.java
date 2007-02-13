/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.failuretests;
import com.cronos.timetracker.entry.expense.search.FieldMatchCriteria;

import junit.framework.TestCase;
/**
 * Tests for FieldMatchCriteria class.
 * 
 * @author qiucx0161
 * @version 1.0
 */
public class TestFieldMatchCriteria extends TestCase {

    /**
     * Tests FieldMatchCriteria(String field, Object value) method with null Field
     * IllegalArgumentException should be thrown.
     */
    public void testFieldMatchCriteriaNullField() {
        try {
            new FieldMatchCriteria(null, "str");
            fail("testFieldMatchCriteriaNullField is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testFieldMatchCriteriaNullField is failure.");
        }
    }

    /**
     * Tests FieldMatchCriteria(String field, Object value) method with empty Field
     * IllegalArgumentException should be thrown.
     */
    public void testFieldMatchCriteriaEmptyField() {
        try {
            new FieldMatchCriteria(" ", "str");
            fail("testFieldMatchCriteriaEmptyField is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testFieldMatchCriteriaEmptyField is failure.");
        }
    }

    /**
     * Tests GetCreationUserMatchCriteria(String user) method with null User
     * IllegalArgumentException should be thrown.
     */
    public void testGetCreationUserMatchCriteriaNullUser() {
        try {
            FieldMatchCriteria.getCreationUserMatchCriteria(null);
            fail("testGetCreationUserMatchCriteriaNullUser is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetCreationUserMatchCriteriaNullUser is failure.");
        }
    }
    
    /**
     * Tests GetCreationUserMatchCriteria(String user) method with empty User
     * IllegalArgumentException should be thrown.
     */
    public void testGetCreationUserMatchCriteriaEmptyUser() {
        try {
            FieldMatchCriteria.getCreationUserMatchCriteria(" ");
            fail("testGetCreationUserMatchCriteriaEmptyUser is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetCreationUserMatchCriteriaEmptyUser is failure.");
        }
    }

    /**
     * Tests GetModificationUserMatchCriteria(String user) method with null User
     * IllegalArgumentException should be thrown.
     */
    public void testGetModificationUserMatchCriteriaNullUser() {
        try {
            FieldMatchCriteria.getModificationUserMatchCriteria(null);
            fail("testGetModificationUserMatchCriteriaNullUser is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetModificationUserMatchCriteriaNullUser is failure.");
        }
    }
    
    /**
     * Tests GetModificationUserMatchCriteria(String user) method with empty User
     * IllegalArgumentException should be thrown.
     */
    public void testGetModificationUserMatchCriteriaEmptyUser() {
        try {
            FieldMatchCriteria.getModificationUserMatchCriteria(" ");
            fail("testGetModificationUserMatchCriteriaEmptyUser is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetModificationUserMatchCriteriaEmptyUser is failure.");
        }
    }
}
