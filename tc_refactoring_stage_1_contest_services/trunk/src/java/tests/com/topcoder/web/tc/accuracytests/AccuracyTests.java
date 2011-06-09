/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * This class aggregates all Accuracy tests for this component.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class AccuracyTests {

    /**
     * <p>
     * Creates TestSuite that aggregates all test Accuracy cases for this component.
     * </p>
     * @return TestSuite that aggregates all test Accuracy cases for this component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ActiveContestsManagerImplAccuracyTest.suite());
        suite.addTest(PastContestsManagerImplAccuracyTest.suite());
        suite.addTest(ContestStatusManagerImplAccuracyTest.suite());
        suite.addTest(CategoriesManagerImplAccuracyTest.suite());
        suite.addTest(ContestStatusManagerActionAccuracyTest.suite());
        suite.addTest(ActiveContestsManagerActionAccuracyTest.suite());
        suite.addTest(PastContestsManagerActionAccuracyTest.suite());
        suite.addTest(CategoriesManagerActionAccuracyTest.suite());
        return suite;
    }
}
