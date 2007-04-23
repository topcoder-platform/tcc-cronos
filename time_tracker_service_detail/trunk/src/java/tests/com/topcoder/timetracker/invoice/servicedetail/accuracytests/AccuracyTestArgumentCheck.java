/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests;

import com.topcoder.timetracker.invoice.servicedetail.ArgumentCheckUtil;

import junit.framework.TestCase;

/**
 * Unit test for <code>ArgumentCheckUtil</code>.
 * @author KLW
 * @version 1.1
 */
public class AccuracyTestArgumentCheck extends TestCase {

    /**
     * Test <code>checkNotNull</code> for accuracy.
     */
    public void testCheckNotNull_01() {
        ArgumentCheckUtil.checkNotNull("testObject", "test");
    }

    /**
     * Test <code>checkNotNull</code> for failure.
     */
    public void testCheckNotNull_02() {
        try {
            ArgumentCheckUtil.checkNotNull("testObject", null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Test <code>checkNotNullAndNotEmpty</code>.
     */
    public void testCheckNotNullAndNotEmpty_03() {
        try {
            ArgumentCheckUtil.checkNotNullAndNotEmpty("testObject", null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>checkNotNullAndNotEmpty</code> .
     */
    public void testCheckNotNullAndNotEmpty_01() {
        try {
            ArgumentCheckUtil.checkNotNullAndNotEmpty("testObject", "  \t");
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>checkNotNullAndNotEmpty</code> for accuracy.
     */
    public void testCheckNotNullAndNotEmpty_02() {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("testObject", "test");
    }

    /**
     * Test <code>checkNotContainsNull</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testCheckNotContainsNull_01() throws Exception {
        try {
            ArgumentCheckUtil.checkNotContainsNull("arrayName", new String[] { null, "test" });
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Success.
        }
    }

    /**
     * Test <code>checkNotContainsNull</code>
     * @throws Exception
     *             to JUnit
     */
    public void testCheckNotContainsNull_02() throws Exception {
        ArgumentCheckUtil.checkNotContainsNull("arrayName", new String[] { "test", "test" });
    }

    /**
     * Test <code>checkNotContainsNull</code>.
     * @throws Exception
     *             to JUnit
     */
    public void testCheckNotContainsNull_03() throws Exception {
        ArgumentCheckUtil.checkNotContainsNull("arrayName", new String[] {});
    }

    /**
     * Test <code>isNullOrEmptyString</code> .
     */
    public void testIsNullOrEmptyString_01() {
        assertTrue("The returned value is not as expected", ArgumentCheckUtil.isNullOrEmptyString("\n"));

    }

    /**
     * Test <code>isNullOrEmptyString</code> .
     */
    public void testIsNullOrEmptyString_02() {
        assertFalse("The returned value is not as expected", ArgumentCheckUtil.isNullOrEmptyString("test"));
    }

    /**
     * Test <code>isNullOrEmptyString</code> .
     */
    public void testIsNullOrEmptyString_03() {
        assertTrue("The returned value is not as expected", ArgumentCheckUtil.isNullOrEmptyString(null));

    }
}
