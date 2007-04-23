/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for <code>ArgumentCheckUtil</code>.
 *
 * @author enefem21
 * @version 1.0
 */
public class ArgumentCheckUtilTest extends TestCase {

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(ArgumentCheckUtilTest.class);
    }

    /**
     * Test <code>checkNotNull</code> for failure. Condition: object is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCheckNotNullObjectIsNull() {
        try {
            ArgumentCheckUtil.checkNotNull("testObject", null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>checkNotNull</code> for accuracy. Condition: object is not null. Expect: no exception should be
     * raised.
     */
    public void testCheckNotNullObjectIsNotNull() {
        ArgumentCheckUtil.checkNotNull("testObject", "test");
    }

    /**
     * Test <code>checkNotNullAndNotEmpty</code> for failure. Condition: string is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCheckNotNullAndNotEmptyStringIsNull() {
        try {
            ArgumentCheckUtil.checkNotNullAndNotEmpty("testObject", null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>checkNotNullAndNotEmpty</code> for failure. Condition: string is emptyString. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testCheckNotNullAndNotEmptyStringIsEmpty() {
        try {
            ArgumentCheckUtil.checkNotNullAndNotEmpty("testObject", "  \t");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>checkNotNullAndNotEmpty</code> for accuracy. Condition: string is not null and not empty.
     * Expect: no exception should be raised.
     */
    public void testCheckNotNullAndNotEmptyStringIsNotNullAndNotEmpty() {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("testObject", "test");
    }

    /**
     * Test <code>checkNotContainsNull</code> for failure. Condition: array contains null. Expect:
     * <code>IllegalArgumentException</code>.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckNotContainsNullContainsNull() throws Exception {
        try {
            ArgumentCheckUtil.checkNotContainsNull("arrayName", new String[] {null, "test"});
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>checkNotContainsNull</code> for accuracy. Condition: array doesn't contain null. Expect: no
     * exception should be raised.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCheckNotContainsNullNotContainsNull() throws Exception {
        ArgumentCheckUtil.checkNotContainsNull("arrayName", new String[] {"test", "test"});
    }

    /**
     * Test <code>isNullOrEmptyString</code> for accuracy. Condition: string is null. Expect: return true.
     */
    public void testIsNullOrEmptyStringStringIsNull() {
        assertTrue("The returned value is not as expected", ArgumentCheckUtil.isNullOrEmptyString(null));

    }

    /**
     * Test <code>isNullOrEmptyString</code> for accuracy. Condition: string is empty. Expect: return true.
     */
    public void testIsNullOrEmptyStringStringIsEmpty() {
        assertTrue("The returned value is not as expected", ArgumentCheckUtil.isNullOrEmptyString("\n"));

    }

    /**
     * Test <code>isNullOrEmptyString</code> for accuracy. Condition: string is not null and not empty. Expect:
     * return false.
     */
    public void testIsNullOrEmptyStringStringIsNotNullAndNotEmpty() {
        assertFalse("The returned value is not as expected", ArgumentCheckUtil.isNullOrEmptyString("test"));
    }

}
