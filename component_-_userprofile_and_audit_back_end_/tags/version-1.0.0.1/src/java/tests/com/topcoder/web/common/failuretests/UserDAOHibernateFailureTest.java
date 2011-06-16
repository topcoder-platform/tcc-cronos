/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.failuretests;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.dao.hibernate.UserDAOHibernate;

/**
 * <p>
 * Failure tests for class <code>UserDAOHibernate</code>.
 * </p>
 * @author stevenfrog
 * @version 1.0
 */
public class UserDAOHibernateFailureTest {
    /**
     * <p>
     * Represents the <code>UserDAOHibernate</code> instance used to test against.
     * </p>
     */
    private UserDAOHibernate impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserDAOHibernateFailureTest.class);
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
        impl = new UserDAOHibernate();
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
     * Failure test for <code>UserDAOHibernate(Session)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUserDAOHibernateSession_ParamNull() throws Exception {
        new UserDAOHibernate(null);
    }

    /**
     * <p>
     * Failure test for <code>find(Long)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFind_ParamNull() throws Exception {
        impl.find((Long) null);
    }

    /**
     * <p>
     * Failure test for <code>find(String, boolean)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFind2_ParamNull() throws Exception {
        impl.find(null, true);
    }

    /**
     * <p>
     * Failure test for <code>find(String, boolean, boolean)</code>.<br>
     * The parameter is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFind3_ParamEmpty() throws Exception {
        impl.find("", true, false);
    }

    /**
     * <p>
     * Failure test for <code>saveOrUpdate(User)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsaveOrUpdate_ParamNull() throws Exception {
        impl.saveOrUpdate(null);
    }

    /**
     * <p>
     * Failure test for <code>find(String)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFind4_ParamNull() throws Exception {
        impl.find((String) null);
    }

    /**
     * <p>
     * Failure test for <code>find(String)</code>.<br>
     * The parameter is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFind4_ParamEmpty() throws Exception {
        impl.find(" ");
    }

    /**
     * <p>
     * Failure test for <code>canChangeHandle(String)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCanChangeHandle_ParamNull() throws Exception {
        impl.canChangeHandle(null);
    }

    /**
     * <p>
     * Failure test for <code>canChangeHandle(String)</code>.<br>
     * The parameter is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCanChangeHandle_ParamEmpty() throws Exception {
        impl.canChangeHandle("  ");
    }
}
