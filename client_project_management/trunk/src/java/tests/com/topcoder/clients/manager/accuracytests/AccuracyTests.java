/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.manager.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author onsky
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>Aggregates all Accuracy test cases.</p>
     *
     * @return all Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ClientManagerTests.class);
        suite.addTestSuite(CompanyManagerTests.class);
        suite.addTestSuite(ProjectManagerTests.class);
        return suite;
    }

}
