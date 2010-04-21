/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress cases for Improve Performance for BPSync
 * Java Adapter version 1.0.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Creates a test suite of the stress tests.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(XMLGregorianCalendarTypeConverterStressTest.class);
        suite.addTestSuite(SaveDraftContestActionStressTest.class);
        suite.addTestSuite(GetAllBillingProjectsActionStressTest.class);
        suite.addTestSuite(CreateProjectActionStressTest.class);
        suite.addTestSuite(GetAllProjectsActionStressTest.class);
        suite.addTestSuite(GetCapacityFullDatesActionStressTest.class);
        suite.addTestSuite(GetContestActionStressTest.class);

        return suite;
    }
}
