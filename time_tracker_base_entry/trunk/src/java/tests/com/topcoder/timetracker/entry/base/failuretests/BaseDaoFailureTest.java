/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.failuretests;

import java.sql.Connection;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.base.BaseDao;
import com.topcoder.timetracker.entry.base.ConfigurationException;
import com.topcoder.timetracker.entry.base.PersistenceException;

import junit.framework.TestCase;

/**
 * <p>
 * The failure test for <code>BaseDao</code>.
 * </p>
 *
 * @author Hacker_QC
 * @version 3.2
 */
public class BaseDaoFailureTest extends TestCase {

    /**
     * Represents the namespace.
     */
    private static final String DEFAULT_CONN_NAME = "informix_connect";

    /**
     * Represents the id generator name.
     */
    private static final String DEFAULT_ID_GENERATOR_NAME = "cut_off_time";

    /**
     * Represents the dbfactory.
     */
    private DBConnectionFactory dbFactory;

    /**
     * Represents the audit manager.
     */
    private MockAuditManager auditManager;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.clearConfig();
        FailureTestHelper.loadConfig("DBConnectionFactory.xml");
        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        auditManager = new MockAuditManager();
    }

    /**
     * Clears down the test environment.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.clearConfig();
    }

    /**
     * <p>
     * Failure test for BaseDao(String connectionName, String idGeneratorName, DBConnectionFactory
     * dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * connectionName is empty.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoFailure1() throws Exception {
        try {
            new MockBaseDao(" ", DEFAULT_ID_GENERATOR_NAME, dbFactory, auditManager);
            fail("connection is empty and IAE is expected");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Failure test for BaseDao(String connectionName, String idGeneratorName, DBConnectionFactory
     * dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * connectionName is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoFailure2() throws Exception {
        try {
            MockBaseDao dao = new MockBaseDao("invalid_connection", DEFAULT_ID_GENERATOR_NAME, dbFactory,
                auditManager);
            dao.getConnection();
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // success
        }
    }
    
    /**
     * <p>
     * Failure test for BaseDao(String connectionName, String idGeneratorName, DBConnectionFactory
     * dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * idGeneratorName is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoFailure3() throws Exception {
        try {
            new MockBaseDao(DEFAULT_CONN_NAME, null, dbFactory, auditManager);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //success   
        }
    }
    
    /**
     * <p>
     * Failure test for BaseDao(String connectionName, String idGeneratorName, DBConnectionFactory
     * dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * ID generator is invalid.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoFailure4() throws Exception {
        try {
            new MockBaseDao(DEFAULT_CONN_NAME, "invalid_id_generator", dbFactory, auditManager);
            fail("id generator name does not exist and ConfigurationException is expected");
        } catch (ConfigurationException e) {
            //success   
        }
    }
    
    /**
     * <p>
     * Failure test for BaseDao(String connectionName, String idGeneratorName, DBConnectionFactory
     * dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * dbConnectionFactory is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoFailure5() throws Exception {
        try {
            new MockBaseDao(DEFAULT_CONN_NAME, DEFAULT_ID_GENERATOR_NAME, null, auditManager);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            //success   
        }
    }
    
    /**
     * <p>
     * Failure test for BaseDao(String connectionName, String idGeneratorName, DBConnectionFactory
     * dbConnectionFactory, AuditManager auditManager).
     * </p>
     *
     * <p>
     * auditManager is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testBaseDaoFailure6() throws Exception {
        try {
            new MockBaseDao(DEFAULT_CONN_NAME, DEFAULT_ID_GENERATOR_NAME, dbFactory, null);
            fail("audit manager is null and IAE is expected");
        } catch (IllegalArgumentException e) {
            //success   
        }
    }

    /**
     * <p>
     * This class is mock class of BaseDao for failure test.
     * </p>
     *
     * @author Hacker_QC
     * @version 1.0
     */
    private class MockBaseDao extends BaseDao {
        /**
         * <p>
         * Creates a new MockBaseDao and initializes the member variables with corresponding
         * parameters.
         * </p>
         *
         * @param connectionName configured connection name.Can be null, but not empty. If null is
         *        given, the default connection will be retrieved
         * @param idGeneratorName configured id generator name, not null
         * @param dbConnectionFactory connection factory to use when gerenating new connections, not
         *        null.
         * @param auditManager Audit Manager to audit methods that change data when necessary. not
         *        null
         * @throws ConfigurationException if failed to get IDGenerator from the given name
         * @throws IllegalArgumentException if any argument is invalid
         */
        public MockBaseDao(String connectionName, String idGeneratorName, DBConnectionFactory dbConnectionFactory,
            AuditManager auditManager) throws ConfigurationException {
            super(connectionName, idGeneratorName, dbConnectionFactory, auditManager);
        }

        /**
         * <p>
         * Using the connectionName and DBConnectionFactory to create a new named connection. If
         * connectionName is null then the default connection will be used for creation.
         * </p>
         *
         * @return new connection object
         * @throws PersistenceException if failed to create the connectino
         */
        public Connection getConnection() throws PersistenceException {
            return super.getConnection();
        }
    }
}
