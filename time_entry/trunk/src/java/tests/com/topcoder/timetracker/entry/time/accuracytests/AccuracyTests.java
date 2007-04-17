/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(BatchOperationExceptionAccuracyTests.suite());
        suite.addTest(TimeEntryAccuracyTests.suite());
        suite.addTest(TaskTypeManagerImplAccuracyTests.suite());
        suite.addTest(TimeEntryManagerImplAccuracyTests.suite());
        suite.addTest(UnrecognizedEntityExceptionAccuracyTests.suite());
        suite.addTest(InvalidCompanyExceptionAccuracyTests.suite());
        suite.addTest(TimeStatusManagerImplAccuracyTests.suite());
        suite.addTest(StringMatchTypeAccuracyTests.suite());
        suite.addTest(TimeStatusAccuracyTests.suite());
        suite.addTest(TaskTypeAccuracyTests.suite());
        suite.addTest(ManagerFactoryAccuracyTests.suite());
        suite.addTest(ConfigurationExceptionAccuracyTests.suite());
        suite.addTest(DataAccessExceptionAccuracyTests.suite());
        suite.addTest(DbTimeEntryRejectReasonDAOAccuracyTests.suite());
        suite.addTest(DbTimeStatusDAOAccuracyTests.suite());
        suite.addTest(DbBaseFilterFactoryAccuracyTests.suite());
        suite.addTest(DbTaskTypeDAOAccuracyTests.suite());
        suite.addTest(DbTimeEntryDAOAccuracyTests.suite());
        suite.addTest(DbTimeEntryFilterFactoryAccuracyTests.suite());
        suite.addTest(DbTaskTypeFilterFactoryAccuracyTests.suite());
        suite.addTest(DbTimeStatusFilterFactoryAccuracyTests.suite());
        suite.addTest(TaskTypeManagerSessionBeanAccuracyTests.suite());
        suite.addTest(TimeStatusManagerSessionBeanAccuracyTests.suite());
        suite.addTest(TimeEntryManagerSessionBeanAccuracyTests.suite());

        return suite;
    }

}
