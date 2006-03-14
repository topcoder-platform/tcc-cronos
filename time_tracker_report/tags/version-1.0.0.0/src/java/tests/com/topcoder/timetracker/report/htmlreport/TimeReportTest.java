/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.htmlreport;

import com.topcoder.timetracker.report.AbstractReport;
import com.topcoder.timetracker.report.AbstractTestcaseForReports;


/**
 * This class contains the unit tests for {@link TimeReport}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TimeReportTest extends AbstractTestcaseForReports {

    /**
     * This method returns an instance of the {@link AbstractReport} implementation to be tested.
     *
     * @return the {@link AbstractReport} implementation to be tested, an {@link TimeReport} in this case
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    protected AbstractReport getImplementation() throws Exception {
        return new TimeReport();
    }

    /**
     * This method calls the constructor of the to-be-tested {@link AbstractReport} subclass, {@link
     * TimeReport#TimeReport()} in this case.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    protected void callConstructor() throws Exception {
        new TimeReport();
    }

    /**
     * This method returns the relative path to the directory that contains the expected render results as XML files
     * here.
     *
     * @return the relative path to the directory containing the expected render result files
     */
    protected String getRelativePathToExpectedResultsDir() {
        return "expectedTimeReportResults/";
    }

}