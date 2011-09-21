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
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.PagedResult;

/**
 * <p>
 * Unit tests for the {@link BillingCostExportDetailsAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BillingCostExportDetailsActionTest {
    /** Represents the billingCostExportId used to test again. */
    private long billingCostExportIdValue;

    /** Represents the instance used to test again. */
    private BillingCostExportDetailsAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new BillingCostExportDetailsAction();
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
     * {@link BillingCostExportDetailsAction#BillingCostExportDetailsAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testBillingCostExportDetailsAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostExportDetailsAction#getBillingCostExportId()}
     * </p>
     * <p>
     * The value of <code>billingCostExportId</code> should be properly
     * retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getBillingCostExportId() throws Exception {
        assertEquals("must be 0", 0, testInstance.getBillingCostExportId());
    }

    /**
     * <p>
     * Accuracy test
     * {@link BillingCostExportDetailsAction#setBillingCostExportId(long)}.
     * </p>
     * <p>
     * The value of <code>billingCostExportId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setBillingCostExportId() throws Exception {
        billingCostExportIdValue = 30;
        testInstance.setBillingCostExportId(billingCostExportIdValue);
        assertEquals("must be 30", billingCostExportIdValue, testInstance
                .getBillingCostExportId());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BillingCostExportDetailsAction</code>
     * subclasses <code>BaseBillingCostAuditAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "BillingCostExportDetailsAction does not subclass BaseBillingCostAuditAction.",
                BillingCostExportDetailsAction.class.getSuperclass() == BaseBillingCostAuditAction.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostExportDetailsAction#getBillingCostExportDetails()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getBillingCostExportDetails() throws Exception {
        PagedResult<BillingCostExportDetail> value = new PagedResult<BillingCostExportDetail>();
        TestHelper.setField(testInstance, "billingCostExportDetails", value);
        assertEquals("must be the same.", value, testInstance
                .getBillingCostExportDetails());
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostExportDetailsAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        testInstance.setBillingCostAuditService(billingCostAuditService);
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostExportId(1);
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be 'success'",
                BillingCostExportDetailsAction.SUCCESS, testInstance.execute());
    }

    /**
     * <p>
     * Failure test for {@link BillingCostExportDetailsAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = Exception.class)
    public void test_execute_failure_1() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        testInstance.setBillingCostAuditService(billingCostAuditService);
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostExportId(-1);
        testInstance.execute();
    }
}
