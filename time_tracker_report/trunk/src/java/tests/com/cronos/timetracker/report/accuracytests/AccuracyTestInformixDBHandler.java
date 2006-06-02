/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.accuracytests;

import com.cronos.timetracker.report.dbhandler.InformixDBHandler;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Accuracy test for <code>InformixDBHandler</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestInformixDBHandler extends AccuracyBaseTest {
    /** The instance of the DBHandlerFactory used for test.*/
    private InformixDBHandler dbHandler = null;

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        super.setUp();

        dbHandler = new InformixDBHandler();
    }

    /**
     * See javadoc for junit.framework.TestCase#tearDown().
     *
     * @throws Exception exception that occurs during the tear down process.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestInformixDBHandler.class);
    }

    /**
     * Tests Creating <code>InformixDBHandler</code> instance.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testConstructor() throws Exception {
        assertNotNull("The instance of the InformixDBHandler is not valid.", dbHandler);
    }

    /**
     * Tests getReportData().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetReportData() throws Exception {
        // This method will be tested in the test cases of the subclass of AbstractReport.
    }
}
