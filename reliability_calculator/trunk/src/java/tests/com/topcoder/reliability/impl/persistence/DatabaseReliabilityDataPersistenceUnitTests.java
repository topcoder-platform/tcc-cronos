/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.reliability.ReliabilityCalculatorConfigurationException;
import com.topcoder.reliability.TestsHelper;
import com.topcoder.reliability.impl.ReliabilityDataPersistenceException;
import com.topcoder.reliability.impl.UserProjectParticipationData;
import com.topcoder.reliability.impl.UserProjectReliabilityData;

/**
 * <p>
 * Unit tests for {@link DatabaseReliabilityDataPersistence} class.
 * </p>
 * @author sparemax
 * @version 1.0
 */
public class DatabaseReliabilityDataPersistenceUnitTests {

    /**
     * <p>
     * Represents the <code>DatabaseReliabilityDataPersistence</code> instance used in tests.
     * </p>
     */
    private DatabaseReliabilityDataPersistence instance;

    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Represents the user id used in tests.
     * </p>
     */
    private long userId = 132456;

    /**
     * <p>
     * Represents the project category id used in tests.
     * </p>
     */
    private long projectCategoryId = 2;

    /**
     * <p>
     * Represents the start date used in tests.
     * </p>
     */
    private Date startDate;

    /**
     * <p>
     * Represents the projects used in tests.
     * </p>
     */
    private List < UserProjectReliabilityData > projects;

    /**
     * <p>
     * Represents the reliability used in tests.
     * </p>
     */
    private double reliability = 0.1;

