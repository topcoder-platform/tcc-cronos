/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.accounting.entities.dto.PaymentIdentifier;

/**
 * <p>
 * Unit tests for the {@link BillingCostReportQuickBooksExportAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BillingCostReportQuickBooksExportActionTest {

    /** Represents the paymentIds used to test again. */
    private List<PaymentIdentifier> paymentIdsValue;

    /** Represents the instance used to test again. */
    private BillingCostReportQuickBooksExportAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new BillingCostReportQuickBooksExportAction();
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
     * {@link BillingCostReportQuickBooksExportAction#BillingCostReportQuickBooksExportAction()}
     * .
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testBillingCostReportQuickBooksExportAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#getPaymentAreaId()}
     * </p>
     * <p>
     * The value of <code>paymentAreaId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPaymentAreaId() throws Exception {
        long paymentAreaIdValue = 10;
        testInstance.setPaymentAreaId(paymentAreaIdValue);
        assertEquals("should be 10", paymentAreaIdValue, testInstance
                .getPaymentAreaId());
    }

    /**
     * <p>
     * Accuracy test
     * {@link BillingCostReportQuickBooksExportAction#setPaymentAreaId(long)}.
     * </p>
     * <p>
     * The value of <code>paymentAreaId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPaymentAreaId() throws Exception {
        long paymentAreaIdValue = 0;
        testInstance.setPaymentAreaId(paymentAreaIdValue);
        assertEquals("should be 0", paymentAreaIdValue, testInstance
                .getPaymentAreaId());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#getPaymentIds()}
     * </p>
     * <p>
     * The value of <code>paymentIds</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPaymentIds() throws Exception {
        assertNull("should be null.", testInstance.getPaymentIds());
    }

    /**
     * <p>
     * Accuracy test
     * {@link BillingCostReportQuickBooksExportAction#setPaymentIds(List)}.
     * </p>
     * <p>
     * The value of <code>paymentIds</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPaymentIds() throws Exception {
        testInstance.setPaymentIds(paymentIdsValue);
        assertEquals("must be the same", paymentIdsValue, testInstance
                .getPaymentIds());
    }

    /**
     * <p>
     * Inheritance test, verifies
     * <code>BillingCostReportQuickBooksExportAction</code> subclasses
     * <code>BaseBillingCostReportAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "BillingCostReportQuickBooksExportAction does not subclass BaseBillingCostReportAction.",
                BillingCostReportQuickBooksExportAction.class.getSuperclass() == BaseBillingCostReportAction.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate1() throws Exception {
        testInstance.setPaymentIds(null);
        testInstance.validate();
        assertEquals("must be 0", 0, testInstance.getActionErrors().size());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate2() throws Exception {
        paymentIdsValue = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier id = new PaymentIdentifier();
        // This payment identifier is invalid
        id.setPaymentDetailId(null);
        id.setContestId(null);
        id.setProjectInfoTypeId(null);
        paymentIdsValue.add(id);

        testInstance.setPaymentIds(paymentIdsValue);
        testInstance.validate();
        assertTrue("must contain key 'paymentIds'", testInstance
                .getFieldErrors().containsKey("paymentIds"));
        assertEquals("must be 1", 1, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate3() throws Exception {
        paymentIdsValue = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier id = new PaymentIdentifier();
        id.setPaymentDetailId(null);
        id.setContestId(1L);
        id.setProjectInfoTypeId(1L);
        paymentIdsValue.add(id);

        testInstance.setPaymentIds(paymentIdsValue);
        testInstance.validate();
        assertFalse("must not contain key 'paymentIds'", testInstance
                .getFieldErrors().containsKey("paymentIds"));
        assertEquals("must be 0", 0, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate4() throws Exception {
        // This payment identifier is invalid
        paymentIdsValue = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier id = new PaymentIdentifier();
        id.setPaymentDetailId(null);
        id.setContestId(null);
        id.setProjectInfoTypeId(1L);
        paymentIdsValue.add(id);

        testInstance.setPaymentIds(paymentIdsValue);
        testInstance.validate();
        assertTrue("must contain key 'paymentIds'", testInstance
                .getFieldErrors().containsKey("paymentIds"));
        assertEquals("must be 1", 1, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate5() throws Exception {
        // This payment identifier is invalid
        paymentIdsValue = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier id = new PaymentIdentifier();
        id.setPaymentDetailId(null);
        id.setContestId(1L);
        id.setProjectInfoTypeId(null);
        paymentIdsValue.add(id);

        testInstance.setPaymentIds(paymentIdsValue);
        testInstance.validate();
        assertTrue("must contain key 'paymentIds'", testInstance
                .getFieldErrors().containsKey("paymentIds"));
        assertEquals("must be 1", 1, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate6() throws Exception {
        paymentIdsValue = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier id = new PaymentIdentifier();
        id.setPaymentDetailId(1L);
        id.setContestId(null);
        id.setProjectInfoTypeId(1L);
        paymentIdsValue.add(id);

        testInstance.setPaymentIds(paymentIdsValue);
        testInstance.validate();
        assertFalse("must not contain key 'paymentIds'", testInstance
                .getFieldErrors().containsKey("paymentIds"));
        assertEquals("must be 0", 0, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate7() throws Exception {
        paymentIdsValue = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier id = new PaymentIdentifier();
        id.setPaymentDetailId(1L);
        id.setContestId(1L);
        id.setProjectInfoTypeId(null);
        paymentIdsValue.add(id);

        testInstance.setPaymentIds(paymentIdsValue);
        testInstance.validate();
        assertFalse("must not contain key 'paymentIds'", testInstance
                .getFieldErrors().containsKey("paymentIds"));
        assertEquals("must be 0", 0, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate8() throws Exception {
        paymentIdsValue = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier id = new PaymentIdentifier();
        id.setPaymentDetailId(1L);
        id.setContestId(1L);
        id.setProjectInfoTypeId(1L);
        paymentIdsValue.add(id);

        testInstance.setPaymentIds(paymentIdsValue);
        testInstance.validate();
        assertFalse("must not contain key 'paymentIds'", testInstance
                .getFieldErrors().containsKey("paymentIds"));
        assertEquals("must be 0", 0, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute1() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setPaymentIds(null);
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be equals.",
                BillingCostReportQuickBooksExportAction.SUCCESS, testInstance
                        .execute());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportQuickBooksExportAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute2() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setPaymentIds(new ArrayList<PaymentIdentifier>());
        testInstance.setPaymentAreaId(1);
        testInstance
                .setBillingCostDataService(new MockBillingCostDataService());
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be equals.",
                BillingCostReportQuickBooksExportAction.SUCCESS, testInstance
                        .execute());
    }

    /**
     * <p>
     * Failure test for
     * {@link BillingCostReportQuickBooksExportAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = Exception.class)
    public void test_execute_failure_1() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setPaymentIds(new ArrayList<PaymentIdentifier>());
        testInstance.setPaymentAreaId(-1);
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        testInstance.execute();
    }

}
