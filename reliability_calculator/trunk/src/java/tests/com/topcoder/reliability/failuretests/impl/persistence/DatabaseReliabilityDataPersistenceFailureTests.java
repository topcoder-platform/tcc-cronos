/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.failuretests.impl.persistence;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.failuretests.FailureTestHelper;
import com.topcoder.reliability.impl.ReliabilityDataPersistenceException;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.persistence.DatabaseReliabilityDataPersistence;

/**
 * Failure tests for DatabaseReliabilityDataPersistence class.
 * @author Yeung
 * @version 1.0
 */
public class DatabaseReliabilityDataPersistenceFailureTests {

    /**
     * The DatabaseReliabilityDataPersistence instance to test against.
     */
    private DatabaseReliabilityDataPersistence instance;

    /**
     * The configuration object for testing.
     */
    private ConfigurationObject config;

    /**
     * Sets up the test environment.
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        config = FailureTestHelper.getConfig("DatabaseReliabilityDataPersistence");
        instance = new DatabaseReliabilityDataPersistence();
        instance.configure(config);
        instance.setIncludedProjectStatuses(new ArrayList < Long >() {

            {
                add(1L);
            }
        });
    }

    /**
     * Tears down the test environment.
     * @throws Exception to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        try {
            instance.configure(config);
        } catch (Exception e) {
            // Ignore
        }
    }

    /**
     * Tests the method configure with null config. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConfigureWithNullConfig() throws Exception {
        instance.configure(null);
    }

    /**
     * Tests the method configure with non-string 'loggerName'. Expect ReliabilityCalculatorConfigurationException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithNonStringLoggerName() throws Exception {
        config.setPropertyValue("loggerName", 1);
        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'loggerName'. Expect ReliabilityCalculatorConfigurationException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyLoggerName() throws Exception {
        config.setPropertyValue("loggerName", " \t \n ");
        instance.configure(config);
    }

    /**
     * Tests the method configure with non-string 'connectionName'. Expect ReliabilityCalculatorConfigurationException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithNonStringConnectionName() throws Exception {
        config.setPropertyValue("connectionName", 1);
        instance.configure(config);
    }

    /**
     * Tests the method configure with empty 'connectionName'. Expect ReliabilityCalculatorConfigurationException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithEmptyConnectionName() throws Exception {
        config.setPropertyValue("connectionName", " \t \n ");
        instance.configure(config);
    }

    /**
     * Tests the method configure with missing 'dbConnectionFactoryConfig'. Expect
     * ReliabilityCalculatorConfigurationException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void testConfigureWithMissingDbConnectionFactoryConfig() throws Exception {
        config.removeChild("dbConnectionFactoryConfig");
        instance.configure(config);
    }

    /**
     * Tests the method open with twice call. Expect IllegalStateException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testOpenWithTwiceCall() throws Exception {
        instance.open();
        instance.open();
    }

    /**
     * Tests the method open without configured. Expect IllegalStateException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testOpenWithoutConfigured() throws Exception {
        new DatabaseReliabilityDataPersistence().open();
    }

    /**
     * Tests the method open with persistence error. Expect ReliabilityDataPersistenceException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void testOpenWithPersistenceError() throws Exception {
        config.getChild("dbConnectionFactoryConfig")
                .getChild("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").getChild("connections")
                .getChild("informix_connection").getChild("parameters").setPropertyValue("jdbc_url", "invalid");
        instance.configure(config);
        instance.open();
    }

    /**
     * Tests the method close with twice call. Expect IllegalStateException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testCloseWithTwiceCall() throws Exception {
        instance.open();
        instance.close();
        instance.close();
    }

    /**
     * Tests the method getIdsOfUsersWithReliability with zero projectCategoryId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetIdsOfUsersWithReliabilityWithZeroProjectCategoryId() throws Exception {
        instance.open();
        instance.getIdsOfUsersWithReliability(0, new Date());
    }

    /**
     * Tests the method getIdsOfUsersWithReliability with negative projectCategoryId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetIdsOfUsersWithReliabilityWithNegativeProjectCategoryId() throws Exception {
        instance.open();
        instance.getIdsOfUsersWithReliability(-1, new Date());
    }

    /**
     * Tests the method getIdsOfUsersWithReliability with null startDate. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetIdsOfUsersWithReliabilityWithNullStartDate() throws Exception {
        instance.open();
        instance.getIdsOfUsersWithReliability(1, null);
    }

    /**
     * Tests the method getIdsOfUsersWithReliability without open. Expect IllegalStateException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testGetIdsOfUsersWithReliabilityWithoutOpen() throws Exception {
        instance.getIdsOfUsersWithReliability(1, new Date());
    }

    /**
     * Tests the method getIdsOfUsersWithReliability with persistence error. Expect
     * ReliabilityDataPersistenceException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void testGetIdsOfUsersWithReliabilityWithPersistenceError() throws Exception {
        instance.open();
        ((Connection) FailureTestHelper.getField(instance, "connection")).close();
        instance.getIdsOfUsersWithReliability(1, new Date());
    }

    /**
     * Tests the method getUserParticipationData with zero projectCategoryId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetUserParticipationDataWithZeroProjectCategoryId() throws Exception {
        instance.open();
        instance.getUserParticipationData(1, 0, new Date());
    }

    /**
     * Tests the method getUserParticipationData with negative projectCategoryId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetUserParticipationDataWithNegativeProjectCategoryId() throws Exception {
        instance.open();
        instance.getUserParticipationData(1, -1, new Date());
    }

    /**
     * Tests the method getUserParticipationData with zero userId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetUserParticipationDataWithZeroUserId() throws Exception {
        instance.open();
        instance.getUserParticipationData(0, 1, new Date());
    }

    /**
     * Tests the method getUserParticipationData with negative userId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetUserParticipationDataWithNegativeUserId() throws Exception {
        instance.open();
        instance.getUserParticipationData(-1, 1, new Date());
    }

    /**
     * Tests the method getUserParticipationData with null startDate. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetUserParticipationDataWithNullStartDate() throws Exception {
        instance.open();
        instance.getUserParticipationData(1, 1, null);
    }

    /**
     * Tests the method getUserParticipationData without open. Expect IllegalStateException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testGetUserParticipationDataWithoutOpen() throws Exception {
        instance.getUserParticipationData(1, 1, new Date());
    }

    /**
     * Tests the method getUserParticipationData with persistence error. Expect ReliabilityDataPersistenceException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void testGetUserParticipationDataWithPersistenceError() throws Exception {
        instance.open();
        ((Connection) FailureTestHelper.getField(instance, "connection")).close();
        instance.getUserParticipationData(1, 1, new Date());
    }

    /**
     * Tests the method updateCurrentUserReliability with zero projectCategoryId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCurrentUserReliabilityWithZeroProjectCategoryId() throws Exception {
        instance.open();
        instance.updateCurrentUserReliability(1, 0, 0.5);
    }

    /**
     * Tests the method updateCurrentUserReliability with negative projectCategoryId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCurrentUserReliabilityWithNegativeProjectCategoryId() throws Exception {
        instance.open();
        instance.updateCurrentUserReliability(1, -1, 0.5);
    }

    /**
     * Tests the method updateCurrentUserReliability with zero userId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCurrentUserReliabilityWithZeroUserId() throws Exception {
        instance.open();
        instance.updateCurrentUserReliability(0, 1, 0.5);
    }

    /**
     * Tests the method updateCurrentUserReliability with negative userId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCurrentUserReliabilityWithNegativeUserId() throws Exception {
        instance.open();
        instance.updateCurrentUserReliability(-1, 1, 0.5);
    }

    /**
     * Tests the method updateCurrentUserReliability with negative reliability. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCurrentUserReliabilityWithNegativeReliability() throws Exception {
        instance.open();
        instance.updateCurrentUserReliability(1, 1, -1);
    }

    /**
     * Tests the method updateCurrentUserReliability with reliability which is greater than 1. Expect
     * IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateCurrentUserReliabilityWithReliabilityGreaterThan1() throws Exception {
        instance.open();
        instance.updateCurrentUserReliability(1, 1, 1.1);
    }

    /**
     * Tests the method updateCurrentUserReliability without open. Expect IllegalStateException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testUpdateCurrentUserReliabilityWithoutOpen() throws Exception {
        instance.updateCurrentUserReliability(1, 1, 0.5);
    }

    /**
     * Tests the method updateCurrentUserReliability with persistence error. Expect
     * ReliabilityDataPersistenceException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void testUpdateCurrentUserReliabilityWithPersistenceError() throws Exception {
        instance.open();
        ((Connection) FailureTestHelper.getField(instance, "connection")).close();
        instance.updateCurrentUserReliability(1, 1, 0.5);
    }

    /**
     * Tests the method saveUserReliabilityData with null projects. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSaveUserReliabilityDataWithNullProjects() throws Exception {
        instance.saveUserReliabilityData(null);
    }

    /**
     * Tests the method saveUserReliabilityData with empty projects. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSaveUserReliabilityDataWithEmptyProjects() throws Exception {
        instance.saveUserReliabilityData(new ArrayList < UserProjectReliabilityData >());
    }

    /**
     * Tests the method saveUserReliabilityData with null project. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSaveUserReliabilityDataWithNullProject() throws Exception {
        List < UserProjectReliabilityData > list = new ArrayList < UserProjectReliabilityData >();
        UserProjectReliabilityData data1 = new UserProjectReliabilityData();
        UserProjectReliabilityData data2 = new UserProjectReliabilityData();
        data1.setUserId(1);
        data2.setUserId(1);
        data1.setResolutionDate(new Date());
        data2.setResolutionDate(new Date());
        list.add(data1);
        list.add(data2);
        list.add(null);
        instance.saveUserReliabilityData(list);
    }

    /**
     * Tests the method saveUserReliabilityData with null resolutionDate. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSaveUserReliabilityDataWithNullResolutionDate() throws Exception {
        List < UserProjectReliabilityData > list = new ArrayList < UserProjectReliabilityData >();
        UserProjectReliabilityData data1 = new UserProjectReliabilityData();
        UserProjectReliabilityData data2 = new UserProjectReliabilityData();
        data1.setUserId(1);
        data2.setUserId(1);
        data1.setResolutionDate(new Date());
        data2.setResolutionDate(null);
        list.add(data1);
        list.add(data2);
        instance.saveUserReliabilityData(list);
    }

    /**
     * Tests the method saveUserReliabilityData with different userId. Expect IllegalArgumentException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSaveUserReliabilityDataWithDifferentUserId() throws Exception {
        List < UserProjectReliabilityData > list = new ArrayList < UserProjectReliabilityData >();
        UserProjectReliabilityData data1 = new UserProjectReliabilityData();
        UserProjectReliabilityData data2 = new UserProjectReliabilityData();
        data1.setUserId(1);
        data2.setUserId(2);
        data1.setResolutionDate(new Date());
        data2.setResolutionDate(new Date());
        list.add(data1);
        list.add(data2);
        instance.saveUserReliabilityData(list);
    }

    /**
     * Tests the method saveUserReliabilityData without open. Expect IllegalStateException.
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void testSaveUserReliabilityDataWithoutOpen() throws Exception {
        List < UserProjectReliabilityData > list = new ArrayList < UserProjectReliabilityData >();
        UserProjectReliabilityData data1 = new UserProjectReliabilityData();
        UserProjectReliabilityData data2 = new UserProjectReliabilityData();
        data1.setUserId(1);
        data2.setUserId(1);
        data1.setResolutionDate(new Date());
        data2.setResolutionDate(new Date());
        list.add(data1);
        list.add(data2);
        instance.saveUserReliabilityData(list);
    }

    /**
     * Tests the method saveUserReliabilityData with persistence error. Expect ReliabilityDataPersistenceException.
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void testSaveUserReliabilityDataWithPersistenceError() throws Exception {
        List < UserProjectReliabilityData > list = new ArrayList < UserProjectReliabilityData >();
        UserProjectReliabilityData data1 = new UserProjectReliabilityData();
        UserProjectReliabilityData data2 = new UserProjectReliabilityData();
        data1.setUserId(1);
        data2.setUserId(1);
        data1.setResolutionDate(new Date());
        data2.setResolutionDate(new Date());
        list.add(data1);
        list.add(data2);
        instance.open();
        ((Connection) FailureTestHelper.getField(instance, "connection")).close();
        instance.saveUserReliabilityData(list);
    }
}
