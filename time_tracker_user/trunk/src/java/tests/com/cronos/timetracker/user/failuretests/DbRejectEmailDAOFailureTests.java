/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user.failuretests;

import java.io.File;

import junit.framework.TestCase;

import com.cronos.timetracker.common.DbRejectEmailDAO;
import com.cronos.timetracker.common.RejectEmail;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * Failure unit test cases for the DbRejectEmailDAO class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class DbRejectEmailDAOFailureTests extends TestCase {
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
     * The DbRejectEmailDAO instance used for testing.
     * </p>
     */
    private DbRejectEmailDAO dbRejectEmailDAO = null;

    /**
     * <p>
     * Creates DbRejectEmailDAO instance.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        FailureTestsHelper.loadConfig(CONFIG_XML);

        connectionFactory = new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE);
        dbRejectEmailDAO = new DbRejectEmailDAO(connectionFactory, CONN_NAME, ID_GENERATOR_NAME);
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
     * Tests DbRejectEmailDAO(DBConnectionFactory, String, String, String) for failure. Passes null as first
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1() throws Exception {
        try {
            new DbRejectEmailDAO(null, CONN_NAME, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbRejectEmailDAO(DBConnectionFactory, String, String, String) for failure. Passes null as second
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor2() throws Exception {
        try {
            new DbRejectEmailDAO(connectionFactory, null, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbRejectEmailDAO(DBConnectionFactory, String, String, String) for failure. Passes null as third
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor3() throws Exception {
        try {
            new DbRejectEmailDAO(connectionFactory, CONN_NAME, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbRejectEmailDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string as
     * second argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor5() throws Exception {
        try {
            new DbRejectEmailDAO(connectionFactory, " ", ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbRejectEmailDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string as
     * third argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor6() throws Exception {
        try {
            new DbRejectEmailDAO(connectionFactory, CONN_NAME, " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createRejectEmail(RejectEmail, String) for failure. Passes null rejectEmail,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateRejectEmail1() throws Exception {
        try {
            dbRejectEmailDAO.createRejectEmail(null, "A");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteRejectEmail(RejectEmail) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteRejectEmail1() throws Exception {
        try {
            dbRejectEmailDAO.deleteRejectEmail(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests retrieveRejectEmail(long) for failure. Passes 0, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRetrieveRejectEmail1() throws Exception {
        try {
            dbRejectEmailDAO.retrieveRejectEmail(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests retrieveRejectEmail(long) for failure. Passes negative arguement, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRetrieveRejectEmail2() throws Exception {
        try {
            dbRejectEmailDAO.retrieveRejectEmail(-3);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests searchRejectEmails(Filter) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSearchRejectEmails1() throws Exception {
        try {
            dbRejectEmailDAO.searchRejectEmails(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateRejectEmail(RejectEmail, String) for failure. Passes null rejectEmail,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateRejectEmail1() throws Exception {
        try {
            dbRejectEmailDAO.updateRejectEmail(null, "A");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
