/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.stresstests;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>
 * This base stress test.
 * </p>
 *
 * @author dingying131
 * @version 1.0
 */
public class BaseStressTest {

    /**
     * <p>
     * The test spring context.
     * </p>
     */
    protected static final ApplicationContext APP_CONTEXT;

    /**
     * <p>
     * Stress test execute times.
     * </p>
     */
    protected static final int TIMES = 10;

    /**
     * <p>
     * The start time of stress test.
     * </p>
     */
    private long start;

    /**
     * <p>
     * The method name of stress test.
     * </p>
     */
    private String method;

    static {
        APP_CONTEXT = new ClassPathXmlApplicationContext("stress/beans.xml");
    }

    /**
     * <p>
     * Logs the stress test entrance.
     * </p>
     *
     * @param method
     *            method name
     */
    protected void logEntrance(String method) {
        this.method = method;
        start = System.currentTimeMillis();
        System.out.println("Start stress test for method " + method + ", times " + TIMES);
    }

    /**
     * <p>
     * Logs the stress test exit.
     * </p>
     */
    protected void logExit() {
        long now = System.currentTimeMillis();
        System.out.println("Finish stress test for method " + method + ", cost " + (now - start));
    }

    /**
     * <p>
     * Loads the test data.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    protected static void loadData() throws Exception {
        executeSQL("test_files/stress/Load.sql");
    }

    /**
     * <p>
     * Clears the test data.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    protected static void clearData() throws Exception {
        executeSQL("test_files/stress/Clear.sql");
    }

    /**
     * <p>
     * Executes the SQL file of given path.
     * </p>
     *
     * @param path
     *            SQL file path
     * @throws SQLException
     *             to jUnit
     */
    private static void executeSQL(String path) throws SQLException {

        DataSource dataSource = (DataSource) APP_CONTEXT.getBean("dataSource");

        Connection connection = dataSource.getConnection();
        Statement statmenet = connection.createStatement();

        File file = new File(path);
        RandomAccessFile raf = null;

        try {

            raf = new RandomAccessFile(file, "r");
            String line = null;
            while ((line = raf.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("--") && line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                    statmenet.execute(line);
                }
            }

        } catch (IOException e) {
            // ignore

        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    // ignore
                }
            }
            connection.close();
        }
    }

}
