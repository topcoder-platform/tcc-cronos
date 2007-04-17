/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.TestHelper;

import java.lang.reflect.Field;

import java.util.List;


/**
 * Unit test for <code>RejectReasonManager</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class RejectReasonManagerTests extends TestHelper {
    /** The RejectReasonManager instance to be tested. */
    private RejectReasonManager manager = null;

    /**
     * Sets up test environment. First we initialize MockEJB environment, then load configurations. RejectReasonManager
     * is also created.
     *
     * @throws Exception pass to JUnit.
     */
    public void setUp() throws Exception {
        loadConfig();
        setUpSessionBeanEnvironment();
        manager = new RejectReasonManager(RejectReasonManager.class.getName());
    }

    /**
     * Tests constructor with a null namespace. IllegalArgumentException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_null() throws Exception {
        try {
            new RejectReasonManager(null);
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
            new RejectReasonManager(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with a unknown namespace. RejectReasonDAOConfigurationException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_unknownNamespace() throws Exception {
        try {
            new RejectReasonManager("UnknownNamespace");
            fail("RejectReasonDAOConfigurationException should be thrown.");
        } catch (RejectReasonDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests constructor accuracy. With context name configured.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_accuracy() throws Exception {
        manager = new RejectReasonManager(RejectReasonManager.class.getName());
        assertNotNull("Unable to instantiate RejectReasonManager.", manager);
    }

    /**
     * Tests constructor accuracy. Without context name configured.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_accuracy_noContextName()
        throws Exception {
        manager = new RejectReasonManager(RejectReasonManager.class.getName() + ".NoContextName");
        assertNotNull("Unable to instantiate RejectReasonManager.", manager);
    }

    /**
     * Tests constructor before JNDI Utility is configured. RejectReasonDAOConfigurationException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_JNDIUtilityNotConfigured()
        throws Exception {
        CM.removeNamespace("com.topcoder.naming.jndiutility");

        try {
            new RejectReasonManager(RejectReasonManager.class.getName());
            fail("RejectReasonDAOConfigurationException should be thrown.");
        } catch (RejectReasonDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests constructor without JNDI name of RejectReasonDAOLocalHome properly configured.
     * RejectReasonDAOConfigurationException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_noJNDIName() throws Exception {
        // Unbind the RejectReasonDAOLocalHome first
        context.unbind(RejectReasonDAOLocalHome.EJB_REF_HOME);

        try {
            new RejectReasonManager(RejectReasonManager.class.getName());
            fail("RejectReasonDAOConfigurationException should be thrown.");
        } catch (RejectReasonDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with RejectReasonDAOLocalHome bound to null. RejectReasonDAOConfigurationException should be
     * thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_nullHome() throws Exception {
        context.rebind(RejectReasonDAOLocalHome.EJB_REF_HOME, null);

        try {
            new RejectReasonManager(RejectReasonManager.class.getName());
            fail("RejectReasonDAOConfigurationException should be thrown.");
        } catch (RejectReasonDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests constructor with RejectReasonDAOLocalHome bound to a String. RejectReasonDAOConfigurationException should
     * be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testConstructor_stringHome() throws Exception {
        context.rebind(RejectReasonDAOLocalHome.EJB_REF_HOME, "string");

        try {
            new RejectReasonManager(RejectReasonManager.class.getName());
            fail("RejectReasonDAOConfigurationException should be thrown.");
        } catch (RejectReasonDAOConfigurationException e) {
            // Ok
        }
    }

    /**
     * Tests createRejectReason method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testCreateRejectReason_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        RejectReason rejectReason = new RejectReason();
        manager.createRejectReason(rejectReason, "lever", true);

        assertEquals("The operation is not correctly delegated.", "createRejectReason", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectReason, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests retrieveRejectReason method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRetrieveRejectReason_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        manager.retrieveRejectReason(100);

        assertEquals("The operation is not correctly delegated.", "retrieveRejectReason", dao.getMethod());
        assertEquals("The operation is not correctly delegated.", new Long(100), dao.getParameters().get(0));
    }

    /**
     * Tests updateRejectReason method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testUpdateRejectReason_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        RejectReason rejectReason = new RejectReason();
        manager.updateRejectReason(rejectReason, "lever", true);

        assertEquals("The operation is not correctly delegated.", "updateRejectReason", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectReason, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests deleteRejectReason method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testDeleteRejectReason_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        RejectReason rejectReason = new RejectReason();
        manager.deleteRejectReason(rejectReason, "lever", true);

        assertEquals("The operation is not correctly delegated.", "deleteRejectReason", dao.getMethod());

        List params = dao.getParameters();
        assertEquals("The operation is not correctly delegated.", rejectReason, params.get(0));
        assertEquals("The operation is not correctly delegated.", "lever", params.get(1));
        assertEquals("The operation is not correctly delegated.", new Boolean(true), params.get(2));
    }

    /**
     * Tests listRejectReasons method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testListRejectReasons_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        manager.listRejectReasons();

        assertEquals("The operation is not correctly delegated.", "listRejectReasons", dao.getMethod());
    }

    /**
     * Tests searchRejectReasons method accuracy. Verifies the operation is delegated correctly.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchRejectReasons_accuracy() throws Exception {
        // Set inner DAO instance to our mock implementation through reflection
        Field field = RejectReasonSessionBean.class.getDeclaredField("dao");
        field.setAccessible(true);

        MockRejectReasonDAO dao = new MockRejectReasonDAO();
        field.set(reasonBean, dao);

        Filter filter = new EqualToFilter("key", "value");
        manager.searchRejectReasons(filter);

        assertEquals("The operation is not correctly delegated.", "searchRejectReasons", dao.getMethod());
        assertEquals("The operation is not correctly delegated.", filter, dao.getParameters().get(0));
    }
}
