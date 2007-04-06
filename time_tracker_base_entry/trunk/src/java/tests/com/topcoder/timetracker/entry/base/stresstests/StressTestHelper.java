/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * This class is a helper class used in stress tests.
 *
 * @author vividmxx
 * @version 3.2
 */
public final class StressTestHelper {

    /**
     * The run time used in stress test.
     */
    public static final int RUN_NUMBER = 100;

    /**
     * The thread number used in stress test.
     */
    public static final int THREAD_NUMBER = 10;

    /**
     * The SQL statement used to insert records into company table.
     */
    private static final String INS_COMPANY = "insert into company "
        + "(company_id, name, passcode, creation_date, creation_user, modification_date, modification_user) "
        + "values (?, ?, ?, CURRENT, '', CURRENT, '')";

    /**
     * The SQL statement used to insert records into cut_off_time table.
     */
    private static final String INS_CUTOFFTIME = "insert into cut_off_time "
        + "(cut_off_time_id, company_id, cut_off_time, creation_date, creation_user, modification_date, "
        + "modification_user) values (?, ?, ?, CURRENT, '', CURRENT, '')";

    /**
     * The SQL statement used to clear records from cut_off_time table.
     */
    private static final String DEL_CUTOFFTIME = "delete from cut_off_time "
        + "where cut_off_time_id >= 41000 and cut_off_time_id < 41200";

    /**
     * The SQL statement used to clear records from company table.
     */
    private static final String DEL_COMPANY = "delete from company "
        + "where company_id >= 41000 and company_id < 41200";

    /**
     * The <code>DBConnectionFactory</code> instance used in stress test.
     */
    private static DBConnectionFactory factory = null;

    /**
     * The <code>Date</code> instance used in the test to record the time.
     */
    private static Date timer = null;

    /**
     * The private constructor prevents creation.
     */
    private StressTestHelper() {
    }

    /**
     * Tear down the database.
     *
     * @throws Exception
     *             if any error occurred when tear down
     */
    public static void tearDownDatabase() throws Exception {
        Connection connection = createConnection();
        Statement stat = null;
        try {
            stat = connection.createStatement();
            stat.execute(DEL_CUTOFFTIME);
            stat.execute(DEL_COMPANY);
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Setup the database used in stress test.
     *
     * @throws Exception
     *             if any error occurred when setup
     */
    public static void setUpDatabase() throws Exception {
        insertCompany();
        insertCutofftime();
    }

    /**
     * Inserts records into "company" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertCompany() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_COMPANY);
            for (int i = 0; i < 2 * RUN_NUMBER; i++) {
                stat.setInt(1, 41000 + i);
                stat.setString(2, "Name" + (41000 + i));
                stat.setString(3, "Code" + (41000 + i));
                stat.execute();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Inserts records into "cut_off_time" table.
     *
     * @throws Exception
     *             if any error occurs when inserting data
     */
    private static void insertCutofftime() throws Exception {
        Connection connection = createConnection();
        PreparedStatement stat = null;
        try {
            stat = connection.prepareStatement(INS_CUTOFFTIME);
            for (int i = 0; i < RUN_NUMBER; i++) {
                stat.setInt(1, 41000 + i);
                stat.setInt(2, 41000 + i);
                stat.setTimestamp(3, new Timestamp(2007, 4, 2, 17, 31, 0, 0));
                stat.execute();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
            connection.close();
        }
    }

    /**
     * Starts the timer. Simply sets the timer to the current time.
     */
    public static void startTimer() {
        timer = new Date();
    }

    /**
     * Prints the test result.
     *
     * @param methodName
     *            the name of the target method
     * @param runNumber
     *            the number the target method is called
     */
    public static void printResultMulTimes(String methodName, int runNumber) {
        System.out.println("Calling " + methodName + " for " + runNumber + " times costs "
                + (new Date().getTime() - timer.getTime()) + " milliseconds.");
    }

    /**
     * Prints the test result.
     *
     * @param methodName
     *            the name of the target method
     * @param threadNumber
     *            the number the thread
     */
    public static void printResultMulThreads(String methodName, int threadNumber) {
        System.out.println("Calling " + methodName + " with " + threadNumber + " threads costs "
                + (new Date().getTime() - timer.getTime()) + " milliseconds.");
    }

    /**
     * Loads the configuration.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public static void loadConfig() throws Exception {
        ConfigManager.getInstance().add("stresstests/config.xml");
    }

    /**
     * Clears the configuration.
     *
     * @throws Exception
     *             throws to JUnit
     */
    public static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Creates a connection for test.
     *
     * @return the created connection
     * @throws Exception
     *             if any error occurs when creating the connection
     */
    private static Connection createConnection() throws Exception {
        if (factory == null) {
            factory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        }
        return factory.createConnection();
    }
}
