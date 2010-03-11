/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * Unit Tests for <code>Helper</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HelperUnitTest extends TestCase {

    /**
     * Accuracy test for checkNotNull. The value is not null, so no exception should be thrown.
     */
    @Test
    public void test_checkNotNull_Accuracy() {
        Helper.checkNotNull(new Object(), "field1");
    }

    /**
     * Failure test for checkNotNull. The value is null and IllegalArgumentException is expected.
     */
    @Test
    public void test_checkNotNull_Failure() {
        try {
            Helper.checkNotNull(null, "field1");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Accuracy test for checkNotNullOrEmpty. The value is not null or empty, so no exception should
     * be thrown.
     */
    @Test
    public void test_checkNotNullOrEmpty_Accuracy() {
        Helper.checkNotNullOrEmpty("value", "field1");
    }

    /**
     * Failure test for checkNotNullOrEmpty. The value is null and IllegalArgumentException is
     * expected.
     */
    @Test
    public void test_checkNotNullOrEmpty_Failure1() {
        try {
            Helper.checkNotNullOrEmpty(null, "field1");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for checkNotNullOrEmpty. The value is empty and IllegalArgumentException is
     * expected.
     */
    @Test
    public void test_checkNotNullOrEmpty_Failure2() {
        try {
            Helper.checkNotNullOrEmpty("\t  ", "field1");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
