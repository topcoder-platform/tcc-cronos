/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.TestHelper;

import java.lang.reflect.Field;

import java.util.List;


/**
 * Unit test for <code>RejectEmailManager</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectEmailManagerTests extends TestHelper {
    /** The RejectEmailManager instance to be tested. */
    private RejectEmailManager manager = null;

    /**
     * Sets up test environment. Configurations are loaded and MockEJB environment is initialized. New instance of
     * RejectEmailManager is also created.
     *
     * @throws Exception pass to JUnit.
     */
    public void setUp() throws Exception {
        loadConfig();
        setUpSessionBeanEnvironment();
        manager = new RejectEmailManager(RejectEmailManager.class.getName());
    }

    /**
     * Tests constructor with a null namespace. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_null() throws Exception {
        try {
            new RejectEmailManager(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with a null namespace. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_empty() throws Exception {
        try {
            new RejectEmailManager(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with a unknown namespace. RejectEmailDAOConfigurationException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_unknownNamespace() throws Exception {
        try {
            new RejectEmailManager("UnknownNamespace");
            fail("RejectEmailDAOConfigurationException should be thrown.");
        } catch (RejectEmailDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests constructor accuracy. With context name configured.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_accuracy() throws Exception {
        manager = new RejectEmailManager(RejectEmailManager.class.getName());
        assertNotNull("Unable to instantiate RejectEmailManager.", manager);
    }

    /**
     * Tests constructor accuracy. Without context name configured.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_accuracy_noContextName()
        throws Exception {
        manager = new RejectEmailManager(RejectEmailManager.class.getName() + ".NoContextName");
        assertNotNull("Unable to instantiate RejectEmailManager.", manager);
    }

    /**
     * Tests constructor before JNDI Utility is configured. RejectEmailDAOConfigurationException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_JNDIUtilityNotConfigured()
        throws Exception {
        CM.removeNamespace("com.topcoder.naming.jndiutility");

        try {
            new RejectEmailManager(RejectEmailManager.class.getName());
            fail("RejectEmailDAOConfigurationException should be thrown.");
        } catch (RejectEmailDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests constructor without JNDI name of RejectEmailDAOLocalHome properly configured.
     * RejectEmailDAOConfigurationException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_noJNDIName() throws Exception {
        // Unbind the RejectEmailDAOLocalHome first
        context.unbind(RejectEmailDAOLocalHome.EJB_REF_HOME);

        try {
            new RejectEmailManager(RejectEmailManager.class.getName());
            fail("RejectEmailDAOConfigurationException should be thrown.");
        } catch (RejectEmailDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with RejectEmailDAOLocalHome bound to null. RejectEmailDAOConfigurationException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_nullHome() throws Exception {
        context.rebind(RejectEmailDAOLocalHome.EJB_REF_HOME, null);

        try {
            new RejectEmailManager(RejectEmailManager.class.getName());
            fail("RejectEmailDAOConfigurationException should be thrown.");
        } catch (RejectEmailDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with RejectEmailDAOLocalHome bound to a String. RejectEmailDAOConfigurationException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_stringHome() throws Exception {
        context.rebind(RejectEmailDAOLocalHome.EJB_REF_HOME, "string");

        try {
            new RejectEmailManager(RejectEmailManager.class.getName());
            fail("RejectEmailDAOConfigurationException should be thrown.");
        } catch (RejectEmailDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectEmail method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectEmail_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        RejectEmail rejectEmail = new RejectEmail();
        manager.createRejectEmail(rejectEmail, "lever", true);

        assertEquals("The operation is not correctly delegated.", "createRejectEmail", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectEmail, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests retrieveRejectEmail method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectEmail_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        manager.retrieveRejectEmail(100);

        assertEquals("The operation is not correctly delegated.", "retrieveRejectEmail", dao.getMethod());
        assertEquals("The operation is not correctly delegated.", new Long(100), dao.getParameters().get(0));
    }

    /**
     * Tests updateRejectEmail method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectEmail_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        RejectEmail rejectEmail = new RejectEmail();
        manager.updateRejectEmail(rejectEmail, "lever", true);

        assertEquals("The operation is not correctly delegated.", "updateRejectEmail", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectEmail, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests deleteRejectEmail method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectEmail_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        RejectEmail rejectEmail = new RejectEmail();
        manager.deleteRejectEmail(rejectEmail, "lever", true);

        assertEquals("The operation is not correctly delegated.", "deleteRejectEmail", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectEmail, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests listRejectEmails method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testListRejectEmails_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        manager.listRejectEmails();

        assertEquals("The operation is not correctly delegated.", "listRejectEmails", dao.getMethod());
    }

    /**
     * Tests searchRejectEmails method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectEmails_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectEmailSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectEmailDAO dao = new MockRejectEmailDAO();
        field.set(emailBean, dao);

        Filter filter = new EqualToFilter("key", "value");
        manager.searchRejectEmails(filter);

        assertEquals("The operation is not correctly delegated.", "searchRejectEmails", dao.getMethod());
        assertEquals("The operation is not correctly delegated.", filter, dao.getParameters().get(0));
    }
}
