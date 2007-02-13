/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.htmlreport;

import com.cronos.timetracker.report.AbstractReport;
import com.cronos.timetracker.report.AbstractTestcaseForReports;


/**
 * This class contains the unit tests for {@link TimeExpenseReport}.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class TimeExpenseReportTest extends AbstractTestcaseForReports {

    /**
     * This method returns an instance of the {@link AbstractReport} implementation to be tested.
     *
     * @return the {@link AbstractReport} implementation to be tested, an {@link TimeExpenseReport} in this case
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    protected AbstractReport getImplementation() throws Exception {
        return new TimeExpenseReport();
    }

    /**
     * This method calls the constructor of the to-be-tested {@link AbstractReport} subclass, {@link
     * TimeExpenseReport#TimeExpenseReport()} in this case.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    protected void callConstructor() throws Exception {
        new TimeExpenseReport();
    }

    /**
     * This method returns the relative path to the directory that contains the expected render results as XML files
     * here.
     *
     * @return the relative path to the directory containing the expected render result files
     */
    protected String getRelativePathToExpectedResultsDir() {
        return "expectedTimeExpenseReportResults/";
    }

}
