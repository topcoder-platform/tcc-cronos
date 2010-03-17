/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.liquid.portal.service;

import com.liquid.portal.service.accuracytests.AccuracyTests;
import com.liquid.portal.service.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //stress tests
        suite.addTestSuite(StressTests.class);

        return suite;
    }

}
