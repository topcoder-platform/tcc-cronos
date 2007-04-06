/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import junit.framework.TestCase;


/**
 * Test case for BaseDao.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class BaseDaoTest extends TestCase {
    /** Default namespace. */
    private static final String DEFAULT_CONN_NAME = "informix_connect";

    /** Default id generator name. */
    private static final String DEFAULT_ID_GENERATOR_NAME = "cut_off_time";

    /** Default dao. */
    private BaseDao dao;

    /** Default dbfactory. */
    private DBConnectionFactory dbFactory;

    /** Default audit manager. */
    private MockAuditManager auditManager;

    /**
     * Tests {@link BaseDao#BaseDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)}.
     *
     * @throws Exception to JUnit
     */
    public void testBaseDao() throws Exception {
        assertNotNull("BaseDao should be instantiated successfully", dao);
        assertNotNull("connection should be obtained", dao.getConnection());
    }

    /**
     * Tests {@link BaseDao#BaseDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)}.
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoEmptyConnName() throws Exception {
        try {
            new StubBaseDao(" ", DEFAULT_ID_GENERATOR_NAME, dbFactory, auditManager);
            fail("connection is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseDao#BaseDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)}.
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoInvalidConnName() throws Exception {
        BaseDao dao = new StubBaseDao("bad_connection", DEFAULT_ID_GENERATOR_NAME, dbFactory, auditManager);

        try {
            dao.getConnection();
            fail("connection name does not exist and PersistenceException is expected");
        } catch (PersistenceException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseDao#BaseDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)}.
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoInvalidIDGenName() throws Exception {
        try {
            new StubBaseDao(DEFAULT_CONN_NAME, "no_such_id_gen_name", dbFactory, auditManager);
            fail("id generator name does not exist and ConfigurationException is expected");
        } catch (ConfigurationException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseDao#BaseDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)}.
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoNullAuditMgr() throws Exception {
        try {
            new StubBaseDao(DEFAULT_CONN_NAME, DEFAULT_ID_GENERATOR_NAME, dbFactory, null);
            fail("audit manager is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseDao#BaseDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)}.
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoNullConnName() throws Exception {
        BaseDao dao = new StubBaseDao(null, DEFAULT_ID_GENERATOR_NAME, dbFactory, auditManager);
        assertNotNull("default connection should be obtained", dao.getConnection());

        //if connection name is null, default connection will be obtained, and query on this
        //connection will cause no exception
    }

    /**
     * Tests {@link BaseDao#BaseDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)}.
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoNullDBFactory() throws Exception {
        try {
            new StubBaseDao(DEFAULT_CONN_NAME, DEFAULT_ID_GENERATOR_NAME, null, auditManager);
            fail("db connection factory is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseDao#BaseDao(String, String, DBConnectionFactory,
     * com.topcoder.timetracker.audit.AuditManager)}.
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoNullIDGenName() throws Exception {
        try {
            new StubBaseDao(DEFAULT_CONN_NAME, null, dbFactory, auditManager);
            fail("id generator name is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success
        }
    }

    /**
     * Tests {@link BaseDao#getAuditManager()}.
     */
    public void testGetAuditManager() {
        assertEquals("audit manager should be obtained", auditManager, dao.getAuditManager());
    }

    /**
     * Tests {@link BaseDao#getConnection()}.
     *
     * @throws Exception to JUnit
     */
    public void testGetConnection() throws Exception {
        assertNotNull("connection should be obtained", dao.getConnection());
    }

    /**
     * Tests {@link BaseDao#nextId()}.
     *
     * @throws Exception to JUnit
     */
    public void testNextId() throws Exception {
        dao.nextId();

        //should be success
    }

    /**
     * Sets up.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.loadConfig("DBConnectionFactory.xml");
        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        auditManager = new MockAuditManager();
        dao = new StubBaseDao(DEFAULT_CONN_NAME, DEFAULT_ID_GENERATOR_NAME, dbFactory, auditManager);
    }

    /**
     * Clear down.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.clearConfig();
    }
}
