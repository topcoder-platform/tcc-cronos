/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.BaseDAO;
import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.ResultData;

/**
 * <p>
 * Failure tests for BaseDAO.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
public class BaseDAO11FailureTests extends TestCase {
    /**
     * Mock-up BaseDAO for testing.
     */
    private BaseDAO dao;
    
    /**
     * <p>
     * Set up.
     * </p>
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        dao = new MockBaseDAO11("mysql", "com.topcoder.timetracker.entry.time.failuretests");
    }

    /**
     * Failure test for BaseDAO.batchCreate(DataObject[], String, boolean, ResultData),
     * UnsupportedOperationException is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreate() throws Exception {
        try {
            dao.batchCreate(new DataObject[0], "topcoder", true, new ResultData());
            fail("UnsupportedOperationException should be thrown.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    /**
     * Failure test for BaseDAO.batchDelete(int[], boolean, ResultData),
     * UnsupportedOperationException is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchDelete() throws Exception {
        try {
            dao.batchDelete(new int[0], true, new ResultData());
            fail("UnsupportedOperationException should be thrown.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    /**
     * Failure test for BaseDAO.batchUpdate(DataObject[], String, boolean, ResultData),
     * UnsupportedOperationException is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdate() throws Exception {
        try {
            dao.batchUpdate(new DataObject[0], "topcoder", true, new ResultData());
            fail("UnsupportedOperationException should be thrown.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    /**
     * Failure test for BaseDAO.batchRead(int[], boolean, ResultData),
     * UnsupportedOperationException is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchRead() throws Exception {
        try {
            dao.batchRead(new int[0], true, new ResultData());
            fail("UnsupportedOperationException should be thrown.");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

}
