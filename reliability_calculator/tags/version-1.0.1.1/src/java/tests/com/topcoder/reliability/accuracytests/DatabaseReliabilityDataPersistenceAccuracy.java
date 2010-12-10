/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;

import com.topcoder.reliability.impl.ReliabilityDataPersistence;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.persistence.DatabaseReliabilityDataPersistence;

import junit.framework.TestCase;

import org.junit.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Accuracy test for <code>DatabaseReliabilityDataPersistence</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DatabaseReliabilityDataPersistenceAccuracy extends TestCase {
    /**
     * The <code>DatabaseReliabilityDataPersistence</code> instance for testing.
     */
    private DatabaseReliabilityDataPersistence instance = new DatabaseReliabilityDataPersistence();

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

        instance.configure(config);
        instance.open();

        Field connection = instance.getClass().getDeclaredField("connection");
        connection.setAccessible(true);

        Connection conn = (Connection) connection.get(instance);

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
            stmt.addBatch(line);
        }

        stmt.executeBatch();
        conn.commit();

        stmt.close();
        instance.close();

        in.close();
    }

    /**
     * Accuracy test for constructor.
     */
    public void testCtor() {
        assertNotNull("The result should match.", instance);
        assertTrue("The result should match.",
            instance instanceof ReliabilityDataPersistence);
    }

    /**
     * Accuracy test for <code>configure()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testConfigure() throws Exception {
        Field field = instance.getClass().getDeclaredField("log");
        field.setAccessible(true);

        Field connectionNameField = instance.getClass()
                                            .getDeclaredField("connectionName");
        connectionNameField.setAccessible(true);

        Field factoryField = instance.getClass()
                                     .getDeclaredField("dbConnectionFactory");
        factoryField.setAccessible(true);

        assertNotNull("the log should be set.", field.get(instance));
        assertNotNull("the log should be set.",
            connectionNameField.get(instance));
        assertNotNull("the log should be set.", factoryField.get(instance));
    }

    /**
     * Accuracy test for <code>open()</code> and <code>close()</code>.
     * @throws Exception if any error occurs
     */
    public void testOpenAndClose() throws Exception {
        instance.open();

        instance.close();
    }

    /**
     * Accuracy test for <code>getIdsOfUsersWithReliability()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testGetIdsOfUsersWithReliability() throws Exception {
        instance.open();

        try {
            Calendar cal = new GregorianCalendar();
            cal.set(1990, 1, 1, 0, 0);

            Date d = cal.getTime();

            List<Long> result = instance.getIdsOfUsersWithReliability(2, d);

            assertEquals("The result should match.", 0, result.size());
        } finally {
            instance.close();
        }
    }

    /**
     * Accuracy test for <code>getUserParticipationData()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testGetUserParticipationData() throws Exception {
        instance.open();

        try {
            Calendar cal = new GregorianCalendar();
            cal.set(1990, 1, 1, 0, 0);

            Date d = cal.getTime();

            List<UserProjectParticipationData> result = instance.getUserParticipationData(2,
                    2, d);

            assertEquals("The result should match.", 0, result.size());
        } finally {
            instance.close();
        }
    }

    /**
     * Accuracy test for <code>saveUserReliabilityData()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testSaveUserReliabilityData() throws Exception {
        instance.open();

        try {
            List<UserProjectReliabilityData> list = new ArrayList<UserProjectReliabilityData>();

            UserProjectReliabilityData data1 = new UserProjectReliabilityData();
            data1.setProjectId(1);
            data1.setUserId(123);
            data1.setReliable(true);
            data1.setResolutionDate(new Date());
            data1.setReliabilityOnRegistration(0.2D);
            list.add(data1);

            instance.saveUserReliabilityData(list);
        } finally {
            instance.close();
        }
    }

    /**
     * Accuracy test for <code>updateCurrentUserReliability()</code>.
     *
     * @throws Exception if any error occurs
     */
    public void testUpdateCurrentUserReliability() throws Exception {
        instance.open();

        try {
            instance.updateCurrentUserReliability(123, 10, 0.6);

            Field connection = instance.getClass().getDeclaredField("connection");
            connection.setAccessible(true);

            Connection conn = (Connection) connection.get(instance);
            PreparedStatement pstmt = conn.prepareStatement(
                    "select rating from user_reliability where user_id = ?");
            pstmt.setLong(1, 123);

            ResultSet rs = pstmt.executeQuery();
            Assert.assertTrue("The data should be exist", rs.next());
            Assert.assertTrue("Incorrect rating", rs.getDouble("rating") == 0.6);

            rs.close();
            pstmt.close();

            instance.updateCurrentUserReliability(123, 10, 1.0);

            pstmt = conn.prepareStatement(
                    "select rating from user_reliability where user_id = ?");
            pstmt.setLong(1, 123);

            rs = pstmt.executeQuery();
            Assert.assertTrue("The data should be exist", rs.next());
            Assert.assertTrue("Incorrect rating", rs.getDouble("rating") == 1.0);
        } finally {
            instance.close();
        }
    }
}
