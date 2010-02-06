/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.contest.coo.impl.contestdataretriever.DBContestDataRetriever;

public class DBContestDataRetrieverFailureTests {

    /**
     * Instance of DBComponentManager for testing.
     */
    private DBContestDataRetriever dbContestDataRetriever;

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
        dbContestDataRetriever = new DBContestDataRetriever(configurationObject);
    }

    /**
     * Method under test
     * DBContestDataRetriever.DBContestDataRetriever(ConfigurationObject).
     * Failure testing of exception in case configuration is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testDBContestDataRetrieverFailureNull() throws Exception {
        try {
            new DBContestDataRetriever(null);
            Assert.fail("IllegalArgumentException is expected since configuration is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test DBContestDataRetriever.retrieveContestData(long).
     * Failure testing of exception in case projectId is negative.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testRetrieveContestDataFailureNegative() throws Exception {
        try {
            dbContestDataRetriever.retrieveContestData(-1);
            Assert.fail("IllegalArgumentException is expected since projectId is negative");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}