    /**
     * <p>
     * Represents the connection used in tests.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents included project statuses for testing.
     * </p>
     */
    private List < Long > includedProjectStatuses;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DatabaseReliabilityDataPersistenceUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        connection = TestsHelper.getConnection();
        TestsHelper.clearDB(connection);
        TestsHelper.loadDB(connection);
        config =
                TestsHelper.getConfig().getChild("reliabilityCalculator1").getChild("config")
                        .getChild("reliabilityDataPersistenceConfig");
        instance = new DatabaseReliabilityDataPersistence();
        instance.configure(config);
        includedProjectStatuses = new ArrayList < Long >();
        includedProjectStatuses.add(1L);
        includedProjectStatuses.add(2L);
        instance.setIncludedProjectStatuses(includedProjectStatuses);
        instance.open();
        startDate = TestsHelper.TIMESTAMP_FORMAT.parse("2005-10-05 09:00");
        projects = new ArrayList < UserProjectReliabilityData >();
        UserProjectReliabilityData project = new UserProjectReliabilityData();
        project.setProjectId(1);
        project.setResolutionDate(new Date());
        project.setReliabilityAfterResolution(1.0);
        project.setReliabilityBeforeResolution(0.5);
        project.setReliabilityOnRegistration(0.2);
        project.setReliable(true);
        project.setUserId(userId);
        projects.add(project);
        project = new UserProjectReliabilityData();
        project.setProjectId(2);
        project.setResolutionDate(new Date());
        projects.add(project);
        project.setUserId(userId);
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     * @throws Exception to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        if (includedProjectStatuses != null) {
            includedProjectStatuses.clear();
            includedProjectStatuses = null;
        }
        if (projects != null) {
            projects.clear();
            projects = null;
        }
        TestsHelper.clearDB(connection);
        TestsHelper.closeConnection(connection);
        if (instance != null) {
            try {
                // Close the connection to persistence
                instance.close();
            } catch (IllegalStateException e) {
                // Ignore
            }
        }
        connection = null;
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DatabaseReliabilityDataPersistence()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DatabaseReliabilityDataPersistence();
        assertNull("'log' should be correct.", TestsHelper.getField(instance, "log"));
        assertNull("'dbConnectionFactory' should be correct.", TestsHelper.getField(instance, "dbConnectionFactory"));
        assertNull("'connectionName' should be correct.", TestsHelper.getField(instance, "connectionName"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject config)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_configure_1() {
        instance.configure(config);
        assertNotNull("'configure' should be correct.", TestsHelper.getField(instance, "log"));
        assertNotNull("'dbConnectionFactory' should be correct.",
                TestsHelper.getField(instance, "dbConnectionFactory"));
        assertEquals("'connectionName' should be correct.", "myConnection",
                TestsHelper.getField(instance, "connectionName"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject config)</code>.<br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_configure_2() throws Exception {
        config.removeProperty("loggerName");
        config.removeProperty("connectionName");
        instance.configure(config);
        assertNull("'configure' should be correct.", TestsHelper.getField(instance, "log"));
        assertNotNull("'dbConnectionFactory' should be correct.",
                TestsHelper.getField(instance, "dbConnectionFactory"));
        assertNull("'connectionName' should be correct.", TestsHelper.getField(instance, "connectionName"));
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with config is <code>null</code>
     * .<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configure_configNull() {
        config = null;
        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'loggerName' is not a
     * string.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_loggerNameNotString() throws Exception {
        config.setPropertyValue("loggerName", 1);
        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'loggerName' is empty.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_loggerNameEmpty() throws Exception {
        config.setPropertyValue("loggerName", TestsHelper.EMPTY_STRING);
        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'dbConnectionFactoryConfig'
     * is missing.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigMissing() throws Exception {
        config.removeChild("dbConnectionFactoryConfig");
        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'dbConnectionFactoryConfig'
     * is invalid.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigInvalid1() throws Exception {
        config.getChild("dbConnectionFactoryConfig").removeChild(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'dbConnectionFactoryConfig'
     * is invalid.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigInvalid2() throws Exception {
        config.getChild("dbConnectionFactoryConfig")
                .getChild("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").getChild("connections")
                .setPropertyValue("default", "invalid");
        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'connectionName' is not a
     * string.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_connectionNameNotString() throws Exception {
        config.setPropertyValue("connectionName", 1);
        instance.configure(config);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject config)</code> with 'connectionName' is empty.<br>
     * <code>ReliabilityCalculatorConfigurationException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityCalculatorConfigurationException.class)
    public void test_configure_connectionNameEmpty() throws Exception {
        config.setPropertyValue("connectionName", TestsHelper.EMPTY_STRING);
        instance.configure(config);
    }

    /**
     * <p>
     * Accuracy test for the method <code>open()</code>.<br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_open_1() throws Exception {
        assertNotNull("'open' should be correct.", TestsHelper.getField(instance, "connection"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>open()</code>.<br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_open_2() throws Exception {
        instance.close();
        config.removeProperty("connectionName");
        instance.configure(config);
        instance.open();
        assertNotNull("'open' should be correct.", TestsHelper.getField(instance, "connection"));
    }

    /**
     * <p>
     * Failure test for the method <code>open()</code> with connection is already opened.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_open_AlreadyOpened() throws Exception {
        instance.open();
    }

    /**
     * <p>
     * Failure test for the method <code>open()</code> with this persistence was not properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_open_NotConfigured() throws Exception {
        instance.close();
        instance = new DatabaseReliabilityDataPersistence();
        instance.open();
    }

    /**
     * <p>
     * Failure test for the method <code>open()</code> with an error occurred.<br>
     * <code>ReliabilityDataPersistenceException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void test_open_Error() throws Exception {
        instance.close();
        config.setPropertyValue("connectionName", "unknown_name");
        instance.configure(config);
        instance.open();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>.
     * <br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getIdsOfUsersWithReliability_1() throws Exception {
        List < Long > res = instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
        assertEquals("'getIdsOfUsersWithReliability' should be correct.", 1, res.size());
        assertEquals("'getIdsOfUsersWithReliability' should be correct.", 132456L, res.get(0).longValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>.
     * <br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getIdsOfUsersWithReliability_2() throws Exception {
        TestsHelper.clearDB(connection);
        List < Long > res = instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
        assertEquals("'getIdsOfUsersWithReliability' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>
     * with projectCategoryId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getIdsOfUsersWithReliability_projectCategoryIdNegative() throws Exception {
        projectCategoryId = -1;
        instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>
     * with projectCategoryId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getIdsOfUsersWithReliability_projectCategoryIdZero() throws Exception {
        projectCategoryId = 0;
        instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>
     * with startDate is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getIdsOfUsersWithReliability_startDateNull() throws Exception {
        startDate = null;
        instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>
     * with persistence connection is not opened.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_getIdsOfUsersWithReliability_NotOpened() throws Exception {
        instance.close();
        instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>
     * with this persistence was not properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_getIdsOfUsersWithReliability_NotConfigured() throws Exception {
        instance.close();
        instance = new DatabaseReliabilityDataPersistence();
        instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>
     * with an error occurred.<br>
     * <code>ReliabilityDataPersistenceException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void test_getIdsOfUsersWithReliability_Error() throws Exception {
        ((Connection) TestsHelper.getField(instance, "connection")).close();
        instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code>. <br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getUserParticipationData_1() throws Exception {
        List < UserProjectParticipationData > res =
                instance.getUserParticipationData(userId, projectCategoryId, startDate);
        assertEquals("'getUserParticipationData' should be correct.", 5, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code>. <br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getUserParticipationData_2() throws Exception {
        TestsHelper.clearDB(connection);
        List < UserProjectParticipationData > res =
                instance.getUserParticipationData(userId, projectCategoryId, startDate);
        assertEquals("'getUserParticipationData' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code> with userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getUserParticipationData_userIdNegative() throws Exception {
        userId = -1;
        instance.getUserParticipationData(userId, projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getUserParticipationData_userIdZero() throws Exception {
        userId = 0;
        instance.getUserParticipationData(userId, projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code> with projectCategoryId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getUserParticipationData_projectCategoryIdNegative() throws Exception {
        projectCategoryId = -1;
        instance.getUserParticipationData(userId, projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code>with projectCategoryId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getUserParticipationData_projectCategoryIdZero() throws Exception {
        projectCategoryId = 0;
        instance.getUserParticipationData(userId, projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code>with startDate is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getUserParticipationData_startDateNull() throws Exception {
        startDate = null;
        instance.getUserParticipationData(userId, projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code> with persistence connection is not opened.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_getUserParticipationData_NotOpened() throws Exception {
        instance.close();
        instance.getUserParticipationData(userId, projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code> with this persistence was not properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_getUserParticipationData_NotConfigured() throws Exception {
        instance.close();
        instance = new DatabaseReliabilityDataPersistence();
        instance.getUserParticipationData(userId, projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code> with an error occurred.<br>
     * <code>ReliabilityDataPersistenceException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void test_getUserParticipationData_Error() throws Exception {
        ((Connection) TestsHelper.getField(instance, "connection")).close();
        instance.getUserParticipationData(userId, projectCategoryId, startDate);
    }

    /**
     * <p>
     * Accuracy test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code>. <br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_saveUserReliabilityData_1() throws Exception {
        instance.saveUserReliabilityData(projects);
        assertEquals("'saveUserReliabilityData' should be correct.", 10,
                TestsHelper.getRowsCount(connection, "project_reliability"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code>. <br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_saveUserReliabilityData_2() throws Exception {
        projects.remove(1);
        instance.saveUserReliabilityData(projects);
        assertEquals("'saveUserReliabilityData' should be correct.", 9,
                TestsHelper.getRowsCount(connection, "project_reliability"));
    }

    /**
     * <p>
     * Failure test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code> with projects is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_saveUserReliabilityData_projectsNull() throws Exception {
        projects = null;
        instance.saveUserReliabilityData(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code> with projects is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_saveUserReliabilityData_projectsEmpty() throws Exception {
        projects.clear();
        instance.saveUserReliabilityData(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code> with projects contains <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_saveUserReliabilityData_projectsContainsNull() throws Exception {
        projects.add(null);
        instance.saveUserReliabilityData(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code> with projects contains element with resolutionDate is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_saveUserReliabilityData_resolutionDateNull() throws Exception {
        projects.get(0).setResolutionDate(null);
        instance.saveUserReliabilityData(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code> with projects contains elements in the list have different user id.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_saveUserReliabilityData_DifferentUserId() throws Exception {
        projects.get(0).setUserId(Long.MAX_VALUE);
        instance.saveUserReliabilityData(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code> with persistence connection is not opened.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_saveUserReliabilityData_NotOpened() throws Exception {
        instance.close();
        instance.saveUserReliabilityData(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code> with this persistence was not properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_saveUserReliabilityData_NotConfigured() throws Exception {
        instance.close();
        instance = new DatabaseReliabilityDataPersistence();
        instance.saveUserReliabilityData(projects);
    }

    /**
     * <p>
     * Failure test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code> with an error occurred.<br>
     * <code>ReliabilityDataPersistenceException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void test_saveUserReliabilityData_Error() throws Exception {
        ((Connection) TestsHelper.getField(instance, "connection")).close();
        instance.saveUserReliabilityData(projects);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code>. <br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_updateCurrentUserReliability_1() throws Exception {
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
        userId = 2;
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
        assertEquals("'updateCurrentUserReliability' should be correct.", 2,
                TestsHelper.getRowsCount(connection, "user_reliability"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code>. <br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_updateCurrentUserReliability_2() throws Exception {
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
        assertEquals("'updateCurrentUserReliability' should be correct.", 1,
                TestsHelper.getRowsCount(connection, "user_reliability"));
    }

    /**
     * <p>
     * Failure test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code> with userId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateCurrentUserReliability_userIdNegative() throws Exception {
        userId = -1;
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
    }

    /**
     * <p>
     * Failure test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code> with userId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateCurrentUserReliability_userIdZero() throws Exception {
        userId = 0;
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
    }

    /**
     * <p>
     * Failure test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code> with projectCategoryId is negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateCurrentUserReliability_projectCategoryIdNegative() throws Exception {
        projectCategoryId = -1;
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
    }

    /**
     * <p>
     * Failure test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code> with projectCategoryId is zero.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateCurrentUserReliability_projectCategoryIdZero() throws Exception {
        projectCategoryId = 0;
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
    }

    /**
     * <p>
     * Failure test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code> with reliability is invalid.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateCurrentUserReliability_reliabilityInvalid1() throws Exception {
        reliability = -0.01;
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
    }

    /**
     * <p>
     * Failure test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code> with reliability is invalid.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_updateCurrentUserReliability_reliabilityInvalid2() throws Exception {
        reliability = 1.01;
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
    }

    /**
     * <p>
     * Failure test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code> with persistence connection is not opened.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_updateCurrentUserReliability_NotOpened() throws Exception {
        instance.close();
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
    }

    /**
     * <p>
     * Failure test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code> with this persistence was not properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_updateCurrentUserReliability_NotConfigured() throws Exception {
        instance.close();
        instance = new DatabaseReliabilityDataPersistence();
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
    }

    /**
     * <p>
     * Failure test for the method <code>updateCurrentUserReliability(long userId, long projectCategoryId,
     * double reliability)</code> with an error occurred.<br>
     * <code>ReliabilityDataPersistenceException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = ReliabilityDataPersistenceException.class)
    public void test_updateCurrentUserReliability_Error() throws Exception {
        ((Connection) TestsHelper.getField(instance, "connection")).close();
        instance.updateCurrentUserReliability(userId, projectCategoryId, reliability);
    }

    /**
     * <p>
     * Accuracy test for the method <code>open()</code>.<br>
     * The result should be correct.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_close() throws Exception {
        instance.close();
        assertNull("'close' should be correct.", TestsHelper.getField(instance, "connection"));
    }

    /**
     * <p>
     * Failure test for the method <code>close()</code> with connection is already closed.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_close_AlreadyClosed() throws Exception {
        instance.close();
        // Again
        instance.close();
    }

    /**
     * <p>
     * Failure test for the method <code>close()</code> with this persistence was not properly configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_close_NotConfigured() throws Exception {
        instance.close();
        instance = new DatabaseReliabilityDataPersistence();
        instance.close();
    }

    /**
     * <p>
     * Accuracy test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>.
     * </p>
     * <p>
     * Only projects with status 1 present in DB and we try to retrieve projects one of this project status.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getIdsOfUsersWithReliability_StatusIncluded() throws Exception {
        includedProjectStatuses.add(3L);
        instance.setIncludedProjectStatuses(includedProjectStatuses);
        List < Long > res = instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
        assertEquals("'getIdsOfUsersWithReliability' should be correct.", 1, res.size());
        assertEquals("'getIdsOfUsersWithReliability' should be correct.", 132456L, res.get(0).longValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>.
     * <br>
     * Only projects with status 1 present in DB and we try to retrieve projects with status 33.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getIdsOfUsersWithReliability_StatusNotIncluded() throws Exception {
        includedProjectStatuses.clear();
        includedProjectStatuses.add(33L);
        instance.setIncludedProjectStatuses(includedProjectStatuses);
        List < Long > res = instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
        assertEquals("'getIdsOfUsersWithReliability' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code>.
     * </p>
     * <p>
     * Only projects with status 1 present in DB and we try to retrieve 6 projects of this project status.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getUserParticipationData_StatusIncluded() throws Exception {
        List < UserProjectParticipationData > res =
                instance.getUserParticipationData(userId, projectCategoryId, startDate);
        assertEquals("'getUserParticipationData' should be correct.", 5, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserParticipationData(long userId, long projectCategoryId,
     * Date startDate)</code>.
     * </p>
     * <p>
     * Only projects with status 1 present in DB and we try to retrieve 6 projects of this project status.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getUserParticipationData_StatusNotIncluded() throws Exception {
        includedProjectStatuses.clear();
        includedProjectStatuses.add(33L);
        List < UserProjectParticipationData > res =
                instance.getUserParticipationData(userId, projectCategoryId, startDate);
        assertEquals("'getUserParticipationData' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code>.
     * </p>
     * <p>
     * Actually we pass only one project of user 1 from category 2, all previous projects should be left.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_saveUserReliabilityData_AllUserProjects() throws Exception {
        int projectId = 9;
        TestsHelper.insertProject(connection, projectId, 2);
        projects = new ArrayList < UserProjectReliabilityData >();
        UserProjectReliabilityData userProjectReliabilityData = new UserProjectReliabilityData();
        userProjectReliabilityData.setProjectId(projectId);
        userProjectReliabilityData.setReliabilityAfterResolution(0.8);
        userProjectReliabilityData.setReliabilityBeforeResolution(0.5);
        userProjectReliabilityData.setReliabilityOnRegistration(0.3);
        userProjectReliabilityData.setUserId(userId);
        userProjectReliabilityData.setReliable(true);
        userProjectReliabilityData.setResolutionDate(new Date());
        projects.add(userProjectReliabilityData);
        instance.saveUserReliabilityData(projects);
        assertEquals("'saveUserReliabilityData' should be correct.", 9,
                TestsHelper.getRowsCount(connection, "project_reliability"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>saveUserReliabilityData(List&lt;UserProjectReliabilityData&gt;
     * projects)</code>. <br>
     * </p>
     * <p>
     * Project reliability of user with id 2 should be saved and previous 8 projects still exists in table.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_saveUserReliabilityData_Saved() throws Exception {
        projects = new ArrayList < UserProjectReliabilityData >();
        UserProjectReliabilityData userProjectReliabilityData = new UserProjectReliabilityData();
        userProjectReliabilityData.setProjectId(1);
        userProjectReliabilityData.setReliabilityAfterResolution(0.8);
        userProjectReliabilityData.setReliabilityBeforeResolution(0.5);
        userProjectReliabilityData.setReliabilityOnRegistration(0.3);
        userProjectReliabilityData.setUserId(2);
        userProjectReliabilityData.setReliable(true);
        userProjectReliabilityData.setResolutionDate(new Date());
        projects.add(userProjectReliabilityData);
        instance.saveUserReliabilityData(projects);
        assertEquals("'saveUserReliabilityData' should be correct.", 9,
                TestsHelper.getRowsCount(connection, "project_reliability"));
    }

    /**
     * <p>
     * Failure test for the method <code>getIdsOfUsersWithReliability(long projectCategoryId, Date startDate)</code>.
     * </p>
     * <p>
     * includedProjectStatuses are not set. <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_getIdsOfUsersWithReliability_NoSetStatuses() throws Exception {
        instance = new DatabaseReliabilityDataPersistence();
        instance.configure(config);
        instance.open();
        instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>getUserParticipationData(long projectCategoryId, Date startDate)</code>.
     * </p>
     * <p>
     * includedProjectStatuses are not set. <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_getUserParticipationData_NoSetStatuses() throws Exception {
        instance = new DatabaseReliabilityDataPersistence();
        instance.configure(config);
        instance.open();
        instance.getIdsOfUsersWithReliability(projectCategoryId, startDate);
    }

    /**
     * <p>
     * Failure test for the method <code>setIncludedProjectStatuses(List<Long> includedProjectStatuses)</code>.
     * </p>
     * <p>
     * includedProjectStatuses are not set. <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_setIncludedProjectStatuses_Null() throws Exception {
        instance.setIncludedProjectStatuses(null);
    }

    /**
     * <p>
     * Accuracy test for the method <code>deleteReliabilityData(long projectCategoryId)</code> with projectCategoryId
     * is 2.<br>
     * All except 1 rows should be removed.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_deleteReliabilityData_1() throws Exception {
        projectCategoryId = 2;
        instance.deleteReliabilityData(projectCategoryId);
        assertEquals("All projects for category 2 should be removed.", 1,
                TestsHelper.getRowsCount(connection, "project_reliability"));
    }
    
    /**
     * <p>
     * Accuracy test for the method <code>deleteReliabilityData(long projectCategoryId)</code> with projectCategoryId
     * is 3.<br>
     * Only one row should be removed.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_deleteReliabilityData_2() throws Exception {
        projectCategoryId = 3;
        instance.deleteReliabilityData(projectCategoryId);
        assertEquals("All projects for category 3 should be removed.", 7,
                TestsHelper.getRowsCount(connection, "project_reliability"));
    }
    
    /**
     * <p>
     * Accuracy test for the method <code>deleteReliabilityData(long projectCategoryId)</code> with projectCategoryId
     * is 1.<br>
     * No rows should be removed.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void test_deleteReliabilityData_3() throws Exception {
        projectCategoryId = 1;
        instance.deleteReliabilityData(projectCategoryId);
        assertEquals("All projects should be preserved.", 8,
                TestsHelper.getRowsCount(connection, "project_reliability"));
    }

    /**
     * <p>
     * Failure test for the method <code>deleteReliabilityData(long projectCategoryId)</code> with projectCategoryId is
     * negative.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_deleteReliabilityData_projectCategoryIdNegative() throws Exception {
        projectCategoryId = -1;
        instance.deleteReliabilityData(projectCategoryId);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteReliabilityData(long projectCategoryId)</code> with closed connection.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_deleteReliabilityData_NotOpened() throws Exception {
        instance.close();
        instance.deleteReliabilityData(projectCategoryId);
    }

    /**
     * <p>
     * Failure test for the method <code>deleteReliabilityData(long projectCategoryId)</code> with not configured
     * DatabaseReliabilityDataPersistence.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_deleteReliabilityData_NotConfigured() throws Exception {
        instance.close();
        instance = new DatabaseReliabilityDataPersistence();
        instance.deleteReliabilityData(projectCategoryId);
    }
}