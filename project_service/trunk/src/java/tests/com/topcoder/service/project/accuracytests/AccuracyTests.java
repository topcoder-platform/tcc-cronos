/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * Return all accuracy test cases.
     * @return all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ProjectServiceBeanAccTests.suite());
        suite.addTest(CompetitionAccTests.suite());
        suite.addTest(ProjectAccTests.suite());
        suite.addTest(ProjectDataAccTests.suite());
        return suite;
    }

}
