/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.failuretests;

import junit.framework.TestCase;

import com.cronos.timetracker.entry.time.AsynchBatchDAOWrapper;
import com.cronos.timetracker.entry.time.DataObject;

/**
 * <p>
 * Failure tests for AsynchBatchDAOWrapper.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
public class AsynchBatchDAOWrapperFailureTests extends TestCase {
    /**
     * AsynchBatchDAOWrapper instance for testing.
     */
    private AsynchBatchDAOWrapper wrapper;

    /**
     * <p>
     * Set up tests.
     * </p>
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        wrapper = new AsynchBatchDAOWrapper(new MockBatchDAO11());
    }

    /**
     * Failure test for AsynchBatchDAOWrapper.AsynchBatchDAOWrapper(BatchDAO),
     * with null BatchDAO, IAE is expected.
     * 
     */
    public void testAsynchBatchDAOWrapperNullBatchDAO() {
        try {
            new AsynchBatchDAOWrapper(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultListener),
     * with null DataObjects, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullDataObjects() throws Exception {
        try {
            wrapper.batchCreate(null, "topcoder", true, new MockResultListener11());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultListener),
     * with null DataObject in the array, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullDataObject() throws Exception {
        try {
            wrapper.batchCreate(new DataObject[2], "topcoder", true, new MockResultListener11());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultListener),
     * with null user, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullUser() throws Exception {
        try {
            wrapper.batchCreate(new DataObject[] {new DataObject() {}}, null, true, new MockResultListener11());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultListener),
     * with null ResultListener, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullResultListener() throws Exception {
        try {
            wrapper.batchCreate(new DataObject[] {new DataObject() {}}, "topcoder", true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultListener),
     * with legal inputs, should be ok.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchCreate() throws Exception {
        try {
            wrapper.batchCreate(new DataObject[] {new DataObject() {}}, "topcoder", true,new MockResultListener11());
            // expected
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        }
    }

    /**
     * Failure test for batchDelete(int[], boolean, ResultListener),
     * with null idList, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchDeleteNullIdList() throws Exception {
        try {
            wrapper.batchDelete(null, true, new MockResultListener11());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchDelete(int[], boolean, ResultListener),
     * with null ResultListener, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchDeleteNullResultListener() throws Exception {
        try {
            wrapper.batchDelete(new int[] {1, 2}, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchDelete(int[], boolean, ResultListener),
     * with legal inputs, should be ok.
     */
    public void testBatchDelete() {
        try {
            wrapper.batchDelete(new int[] {1, 2}, true, new MockResultListener11());
            // expected
        } catch (Exception e) {
            fail("Exception should not be thrown.");
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultListener),
     * with null DataObjects, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullDataObjects() throws Exception {
        try {
            wrapper.batchUpdate(null, "topcoder", true, new MockResultListener11());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultListener),
     * with null DataObject in the array, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullDataObject() throws Exception {
        try {
            wrapper.batchUpdate(new DataObject[2], "topcoder", true, new MockResultListener11());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultListener),
     * with null user , IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullUser() throws Exception {
        try {
            wrapper.batchUpdate(new DataObject[] {new DataObject() {}}, null, true, new MockResultListener11());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultListener),
     * with null ResultListener , IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullResultListener() throws Exception {
        try {
            wrapper.batchUpdate(new DataObject[] {new DataObject() {}}, "topcoder", true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultListener),
     * with legal inputs, should be ok.
     */
    public void testBatchUpdate() {
        try {
            wrapper.batchUpdate(new DataObject[] {new DataObject() {}}, "topcoder", true, new MockResultListener11());
            // expected
        } catch (Exception e) {
            fail("Exception should not be thrown.");
        }
    }

    /**
     * Failure test for batchRead(int[], boolean, ResultListener),
     * with null idList, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchReadNullIdList() throws Exception {
        try {
            wrapper.batchRead(null, true, new MockResultListener11());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchRead(int[], boolean, ResultListener),
     * with null idList, IAE is expected.
     * 
     * @throws Exception to JUnit
     */
    public void testBatchReadNullResultListener() throws Exception {
        try {
            wrapper.batchRead(new int[] {1, 2}, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchRead(int[], boolean, ResultListener),
     * with legal inputs, should be ok.
     */
    public void testBatchRead() {
        try {
            wrapper.batchRead(new int[] {1, 2}, true, new MockResultListener11());
            // expected
        } catch (Exception e) {
            fail("Exception should not be thrown.");
        }
    }

}
