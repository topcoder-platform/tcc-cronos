/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.utilities.cp.stresstests;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * <p>
 * BaseStressTest class for the stress tests.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
public class BaseStressTest extends TestCase {
    /**
     * <p>
     * Represents the <code>ApplicationContext </code> for tests.
     * </p>
     */
    static final ApplicationContext APP_CONTEXT;

    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    static final long LOOP = 100;

    /**
     * <p>
     * Represents the delete sql.
     * </p>
     */
    private static final String[] DELETE_SQL = {"DELETE FROM member_contribution_points",
        "DELETE FROM original_payment", "DELETE FROM project_contest_cp_config",
        "DELETE FROM direct_project_cp_config", "DELETE FROM project", "DELETE FROM tc_direct_project" };

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private static long current = -1;

    /**
     * <p>
     * Represents the connection for testing.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Initialization.
     * </p>
     */
    static {
        APP_CONTEXT = new ClassPathXmlApplicationContext("stresstests/applicationContext.xml");
    }

    /**
     * <p>
     * Setup the environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        connection = ((DataSource) APP_CONTEXT.getBean("dataSource")).getConnection();
        clearDB(connection);
        loadDB(connection);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        clearDB(connection);
        connection.close();
    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    static void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name
     *            the test name
     * @param count
     *            the run count
     */
    void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: "
                + (System.currentTimeMillis() - current) + " ms");
    }

    /**
     * <p>
     * Loads data into the database.
     * </p>
     *
     * @param connection
     *            the connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void loadDB(Connection connection) throws Exception {
        String[] insertTcDirectProject = new String[(int) LOOP];
        String[] insertProject = new String[(int) LOOP];
        for (int i = 0; i < LOOP; i++) {
            insertTcDirectProject[i] = "INSERT INTO 'informix'.tc_direct_project(project_id, name, user_id,"
                    + " create_date) VALUES(" + (i + 1) + ", 'Project 1', 1, CURRENT);";
            insertProject[i] = "INSERT INTO 'informix'.project(project_id, project_status_id,project_category_id,"
                    + "create_user,create_date,modify_user,modify_date,tc_direct_project_id) VALUES(" + (i + 1)
                    + ", 1, 1, 'admin', CURRENT, 'admin', CURRENT, 1)";
        }
        executeSQL(connection, insertTcDirectProject);
        executeSQL(connection, insertProject);
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @param connection
     *            the connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void clearDB(Connection connection) throws Exception {
        executeSQL(connection, DELETE_SQL);
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Empty lines will be ignored.
     * </p>
     *
     * @param connection
     *            the connection.
     * @param sqls
     *            the sqls.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void executeSQL(Connection connection, String[] sqls) throws Exception {
        Statement statement = connection.createStatement();
        try {
            for (int i = 0; i < sqls.length; i++) {
                statement.executeUpdate(sqls[i]);
            }
        } finally {
            statement.close();
        }
    }
}
