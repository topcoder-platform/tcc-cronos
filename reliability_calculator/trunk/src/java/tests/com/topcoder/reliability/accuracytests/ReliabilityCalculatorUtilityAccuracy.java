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
import com.topcoder.reliability.ExitSecurityManager;
import com.topcoder.reliability.ReliabilityCalculationUtility;
import com.topcoder.reliability.impl.persistence.DatabaseReliabilityDataPersistence;


/**
 * Accuracy test for <code>ReliabilityCalculatorUtility</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReliabilityCalculatorUtilityAccuracy extends TestCase {
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
     * Accuracy test <code>main</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testMain_NoUpdate() throws Exception {
        String[] values = new String[] {
                "-pc", "1,2", "-u", "no", "-c",
                "test_files/accuracy/accuracy.properties",
            };

        // Save the security manager
        SecurityManager oldSecurityManager = System.getSecurityManager();
        // Throw SecurityException if System.exit called
        System.setSecurityManager(new ExitSecurityManager());

        ReliabilityCalculationUtility.main(values);

        System.setSecurityManager(oldSecurityManager);

        // verified
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
     * Accuracy test <code>main</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testMain_Update() throws Exception {
        String[] values = new String[] {
                "-pc", "1,2", "-u", "yes", "-c",
                "test_files/accuracy/accuracy.properties",
            };

        // Save the security manager
        SecurityManager oldSecurityManager = System.getSecurityManager();
        // Throw SecurityException if System.exit called
        System.setSecurityManager(new ExitSecurityManager());

        ReliabilityCalculationUtility.main(values);

        System.setSecurityManager(oldSecurityManager);

        // verified
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
        PreparedStatement pstmt = conn.prepareStatement(
                "select rating from user_reliability");

        ResultSet rs = pstmt.executeQuery();

        rs.close();
        pstmt.close();
        persistence.close();
    }
}
