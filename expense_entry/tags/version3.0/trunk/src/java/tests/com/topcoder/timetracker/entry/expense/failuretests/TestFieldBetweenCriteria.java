/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;
import com.topcoder.timetracker.entry.expense.search.FieldBetweenCriteria;

import junit.framework.TestCase;
/**
 * Tests for FieldBetweenCriteria class.
 * @author qiucx0161
 * @version 1.0
 */
public class TestFieldBetweenCriteria extends TestCase {

    /**
     * Tests FieldBetweenCriteria(String field, Object fromValue, Object toValue) method with null Field
     * IllegalArgumentException should be thrown.
     */
    public void testFieldBetweenCriteriaNullField() {
        try {
            new FieldBetweenCriteria(null, "ob1", "ob2");
            fail("testFieldBetweenCriteriaNullField is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testFieldBetweenCriteriaNullField is failure.");
        }
    }

    /**
     * Tests FieldBetweenCriteria(String field, Object fromValue, Object toValue) method with null FromValue
     * and toValue, IllegalArgumentException should be thrown.
     */
    public void testFieldBetweenCriteriaNullParam() {
        try {
            new FieldBetweenCriteria("field", null, null);
            fail("testFieldBetweenCriteriaNullParam is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testFieldBetweenCriteriaNullParam is failure.");
        }
    }

    /**
     * Tests GetAmountBetweenCriteria(BigDecimal fromValue, BigDecimal toValue) method with null FromValue
     * and IllegalArgumentException should be thrown.
     */
    public void testGetAmountBetweenCriteriaNullParam() {
        try {
            FieldBetweenCriteria.getAmountBetweenCriteria(null, null);
            fail("testGetAmountBetweenCriteriaNullParam is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetAmountBetweenCriteriaNullParam is failure.");
        }
    }
    
    /**
     * Tests GetCreationDateBetweenCriteria(Date fromDate, Date toDate) method with null FromDate
     * IllegalArgumentException should be thrown.
     */
    public void testGetCreationDateBetweenCriteriaNullParam() {
        try {
            FieldBetweenCriteria.getCreationDateBetweenCriteria(null, null);
            fail("testGetCreationDateBetweenCriteriaNullFromDate is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetCreationDateBetweenCriteriaNullFromDate is failure.");
        }
    }

    /**
     * Tests GetModificationDateBetweenCriteria(Date fromDate, Date toDate) method with null FromDate
     * and toDate, IllegalArgumentException should be thrown.
     */
    public void testGetModificationDateBetweenCriteriaNullParam() {
        try {
            FieldBetweenCriteria.getModificationDateBetweenCriteria(null, null);
            fail("testGetModificationDateBetweenCriteriaNullFromDate is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("testGetModificationDateBetweenCriteriaNullFromDate is failure.");
        }
    }
}
