/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author gjw99
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(AmazonPaymentManagerImplStressTest.suite());
        return suite;
    }
}
