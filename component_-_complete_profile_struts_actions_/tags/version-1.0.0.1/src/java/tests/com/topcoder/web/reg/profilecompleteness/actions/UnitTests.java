/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This class aggregates all Unit tests for this component.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Creates TestSuite that aggregates all Unit tests for this component.
     * </p>
     * @return TestSuite that aggregates all Unit tests for this component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        // action
        suite.addTestSuite(CompleteProfileActionParametersUnitTest.class);
        suite.addTestSuite(CompleteProfileActionAccUnitTest.class);
        suite.addTestSuite(CompleteProfileActionFailureUnitTest.class);
        suite.addTestSuite(CompleteProfileActionAccUnitTest.class);
        // exceptions
        suite.addTestSuite(CompleteProfileActionConfigurationExceptionUnitTest.class);
        suite.addTestSuite(CompleteProfileActionExceptionUnitTest.class);
        // demo
        return suite;
    }
}
