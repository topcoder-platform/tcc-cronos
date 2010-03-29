/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user.stresstests;

import com.topcoder.service.user.Address;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserInfo;
import com.topcoder.service.user.UserServiceRemote;

import junit.extensions.TestSetup;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 * <p>
 * Stress tests cases for the <code>UserServiceBean</code> in version 1.1.
 * </p>
 *
 * @author moon.river
 * @version 1.1
 */
public class UserServiceBeanTestsV11 extends TestCase {
    /**
     * <p>
     * The instance of <code>EntityManager</code> used in Unit tests.
     * </p>
     */
    private static EntityManager em;

    /**
     * The target.
     */
    private static UserServiceRemote userService;

    /**
     * The handle.
     */
    private static final String HANDLE = "moon.river";

    static {
        em = Persistence.createEntityManagerFactory(
                "HibernateProjectPersistence").createEntityManager();
    }

    /**
     * <p>
     * Return All the EJB test suite.
     * </p>
     *
     * @return all the EJB test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(UserServiceBeanTestsV11.class);

        /**
         * <p>
         * Setup the unit test.
         * </p>
         */
        TestSetup wrapper = new TestSetup(suite) {
                /**
                 * <p>
                 * Tear down the EJB test.
                 * </p>
                 */
                @Override
                protected void tearDown() throws Exception {
                    em.close();
                }
            };

        return wrapper;
    }

    /**
     * <p>
     * Setup the EJB test.
     * </p>
     */
    @Override
    protected void setUp() throws Exception {
        userService = (UserServiceRemote) lookupUserServiceRemoteWithUserRole();
        executeSqlFile("/stress/clean.sql");
        executeSqlFile("/stress/prepare.sql");
    }

    /**
     * <p>
     * Tear down the EJB test.
     * </p>
     */
    @Override
    protected void tearDown() throws Exception {
        em.clear();
    }

    /**
     * Tests the <code>getUserInfo</code> method.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetUserInfo() throws Exception {
        User user = createDefaultUser();
        userService.registerUser(user);

		long start = System.currentTimeMillis();
		UserInfo info = null;
		for (int i = 0; i < 100; i++) {
	        info = userService.getUserInfo(HANDLE);
		}
		long end = System.currentTimeMillis();
		System.out.println("Run getUserInfo used " + (end - start) + "ms");

        assertEquals("The user info is wrong", user.getHandle(),
            info.getHandle());
        assertEquals("The user info is wrong", user.getEmailAddress(),
            info.getEmailAddress());
        assertEquals("The user info is wrong", user.getLastName(),
            info.getLastName());
        assertEquals("The user info is wrong", user.getFirstName(),
            info.getFirstName());
    }

    /**
     * Creates a user with all the fields set.
     *
     * @return a new <code>User</code> instance.
     */
    public User createDefaultUser() {
        Address address = new Address();
        address.setAddress1("ADDRESS1");
        address.setAddress2("ADDRESS2");
        address.setCity("LA");
        address.setCountryCode("1");
        address.setZip("07870");
        address.setProvince("CA");
        address.setStateCode("2");

        User user = new User();
        user.setAddress(address);
        user.setEmailAddress("dummy@gmail.com");
        user.setFirstName("Foo");
        user.setLastName("Foo");
        user.setPassword("foo");
        user.setPhone("55738293");
        user.setHandle(HANDLE);

        user.setGroupIds(new long[] {1000001});

        return user;
    }

    /**
     * <p>
     * Lookup the <code>UserServiceRemote</code> with user role.
     * </p>
     *
     * @return The <code>UserServiceRemote</code> with user role.
     * @throws Exception
     *             to JUnit.
     */
    private static UserServiceRemote lookupUserServiceRemoteWithUserRole()
        throws Exception {
        Properties env = new Properties();

        // Specify principal
        env.setProperty(Context.SECURITY_PRINCIPAL, "username");

        // Specify credential
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");

        Context ctx = new InitialContext(env);

        return (UserServiceRemote) ctx.lookup("remote/UserServiceBean");
    }

    /**
     * <p>
     * Execute the sql file.
     * </p>
     *
     * @param file
     *            The sql file to be executed.
     * @throws Exception
     *             to JUnit
     */
    private static void executeSqlFile(String file) throws Exception {
        String sql = getSql(file);
        EntityTransaction et = em.getTransaction();

        if (!et.isActive()) {
            et.begin();
        }

        StringTokenizer st = new StringTokenizer(sql, ";");

        final int maxCount = 20;

        for (int count = 1; st.hasMoreTokens(); count++) {
            String statement = st.nextToken().trim();

            if (statement.length() > 0) { // An empty statement is technically
                                          // erroneous, but ignore them silently
                em.createNativeQuery(statement).executeUpdate();

                if ((count % maxCount) == 0) {
                    // periodically flush and clear the getEntityManager() to control
                    // memory usage
                    em.flush();
                    em.clear();
                }
            }
        }

        et.commit();

        // The transaction is automatically rolled back if a PersistenceException is
        // thrown
    }

    /**
     * <p>
     * Get the sql file content.
     * </p>
     *
     * @param file
     *            The sql file to get its content.
     * @return The content of sql file.
     * @throws Exception
     *             to JUnit
     */
    private static String getSql(String file) throws Exception {
        StringBuilder sql = new StringBuilder();
        InputStream is = UserServiceBeanTestsV11.class.getResourceAsStream(file);

        if (is == null) {
            throw new FileNotFoundException("Not found: " + file);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        try {
            for (String s = in.readLine(); s != null; s = in.readLine()) {
                int commentIndex = s.indexOf("--");

                if (commentIndex >= 0) { // Remove any SQL comment
                    s = s.substring(0, commentIndex);
                }

                sql.append(s).append(' '); // The BufferedReader drops newlines so insert
                                           // whitespace
            }
        } finally {
            in.close();
        }

        return sql.toString();
    }
}
