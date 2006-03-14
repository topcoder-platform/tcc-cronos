/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests.htmlreport;

import com.topcoder.timetracker.report.htmlreport.TimeReport;
import com.topcoder.timetracker.report.failuretests.ConfigHelper;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * <p>A failure test for {@link com.topcoder.timetracker.report.htmlreport.TimeReport} class.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class TimeReportTest extends TestCase {

    /**
     * <p>An instance of {@link com.topcoder.timetracker.report.htmlreport.TimeReport} which is tested. This instance is initialized in {@link #setUp()} method and
     * released in {@link #tearDown()} method.<p>
     */
    private TimeReport testedInstance = null;

    /**
     * <p>Gets the test suite for {@link com.topcoder.timetracker.report.htmlreport.TimeReport} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.topcoder.timetracker.report.htmlreport.TimeReport} class.
     */
    public static Test suite() {
        return new TestSuite(TimeReportTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("failure/FailureTestsConfig.xml"));
        this.testedInstance = new TimeReport();
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
     * <p>Failure test. Tests the {@link com.topcoder.timetracker.report.htmlreport.TimeReport#executeReport(com.topcoder.timetracker.report.ReportConfiguration)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>config</code> and expects the <code>NullPointerException</code> to be
     * thrown.</p>
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
