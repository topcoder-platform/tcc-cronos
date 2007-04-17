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
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonDAOConfigurationException;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;

/**
 * Failure test for <code>{@link com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager}</code>
 * class.
 *
 * @author mittu
 * @version 1.0
 */
public class RejectReasonManagerTest extends TestCase {
    /**
     * <p>
     * Represents the connection for data setup.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * Represents the RejectReasonManager for testing.
     * </p>
     */
    private RejectReasonManager manager;

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
        manager = new RejectReasonManager("com.topcoder.timetracker.rejectreason.ejb.failure");
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
     * Failure test for <code>{@link RejectReasonManager#RejectReasonManager(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_IAE() throws Exception {
        try {
            new RejectReasonManager(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            new RejectReasonManager("");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonManager#RejectReasonManager(String)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_RCE() throws Exception {
        try {
            new RejectReasonManager("wrong");
            fail("RejectReasonDAOConfigurationException expected");
        } catch (RejectReasonDAOConfigurationException e) {
            // expect
        }
        try {
            FailureTestHelper.undeployEJB();
            new RejectReasonManager("com.topcoder.timetracker.rejectreason.ejb.failure");
            fail("RejectReasonDAOConfigurationException expected");
        } catch (RejectReasonDAOConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link RejectReasonManager#createRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateRejectReason_IAE() throws Exception {
        try {
            manager.createRejectReason(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.createRejectReason(FailureTestHelper.createRejectReason(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.createRejectReason(FailureTestHelper.createRejectReason(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link RejectReasonManager#createRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodCreateRejectReason_RDE() throws Exception {
        try {
            manager.createRejectReason(FailureTestHelper.createRejectReason(), "failure_user", false);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link RejectReasonManager#deleteRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteRejectReason_IAE() throws Exception {
        try {
            manager.deleteRejectReason(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.deleteRejectReason(FailureTestHelper.createRejectReason(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.deleteRejectReason(FailureTestHelper.createRejectReason(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link RejectReasonManager#deleteRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodDeleteRejectReason_RDE() throws Exception {
        try {
            manager.deleteRejectReason(FailureTestHelper.createRejectReason(), "failure_user", false);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonManager#retrieveRejectReason(long)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodRetrieveRejectReason_IAE() throws Exception {
        try {
            manager.retrieveRejectReason(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link RejectReasonManager#updateRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateRejectReason_IAE() throws Exception {
        try {
            manager.updateRejectReason(null, "failure_user", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.updateRejectReason(FailureTestHelper.createRejectReason(), null, false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
        try {
            manager.updateRejectReason(FailureTestHelper.createRejectReason(), " ", false);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for
     * <code>{@link RejectReasonManager#updateRejectReason(RejectReason,String,boolean)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodUpdateRejectReason_RDE() throws Exception {
        RejectReason reason = FailureTestHelper.createRejectReason();
        reason.setId(1);
        try {
            manager.updateRejectReason(reason, "failure_user", false);
            fail("RejectReasonDAOException expected");
        } catch (RejectReasonDAOException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonManager#searchRejectReasons(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchRejectReasons_IAE() throws Exception {
        try {
            manager.searchRejectReasons(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test for <code>{@link RejectReasonManager#searchRejectReasons(Filter)}</code>.
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testMethodSearchRejectReasons_RDE() throws Exception {
        try {
            manager.searchRejectReasons(new EqualToFilter("dummy", new Long(1)));
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
        return new TestSuite(RejectReasonManagerTest.class);
    }
}
