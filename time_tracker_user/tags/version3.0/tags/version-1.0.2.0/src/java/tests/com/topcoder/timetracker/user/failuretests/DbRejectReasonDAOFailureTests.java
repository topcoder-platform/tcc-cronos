/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import java.io.File;

import junit.framework.TestCase;

import com.cronos.timetracker.common.DbRejectReasonDAO;
import com.cronos.timetracker.common.RejectReason;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * Failure unit test cases for the DbRejectReasonDAO class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class DbRejectReasonDAOFailureTests extends TestCase {
    /**
     * <p>
     * Connection name.
     * </p>
     */
    private static final String CONN_NAME = "informix";

    /**
     * <p>
     * Id generator name.
     * </p>
     */
    private static final String ID_GENERATOR_NAME = "ttu2";

    /**
     * <p>
     * DBConnection Factory namespace.
     * </p>
     */
    private static final String DB_CONN_FACTORY_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /**
     * <p>
     * The name of the file containing configuration data.
     * </p>
     */
    private static final String CONFIG_XML = "failuretests" + File.separatorChar + "dbconfig.xml";

    /**
     * <p>
     * DBConnectionFactory instance used for testing
     * </p>
     */
    private DBConnectionFactory connectionFactory = null;

    /**
     * <p>
     * The DbRejectReasonDAO instance used for testing.
     * </p>
     * @throws Exception to JUnit.
     */
    private DbRejectReasonDAO dbRejectReasonDAO = null;

    /**
     * <p>
     * Creates DbRejectReasonDAO instance.
     * </p>
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        FailureTestsHelper.loadConfig(CONFIG_XML);

        connectionFactory = new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE);
        dbRejectReasonDAO = new DbRejectReasonDAO(connectionFactory, CONN_NAME, ID_GENERATOR_NAME);
    }

    /**
     * <p>
     * Removes the configuration if it exists.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        FailureTestsHelper.cleanConfig();
    }

    /**
     * <p>
     * Tests DbRejectReasonDAO(DBConnectionFactory, String, String, String) for failure. Passes null as first
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1() throws Exception {
        try {
            new DbRejectReasonDAO(null, CONN_NAME, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbRejectReasonDAO(DBConnectionFactory, String, String, String) for failure. Passes null as second
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor2() throws Exception {
        try {
            new DbRejectReasonDAO(connectionFactory, null, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbRejectReasonDAO(DBConnectionFactory, String, String, String) for failure. Passes null as third
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor3() throws Exception {
        try {
            new DbRejectReasonDAO(connectionFactory, CONN_NAME, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbRejectReasonDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string
     * as second argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor5() throws Exception {
        try {
            new DbRejectReasonDAO(connectionFactory, " ", ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbRejectReasonDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string
     * as third argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor6() throws Exception {
        try {
            new DbRejectReasonDAO(connectionFactory, CONN_NAME, " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createRejectReason(RejectReason, String) for failure. Passes null rejectReason,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateRejectReason1() throws Exception {
        try {
            dbRejectReasonDAO.createRejectReason(null, "A");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteRejectReason(RejectReason) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteRejectReason1() throws Exception {
        try {
            dbRejectReasonDAO.deleteRejectReason(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests retrieveRejectReason(long) for failure. Passes 0, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRetrieveRejectReason1() throws Exception {
        try {
            dbRejectReasonDAO.retrieveRejectReason(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests retrieveRejectReason(long) for failure. Passes negative arguement, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRetrieveRejectReason2() throws Exception {
        try {
            dbRejectReasonDAO.retrieveRejectReason(-3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests searchRejectReasons(Filter) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSearchRejectReasons1() throws Exception {
        try {
            dbRejectReasonDAO.searchRejectReasons(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateRejectReason(RejectReason, String) for failure. Passes null rejectReason,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateRejectReason1() throws Exception {
        try {
            dbRejectReasonDAO.updateRejectReason(null, "A");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
