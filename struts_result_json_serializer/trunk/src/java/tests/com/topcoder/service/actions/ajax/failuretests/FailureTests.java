/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.ajax.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * <p>
     * Aggregates all unit tests.
     * </p>
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(CustomFormatAJAXResultFailureTests.suite());
        suite.addTest(JSONDataSerializerFailureTests.suite());
        return suite;
    }

}
