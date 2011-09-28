/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link BaseBillingCostAuditAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseBillingCostAuditActionTest {
    /** Represents the pageNumber used to test again. */
    private int pageNumberValue;

    /** Represents the pageSize used to test again. */
    private int pageSizeValue;

    /** Represents the instance used to test again. */
    private BaseBillingCostAuditAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() throws Exception {
        testInstance = new BaseBillingCostAuditAction() {
        };
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BaseBillingCostAuditAction#BaseBillingCostAuditAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testBaseBillingCostAuditAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Accuracy test for {@link BaseBillingCostAuditAction#getPageNumber()}
     * </p>
     * <p>
     * The value of <code>pageNumber</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPageNumber() throws Exception {
        pageNumberValue = 100;
        testInstance.setPageNumber(pageNumberValue);
        assertEquals("must be 100", pageNumberValue, testInstance
                .getPageNumber());
    }

    /**
     * <p>
     * Accuracy test {@link BaseBillingCostAuditAction#setPageNumber(int)}.
     * </p>
     * <p>
     * The value of <code>pageNumber</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPageNumber() throws Exception {
        pageNumberValue = 0;
        testInstance.setPageNumber(pageNumberValue);
        assertEquals("must be 0", pageNumberValue, testInstance.getPageNumber());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseBillingCostAuditAction#getPageSize()}
     * </p>
     * <p>
     * The value of <code>pageSize</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPageSize() throws Exception {
        assertEquals("must be 0", 0, testInstance.getPageSize());
    }

    /**
     * <p>
     * Accuracy test {@link BaseBillingCostAuditAction#setPageSize(int)}.
     * </p>
     * <p>
     * The value of <code>pageSize</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPageSize() throws Exception {
        pageSizeValue = 20;
        testInstance.setPageSize(pageSizeValue);
        assertEquals("must be 20", pageSizeValue, testInstance.getPageSize());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BaseBillingCostAuditAction</code>
     * subclasses <code>BaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "BaseBillingCostAuditAction does not subclass BaseAction.",
                BaseBillingCostAuditAction.class.getSuperclass() == BaseAction.class);
    }
}
