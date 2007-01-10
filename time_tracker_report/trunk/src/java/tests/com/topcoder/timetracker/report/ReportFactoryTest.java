/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import com.cronos.timetracker.report.htmlreport.ExpenseReport;
import com.cronos.timetracker.report.htmlreport.TimeExpenseReport;
import com.cronos.timetracker.report.htmlreport.TimeReport;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;


/**
 * This class contains the unit tests for {@link ReportFactory}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportFactoryTest extends BaseTimeTrackerReportTest {
    /**
     * This is the constant to retrieve the reports that render to HTML.
     */
    protected static final String FORMAT_HTML = "HTML";

    /**
     * This is the {@link ReportFactory} instance tested in the test cases. It is instantiated in {@link #setUp()}.
     */
    private ReportFactory reportFactory;

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correctness.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtor() throws Exception {
        new ReportFactory();
    }

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} is completely empty
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailEmptyCM() throws Exception {
        try {
            clearConfig();
            new ReportFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for reports does not exist
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailNoReportClasses() throws Exception {
        try {
            loadReportsConfiguration("Reports_noReportClasses.xml");
            new ReportFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for reports is empty
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailEmptyReportClasses() throws Exception {
        try {
            loadReportsConfiguration("Reports_emptyReportClasses.xml");
            new ReportFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for reports contains empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailEmptyClassname() throws Exception {
        try {
            loadReportsConfiguration("Reports_emptyClassname.xml");
            new ReportFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for reports contains classname of unknown class
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailUnknownClass() throws Exception {
        try {
            loadReportsConfiguration("Reports_unknownClass.xml");
            new ReportFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for reports contains class that has no no-arg constructor
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailNoNoArgConstructor() throws Exception {
        try {
            loadReportsConfiguration("Reports_classNoNoArgConstructor.xml");
            new ReportFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for reports contains class that throws an exception upon
     * constructor invocation
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailExceptionInConstructor() throws Exception {
        try {
            loadReportsConfiguration("Reports_exceptionInConstructorClass.xml");
            new ReportFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for reports contains class that does not implement {@link
     * Report}
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailClassNotImplemetingReport() throws Exception {
        try {
            loadReportsConfiguration("Reports_classNotImplementingReport.xml");
            new ReportFactory();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#ReportFactory()} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: {@link ConfigManager} property for reports contains two reports of same format and
     * category
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtorFailDuplicateReportFormatAndCategory() throws Exception {
        try {
            loadReportsConfiguration("Reports_duplicateReportNameAndCategory.xml");
            new ReportFactory();
            fail("should throw");
        } catch (ReportException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#getReport(String, ReportCategory)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: format is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportFailNullFormat() throws Exception {
        try {
            reportFactory.getReport(null, ReportCategory.EXPENSE);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#getReport(String, ReportCategory)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: category is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportFailNullCategory() throws Exception {
        try {
            reportFactory.getReport(FORMAT_HTML, null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#getReport(String, ReportCategory)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: format is an empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportFailEmptyFormat() throws Exception {
        try {
            reportFactory.getReport("    ", ReportCategory.EXPENSE);
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#getReport(String, ReportCategory)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: format denotes an unknown format - no report found
     */
    public void testGetReportFailUnknownFormat() {
        try {
            reportFactory.getReport("UNKNOWN", ReportCategory.EXPENSE);
            fail("should throw");
        } catch (ReportNotFoundException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link ReportFactory#getReport(String, ReportCategory)} for correct behavior.
     * <p/>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReport() throws Exception {
        assertEquals(ExpenseReport.class, reportFactory.getReport(FORMAT_HTML, ReportCategory.EXPENSE).getClass());
        assertEquals(TimeReport.class, reportFactory.getReport(FORMAT_HTML, ReportCategory.TIME).getClass());
        assertEquals(TimeExpenseReport.class, reportFactory.getReport(FORMAT_HTML,
            ReportCategory.TIMEEXPENSE).getClass());
    }

    /**
     * This method clears the {@link #REPORTS_CONFIGURATION_NAMESPACE} and loads the file with the given name into
     * {@link ConfigManager} afterwards.
     *
     * @param filename the name of the configuration file to be loaded
     *
     * @throws ConfigManagerException in case an unexpected error occurs
     */
    private void loadReportsConfiguration(final String filename) throws ConfigManagerException {
        removeNamespace(REPORTS_CONFIGURATION_NAMESPACE);
        ConfigManager.getInstance().add("configuration/" + filename);
    }

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        reportFactory = new ReportFactory();
    }
}
