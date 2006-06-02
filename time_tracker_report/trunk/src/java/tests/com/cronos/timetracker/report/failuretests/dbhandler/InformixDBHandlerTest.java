/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.failuretests.dbhandler;

import com.cronos.timetracker.report.dbhandler.InformixDBHandler;
import com.cronos.timetracker.report.failuretests.ConfigHelper;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

/**
 * <p>A failure test for {@link com.cronos.timetracker.report.dbhandler.InformixDBHandler} class.</p>
 *
 * @author  isv
 * @version 2.0
 */
public class InformixDBHandlerTest extends TestCase {

    /**
     * <p>An instance of {@link com.cronos.timetracker.report.dbhandler.InformixDBHandler} which is tested. This instance is initialized in {@link #setUp()}
     * method and released in {@link #tearDown()} method.<p>
     */
    private InformixDBHandler testedInstance = null;

    /**
     * <p>Gets the test suite for {@link com.cronos.timetracker.report.dbhandler.InformixDBHandler} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link com.cronos.timetracker.report.dbhandler.InformixDBHandler} class.
     */
    public static Test suite() {
        return new TestSuite(InformixDBHandlerTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration("failure/FailureTestsConfig.xml");
        this.testedInstance = new InformixDBHandler();
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
     * <p>Failure test. Tests the {@link com.cronos.timetracker.report.dbhandler.InformixDBHandler#getReportData(com.cronos.timetracker.report.ReportConfiguration)}
     * method for proper handling the invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>config</code> and expects the <code>NullPointerException</code> to be
     * thrown.</p>
     */
    public void testGetReportData_ReportConfiguration_config_NULL() {
        try {
            this.testedInstance.getReportData(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>Failure test. Tests the {@link com.cronos.timetracker.report.dbhandler.InformixDBHandler#release(java.sql.ResultSet)} method for proper handling the
     * invalid input arguments.</p>
     *
     * <p>Passes <code>null</code> as <code>resultset</code> and expects the <code>NullPointerException</code> to be
     * thrown.</p>
     */
    public void testRelease_ResultSet_resultset_NULL() {
        try {
            this.testedInstance.release(null);
            Assert.fail("NullPointerException should have been thrown");
        } catch (NullPointerException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("NullPointerException was expected but the original exception is : " + e);
        }
    }
}
