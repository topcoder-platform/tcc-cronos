/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.accuracytests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

/**
 * <p>
 * This base test case provides common functionality for configuration and database.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseTestCase extends TestCase {

    /**
     * <p>
     * Represents the date format for parsing date string.
     * </p>
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getDBConnectionFactory();
    }

    /**
     * <p>
     * Returns the <code>DBConnectionFactory</code> instance.
     * </p>
     *
     * @return the <code>DBConnectionFactory</code> instance
     * @throws Exception to JUnit
     */
    public DBConnectionFactory getDBConnectionFactory() throws Exception {
        return com.topcoder.service.studio.submission.BaseTestCase.getDBConnectionFactory();
    }

    /**
     * <p>
     * Executes the sql script against the database.
     * </p>
     *
     * @param filename
     *            the file name.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void executeScriptFile(String filename) throws Exception {
        com.topcoder.service.studio.submission.BaseTestCase.executeScriptFile(filename);
    }

    /**
     * <p>
     * Executes the sql statements against the database.
     * </p>
     *
     * @param sqls
     *            the array of sql statements.
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void executeSQL(String[] sqls) throws Exception {
        com.topcoder.service.studio.submission.BaseTestCase.executeSQL(sqls);
    }
}
