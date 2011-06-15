/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model.accuracytests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * This class contains all Accuracy tests for this component.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class AccuracyTests {

    /**
     * <p>
     * Creates TestSuite that aggregates all Accuracy test cases for this component.
     * </p>
     * @return TestSuite that aggregates all Accuracy test cases for this component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(UserAccuracyTest.suite());
        suite.addTest(UserProfileAccuracyTest.suite());
        suite.addTest(AuditRecordAccuracyTest.suite());
        suite.addTest(UserDAOHibernateAccuracyTest.suite());
        suite.addTest(AuditDAOHibernateAccuracyTest.suite());
        suite.addTest(UserBeanAccuracyTest.suite());
        return suite;
    }
}
