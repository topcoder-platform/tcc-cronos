/*
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.onlinereview.autoscreening.tool.persistence.informix.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 *
 * @author crackme
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Aggregates all failure test cases.
     *
     * @return Test instance
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestInformixResponseDAO.class);
        suite.addTestSuite(TestInformixTaskDAO.class);

        return suite;
    }
}
