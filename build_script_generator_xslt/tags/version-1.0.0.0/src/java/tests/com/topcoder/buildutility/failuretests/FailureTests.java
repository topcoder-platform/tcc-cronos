/*
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
 package com.topcoder.buildutility.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author albertwang
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Return the aggregated failure tests.
     * @return the aggregated failure tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(BuildScriptGeneratorImplFailureTests.class);
        suite.addTestSuite(BuildScriptGeneratorFactoryImplFailureTests.class);
        return suite;
    }

}
