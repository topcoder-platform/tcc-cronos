/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import junit.framework.TestCase;

import com.topcoder.util.log.Level;

/**
 * Unit tests for <code>Helper</code> class.
 *
 * @author nowind_lee
 * @version 1.0
 *
 */
public class HelperTest extends TestCase {
    /**
     * Represents the method signature used in tests.
     */
    private static final String METHOD = "cls.method";

    /**
     * Represents the <code>Log</code> instance used in tests.
     */
    private MockLog log;

    /**
     * Sets up the fixture. This method is called before a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void setUp() throws Exception {
        log = new MockLog();
    }

    /**
     * Tears down the fixture. This method is called after a test is executed.
     *
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void tearDown() throws Exception {
        log = null;
    }

    /**
     * Accuracy test for {@link Helper#notNull(Object, String)}.
     *
     * Passes null, {@link IllegalArgumentException} will be caught.
     *
     * @throws Exception
     *             to jUnit, to indicates an error.
     */
    public void testNotNull_Accuracy1() throws Exception {
        try {
            Helper.notNull(null, "testName", log, METHOD);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Accuracy test for {@link Helper#notNull(Object, String)}.
     *
     * Other arguments, such as empty/blank/non-blank strings, no exception throws.
     *
     * @throws Exception
     *             to jUnit, to indicates an error.
     */
    public void testNotNull_Accuracy2() throws Exception {
        Helper.notNull("", "testName", log, METHOD);
        Helper.notNull("  ", "testName", log, METHOD);
        Helper.notNull("test", "testName", log, METHOD);
    }

    /**
     * Accuracy test for <code>checkConfigNull(Object field, String fieldName)</code>.
     */
    public void testCheckConfigNull_Accuracy() {
        Helper.checkConfigNull("a string", "fieldName", log, METHOD);
        // empty string is not null
        Helper.checkConfigNull("", "fieldName", log, METHOD);
        // no exception throws
    }

    /**
     * Failure test for method <code>checkConfigNull(Object field, String fieldName)</code>.
     *
     * If the field value is null, <code>CheckProfileCompletenessConfigurationException</code> will
     * be thrown
     */
    public void testCheckConfigNull_Failure_CheckProfileCompletenessConfigurationException() {
        try {
            Helper.checkConfigNull(null, "fieldName", log, METHOD);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Accuracy test for <code>notNull(Object arg, String argName)</code>.
     */
    public void testNotNull_Accuracy() {
        Helper.notNull("a string", "fieldName", log, METHOD);
        Helper.notNull("", "fieldName", log, METHOD);
    }

    /**
     * Failure test for method <code>notNull(Object arg, String argName)</code>.
     *
     * If argument is null, <code>IllegalArgumentException</code> will be thrown
     */
    public void testNotNull_Failure_IllegalArgumentException() {
        try {
            Helper.notNull(null, "fieldName", log, METHOD);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Accuracy test for <code>checkConfigNullEmpty(String field, String fieldName)</code>.
     */
    public void testCheckConfigNullEmpty_Accuracy() {
        Helper.checkConfigNullEmpty("non-null-non-empty", "fieldName", log, METHOD);
        // no exception thrown
    }

    /**
     * Failure test for method <code>checkConfigNullEmpty(String field, String fieldName)</code>.
     *
     * If the field value if null or empty,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckConfigNullEmpty_Failure_CheckProfileCompletenessConfigurationException1() {
        try {
            Helper.checkConfigNullEmpty(null, "fieldName", log, METHOD);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkConfigNullEmpty(String field, String fieldName)</code>.
     *
     * If the field value if null or empty,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckConfigNullEmpty_Failure_CheckProfileCompletenessConfigurationException2() {
        try {
            Helper.checkConfigNullEmpty("", "fieldName", log, METHOD);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Failure test for method <code>checkConfigNullEmpty(String field, String fieldName)</code>.
     *
     * If the field value if null or empty,
     * <code>CheckProfileCompletenessConfigurationException</code> will be thrown
     */
    public void testCheckConfigNullEmpty_Failure_CheckProfileCompletenessConfigurationException3() {
        try {
            Helper.checkConfigNullEmpty("  ", "fieldName", log, METHOD);
            fail("CheckProfileCompletenessConfigurationException expected.");
        } catch (CheckProfileCompletenessConfigurationException e) {
            // expected
            System.out.println(e.toString());
        }
    }

    /**
     * Accuracy test for <code>logEntry(Log log, String method)</code>.
     *
     * Entry message should be logged is Level.DEBUG is enabled.
     */
    public void testLogEntry1_Accuracy1() {
        Helper.logEntry(log, "a.b");
        assertEquals("[Entering method {a.b}]", log.getLogMessages().trim());
    }

    /**
     * Accuracy test for <code>logEntry(Log log, String method)</code>.
     *
     * No message should be logged is Level.DEBUG is disabled.
     */
    public void testLogEntry1_Accuracy2() {
        log.enableLevel(Level.INFO);
        Helper.logEntry(log, "a.b");
        assertEquals("", log.getLogMessages().trim());
    }

    /**
     * Accuracy test for
     * <code>logEntry(Log log, String method, String paramNames, Object... params)</code>.
     */
    public void testLogEntry2_Accuracy1() {
        Helper.logEntry(log, "a.b", "name: %s", "Jack");
        assertEquals("[Entering method {a.b}]\n[Input parameters[{name: Jack}]]", log
            .getLogMessages().trim());
    }

    /**
     * Accuracy test for
     * <code>logEntry(Log log, String method, String paramNames, Object... params)</code>.
     */
    public void testLogEntry2_Accuracy2() {
        log.enableLevel(Level.INFO);
        Helper.logEntry(log, "a.b", "name: %s", "Jack");
        assertEquals("", log.getLogMessages().trim());
    }

    /**
     * Accuracy test for <code>logExit(Log log, String method)</code>.
     */
    public void testLogExit1_Accuracy1() {
        Helper.logExit(log, "a.b");
        assertEquals("[Exiting method {a.b}]", log.getLogMessages().trim());
    }

    /**
     * Accuracy test for <code>logExit(Log log, String method)</code>.
     */
    public void testLogExit1_Accuracy2() {
        log.enableLevel(Level.INFO);
        Helper.logExit(log, "a.b");
        assertEquals("", log.getLogMessages().trim());
    }

    /**
     * Accuracy test for <code>logExit(Log log, String method, T returnValue)</code>.
     */
    public void testLogExit2_Accuracy1() {
        Helper.logExit(log, "a.b", "Hello");
        assertEquals("[Exiting method {a.b}]\n[Output parameter {Hello}]", log.getLogMessages()
            .trim());
    }

    /**
     * Accuracy test for <code>logExit(Log log, String method, T returnValue)</code>.
     */
    public void testLogExit2_Accuracy2() {
        log.enableLevel(Level.INFO);
        Helper.logExit(log, "a.b", "Hello");
        assertEquals("", log.getLogMessages().trim());
    }

    /**
     * Accuracy test for <code>logError(Log log, String method, String detail)</code>.
     */
    public void testLogError1_Accuracy1() {
        Helper.logError(log, "a.b", "detail");
        assertEquals("[Error in method {a.b}: Details {detail}]", log.getLogMessages().trim());
    }

    /**
     * Accuracy test for <code>logError(Log log, String method, String detail)</code>.
     */
    public void testLogError1_Accuracy2() {
        log.enableLevel(Level.OFF);
        Helper.logError(log, "a.b", "detail");
        assertEquals("", log.getLogMessages().trim());
    }

    /**
     * Accuracy test for <code>logError(Log log, String method, Throwable exception)</code>.
     */
    public void testLogError2_Accuracy1() {
        Exception cause = new Exception("test");
        Helper.logError(log, "a.b", cause);
        assertEquals("[Error in method {a.b}: Details {java.lang.Exception: test}]",
            log.getFirstLine());
    }

    /**
     * Accuracy test for <code>logError(Log log, String method, Throwable exception)</code>.
     */
    public void testLogError2_Accuracy2() {
        log.enableLevel(Level.OFF);
        Exception cause = new Exception("test");
        Helper.logError(log, "a.b", cause);
        assertEquals("", log.getLogMessages().trim());
    }
}
