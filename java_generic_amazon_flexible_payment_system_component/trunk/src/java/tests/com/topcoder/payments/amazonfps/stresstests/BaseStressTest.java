/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.stresstests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

import junit.framework.TestCase;


/**
 * <p>
 * BaseStressTest class for the stress tests.
 * </p>
 * <p>
 * Thread safe: This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author gjw99
 * @version 1.0
 */
public class BaseStressTest extends TestCase {

    /** The test count. */
    protected static int testCount = 50;

    /** time started to test. */
    protected long start = 0;

    /** The last error occurred. */
    protected Throwable lastError;

    /**
     * Initialize variables.
     *
     * @throws Exception if anything goes wrong
     */
    public void setUp() throws Exception {
        start = new Date().getTime();
        lastError = null;
    }
    /**
     * <p>Executes the sql scripts in the given sql file.</p>
     *
     * @param connection JDBC connection.
     * @param sqlPath the path of the sql file to execute
     *
     * @throws Exception if exception occurs during database operation
     */
    static void executeSqlFile(Connection connection, String sqlPath)
        throws Exception {
        String[] sqlStatements = getFileAsString(sqlPath).split(";");

        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            for (int i = 0; i < sqlStatements.length; i++) {
                if ((sqlStatements[i].trim().length() != 0) && !sqlStatements[i].trim().startsWith("--")) {
                    stmt.executeUpdate(sqlStatements[i]);
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * Gets the file content as string.
     *
     * @param filePath the file path
     *
     * @return The content of file
     *
     * @throws Exception to JUnit
     */
    static String getFileAsString(String filePath) throws Exception {
        StringBuilder buf = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

        try {
            String s;

            while ((s = in.readLine()) != null) {
                buf.append(s);
            }

            return buf.toString();
        } finally {
            in.close();
        }
    }

    /**
     * The thread that is used in the test.
     *
     * @author gjw99
     * @version 1.0
     */
    class TestThread extends Thread {
        /**
         * The index.
         */
        private int index;

        /**
         * Create the test thread.
         *
         * @param index the index.
         */
        public TestThread(int index) {
            super();
            this.index = index;
        }

        /**
         * @return the index
         */
        public int getIndex() {
            return index;
        }
    }
}
