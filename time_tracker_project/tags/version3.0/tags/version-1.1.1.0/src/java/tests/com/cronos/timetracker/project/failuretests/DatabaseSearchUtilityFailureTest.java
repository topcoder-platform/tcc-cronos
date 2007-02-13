/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.project.failuretests;

import java.sql.Connection;

import junit.framework.TestCase;

import com.cronos.timetracker.project.ConfigurationException;
import com.cronos.timetracker.project.persistence.DatabaseSearchUtility;
import com.cronos.timetracker.project.persistence.PersistenceException;
import com.cronos.timetracker.project.searchfilters.CompareOperation;
import com.cronos.timetracker.project.searchfilters.Filter;
import com.cronos.timetracker.project.searchfilters.ValueFilter;

/**
 * Failure unit tests for {@link com.cronos.timetracker.project.persistence.DatabaseSearchUtility} class.
 *
 * @author kr00tki
 * @version 1.1
 */
public class DatabaseSearchUtilityFailureTest extends TestCase {
    /**
     * Configuration namespace.
     */
    private static final String NAMESPACE = DatabaseSearchUtilityFailureTest.class.getName();

    /**
     * Database connection used in tests,
     */
    private Connection connection = null;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfig();
        connection = FailureTestHelper.getConnection();
    }

    /**
     * Clears after tests.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.closeConnection(connection);
        FailureTestHelper.unloadConfig();
    }

    /**
     * Tests failure of {@link DatabaseSearchUtility#DatabaseSearchUtility(Connection, String)} constructor.
     * Checks if IAE exception is thrown when connection is <code>null</code>.
     *
     * @throws ConfigurationException to JUnit.
     */
    public void testDatabaseSearchUtility_NullConnection() throws ConfigurationException {
        try {
            new DatabaseSearchUtility(null, NAMESPACE);
            fail("Null connection, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests failure of {@link DatabaseSearchUtility#DatabaseSearchUtility(Connection, String)} constructor.
     * Checks if IAE exception is thrown when namespace is <code>null</code>.
     *
     * @throws ConfigurationException to JUnit.
     */
    public void testDatabaseSearchUtility_NullNamespace() throws ConfigurationException {
        try {
            new DatabaseSearchUtility(connection, null);
            fail("Null namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests failure of {@link DatabaseSearchUtility#DatabaseSearchUtility(Connection, String)} constructor.
     * Checks if IAE exception is thrown when namespace is empty.
     *
     * @throws ConfigurationException to JUnit.
     */
    public void testDatabaseSearchUtility_EmptyNamespace() throws ConfigurationException {
        try {
            new DatabaseSearchUtility(connection, " ");
            fail("Empty namespace, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests failure of {@link DatabaseSearchUtility#DatabaseSearchUtility(Connection, String)} constructor.
     * Checks if CE exception is thrown when required config property is missing.
     *
     */
    public void testDatabaseSearchUtility_InvalidConfig()  {
        try {
            new DatabaseSearchUtility(connection, NAMESPACE + ".1");
            fail("Empty query_template property, CE expected.");
        } catch (ConfigurationException ex) {
            // ok
        }
    }

    /**
     * Tests failure of (@link DatabaseSearchUtility#prepareSearchStatement(Filter)} method.
     * Checks is IAE is thrown when filter is <code>null</code>.
     *
     * @throws ConfigurationException to JUnit.
     * @throws PersistenceException to JUnit.
     */
    public void testPrepareSearchStatement_Null() throws ConfigurationException, PersistenceException {
        try {
            new DatabaseSearchUtility(connection, NAMESPACE).prepareSearchStatement(null);
            fail("Null filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests failure of (@link DatabaseSearchUtility#prepareSearchStatement(Filter)} method.
     * Checks is IAE is thrown when filter is not any of default ones.
     *
     * @throws ConfigurationException to JUnit.
     * @throws PersistenceException to JUnit.
     */
    public void testPrepareSearchStatement_InvalidFilter() throws ConfigurationException, PersistenceException {
        try {
            new DatabaseSearchUtility(connection, NAMESPACE).prepareSearchStatement(new Filter(){});
            fail("Invalid filter, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests failure of (@link DatabaseSearchUtility#prepareSearchStatement(Filter)} method.
     * Checks is IAE is thrown when filtered field is not configured (no alias for field exists).
     *
     * @throws ConfigurationException to JUnit.
     * @throws PersistenceException to JUnit.
     */
    public void testPrepareSearchStatement_NoAliasForField() throws ConfigurationException, PersistenceException {
        try {
            Filter filter = new ValueFilter(CompareOperation.EQUAL, "any", new Integer(1));
            new DatabaseSearchUtility(connection, NAMESPACE).prepareSearchStatement(filter);
            fail("Invalid alias, IAE expected.");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }

    /**
     * Tests failure of (@link DatabaseSearchUtility#prepareSearchStatement(Filter)} method.
     * Checks is PersistenceException is thrown when SQL query is invalid.
     *
     * @throws ConfigurationException to JUnit.
     */
    public void testPrepareSearchStatement_InvalidQuery() throws ConfigurationException {
        try {
            Filter filter = new ValueFilter(CompareOperation.EQUAL, "any", new Integer(1));
            new DatabaseSearchUtility(connection, NAMESPACE + ".2").prepareSearchStatement(filter);
            fail("Invalid alias, IAE expected.");
        } catch (PersistenceException ex) {
            // ok
        }
    }
}
