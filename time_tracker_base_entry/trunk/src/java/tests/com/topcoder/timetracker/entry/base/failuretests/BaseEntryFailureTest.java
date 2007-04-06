/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.timetracker.entry.base.BaseEntry;

import junit.framework.TestCase;

/**
 * <p>
 * The failure test for <code>BaseEntry</code>.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class BaseEntryFailureTest extends TestCase {
    
    /**
     * The BaseEntry instance used for failure test.
     */
    private BaseEntry entry;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        entry = new MockBaseEntry();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        entry = null;
    }

    /**
     * <p>
     * Failure test for setDate(Date date).
     * </p>
     *
     * <p>
     * date is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetDateFailure1() throws Exception {
        try {
            entry.setDate(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for setDescription(String description).
     * </p>
     *
     * <p>
     * description is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetDescription1() throws Exception {
        try {
            entry.setDescription(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for setRejectReasons(Map rejectReasons).
     * </p>
     *
     * <p>
     * rejectReasons is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetRejectReasons1() throws Exception {
        try {
            entry.setRejectReasons(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
    
    /**
     * <p>
     * Failure test for setRejectReasons(Map rejectReasons).
     * </p>
     *
     * <p>
     * rejectReasons is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetRejectReasons2() throws Exception {
        Map map = new HashMap();
        map.put("str", new Long(1));

        try {
            entry.setRejectReasons(map);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }
    
    /**
     * <p>
     * Failure test for setRejectReasons(Map rejectReasons).
     * </p>
     *
     * <p>
     * rejectReasons is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetRejectReasons3() throws Exception {
        Map map = new HashMap();
        map.put(null, new Long(1));

        try {
            entry.setRejectReasons(map);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }
    
    /**
     * <p>
     * Failure test for setRejectReasons(Map rejectReasons).
     * </p>
     *
     * <p>
     * rejectReasons is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSetRejectReasons4() throws Exception {
        Map map = new HashMap();
        map.put("str", null);

        try {
            entry.setRejectReasons(map);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * <p>
     * This class is mock class of BaseEntry for failure test.
     * </p>
     *
     * @author Hacker_QC
     * @version 1.0
     */
    private class MockBaseEntry extends BaseEntry {
    }
}
