/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.failuretests.htmlreport;

import com.cronos.timetracker.report.failuretests.ConfigHelper;
import com.cronos.timetracker.report.failuretests.TestDataFactory;
import com.cronos.timetracker.report.htmlreport.ExpenseReport;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * <p>A failure test for {@link com.cronos.timetracker.report.htmlreport.ExpenseReport} class.</p>
 *
 * @author isv
 * @version 2.0
 */
public class ExpenseReportTest extends TestCase {

    /**
     * <p>An instance of {@link com.cronos.timetracker.report.htmlreport.ExpenseReport} which is tested. This instance is initialized in {@link #setUp()} method
     * and released in {@link #tearDown()} method.<p>
     */
    private ExpenseReport testedInstance = null;

    /**
     * <p>Gets the test suite for {@link com.cronos.timetracker.report.htmlreport.ExpenseReport} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.cronos.timetracker.report.htmlreport.ExpenseReport} class.
     */
    public static Test suite() {
        return new TestSuite(ExpenseReportTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration("failure/FailureTestsConfig.xml");
        this.testedInstance = new ExpenseReport();
    }

    /**
     * <p>Tears down the fixture. This method is called after a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        this.testedInstance = null;
        ConfigHelper.releaseNamespaces();
    }

    /**
     * <p>Failure test. Tests the {@link com.cronos.timetracker.report.htmlreport.ExpenseReport#executeReport(com.cronos.timetracker.report.ReportConfiguration)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes {@link TestDataFactory#NULL} as <code>config</code> and expects the <code>NullPointerException</code>
     * to be thrown.</p>
     */
    public void testExecuteReport_ReportConfiguration_config_NULL() {
        try {
            this.testedInstance.executeReport(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }
}
