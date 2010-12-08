/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection;

import junit.framework.TestCase;

import com.topcoder.util.log.Log;

/**
 * Unit tests for <code>{@link Helper}</code> class.
 *
 * @author xianwenchen
 * @version 1.0
 */
public class HelperTests extends TestCase {
    /**
     * The <code>{@link Log}</code> instance used for testing.
     */
    private Log log;

    /**
     * <p>
     * Tests failure of <code>checkState(Object, String, Log, String)</code> method with
     * <code>value</code> is <code>null</code>.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     */
    public void testCheckStateNull() {
        try {
            Helper.checkState(null, "name", log, "name");
            fail("IllegalStateException is expected.");
        } catch (IllegalStateException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>checkState(Object, String, Log, String)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    public void testCheckState() {
        Helper.checkState(new Object(), "name", log, "name");
        // Good
    }

    /**
     * Tests checkNull(String, Object), with non null param. No exception is expected.
     */
    public void testCheckNull() {
        Helper.checkNull(new Object(), "TEST_OBJECT");
    }

    /**
     * Tests checkNull(String, Object), with null param. IllegalArgumentException should be thrown.
     */
    public void testCheckNullWithNullParam() {
        try {
            Helper.checkNull(null, "TEST_OBJECT");
            fail("IllegalArgumentException should be thrown because of the null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>logEntrance(Log, String, String[], Object[])</code>
     * method.
     * </p>
     * <p>
     * Log should be done.
     * </p>
     */
    public void testLogEntrance1() {
        Helper.logEntrance(log, "name", new String[] {"name1"}, new Object[] {"value1"});
        // log to console
    }

    /**
     * <p>
     * Tests accuracy of <code>logEntrance(Log, String, String[], Object[])</code>
     * method.
     * </p>
     * <p>
     * The log is null, no log done.
     * </p>
     */
    public void testLogEntrance2() {
        Helper.logEntrance(null, "name", new String[] {"name1"}, new Object[] {"value1"});
    }

    /**
     * <p>
     * Tests accuracy of <code>logExit(Log, String, Object, long)</code> method.
     * </p>
     * <p>
     * Log should be done.
     * </p>
     */
    public void testLogExit1() {
        Helper.logExit(log, "name", "value", System.currentTimeMillis());
        // log to console
    }

    /**
     * <p>
     * Tests accuracy of <code>logExit(Log, String, Object, long)</code> method.
     * </p>
     * <p>
     * The log is null, no log done.
     * </p>
     */
    public void testLogExit2() {
        Helper.logExit(null, "name", "value", System.currentTimeMillis());
    }

    /**
     * <p>
     * Tests accuracy of <code>logException(Log, String, T)</code> method.
     * </p>
     * <p>
     * Log should be done.
     * </p>
     */
    public void testLogException1() {
        Helper.logException(log, "name", new Exception("message"));
        // log to console
    }

    /**
     * <p>
     * Tests accuracy of <code>logException(Log, String, T)</code> method.
     * </p>
     * <p>
     * The log is null, no log done.
     * </p>
     */
    public void testLogException2() {
        Helper.logException(null, "name", new Exception("message"));
    }
}
