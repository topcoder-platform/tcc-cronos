/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.management.contest.coo.impl.BaseDBConnector;
import com.topcoder.management.contest.coo.impl.ConfigurationException;

import com.topcoder.util.log.Log;

import junit.framework.TestCase;


/**
 * <p>Accuracy test case of {@link BaseDBConnector}.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class BaseDBConnectorAccTests extends TestCase {
    /** Target. */
    private MyDBConnector instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        ConfigurationObject config = BaseTestCase
            .getConfigurationObject(BaseTestCase.ACCURACY + "Config.xml");

        instance = new MyDBConnector(config);
    }

    /**
     * Accuracy test case for constructor.
     *
     * @throws Exception to JUnit.
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull(instance);
        assertEquals("informix", instance.getConnectionName());
        assertNotNull(instance.getDbConnectionFactory());
        assertNotNull(instance.getLogger());
    }

    /**
     * Simple sub class of {@link BaseDBConnector} used for testing.
     *
     * @author myxgyy
     * @version 1.0
     */
    public class MyDBConnector extends BaseDBConnector {
        /**
         * Constructor.
         *
         * @param configuration the configuration
         * @throws ConfigurationException if it throws from super class.
         */
        public MyDBConnector(ConfigurationObject configuration)
            throws ConfigurationException {
            super(configuration);
        }

        /**
         * Getter for the namesake variable
         *
         * @return The value of the namesake variable
         */
        protected String getConnectionName() {
            return super.getConnectionName();
        }

        /**
         * Getter for the namesake variable
         *
         * @return The value of the namesake variable
         */
        protected DBConnectionFactory getDbConnectionFactory() {
            return super.getDbConnectionFactory();
        }

        /**
         * Getter for the namesake variable
         *
         * @return The value of the namesake variable
         */
        protected Log getLogger() {
            return super.getLogger();
        }
    }
}
