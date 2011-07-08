/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.stresstests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Base stress test for classes.
 *
 * @author extra
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class AbstractStressTests extends AbstractJUnit4SpringContextTests {

    /**
     * Set up environment.
     *
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        executeSQL("test_files/stress/drop.sql");
        executeSQL("test_files/stress/test-data-all.sql");
    }

    /**
     * Tear down environment.
     *
     * @throws Exception
     *             to JUnit
     */
    public void tearDown() throws Exception {
        executeSQL("test_files/stress/drop.sql");
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file
     *            the file contains the sql statements.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void executeSQL(String file) throws Exception {
        DriverManagerDataSource dataSource = (DriverManagerDataSource) applicationContext.getBean("dataSource");
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        // get sql statements and add to statement
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                statement.execute(line);
            }
        }

        in.close();
        connection.close();
    }
}
