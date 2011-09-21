/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;


/**
 * <p>
 * Unit tests for the {@link BillingCostExportHistoryAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BillingCostExportHistoryActionTest {
    /** Represents the criteria used to test again. */
    private BillingCostExportHistoryCriteria criteriaValue;

    /** Represents the instance used to test again. */
    private BillingCostExportHistoryAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new BillingCostExportHistoryAction();
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
     * {@link BillingCostExportHistoryAction#BillingCostExportHistoryAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testBillingCostExportHistoryAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostExportHistoryAction#getCriteria()}
     * </p>
     * <p>
     * The value of <code>criteria</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getCriteria() throws Exception {
        assertNull("must be null", testInstance.getCriteria());
    }

    /**
     * <p>
     * Accuracy test
     * {@link BillingCostExportHistoryAction#setCriteria(BillingCostExportHistoryCriteria)}
     * .
     * </p>
     * <p>
     * The value of <code>criteria</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCriteria() throws Exception {
        testInstance.setCriteria(criteriaValue);
        assertEquals("must be the same", criteriaValue, testInstance
                .getCriteria());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BillingCostExportHistoryAction</code>
     * subclasses <code>BaseBillingCostAuditAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "BillingCostExportHistoryAction does not subclass BaseBillingCostAuditAction.",
                BillingCostExportHistoryAction.class.getSuperclass() == BaseBillingCostAuditAction.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostExportHistoryAction#getBillingCostExports()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getBillingCostExports() throws Exception {
        assertNull("must be null", testInstance.getBillingCostExports());
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostExportHistoryAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute1() throws Exception {
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        assertEquals("must be 'success'",
                BillingCostExportHistoryAction.SUCCESS, testInstance.execute());
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostExportHistoryAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute2() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setCriteria(new BillingCostExportHistoryCriteria());
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be 'success'",
                BillingCostExportHistoryAction.SUCCESS, testInstance.execute());
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostExportHistoryAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = Exception.class)
    public void test_execute_failure_1() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(true);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setCriteria(new BillingCostExportHistoryCriteria());
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be 'success'",
                BillingCostExportHistoryAction.SUCCESS, testInstance.execute());
    }
}
