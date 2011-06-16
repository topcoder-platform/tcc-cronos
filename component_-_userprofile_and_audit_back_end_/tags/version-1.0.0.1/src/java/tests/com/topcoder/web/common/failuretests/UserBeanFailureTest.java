/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.failuretests;

import javax.ejb.EJBException;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.ejb.user.UserBean;

/**
 * <p>
 * Failure tests for class <code>UserBean</code>.
 * </p>
 * @author stevenfrog
 * @version 1.0
 */
public class UserBeanFailureTest {
    /**
     * <p>
     * Represents the <code>UserBean</code> instance used to test against.
     * </p>
     */
    private UserBean impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserBeanFailureTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     * @throws Exception
     *             to jUnit.
     */
    @Before
    public void setUp() throws Exception {
        impl = new UserBean();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Failure test for <code>createNewUser(String, char, String, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testCreateNewUser_ParamNull() throws Exception {
        impl.createNewUser(null, 'c', "dataSource", "idDataSource");
    }

    /**
     * <p>
     * Failure test for <code>createNewUser(String, char, String, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testCreateNewUser_ParamEmpty() throws Exception {
        impl.createNewUser("handle", 'c', "", "idDataSource");
    }

    /**
     * <p>
     * Failure test for <code>createUser(long, String, char, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testCreateUser_ParamNull() throws Exception {
        impl.createUser(1, null, 'c', "dataSource");
    }

    /**
     * <p>
     * Failure test for <code>createUser(long, String, char, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testCreateUser_ParamEmpty() throws Exception {
        impl.createUser(1, "handle", 'c', "   ");
    }

    /**
     * <p>
     * Failure test for <code>setFirstName(long, String, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testSetFirstName_ParamNull() throws Exception {
        impl.setFirstName(0, "firstName", null);
    }

    /**
     * <p>
     * Failure test for <code>setLastName(long, String, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testSetLastName_ParamEmpty() throws Exception {
        impl.setLastName(0, "lastName", "  ");
    }

    /**
     * <p>
     * Failure test for <code>setMiddleName(long, String, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testSetMiddleName_ParamNull() throws Exception {
        impl.setMiddleName(0, "middleName", null);
    }

    /**
     * <p>
     * Failure test for <code>setStatus(long, char, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testSetStatus_ParamNull() throws Exception {
        impl.setStatus(0, 's', null);
    }

    /**
     * <p>
     * Failure test for <code>setActivationCode(long, String, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testSetActivationCode_ParamNull() throws Exception {
        impl.setActivationCode(0, "code", null);
    }

    /**
     * <p>
     * Failure test for <code>getFirstName(long, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testGetFirstName_ParamEmpty() throws Exception {
        impl.getFirstName(0, " ");
    }

    /**
     * <p>
     * Failure test for <code>getMiddleName(long, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void getMiddleName_ParamEmpty() throws Exception {
        impl.getMiddleName(0, "");
    }

    /**
     * <p>
     * Failure test for <code>getLastName(long, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testGetLastName_ParamEmpty() throws Exception {
        impl.getLastName(0, "   ");
    }

    /**
     * <p>
     * Failure test for <code>getActivationCode(long, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testGetActivationCode_ParamNull() throws Exception {
        impl.getActivationCode(0, null);
    }

    /**
     * <p>
     * Failure test for <code>getStatus(long, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testGetStatus_ParamNull() throws Exception {
        impl.getStatus(0, null);
    }

    /**
     * <p>
     * Failure test for <code>setHandle(long, String, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testSetHandle_ParamEmpty() throws Exception {
        impl.setHandle(0, "", "datasource");
    }

    /**
     * <p>
     * Failure test for <code>setHandle(long, String, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testSetHandle2_ParamNull() throws Exception {
        impl.setHandle(0, "handle", null);
    }

    /**
     * <p>
     * Failure test for <code>getHandle(long, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testGetHandle_ParamEmpty() throws Exception {
        impl.getHandle(0, " ");
    }

    /**
     * <p>
     * Failure test for <code>userExists(long, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testUserExists_ParamNull() throws Exception {
        impl.userExists(0, null);
    }

    /**
     * <p>
     * Failure test for <code>userExists(String, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testUserExists2_ParamNull() throws Exception {
        impl.userExists("handle", null);
    }

    /**
     * <p>
     * Failure test for <code>setPassword(long, String, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testSetPassword_ParamEmpty() throws Exception {
        impl.setPassword(0, "", "datasource");
    }

    /**
     * <p>
     * Failure test for <code>setPassword(long, String, String)</code>.<br>
     * The parameter is null.<br>
     * Expect EJBException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EJBException.class)
    public void testSetPassword2_ParamNull() throws Exception {
        impl.setPassword(0, "Password", null);
    }

}
