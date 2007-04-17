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
     * Tests getDAO method without AuditManager key being bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_noAuditManagerKey() throws Exception {
        context.unbind("audit_manager_key");

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with null AuditManager key being bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_nullAuditManagerKey() throws Exception {
        context.rebind("audit_manager_key", null);

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with AuditManager key with incorrect type bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_wrongTypeAuditManagerKey() throws Exception {
        context.rebind("audit_manager_key", new Integer(0));

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method without id generator name being bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_noIdGeneratorName() throws Exception {
        context.unbind("id_generator_name");

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with null id generator name being bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_nullIdGeneratorName() throws Exception {
        context.rebind("id_generator_name", null);

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with id generator name with incorrect type bound. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_wrongTypeIdGeneratorName() throws Exception {
        context.rebind("id_generator_name", new Integer(0));

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

    /**
     * Tests getDAO method with AuditManager key bound to an unknown object. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_unknownAuditManagerKey() throws Exception {
        context.rebind("audit_manager_key", "unknown");

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }

    /**
     * Tests getDAO method with AuditManager key bound to an object of String type. BaseException should be thrown.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetDAO_wrongTypeAuditManager() throws Exception {
        context.rebind("audit_manager_key", "String");

        try {
            DAOFactory.getDAO(false);
            fail("BaseException should be thrown.");
        } catch (BaseException e) {
            // Ok
        }
    }
}
