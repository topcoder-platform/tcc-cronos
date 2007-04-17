/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.failuretests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.rejectreason.RejectEmail;
import com.topcoder.timetracker.rejectreason.RejectEmailDAOException;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailDAOConfigurationException;
import com.topcoder.timetracker.rejectreason.ejb.RejectEmailManager;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rejectreason.ejb.RejectEmailManager}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class RejectEmailManagerTest extends TestCase {
    /**
     * <p>
     * Represents the connection for data setup.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the RejectEmailManager for testing.
     * </p>
     */
    private RejectEmailManager manager;

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
        FailureTestHelper.deployEJB();
        manager = new RejectEmailManager("com.topcoder.timetracker.rejectreason.ejb.failure");
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
     * Failure test for <code>{@link RejectEmailManager#RejectEmailManager(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_IAE() throws Exception {
        try {
            new RejectEmailManager(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new RejectEmailManager("");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#RejectEmailManager(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_RCE() throws Exception {
        try {
            new RejectEmailManager("wrong");
            fail("RejectEmailDAOConfigurationException expected");
        } catch (RejectEmailDAOConfigurationException e) {
            // expect
        }
        try {
            FailureTestHelper.undeployEJB();
            new RejectEmailManager("com.topcoder.timetracker.rejectreason.ejb.failure");
            fail("RejectEmailDAOConfigurationException expected");
        } catch (RejectEmailDAOConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#deleteRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteRejectEmail_IAE() throws Exception {
        try {
            manager.deleteRejectEmail(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.deleteRejectEmail(FailureTestHelper.createRejectEmail(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.deleteRejectEmail(FailureTestHelper.createRejectEmail(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#deleteRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteRejectEmail_RDE() throws Exception {
        try {
            manager.deleteRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#deleteRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteRejectEmail_RCE() throws Exception {
        try {
            FailureTestHelper.releaseConfigs();
            manager.deleteRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#createRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateRejectEmail_IAE() throws Exception {
        try {
            manager.createRejectEmail(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.createRejectEmail(FailureTestHelper.createRejectEmail(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.createRejectEmail(FailureTestHelper.createRejectEmail(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#createRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateRejectEmail_RDE() throws Exception {
        try {
            manager.createRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#createRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateRejectEmail_RCE() throws Exception {
        try {
            FailureTestHelper.releaseConfigs();
            manager.createRejectEmail(FailureTestHelper.createRejectEmail(), "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#updateRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateRejectEmail_IAE() throws Exception {
        try {
            manager.updateRejectEmail(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.updateRejectEmail(FailureTestHelper.createRejectEmail(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.updateRejectEmail(FailureTestHelper.createRejectEmail(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#updateRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateRejectEmail_RDE() throws Exception {
        RejectEmail email = FailureTestHelper.createRejectEmail();
        email.setId(1);
        try {
            manager.updateRejectEmail(email, "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#updateRejectEmail(RejectEmail,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateRejectEmail_RCE() throws Exception {
        RejectEmail email = FailureTestHelper.createRejectEmail();
        email.setId(1);
        try {
            FailureTestHelper.releaseConfigs();
            manager.updateRejectEmail(email, "failure_user", false);
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#searchRejectEmails(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchRejectEmails_IAE() throws Exception {
        try {
            manager.searchRejectEmails(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#searchRejectEmails(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchRejectEmails_RDE() throws Exception {
        try {
            manager.searchRejectEmails(new EqualToFilter("dummy", new Long(1)));
            fail("RejectEmailDAOException expected");
        } catch (RejectEmailDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectEmailManager#retrieveRejectEmail(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRetrieveRejectEmail_IAE() throws Exception {
        try {
            manager.retrieveRejectEmail(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RejectEmailManagerTest.class);
    }
}
