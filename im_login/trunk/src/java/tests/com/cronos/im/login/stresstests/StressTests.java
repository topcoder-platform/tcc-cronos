/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author radium
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Aggregates the tests to a new test suite.
     *
     * @return test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AdminLoginActionStressTest.class);
        suite.addTestSuite(ClientLoginActionStressTest.class);
        suite.addTestSuite(ManagerLoginActionStressTest.class);

        return suite;
    }
}
