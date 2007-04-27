/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.MockAuditManager;
import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.TestHelper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;


/**
 * Unit test cases for <code>{@link BaseDAO}</code> class.
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class BaseDAOTest extends TestCase {
    /** The DBConnectionFactory instance for testing. */
    private DBConnectionFactory connFactory;

    /** The String instance for testing. */
    private String connName;

    /** The String instance for testing. */
    private String idGen;

    /** The String instance for testing. */
    private String searchBundleNamespace;

    /** The AuditManager instance for testing. */
    private AuditManager auditor;

    /** The BaseDAO instance for testing. */
    private BaseDAO baseDAO;

    /**
     * Set up the initial values.
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        TestHelper.loadConfigFile(TestHelper.DB_FACTORY_CONFIGE_FILE_NAME);
        TestHelper.loadConfigFile(TestHelper.DB_CONFIGE_FILE_NAME);
        connFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory");
        connName = "Informix_Connection_Producer";
        idGen = "com.topcoder.timetracker.entry.fixedbilling";
        searchBundleNamespace = "com.topcoder.search.builder";
        auditor = new MockAuditManager(false);
        baseDAO = new MockBaseDAO(connFactory, connName, idGen, searchBundleNamespace, "StatusSearchBundle", auditor);
    }

    /**
     * Clear up the config.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        TestHelper.removeNamespaces();
    }

    /**
     * Test the <code>{@link BaseDAO#BaseDAO(DBConnectionFactory, String, String, String, AuditManager)}</code> with
     * null connection factory. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAudit_NullFacotry()
        throws Exception {
        try {
            connFactory = null;
            new MockBaseDAO(connFactory, connName, idGen, searchBundleNamespace, "FirstSearchBundle", auditor);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link BaseDAO#BaseDAO(DBConnectionFactory, String, String, String, AuditManager)}</code> with
     * empty connection name. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAudit_EmptyConnName()
        throws Exception {
        try {
            connName = " ";
            new MockBaseDAO(connFactory, connName, idGen, searchBundleNamespace, "FirstSearchBundle", auditor);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link BaseDAO#BaseDAO(DBConnectionFactory, String, String, String, AuditManager)}</code> with
     * empty idGen name. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAudit_EmptyIdGen()
        throws Exception {
        try {
            idGen = " ";
            new MockBaseDAO(connFactory, connName, idGen, searchBundleNamespace, "FirstSearchBundle", auditor);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link BaseDAO#BaseDAO(DBConnectionFactory, String, String, String, AuditManager)}</code> with
     * empty search name space. Should throw an IllegalArgumentException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAudit_EmptySearchNameSpace()
        throws Exception {
        try {
            searchBundleNamespace = " ";
            new MockBaseDAO(connFactory, connName, idGen, searchBundleNamespace, "FirstSearchBundle", auditor);
            fail("Should throw an IllegalArgumentException here.");
        } catch (IllegalArgumentException iae) {
            //success
        }
    }

    /**
     * Test the <code>{@link BaseDAO#BaseDAO(DBConnectionFactory, String, String, String, AuditManager)}</code> with
     * exception. Should throw a ConfigurationException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAudit_Exception1()
        throws Exception {
        try {
            idGen = "wrong idGen";
            new MockBaseDAO(connFactory, connName, idGen, searchBundleNamespace, "FirstSearchBundle", auditor);
            fail("Should throw a ConfigurationException here.");
        } catch (ConfigurationException ce) {
            //success
        }
    }

    /**
     * Test the <code>{@link BaseDAO#BaseDAO(DBConnectionFactory, String, String, String, AuditManager)}</code> with
     * exception. Should throw a ConfigurationException here.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAudit_Exception2()
        throws Exception {
        try {
            searchBundleNamespace = "wrong name space";
            new MockBaseDAO(connFactory, connName, idGen, searchBundleNamespace, "FirstSearchBundle", auditor);
            fail("Should throw a ConfigurationException here.");
        } catch (ConfigurationException ce) {
            //success
        }
    }

    /**
     * Test the <code>{@link BaseDAO#BaseDAO(DBConnectionFactory, String, String, String, AuditManager)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAudit_Success1()
        throws Exception {
        baseDAO = new MockBaseDAO(connFactory, connName, idGen, searchBundleNamespace, "FirstSearchBundle", auditor);
        assertNotNull("Unable to create the instance.", baseDAO);
    }

    /**
     * Test the <code>{@link BaseDAO#BaseDAO(DBConnectionFactory, String, String, String, AuditManager)}</code> with
     * success process.
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_FactoryStringStringStringAudit_Success2()
        throws Exception {
        baseDAO = new MockBaseDAO(connFactory, null, null, null, "FirstSearchBundle", null);
        assertNotNull("Unable to create the instance.", baseDAO);
    }

    /**
     * Test the <code>{@link BaseDAO#getConnection()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetConnection_Success1() throws Exception {
        baseDAO = new MockBaseDAO(connFactory, connName, idGen, searchBundleNamespace, "FirstSearchBundle", auditor);

        Connection conn = baseDAO.getConnection();
        assertNotNull("Unable to get the connection.", conn);
        assertFalse("Unable to get the connection.", conn.isClosed());
        conn.close();
    }

    /**
     * Test the <code>{@link BaseDAO#getConnection()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetConnection_Success2() throws Exception {
        baseDAO = new MockBaseDAO(connFactory, null, null, null, "FirstSearchBundle", null);

        Connection conn = baseDAO.getConnection();
        assertNotNull("Unable to get the connection.", conn);
        assertFalse("Unable to get the connection.", conn.isClosed());
        conn.close();
    }

    /**
     * Test the <code>{@link BaseDAO#getNextId()}</code> with exception. Should throw a DataAccessException here.
     *
     * @throws Exception to JUnit.
     */
    public void testGetNextId_Exception() throws Exception {
        baseDAO = new MockBaseDAO(connFactory, null, null, null, "FirstSearchBundle", null);

        try {
            baseDAO.getNextId();
            fail("Should throw a DataAccessException here.");
        } catch (DataAccessException dae) {
            //success
        }
    }

    /**
     * Test the <code>{@link BaseDAO#getNextId()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetNextId_Success() throws Exception {
        assertTrue("The return id should be greater than 0.", baseDAO.getNextId() > 0);
    }

    /**
     * Test the <code>{@link BaseDAO#getSearchBundle()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetSearchBundle_Success() throws Exception {
        assertNotNull("The return result should not be null.", baseDAO.getSearchBundle());
        baseDAO = new MockBaseDAO(connFactory, null, null, null, "StatusSearchBundle", null);
        assertNull("The return result should be null,", baseDAO.getSearchBundle());
    }

    /**
     * Test the <code>{@link BaseDAO#getAuditManager()}</code> with success process.
     *
     * @throws Exception to JUnit.
     */
    public void testGetAuditManager_Success() throws Exception {
        assertNotNull("The return result should not be null.", baseDAO.getAuditManager());
        baseDAO = new MockBaseDAO(connFactory, null, null, null, "FirstSearchBundle", null);
        assertNull("The return result should be null,", baseDAO.getAuditManager());
    }

    /**
     * Returns the test suite of this test case.
     *
     * @return the test suite of this test case.
     */
    public static Test suite() {
        return new TestSuite(BaseDAOTest.class);
    }
}
