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
import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectEmailDAOException;
import com.topcoder.timetracker.rejectreason.RejectEmailNotFoundException;
import com.topcoder.timetracker.rejectreason.informix.DbRejectEmailDAO;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rejectreason.informix.DbRejectEmailDAO}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class DbRejectEmailDAOTest extends TestCase {
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
    private DbRejectEmailDAO dbRejectEmailDAO1, dbRejectEmailDAO2, dbRejectEmailDAO3;

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
        dbRejectEmailDAO1 = new DbRejectEmailDAO(factory, "informix_connect", auditManager, "RejectReasonFailure"
                );
        // connection error
        dbRejectEmailDAO2 = new DbRejectEmailDAO(factory, "informix_connect_failure_1",
                auditManager, "RejectReasonFailure");
        // sqlexception
        dbRejectEmailDAO3 = new DbRejectEmailDAO(factory, "informix_connect_failure_2",
                auditManager, "RejectReasonFailure");
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
     * <code>{@link DbRejectEmailDAO#DbRejectEmailDAO(DBConnectionFactory,String,String,AuditManager)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_IAE() throws Exception {
        try {
            new DbRejectEmailDAO(null, "informix_connect", auditManager, "RejectReasonFailure");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectEmailDAO(factory, null, auditManager, "RejectReasonFailure");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectEmailDAO(factory, " ", auditManager, "RejectReasonFailure");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectEmailDAO(factory, "informix_connect", auditManager, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectEmailDAO(factory, "informix_connect", auditManager, " ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new DbRejectEmailDAO(factory, "informix_connect", null, "RejectReasonFailure");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link DbRejectEmailDAO#DbRejectEmailDAO(DBConnectionFactory,String,String,AuditManager)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_RDE() throws Exception {
        try {
            new DbRejectEmailDAO(factory, "informix_connect", auditManager, "WrongId");
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
        try {
            FailureTestHelper.releaseConfigs();
            new DbRejectEmailDAO(factory, "informix_connect", auditManager, "RejectReasonFailure");
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#listRejectEmails()}</code>.
     */
    public void testMethodListRejectEmails_RDE() {
        try {
            dbRejectEmailDAO2.listRejectEmails();
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
        try {
            dbRejectEmailDAO3.listRejectEmails();
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#updateRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateRejectEmail_IAE() throws Exception {
        try {
            dbRejectEmailDAO1.updateRejectEmail(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectEmailDAO1.updateRejectEmail(FailureTestHelper.createRejectEmail(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectEmailDAO1.updateRejectEmail(FailureTestHelper.createRejectEmail(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#updateRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateRejectEmail_RE() throws Exception {
        RejectEmail email = FailureTestHelper.createRejectEmail();
        email.setId(1);
        try {
            dbRejectEmailDAO1.updateRejectEmail(email, "failure_user", true);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailNotFoundException e) {
            // expect
        }
        try {
            dbRejectEmailDAO2.updateRejectEmail(email, "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
        try {
            dbRejectEmailDAO3.updateRejectEmail(email, "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#retrieveRejectEmail(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRetrieveRejectEmail_IAE() throws Exception {
        try {
            dbRejectEmailDAO1.retrieveRejectEmail(0);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#retrieveRejectEmail(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRetrieveRejectEmail_RDE() throws Exception {
        try {
            dbRejectEmailDAO2.retrieveRejectEmail(1);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
        try {
            dbRejectEmailDAO3.retrieveRejectEmail(1);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#createRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateRejectEmail_IAE() throws Exception {
        try {
            dbRejectEmailDAO1.createRejectEmail(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectEmailDAO1.createRejectEmail(FailureTestHelper.createRejectEmail(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectEmailDAO1.createRejectEmail(FailureTestHelper.createRejectEmail(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#createRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateRejectEmail_RDE() throws Exception {
        try {
            dbRejectEmailDAO1.createRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", true);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
        try {
            dbRejectEmailDAO2.createRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
        try {
            dbRejectEmailDAO3.createRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#searchRejectEmails(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchRejectEmails_IAE() throws Exception {
        try {
            dbRejectEmailDAO1.searchRejectEmails(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#searchRejectEmails(Filter)}</code>.
     */
    public void testMethodSearchRejectEmails_RDE() {
        try {
            dbRejectEmailDAO1.searchRejectEmails(new EqualToFilter("dummy", new Long(1)));
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#deleteRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteRejectEmail_IAE() throws Exception {
        try {
            dbRejectEmailDAO1.deleteRejectEmail(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectEmailDAO1.deleteRejectEmail(FailureTestHelper.createRejectEmail(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            dbRejectEmailDAO1.deleteRejectEmail(FailureTestHelper.createRejectEmail(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link DbRejectEmailDAO#deleteRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteRejectEmail_RDE() throws Exception {
        try {
            dbRejectEmailDAO1.deleteRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", true);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
        try {
            dbRejectEmailDAO2.deleteRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
        try {
            dbRejectEmailDAO3.deleteRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DbRejectEmailDAOTest.class);
    }
}
