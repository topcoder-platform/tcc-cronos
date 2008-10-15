/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio;

import com.topcoder.management.project.studio.converter.ProjectToContestConverterImplTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all test cases.
     * </p>
     * @return A test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ContestManagerProjectAdapterTest.class);
        suite.addTestSuite(ConversionExceptionTest.class);
        suite.addTestSuite(Demo.class);
        suite.addTestSuite(ProjectToContestConverterImplTest.class);

        return suite;
    }
}
