/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.config.ConfigManager;

import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>DAOFactory</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class DAOFactoryTests extends TestCase {
    /**
     * The configuration manager instance.
     */
    private static final ConfigManager MANAGER = ConfigManager.getInstance();

    /**
     * Post-test cleanup: removes all configuration namespaces.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        for (Iterator it = MANAGER.getAllNamespaces(); it.hasNext();) {
            MANAGER.removeNamespace((String) it.next());
        }
    }

    /**
     * Tests that <code>getAdminDataDAO</code> throws <code>InstantiationException</code> when the admin data DAO
     * cannot be instantiated.
     */
    public void test_getAdminDataDAO_error() {
        try {
            DAOFactory.getAdminDataDAO();
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getAdminDataDAO</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getAdminDataDAO() throws Exception {
        MANAGER.add("database_config.xml");
        MANAGER.add("dao_factory_config.xml");
        MANAGER.add("admin_dao_tests.xml");

        assertNotNull("DAOFactory.getAdminDataDAO should succeed", DAOFactory.getAdminDataDAO());
    }

    /**
     * Tests that <code>getAdminDataDAO</code> throws <code>InstantiationException</code> when the message DAO
     * cannot be instantiated.
     */
    public void test_getMessageDAO_error() {
        try {
            DAOFactory.getMessageDAO();
            fail("should have thrown InstantiationException");
        } catch (InstantiationException ex) {
            // pass
        }
    }

    /**
     * Tests the <code>getMessageDAO</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_getMessageDAO() throws Exception {
        MANAGER.add("database_config.xml");
        MANAGER.add("dao_factory_config.xml");
        MANAGER.add("message_dao_tests.xml");

        assertNotNull("DAOFactory.getMessageDAO should succeed", DAOFactory.getMessageDAO());
    }
}
