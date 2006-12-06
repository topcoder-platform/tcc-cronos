/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
 package com.orpheus.plugin.firefox.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(FirefoxClientExceptionTest.class);
        suite.addTestSuite(FirefoxExtensionConfigurationExceptionTest.class);
        suite.addTestSuite(FirefoxExtensionHelperTest.class);
        suite.addTestSuite(FirefoxExtensionPersistenceExceptionTest.class);
        suite.addTestSuite(OrpheusServerTest.class);
        return suite;
    }

}
