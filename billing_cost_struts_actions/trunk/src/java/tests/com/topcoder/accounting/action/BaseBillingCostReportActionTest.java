/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * <p>
 * Unit tests for the {@link BaseBillingCostReportAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseBillingCostReportActionTest {
    /** Represents the instance used to test again. */
    private BaseBillingCostReportAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() throws Exception {
        testInstance = new BaseBillingCostReportAction() {
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
     * {@link BaseBillingCostReportAction#BaseBillingCostReportAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testBaseBillingCostReportAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BaseBillingCostReportAction</code>
     * subclasses <code>BaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "BaseBillingCostReportAction does not subclass BaseAction.",
                BaseBillingCostReportAction.class.getSuperclass() == BaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BaseBillingCostReportAction#checkConfiguration()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_checkConfiguration() throws Exception {
        testInstance.setAction("action");
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        testInstance.setBillingCostAuditService(billingCostAuditService);
        billingCostAuditService.setFlag(false);
        testInstance
                .setBillingCostDataService(new MockBillingCostDataService());
    }

    /**
     * <p>
     * Failure test for {@link BaseBillingCostReportAction#checkConfiguration()}
     * .
     * </p>
     * <p>
     * billing cost data service is null.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = BillingCostActionConfigurationException.class)
    public void test_checkConfiguration_failure_1() throws Exception {
        testInstance.setAction("action");
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        testInstance.setBillingCostAuditService(billingCostAuditService);
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostDataService(null);
        testInstance.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for {@link BaseBillingCostReportAction#checkConfiguration()}
     * .
     * </p>
     * <p>
     * action name is empty.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = BillingCostActionConfigurationException.class)
    public void test_checkConfiguration_failure_2() throws Exception {
        testInstance.setAction(" ");
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        testInstance.setBillingCostAuditService(billingCostAuditService);
        billingCostAuditService.setFlag(false);
        testInstance
                .setBillingCostDataService(new MockBillingCostDataService());
        testInstance.checkConfiguration();
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BaseBillingCostReportAction#getBillingCostDataService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getBillingCostDataService() throws Exception {
        testInstance.setBillingCostDataService(null);
        assertNull("must be null.", testInstance.getBillingCostDataService());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BaseBillingCostReportAction#setBillingCostDataService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setBillingCostDataService() throws Exception {
        MockBillingCostDataService mock = new MockBillingCostDataService();
        testInstance.setBillingCostDataService(mock);
        assertEquals("must be the same.", mock, testInstance
                .getBillingCostDataService());
    }
}
