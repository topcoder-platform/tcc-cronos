/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user.accuracytests;

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
 * Accuracy Test cases for the newly added method of <code>UserServiceBean</code> in
 * version 1.1.
 * </p>
 *
 * @author myxgyy
 * @version 1.1
 */
public class UserServiceBeanAccTestsV11 extends TestCase {
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
    private static final String HANDLE = "myxgyy";

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

        suite.addTestSuite(UserServiceBeanAccTestsV11.class);

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
        executeSqlFile("/accuracy/clean.sql");
        executeSqlFile("/accuracy/prepare.sql");
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
     * Tests the <code>registerUser</code> method.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRegisterUser() throws Exception {
        User user = createDefaultUser();

        long userId = userService.registerUser(user);

        // verify user table
        Query query = em.createNativeQuery(
                "select first_name, last_name, handle, password from user where user_id = :userId");
        query.setParameter("userId", userId);

        Object[] values = (Object[]) query.getResultList().get(0);
        assertEquals(user.getFirstName(), values[0].toString());
        assertEquals(user.getLastName(), values[1].toString());
        assertEquals(user.getHandle(), values[2].toString());
        assertEquals(user.getPassword(), values[3].toString());

        // verify email table
        query = em.createNativeQuery(
                "select address, status_id from email where user_id = :userId");
        query.setParameter("userId", userId);
        values = (Object[]) query.getResultList().get(0);
        assertEquals(user.getEmailAddress(), values[0].toString());
        assertEquals(1, Long.parseLong(values[1].toString()));

        // verify phone table
        query = em.createNativeQuery(
                "select phone_number from phone where user_id = :userId");
        query.setParameter("userId", userId);
        assertEquals(user.getPhone(), query.getResultList().get(0));

        // verify address table
        query = em.createNativeQuery(
                "select address1, address2, address3, city, state_code,"
                + "zip, country_code, province from address,"
                + " user_address_xref where user_address_xref.user_id"
                + " = :userId and user_address_xref.address_id =address.address_id");
        query.setParameter("userId", userId);
        values = (Object[]) query.getResultList().get(0);
        assertEquals(user.getAddress().getAddress1(), values[0].toString());
        assertEquals(user.getAddress().getAddress2(), values[1].toString());
        assertNull(values[2]);
        assertEquals(user.getAddress().getCity(), values[3].toString());
        assertEquals(user.getAddress().getStateCode(), values[4].toString());
        assertEquals(user.getAddress().getZip(), values[5].toString());
        assertEquals(user.getAddress().getCountryCode(), values[6].toString());
        assertEquals(user.getAddress().getProvince(), values[7].toString());
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
        UserInfo info = userService.getUserInfo(HANDLE);
        assertUserInfoEquals(user, info);
    }

    /**
     * Tests the <code>addUserToGroups</code> method registering a new user with one
     * group and then adding another, and then tries to add a non existing group.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserToGroups() throws Exception {
        User expectedUser = createDefaultUser();

        long userId = userService.registerUser(expectedUser);

        expectedUser.setUserId(userId);
        expectedUser.setGroupIds(new long[] {1000001, 1000002});

        userService.addUserToGroups(HANDLE, new long[] {1000001, 1000002});

        UserInfo retrievedUser = userService.getUserInfo(HANDLE);

        assertUserInfoEquals(expectedUser, retrievedUser);
    }

    /**
     * Tests the <code>removeUserToGroups</code> method.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserFromGroups() throws Exception {
        User expectedUser = createDefaultUser();
        expectedUser.setGroupIds(new long[] {1000001, 1000002, 1000003});

        long userId = userService.registerUser(expectedUser);

        userService.removeUserFromGroups(HANDLE, new long[] {1000002});

        expectedUser.setUserId(userId);
        expectedUser.setGroupIds(new long[] {1000001, 1000003});

        UserInfo retrievedUser = userService.getUserInfo(HANDLE);

        assertUserInfoEquals(expectedUser, retrievedUser);
    }

    /**
     * Tests the <code>addUserTerm</code> method.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddUserTerm() throws Exception {
        User user = createDefaultUser();
        long userId = userService.registerUser(user);
        // simply add a term
        userService.addUserTerm(HANDLE, 10001, null);

        // check that the association was created
        Query query = em.createNativeQuery(
                "select terms_of_use_id, create_date, modify_date from user_terms_of_use_xref where "
                + "user_id = :userId and terms_of_use_id = :termsId");
        query.setParameter("userId", userId);
        query.setParameter("termsId", 10001);

        assertFalse("The association was not made",
            query.getResultList().isEmpty());
        Object[] values = (Object[]) query.getResultList().get(0);
        assertNotNull(values[1]);
        assertNotNull(values[1]);
        assertNotNull(values[2]);
    }

    /**
     * Tests the <code>removeUserTerm</code> method.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUserTerm() throws Exception {
        User user = createDefaultUser();

        long userId = userService.registerUser(user);

        Date nowDate = new Date();

        // add a term and then remove it
        userService.addUserTerm(HANDLE, 10001, nowDate);
        userService.removeUserTerm(HANDLE, 10001);

        // now lets check that the association does not exist
        Query query = em.createNativeQuery(
                "select terms_of_use_id from user_terms_of_use_xref where "
                + "user_id = :userId and terms_of_use_id = :termsId");
        query.setParameter("userId", userId);
        query.setParameter("termsId", 10001);

        assertTrue("The association was not removed",
            query.getResultList().isEmpty());
    }

    /**
     * Asserts that two given <code>UserInfo</code> instances match.
     *
     * @param expected
     *            the expected user info.
     * @param actual
     *            the actual user info.
     */
    public void assertUserInfoEquals(UserInfo expected, UserInfo actual) {
        assertEquals("users don't match", expected.getHandle(),
            actual.getHandle());
        assertEquals("users don't match", expected.getEmailAddress(),
            actual.getEmailAddress());
        assertEquals("users don't match", expected.getLastName(),
            actual.getLastName());
        assertEquals("users don't match", expected.getFirstName(),
            actual.getFirstName());

        long[] expectedGroupIds = expected.getGroupIds();
        long[] actualGroupIds = actual.getGroupIds();
        Arrays.sort(expectedGroupIds);
        Arrays.sort(actualGroupIds);
        assertTrue("users don't match",
            Arrays.equals(expectedGroupIds, actualGroupIds));
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
        InputStream is = UserServiceBeanAccTestsV11.class.getResourceAsStream(file);

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
