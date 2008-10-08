/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.webservices.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * 
 * @author waits
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * Creates and returns a {@code Test} representing all the accuracy tests
     * 
     * @return a {@code Test} representing all the component tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(ClientStatusServiceClientTests.class);
        suite.addTestSuite(ProjectStatusServiceClientTests.class);
        suite.addTestSuite(LookupServiceClientTests.class);
        return suite;
    }

}
