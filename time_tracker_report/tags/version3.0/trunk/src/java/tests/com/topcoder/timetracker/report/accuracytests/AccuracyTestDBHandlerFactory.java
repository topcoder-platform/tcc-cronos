/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.accuracytests;

import com.cronos.timetracker.report.dbhandler.DBHandler;
import com.cronos.timetracker.report.dbhandler.DBHandlerFactory;
import com.cronos.timetracker.report.dbhandler.InformixDBHandler;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Accuracy test for <code>DBHandlerFactory</code>.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class AccuracyTestDBHandlerFactory extends AccuracyBaseTest {
    /** The name of the DB handler used for test. */
    private static final String DB_HANDLER_NAME = "InformixDBHandler";

    /** The instance of the DBHandlerFactory used for test. */
    private DBHandlerFactory factory = null;

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        super.setUp();
        factory = new DBHandlerFactory();
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
        return new TestSuite(AccuracyTestDBHandlerFactory.class);
    }

    /**
     * Tests Creating <code>DBHandlerFactory</code> instance.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testConstructor() throws Exception {
        DBHandler dbHandler = factory.getDBHandler(DB_HANDLER_NAME);

        assertTrue("The dbHandler is not valid.", dbHandler instanceof InformixDBHandler);
    }

    /**
     * Tests getDBHandler with legal parameter.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetDBHandler() throws Exception {
        DBHandler dbHandler = factory.getDBHandler(DB_HANDLER_NAME);

        assertTrue("The dbHandler is not valid.", dbHandler instanceof InformixDBHandler);
    }
}
