/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.report.informix.TestFixedBillingEntriesReportBuilderAcc;
import com.topcoder.timetracker.report.informix.TestFixedBillingEntriesReportBuilderFailure;
import com.topcoder.timetracker.report.informix.TestFixedBillingEntryReport;

import com.topcoder.timetracker.report.informix.TestExpenseEntriesReportBuilderAcc;
import com.topcoder.timetracker.report.informix.TestExpenseEntriesReportBuilderFailure;
import com.topcoder.timetracker.report.informix.TestExpenseEntryReport;

import com.topcoder.timetracker.report.informix.TestTimeEntriesReportBuilderAcc;
import com.topcoder.timetracker.report.informix.TestTimeEntriesReportBuilderFailure;
import com.topcoder.timetracker.report.informix.TestTimeEntryReport;

import com.topcoder.timetracker.report.informix.TestInformixFilter;
import com.topcoder.timetracker.report.informix.TestInformixReportDAOAcc;
import com.topcoder.timetracker.report.informix.TestInformixReportDAOFailure;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all Unit test cases.
     * </p>
     *
     * @return All Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Exceptions tests
        suite.addTestSuite(TestReportException.class);
        suite.addTestSuite(TestReportConfigException.class);
        suite.addTestSuite(TestReportDataAccessException.class);

        // Helper tests
        suite.addTestSuite(TestHelper.class);

        // report package
        suite.addTestSuite(TestReportDAOFactory.class);

        // filter
        suite.addTestSuite(TestInformixFilter.class);

        // reports
        suite.addTestSuite(TestFixedBillingEntryReport.class);
        suite.addTestSuite(TestExpenseEntryReport.class);
        suite.addTestSuite(TestTimeEntryReport.class);

        // builders
        suite.addTestSuite(TestFixedBillingEntriesReportBuilderAcc.class);
        suite.addTestSuite(TestFixedBillingEntriesReportBuilderFailure.class);
        suite.addTestSuite(TestExpenseEntriesReportBuilderAcc.class);
        suite.addTestSuite(TestExpenseEntriesReportBuilderFailure.class);
        suite.addTestSuite(TestTimeEntriesReportBuilderAcc.class);
        suite.addTestSuite(TestTimeEntriesReportBuilderFailure.class);

        // DAO
        suite.addTestSuite(TestInformixReportDAOAcc.class);
        suite.addTestSuite(TestInformixReportDAOFailure.class);

        // demo
        suite.addTestSuite(Demo.class);

        return suite;
    }
}
