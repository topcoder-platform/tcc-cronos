/*
 * ---------------------------------------------------------------------------
 * $Id$
 *
 * Creator: ThomasR [16.02.2006]
 * (c) LINEAS Integral Software GmbH, D-38122 Braunschweig
 * ---------------------------------------------------------------------------
 */

package com.topcoder.timetracker.report;

import java.util.ArrayList;


/**
 * This class shows how he component can be used to send a report programmatically.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProgrammaticallyReportGenerationDemoTest extends BaseTimeTrackerReportTest {

    /**
     * This method shows how to send a report programmatically.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGenerateReportProgrammatically() throws Exception {
        // create a reportFactory
        ReportFactory reportFactory = new ReportFactory();

        //get a report instance
        Report report = reportFactory.getReport("HTML", ReportCategory.EXPENSE);

        // define the filters
        final ArrayList filters = new ArrayList();
        final EqualityFilter equalityFilter = new EqualityFilter(Column.CLIENT, FilterCategory.CLIENT);
        equalityFilter.addFilterValue("client1");
        filters.add(equalityFilter);

        // render the report
        String renderedResult = report.execute(DEFAULT_CONFIGURATION_NAMESPACE, ReportType.CLIENT, filters);
    }
}
