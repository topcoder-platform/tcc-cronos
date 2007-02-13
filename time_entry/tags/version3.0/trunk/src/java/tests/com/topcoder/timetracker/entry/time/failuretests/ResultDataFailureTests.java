/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.ResultData;

/**
 * <p>
 * Failure tests for <code>ResultData</code>s.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
public class ResultDataFailureTests extends TestCase {
    /**
     * ResultData instance for testing.
     */
    private ResultData data;

    /**
     * <p>
     * Set up tests.
     * </p>
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        data = new ResultData();
    }

    /**
     * Failure test for ResultData.setBatchResults(DataObject[]),
     * with null DataObjects, IAE is expected.
     */
    public void testSetBatchResultsNullDataObjects() {
        try {
            data.setBatchResults(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ResultData.setExceptionList(BatchOperationException[]),
     * with null BatchOperationException(s), should be ok.
     */
    public void testSetExceptionListNullBatchOperationExceptions() {
        data.setExceptionList(null);
    }

    /**
     * Failure test for ResultData.setExceptionList(BatchOperationException[]),
     * with BatchOperationException(s) containing null elements, should be ok.
     */
    public void testSetExceptionListContainingNull() {
        data.setExceptionList(new BatchOperationException[] {new BatchOperationException("test")});
    }

    /**
     * Failure test for ResultData.setProperty(String, Object),
     * with null key, IAE is expected.
     */
    public void testSetPropertyNullKey() {
        try {
            data.setProperty(null, new Object());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ResultData.setProperty(String, Object),
     * with null value, IAE is expected.
     */
    public void testSetPropertyNullValue() {
        try {
            data.setProperty("topcoder", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ResultData.getProperty(String),
     * with null key, IAE is expected.
     */
    public void testGetPropertyNullKey() {
        try {
            data.getProperty(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ResultData.setOperations(Object[]),
     * with null operations, IAE is expected.
     */
    public void testSetOperationsNullOperations() {
        try {
            data.setOperations(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ResultData.setOperations(Object[]),
     * with illegal operations, IAE is expected.
     */
    public void testSetOperationsLongOperations() {
        try {
            data.setOperations(new Object[] {new Long(1), new Long(2)});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for ResultData.setOperations(Object[]),
     * with inconsistent operations, IAE is expected.
     */
    public void testSetOperationsInconsistentOperations() {
        try {
            data.setOperations(new Object[] {new Integer(1), new DataObject() {}});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
