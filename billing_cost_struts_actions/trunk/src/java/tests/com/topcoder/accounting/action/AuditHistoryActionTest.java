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

import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;


/**
 * <p>
 * Unit tests for the {@link AuditHistoryAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuditHistoryActionTest {
    /** Represents the criteria used to test again. */
    private AccountingAuditRecordCriteria criteriaValue;

    /** Represents the instance used to test again. */
    private AuditHistoryAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new AuditHistoryAction();
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
     * Accuracy test for {@link AuditHistoryAction#AuditHistoryAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testAuditHistoryAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Accuracy test for {@link AuditHistoryAction#getCriteria()}
     * </p>
     * <p>
     * The value of <code>criteria</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getCriteria() throws Exception {
        testInstance.setCriteria(null);
        assertNull("must be null value", testInstance.getCriteria());
    }

    /**
     * <p>
     * Accuracy test
     * {@link AuditHistoryAction#setCriteria(AccountingAuditRecordCriteria)}.
     * </p>
     * <p>
     * The value of <code>criteria</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCriteria() throws Exception {
        criteriaValue = new AccountingAuditRecordCriteria();
        testInstance.setCriteria(criteriaValue);
        assertEquals("New value", criteriaValue, testInstance.getCriteria());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>AuditHistoryAction</code> subclasses
     * <code>BaseBillingCostAuditAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "AuditHistoryAction does not subclass BaseBillingCostAuditAction.",
                AuditHistoryAction.class.getSuperclass() == BaseBillingCostAuditAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link AuditHistoryAction#getAccountingAuditRecords()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAccountingAuditRecords() throws Exception {
        assertNull("must be null value", testInstance
                .getAccountingAuditRecords());
    }

    /**
     * <p>
     * Accuracy test for {@link AuditHistoryAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute1() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be success", AuditHistoryAction.SUCCESS,
                testInstance.execute());
    }

    /**
     * <p>
     * Accuracy test for {@link AuditHistoryAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute2() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setCriteria(new AccountingAuditRecordCriteria());
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be success", AuditHistoryAction.SUCCESS,
                testInstance.execute());
    }

    /**
     * <p>
     * Failure test for {@link AuditHistoryAction#execute()}.
     * </p>
     * <p>
     * billingCostAuditService will throw exception.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = Exception.class)
    public void test_execute_failure_1() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(true);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setCriteria(new AccountingAuditRecordCriteria());
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        testInstance.execute();
    }
}
