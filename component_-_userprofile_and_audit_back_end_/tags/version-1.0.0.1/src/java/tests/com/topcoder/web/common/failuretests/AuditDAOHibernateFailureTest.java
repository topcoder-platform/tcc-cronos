/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.failuretests;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.common.dao.hibernate.AuditDAOHibernate;

/**
 * <p>
 * Failure tests for class <code>AuditDAOHibernate</code>.
 * </p>
 * @author stevenfrog
 * @version 1.0
 */
public class AuditDAOHibernateFailureTest {
    /**
     * <p>
     * Represents the <code>AuditDAOHibernate</code> instance used to test against.
     * </p>
     */
    private AuditDAOHibernate impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AuditDAOHibernateFailureTest.class);
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
        impl = new AuditDAOHibernate();
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
     * Failure test for <code>AuditDAOHibernate(Session)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAuditDAOHibernateSession_ParamNull() throws Exception {
        new AuditDAOHibernate(null);
    }

    /**
     * <p>
     * Failure test for <code>audit(AuditRecord)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAudit_ParamNull() throws Exception {
        impl.audit(null);
    }

    /**
     * <p>
     * Failure test for <code>hasOperation(String, String)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testHasOperation_ParamNull() throws Exception {
        impl.hasOperation(null, "operationType");
    }

    /**
     * <p>
     * Failure test for <code>hasOperation(String, String)</code>.<br>
     * The parameter is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testHasOperation_ParamEmpty() throws Exception {
        impl.hasOperation("handle", " ");
    }
}
