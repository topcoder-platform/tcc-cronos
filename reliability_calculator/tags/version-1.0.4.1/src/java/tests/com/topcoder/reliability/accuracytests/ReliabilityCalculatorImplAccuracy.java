/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;

import org.junit.Assert;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.reliability.impl.ReliabilityCalculatorImpl;
import com.topcoder.reliability.impl.persistence.DatabaseReliabilityDataPersistence;


/**
 * Accuracy test for <code>ReliabilityCalculatorImpl</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReliabilityCalculatorImplAccuracy extends TestCase {
    /**
     * The instance for testing.
     */
    private ConfigurationObject config;

    /**
     * Sets up environment.
     *
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        XMLFilePersistence filePersistence = new XMLFilePersistence();
        ConfigurationObject myconfig = filePersistence.loadFile("myconfig",
                new File("test_files/accuracy/db.xml"));

        config = myconfig.getChild("myconfig");

        DatabaseReliabilityDataPersistence persistence = new DatabaseReliabilityDataPersistence();
        persistence.configure(config);
        persistence.open();

        Field connection = persistence.getClass().getDeclaredField("connection");
        connection.setAccessible(true);

        Connection conn = (Connection) connection.get(persistence);

        BufferedReader in = new BufferedReader(new FileReader(
                    "test_files/accuracy/cleandb.sql"));
        String line = null;

        Statement stmt = conn.createStatement();

        while ((line = in.readLine()) != null) {
            stmt.addBatch(line);
        }

        stmt.executeBatch();
        conn.commit();
        stmt.close();
        in.close();

        in = new BufferedReader(new FileReader("test_files/accuracy/init.sql"));
        line = null;

        stmt = conn.createStatement();

        while ((line = in.readLine()) != null) {
        	if (line.trim().length() != 0 && !line.startsWith("--")) {
        		stmt.addBatch(line);
        	}
        }

        stmt.executeBatch();
        conn.commit();

        stmt.close();
        persistence.close();

        in.close();
    }

    /**
     * Accuracy test for constructor.
     */
    public void testCtor() {
        assertNotNull("The result should match.",
            new ReliabilityCalculatorImpl());
    }

    /**
     * Accuracy test for <code>calculate()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testConfigure() throws Exception {
        ReliabilityCalculatorImpl calculator = new ReliabilityCalculatorImpl();

        XMLFilePersistence filePersistence = new XMLFilePersistence();
        ConfigurationObject myconfig = filePersistence.loadFile("myconfig",
                new File("test_files/accuracy/db.xml"));

        config = myconfig.getChild("calculatorImpl");

        calculator.configure(config);

        Field field = calculator.getClass()
                                .getDeclaredField("reliabilityDataPersistence");
        field.setAccessible(true);
        assertNotNull("the persistence should be set.", field.get(calculator));
    }

    /**
     * Accuracy test for <code>calculate()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testCalculate_1() throws Exception {
        ReliabilityCalculatorImpl calculator = new ReliabilityCalculatorImpl();
        XMLFilePersistence filePersistence = new XMLFilePersistence();
        ConfigurationObject myconfig = filePersistence.loadFile("myconfig",
                new File("test_files/accuracy/db.xml"));
        config = myconfig.getChild("calculatorImpl");
        calculator.configure(config);

        calculator.calculate(1, false);

        // verified
        Field field = calculator.getClass()
                                .getDeclaredField("reliabilityDataPersistence");
        field.setAccessible(true);
        assertNotNull("the persistence should be set.", field.get(calculator));

        DatabaseReliabilityDataPersistence persistence = (DatabaseReliabilityDataPersistence) field.get(calculator);
        persistence.open();

        Field connection = persistence.getClass().getDeclaredField("connection");
        connection.setAccessible(true);

        Connection conn = (Connection) connection.get(persistence);
        PreparedStatement pstmt = conn.prepareStatement(
                "select rating from user_reliability where user_id = ?");
        pstmt.setLong(1, 2);

        ResultSet rs = pstmt.executeQuery();
        Assert.assertFalse("The data should not exist", rs.next());

        rs.close();
        pstmt.close();
        persistence.close();
    }

    /**
     * Accuracy test for <code>calculate()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testCalculate_2() throws Exception {
        ReliabilityCalculatorImpl calculator = new ReliabilityCalculatorImpl();
        XMLFilePersistence filePersistence = new XMLFilePersistence();
        ConfigurationObject myconfig = filePersistence.loadFile("myconfig",
                new File("test_files/accuracy/db.xml"));
        config = myconfig.getChild("calculatorImpl");
        calculator.configure(config);

        calculator.calculate(1, true);

        // verified
        Field field = calculator.getClass()
                                .getDeclaredField("reliabilityDataPersistence");
        field.setAccessible(true);
        assertNotNull("the persistence should be set.", field.get(calculator));

        DatabaseReliabilityDataPersistence persistence = (DatabaseReliabilityDataPersistence) field.get(calculator);
        persistence.open();

        Field connection = persistence.getClass().getDeclaredField("connection");
        connection.setAccessible(true);

        Connection conn = (Connection) connection.get(persistence);
        PreparedStatement pstmt = conn.prepareStatement(
                "select rating from user_reliability");

        ResultSet rs = pstmt.executeQuery();

        rs.close();
        pstmt.close();
        persistence.close();
    }
}
