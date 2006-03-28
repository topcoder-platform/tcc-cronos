/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.expense.failuretests;
import com.cronos.timetracker.entry.expense.search.NotCriteria;

import junit.framework.TestCase;
/**
 * Tests for NotCriteria class.
 * 
 * @author qiucx0161
 * @version 1.0
 */
public class TestNotCriteria extends TestCase {

    /**
     * Tests NotCriteria(Criteria criteria) method with null Criteria
     * IllegalArgumentException should be thrown.
     */
    public void testNotCriteriaNullCriteria() {
        try {
            new NotCriteria(null);
            fail("testNotCriteriaNullCriteria is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testNotCriteriaNullCriteria is failure.");
        }
    }
}
