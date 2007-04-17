/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.failuretests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;
import com.topcoder.timetracker.rejectreason.RejectReasonNotFoundException;
import com.topcoder.timetracker.rejectreason.informix.DbRejectReasonDAO;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rejectreason.informix.DbRejectReasonDAO}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class DbRejectReasonDAOTest extends TestCase {
    /**
     * <p>
     * Represents the connection for data setup.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the DBConnectionFactory for testing.
     * </p>
     */
    private DBConnectionFactory factory;

    /**
     * <p>
     * Represents the AuditManager for testing.
     * </p>
     */
    private AuditManager auditManager;

    /**
     * <p>
     * Represents the DbRejectEmailDAO for testing.
     * </p>
     */
    private DbRejectReasonDAO dbRejectReasonDAO1, dbRejectReasonDAO2, dbRejectReasonDAO3;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        FailureTestHelper.loadConfigs();
        connection = FailureTestHelper.getConnection();
        FailureTestHelper.prepareData(connection);

        factory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        auditManager = new MockAuditManager();
        // correct one
        dbRejectReasonDAO1 = new DbRejectReasonDAO(factory, "informix_connect", auditManager,
                "RejectReasonFailure");
        // connection error
        dbRejectReasonDAO2 = new DbRejectReasonDAO(factory, "informix_connect_failure_1", auditManager,
                "RejectReasonFailure");
        // sqlexception
        dbRejectReasonDAO3 = new DbRejectReasonDAO(factory, "informix_connect_failure_2", auditManager,
                "RejectReasonFailure");

    }

    /**
     * Tears down test environment.
     *
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.clearDatabase(connection);
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        FailureTestHelper.releaseConfigs();
    }

    /**
     * Failure test for
     * <code>{@link DbRejectReasonDAO#DbRejectReasonDAO(DBConnectionFactory,String,AuditManager,String)}</code>.
     *
     * @throws Exception
     *             if any erroo occurs
     */
    public void testConstructor_IAE() throws Exception {
        try {
            new DbRejectReasonDAO(null, "informix_connect", auditManager, "RejectReasonFailure");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectReasonDAO(factory, null, auditManager, "RejectReasonFailure");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectReasonDAO(factory, " ", auditManager, "RejectReasonFailure");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectReasonDAO(factory, "informix_connect", null, "RejectReasonFailure");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectReasonDAO(factory, "informix_connect", auditManager, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectReasonDAO(factory, "informix_connect", auditManager, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbRejectReasonDAO#DbRejectReasonDAO(DBConnectionFactory,String,AuditManager,String)}</code>.
     *
     * @throws Exception
     *             if any erroo occurs
     */
    public void testConstructor_RDE() throws Exception {
        try {
            new DbRejectReasonDAO(factory, "informix_connect", auditManager, "WrongId");
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            new DbRejectReasonDAO(factory, "informix_connect", auditManager, "WrongId");
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#retrieveRejectReason(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRetrieveRejectReason_IAE() throws Exception {
        try {
            dbRejectReasonDAO1.retrieveRejectReason(0);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#retrieveRejectReason(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRetrieveRejectReason_RDE() throws Exception {
        try {
            dbRejectReasonDAO2.retrieveRejectReason(1);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
        try {
            dbRejectReasonDAO3.retrieveRejectReason(1);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#searchRejectReasons(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchRejectReasons_IAE() throws Exception {
        try {
            dbRejectReasonDAO1.searchRejectReasons(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#searchRejectReasons(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchRejectReasons_RDE() throws Exception {
        try {
            dbRejectReasonDAO1.searchRejectReasons(new EqualToFilter("dummy", new Long(1)));
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#updateRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateRejectReason_IAE() throws Exception {
        try {
            dbRejectReasonDAO1.updateRejectReason(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectReasonDAO1.updateRejectReason(FailureTestHelper.createRejectReason(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectReasonDAO1.updateRejectReason(FailureTestHelper.createRejectReason(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#updateRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateRejectReason_RE() throws Exception {
        RejectReason reason = FailureTestHelper.createRejectReason();
        reason.setId(1);
        try {
            dbRejectReasonDAO1.updateRejectReason(reason, "failure_user", true);
            fail("RejectReasonNotFoundException expected");
        } catch (RejectReasonNotFoundException e) {
            // expect
        }
        try {
            dbRejectReasonDAO2.updateRejectReason(reason, "failure_user", true);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
        try {
            dbRejectReasonDAO3.updateRejectReason(reason, "failure_user", true);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#createRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateRejectReason_IAE() throws Exception {
        try {
            dbRejectReasonDAO1.createRejectReason(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectReasonDAO1.createRejectReason(FailureTestHelper.createRejectReason(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectReasonDAO1.createRejectReason(FailureTestHelper.createRejectReason(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#createRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateRejectReason_RDE() throws Exception {
        try {
            dbRejectReasonDAO1.createRejectReason(FailureTestHelper.createRejectReason(), "failure_user",
                    true);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
        try {
            dbRejectReasonDAO2.createRejectReason(FailureTestHelper.createRejectReason(), "failure_user",
                    true);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
        try {
            dbRejectReasonDAO3.createRejectReason(FailureTestHelper.createRejectReason(), "failure_user",
                    true);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#deleteRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteRejectReason_IAE() throws Exception {
        try {
            dbRejectReasonDAO1.deleteRejectReason(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectReasonDAO1.deleteRejectReason(FailureTestHelper.createRejectReason(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectReasonDAO1.deleteRejectReason(FailureTestHelper.createRejectReason(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#deleteRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteRejectReason_RDE() throws Exception {
        try {
            dbRejectReasonDAO1.deleteRejectReason(FailureTestHelper.createRejectReason(), "failure_user",
                    true);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
        try {
            dbRejectReasonDAO2.deleteRejectReason(FailureTestHelper.createRejectReason(), "failure_user",
                    true);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
        try {
            dbRejectReasonDAO3.deleteRejectReason(FailureTestHelper.createRejectReason(), "failure_user",
                    true);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectReasonDAO#listRejectReasons()}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodListRejectReasons_RDE() throws Exception {
        try {
            dbRejectReasonDAO2.listRejectReasons();
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
        try {
            dbRejectReasonDAO3.listRejectReasons();
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbRejectReasonDAOTest.class);
    }
}
