/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.timetracker.rejectreason.RejectEmailDAO;
import com.topcoder.timetracker.rejectreason.RejectReasonDAO;
import com.topcoder.timetracker.rejectreason.TestHelper;

import com.topcoder.util.errorhandling.BaseException;


/**
 * Unit test for <code>DAOFactory</code> class.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DAOFactoryTests extends TestHelper {
    /**
     * Sets up the test environment. We bind DataSource to JNDI context, add necessary environment variables, and load
     * configurations.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        loadConfig();
        setUpSessionBeanEnvironment();
    }

    /**
     * Tests getDAO method accuracy.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_accuracy() throws Exception {
        assertTrue("RejectEmailDAO instance should be returned.", DAOFactory.getDAO(true) instanceof RejectEmailDAO);
        assertTrue("RejectReasonDAO instance should be returned.", DAOFactory.getDAO(false) instanceof RejectReasonDAO);
    }

    /**
     * Tests getDAO method without environment being bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_noEnvironment() throws Exception {
        context.unbind("java:comp/env");

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with null environment being bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_nullEnvironment() throws Exception {
        context.rebind("java:comp/env", null);

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with environment with incorrect type bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_wrongTypeEnvironment() throws Exception {
        context.rebind("java:comp/env", new Integer(0));

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method without object factory namespace being bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_noOFNamespace() throws Exception {
        context.unbind("of_namespace");

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with null object factory namespace being bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_nullOFNamespace() throws Exception {
        context.rebind("of_namespace", null);

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with object factory namespace with incorrect type bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_wrongTypeOFNamespace() throws Exception {
        context.rebind("of_namespace", new Integer(0));

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with object factory namespace bound to an unknown name. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_unknownOFNamespace() throws Exception {
        context.rebind("of_namespace", "unknown");

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }
}
