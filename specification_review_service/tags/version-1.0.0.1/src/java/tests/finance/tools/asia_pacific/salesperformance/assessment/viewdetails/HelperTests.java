/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package finance.tools.asia_pacific.salesperformance.assessment.viewdetails;

import java.lang.reflect.Constructor;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

/**
 * <p>
 * Unit tests for the {@link Helper} class.
 * </p>
 *
 * @author akinwale
 * @version 1.0
 */
public class HelperTests extends TestCase {
    /**
     * <p>
     * The logger to be used for the tests.
     * </p>
     */
    private static final Logger LOGGER = Logger.getLogger(HelperTests.class);

    /**
     * <p>
     * Tests the private constructor using reflection.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testCtor() throws Exception {
        Constructor constructor = Helper.class.getDeclaredConstructor(new Class[0]);
        constructor.setAccessible(true);
        Object instance = constructor.newInstance(new Object[0]);
        constructor.setAccessible(false);

        assertNotNull("instance should not be null", instance);
    }

    /**
     * <p>
     * Tests that the checkNull(Object, String, String, Logger) method works properly by
     * throwing {@link IllegalArgumentException} when the value to check is null.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     */
    public void testCheckNull_NullValue() {
        try {
            Helper.checkNull(null, "nullvalue", "methodName", LOGGER);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the checkNull(Object, String, String, Logger) method works properly by
     * throwing {@link IllegalArgumentException} when the string value to check is null.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     */
    public void testCheckNullOrEmpty_NullValue() {
        try {
            Helper.checkNullOrEmpty(null, "nullstring", "methodName", LOGGER);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the checkNull(Object, String, String, Logger) method works properly by
     * throwing {@link IllegalArgumentException} when the string value to check is an
     * empty string.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     */
    public void testCheckNullOrEmpty_EmptyStringValue() {
        try {
            Helper.checkNullOrEmpty("", "emptystring", "methodName", LOGGER);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the checkPositive(long, String, String, Logger) method works properly by
     * throwing {@link IllegalArgumentException} when the long value to check is not
     * positive.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     */
    public void testCheckPositive() {
        try {
            Helper.checkPositive(-1, "negative", "methodName", LOGGER);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the checkArray(long[], String, String, Logger) method works properly by
     * throwing {@link IllegalArgumentException} when the array is null.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     */
    public void testCheckArray_Long_Null() {
        try {
            Helper.checkArray((long[]) null, "nullArray", "methodName", LOGGER);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the checkArray(long[], String, String, Logger) method works properly by
     * throwing {@link IllegalArgumentException} when the array is empty.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     */
    public void testCheckArray_Long_Empty() {
        try {
            Helper.checkArray(new long[0], "emptyArray", "methodName", LOGGER);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the checkArray(short[], String, String, Logger) method works properly by
     * throwing {@link IllegalArgumentException} when the array is null.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     */
    public void testCheckArray_Short_Null() {
        try {
            Helper.checkArray((short[]) null, "nullArray", "methodName", LOGGER);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the checkArray(long[], String, String, Logger) method works properly by
     * throwing {@link IllegalArgumentException} when the array is empty.
     * </p>
     *
     * <p>
     * {@link IllegalArgumentException} is expected.
     * </p>
     */
    public void testCheckArray_Short_Empty() {
        try {
            Helper.checkArray(new short[0], "emptyArray", "methodName", LOGGER);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests that the logEntry(String, String[], Object[], Logger) method works properly.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void testLogEntry() throws Exception {
        long time = System.currentTimeMillis();

        Thread.sleep(1);
        long startTime = Helper.logEntry("Method", new String[0], new Object[0], LOGGER);
        assertTrue("logEntry does not work properly", startTime > time);

        Thread.sleep(1);
        long newStartTime = Helper.logEntry("Method", new String[] {"o"}, new Object[] {new Object()}, LOGGER);
        assertTrue("logEntry does not work properly", newStartTime > startTime);
    }

    /**
     * <p>
     * Tests that the logExit(String, Object, long, Logger) method works properly.
     * </p>
     */
    public void testLogExit() {
        Helper.logExit("Method", "Return", System.currentTimeMillis(), LOGGER);
    }

    /**
     * <p>
     * Tests that the logError(String, Exception, Logger) method works properly.
     * </p>
     */
    public void testLogError() {
        Exception e = new Exception();
        Exception loggedException = (Exception) Helper.logError("Method", e, LOGGER);
        assertEquals("logError does not work properly", e, loggedException);
    }
}