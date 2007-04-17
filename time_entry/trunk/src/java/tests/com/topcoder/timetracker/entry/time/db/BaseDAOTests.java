/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.ejb.AuditDelegate;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TestHelper;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Unit test cases for BaseDAO.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class BaseDAOTests extends TestCase {
    /**
     * <p>
     * The constant represents the id generator namespace for testing.
     * </p>
     */
    private static final String ID_GENERATOR_NAME = "AuditIdGenerator";

    /**
     * <p>
     * The BaseDAO instance for testing.
     * </p>
     */
    private BaseDAO dao;

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private DBConnectionFactory connFactory;

    /**
     * <p>
     * The AuditManager instance for testing.
     * </p>
     */
    private AuditManager auditor;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);
        TestHelper.loadXMLConfig(TestHelper.AUDIT_CONFIG_FILE);
        TestHelper.setUpDataBase();
        TestHelper.setUpEJBEnvironment(null, null, null);

        connFactory = new DBConnectionFactoryImpl(TestHelper.DB_FACTORY_NAMESPACE);
        auditor = new AuditDelegate(TestHelper.AUDIT_NAMESPACE);

        dao = new MockBaseDAO(connFactory, "tt_time_entry", ID_GENERATOR_NAME, TestHelper.SEARCH_NAMESPACE, auditor);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        dao = null;
        auditor = null;
        connFactory = null;

        TestHelper.tearDownDataBase();
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseDAOTests.class);
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,String,String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created BaseDAO instance should not be null.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create a new BaseDAO instance.", dao);
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when connName is null and verifies the newly created BaseDAO instance should not be null.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnName() throws Exception {
        assertNotNull("Failed to create a new BaseDAO instance.", new MockBaseDAO(connFactory, null, ID_GENERATOR_NAME,
            TestHelper.SEARCH_NAMESPACE, auditor));
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when searchStrategyNamespace is null and verifies the newly
     * created BaseDAO instance should not be null.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_NullSearchStrategyNamespace() throws Exception {
        assertNotNull("Failed to create a new BaseDAO instance.", new MockBaseDAO(connFactory, "tt_time_entry",
            ID_GENERATOR_NAME, null, auditor));
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when auditor is null verifies the newly created BaseDAO instance should not be null.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_NullAuditor() throws Exception {
        assertNotNull("Failed to create a new BaseDAO instance.", new MockBaseDAO(connFactory, "tt_time_entry",
            ID_GENERATOR_NAME, TestHelper.SEARCH_NAMESPACE, null));
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connFactory is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_NullConnFactory() throws Exception {
        try {
            new MockBaseDAO(null, "tt_time_entry", ID_GENERATOR_NAME, TestHelper.SEARCH_NAMESPACE, auditor);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when connName is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyConnName() throws Exception {
        try {
            new MockBaseDAO(connFactory, " ", ID_GENERATOR_NAME, TestHelper.SEARCH_NAMESPACE, auditor);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_EmptyIdGen() throws Exception {
        try {
            new MockBaseDAO(connFactory, "tt_time_entry", "  ", TestHelper.SEARCH_NAMESPACE, auditor);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when idGen is unknown and expects ConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_UnknownIdGen() throws Exception {
        try {
            new MockBaseDAO(connFactory, "tt_time_entry", "HelloWorld", TestHelper.SEARCH_NAMESPACE, auditor);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case that when searchStrategyNamespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor_EmptySearchStrategyNamespace() throws Exception {
        try {
            new MockBaseDAO(connFactory, "tt_time_entry", ID_GENERATOR_NAME, " ", auditor);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor BaseDAO#BaseDAO(DBConnectionFactory,String,IDGenerator,String,AuditManager) for failure.
     * </p>
     *
     * <p>
     * It tests the case when searchStrategyNamespace is invalid and expects for ConfigurationException.
     * </p>
     */
    public void testCtor_ConfigurationException() {
        try {
            new MockBaseDAO(connFactory, "tt_time_entry", ID_GENERATOR_NAME, "invalid", auditor);
            fail("ConfigurationException expected.");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseDAO#getAuditManager() for accuracy.
     * </p>
     *
     * <p>
     * It verifies BaseDAO#getAuditManager() is correct.
     * </p>
     */
    public void testGetAuditManager() {
        assertSame("Failed to get the audit manager correctly.", this.auditor, dao.getAuditManager());
    }

    /**
     * <p>
     * Tests BaseDAO#getSearchStrategy() for accuracy.
     * </p>
     *
     * <p>
     * It verifies BaseDAO#getSearchStrategy() is correct.
     * </p>
     */
    public void testGetSearchStrategy() {
        assertNotNull("Failed to get the search strategy correctly.", dao.getSearchStrategy());
    }

    /**
     * <p>
     * Tests BaseDAO#getNextId() for accuracy.
     * </p>
     *
     * <p>
     * It verifies BaseDAO#getNextId() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetNextId() throws Exception {
        long id1 = dao.getNextId();
        long id2 = dao.getNextId();
        long id3 = dao.getNextId();

        assertTrue("Failed to get the next id correctly.", (id3 - id2) == (id2 - id1));
    }

    /**
     * <p>
     * Tests BaseDAO#getNextId() for failure.
     * </p>
     *
     * <p>
     * Expects DataAccessException.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetNextId_DataAccessException() throws Exception {
        dao = new MockBaseDAO(connFactory, "tt_time_entry", null, TestHelper.SEARCH_NAMESPACE, auditor);

        try {
            dao.getNextId();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseDAO#getConnection() for accuracy.
     * </p>
     *
     * <p>
     * It verifies BaseDAO#getConnection() is correct.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetConnection() throws Exception {
        assertNotNull("Failed to get the connection correctly.", dao.getConnection());
    }

    /**
     * <p>
     * Tests BaseDAO#getConnection() for failure.
     * </p>
     *
     * <p>
     * It tests the case when connName is invalid and expects DataAccessException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetConnection_DataAccessException() throws Exception {
        dao = new MockBaseDAO(connFactory, "invalid", ID_GENERATOR_NAME, TestHelper.SEARCH_NAMESPACE, auditor);
        try {
            dao.getConnection();
            fail("DataAccessException expected.");
        } catch (DataAccessException e) {
            //good
        }
    }
}