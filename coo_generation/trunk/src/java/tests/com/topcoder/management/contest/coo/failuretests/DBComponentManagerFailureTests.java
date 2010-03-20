/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.impl.componentmanager.DBComponentManager;

/**
 * <p>
 * Set of failure tests for BaseDBConnector,
 * </p>
 * @author vilain
 * @version 1.0
 */
public class DBComponentManagerFailureTests {

    /**
     * Instance of DBComponentManager for testing.
     */
    private DBComponentManager dbComponentManager;

    /**
     * Setting up environment.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        DefaultConfigurationObject configurationObject = new DefaultConfigurationObject("config");
        // default child
        DefaultConfigurationObject defaultChild = new DefaultConfigurationObject("default");
        defaultChild.setPropertyValue("connectionName", "informix");
        configurationObject.addChild(defaultChild);
        // connection factory child
        DefaultConfigurationObject connectionFactoryChild =
            new DefaultConfigurationObject("dbConnectionFactoryConfig");
        defaultChild.addChild(connectionFactoryChild);
        // connection factory child
        DefaultConfigurationObject connectionFactoryImplChild =
            new DefaultConfigurationObject("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        connectionFactoryChild.addChild(connectionFactoryImplChild);
        // connections child
        DefaultConfigurationObject connectionsChild = new DefaultConfigurationObject("connections");
        connectionFactoryImplChild.addChild(connectionsChild);
        dbComponentManager = new DBComponentManager(configurationObject);
    }

    /**
     * Method under test
     * DBComponentManager.DBComponentManager(ConfigurationObject). Failure
     * testing of exception in case configuration is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testDBComponentManagerFailureNull() throws Exception {
        try {
            new DBComponentManager(null);
            Assert.fail("IllegalArgumentException is expected since configuration is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test DBComponentManager.retrieveComponent(String, String).
     * Failure testing of exception in case name is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testRetrieveComponentFailureNameNull() throws Exception {
        try {
            dbComponentManager.retrieveComponent(null, "version");
            Assert.fail("IllegalArgumentException is expected since name is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test DBComponentManager.retrieveComponent(String, String).
     * Failure testing of exception in case name is empty.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testRetrieveComponentFailureNameEmpty() throws Exception {
        try {
            dbComponentManager.retrieveComponent("", "version");
            Assert.fail("IllegalArgumentException is expected since name is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test DBComponentManager.retrieveComponent(String, String).
     * Failure testing of exception in case name is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testRetrieveComponentFailureNameEmptyTrimmed() throws Exception {
        try {
            dbComponentManager.retrieveComponent("     ", "version");
            Assert.fail("IllegalArgumentException is expected since name is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}