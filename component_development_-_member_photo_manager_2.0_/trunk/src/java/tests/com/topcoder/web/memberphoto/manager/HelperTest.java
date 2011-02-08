/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.manager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link Helper} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class HelperTest extends TestCase {

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(HelperTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link Helper#checkState(T, String)} method.
     * </p>
     * <p>
     * Valid input - No exception thrown
     * </p>
     *
     */
    public void test_accuracy_checkState() {
        Helper.checkState(new Object(), "test");
    }

    /**
     * <p>
     * Failure test for {@link Helper#checkState(T, String)} method.
     * </p>
     * <p>
     * For the following inputs:
     * </p>
     *
     * <pre>
     * Input
     *      T obj : null
     *      String paramName : Valid Value
     * </pre>
     *
     * <p>
     * Expected {@link IllegalStateException}.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void test_failure_checkState() throws Exception {
        try {
            Helper.checkState(null, "test");
            fail("IllegalStateException Expected.");
        } catch (IllegalStateException e) {
            // As expected
        }
    }
}
