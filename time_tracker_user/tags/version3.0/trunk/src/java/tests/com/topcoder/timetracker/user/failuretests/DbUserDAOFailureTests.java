/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.failuretests;

import java.io.File;

import junit.framework.TestCase;

import com.topcoder.timetracker.user.DbUserDAO;
import com.topcoder.timetracker.user.User;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * Failure unit test cases for the DbUserDAO class.
 * </p>
 * @author agh
 * @version 2.0
 * @since 2.0
 */
public class DbUserDAOFailureTests extends TestCase {

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
     * Algorithm name.
     * </p>
     */
    private static final String ALGORITHM_NAME = "ttu2";

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
     * The DbUserDAO instance used for testing.
     * </p>
     */
    private DbUserDAO dbUserDAO = null;

    /**
     * <p>
     * Creates DbUserDAO instance.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        FailureTestsHelper.loadConfig(CONFIG_XML);

        connectionFactory = new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE);
        dbUserDAO = new DbUserDAO(connectionFactory, CONN_NAME, ALGORITHM_NAME, ID_GENERATOR_NAME);
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
     * Tests DbUserDAO(DBConnectionFactory, String, String, String) for failure. Passes null as first
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor1() throws Exception {
        try {
            new DbUserDAO(null, CONN_NAME, ALGORITHM_NAME, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbUserDAO(DBConnectionFactory, String, String, String) for failure. Passes null as second
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor2() throws Exception {
        try {
            new DbUserDAO(connectionFactory, null, ALGORITHM_NAME, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbUserDAO(DBConnectionFactory, String, String, String) for failure. Passes null as third
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor3() throws Exception {
        try {
            new DbUserDAO(connectionFactory, CONN_NAME, null, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbUserDAO(DBConnectionFactory, String, String, String) for failure. Passes null as fourth
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor4() throws Exception {
        try {
            new DbUserDAO(connectionFactory, CONN_NAME, ALGORITHM_NAME, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbUserDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string as second
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor5() throws Exception {
        try {
            new DbUserDAO(connectionFactory, " ", ALGORITHM_NAME, ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbUserDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string as third
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor6() throws Exception {
        try {
            new DbUserDAO(connectionFactory, CONN_NAME, " ", ID_GENERATOR_NAME);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DbUserDAO(DBConnectionFactory, String, String, String) for failure. Passes empty string as fourth
     * argument, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testConstructor7() throws Exception {
        try {
            new DbUserDAO(connectionFactory, CONN_NAME, ALGORITHM_NAME, " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createUser(User, String) for failure. Passes null company, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateUser1() throws Exception {
        try {
            dbUserDAO.createUser(null, "A");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createUsers(User[], String, boolean) for failure. Passes null array, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateUsers1() throws Exception {
        try {
            dbUserDAO.createUsers(null, "A", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createUsers(User[], String, boolean) for failure. Passes array with null elements,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateUsers2() throws Exception {
        try {
            dbUserDAO.createUsers(new User[] {new User(), null}, "A", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests createUsers(User[], String, boolean) for failure. Passes empty user name,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testCreateUsers3() throws Exception {
        try {
            dbUserDAO.createUsers(new User[] {new User()}, " ", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteUser(User) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteUser1() throws Exception {
        try {
            dbUserDAO.deleteUser(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteUsers(User[], boolean) for failure. Passes null array, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteUsers1() throws Exception {
        try {
            dbUserDAO.deleteUsers(null, false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests deleteUsers(User[], boolean) for failure. Passes 0, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDeleteUsers2() throws Exception {
        try {
            dbUserDAO.deleteUsers(new User[] {new User(), null}, false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests retrieveUsers(long[]) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testRetrieveUsers1() throws Exception {
        try {
            dbUserDAO.retrieveUsers(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests searchUsers(Filter) for failure. Passes null, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSearchUsers1() throws Exception {
        try {
            dbUserDAO.searchUsers(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateUsers(User[], String, boolean) for failure. Passes null array, IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateUsers1() throws Exception {
        try {
            dbUserDAO.updateUsers(null, "A", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateUsers(User[], String, boolean) for failure. Passes array with null elements,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateUsers2() throws Exception {
        try {
            dbUserDAO.updateUsers(new User[] {new User(), null}, "A", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateUsers(User[], String, boolean) for failure. Passes empty user name,
     * IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateUsers3() throws Exception {
        try {
            dbUserDAO.updateUsers(new User[] {new User()}, " ", false);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests updateUser(User, String) for failure. Passes null company, IllegalArgumentException is expected.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testUpdateUser1() throws Exception {
        try {
            dbUserDAO.updateUser(null, "A");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
