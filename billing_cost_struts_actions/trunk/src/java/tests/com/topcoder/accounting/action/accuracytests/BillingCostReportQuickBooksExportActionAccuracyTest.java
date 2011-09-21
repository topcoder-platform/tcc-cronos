/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.accounting.action.BillingCostReportQuickBooksExportAction;
import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostAuditService;
import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostDataService;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dto.PaymentIdentifier;

/**
 * Accuracy test for BillingCostReportQuickBooksExportAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BillingCostReportQuickBooksExportActionAccuracyTest {
    /**
     * Represents the instance of BillingCostReportQuickBooksExportAction used in test.
     */
    private BillingCostReportQuickBooksExportAction action;

    /**
     * Represents the instance of BillingCostDataService used in test.
     */
    private MockBillingCostDataService billingCostDataService;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() {
        action = new BillingCostReportQuickBooksExportAction();
        action.setBillingCostAuditService(new MockBillingCostAuditService());
        action.setServletRequest(new MockHttpServletRequest());
        action.setAction("accuracytest");
        action.setPaymentAreaId(1L);

        List<PaymentIdentifier> ids = new ArrayList<PaymentIdentifier>();
        action.setPaymentIds(ids);

        billingCostDataService = new MockBillingCostDataService();
        action.setBillingCostDataService(billingCostDataService);
    }

    /**
     * Accuracy test for setter and getter of paymentAreaId.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testPaymentAreaId() throws Exception {
        action.setPaymentAreaId(12);
        assertEquals("The paymentAreaId is incorrect.", 12, action.getPaymentAreaId());
    }

    /**
     * Accuracy test for getter and setter of paymentIds. The paymentIds should be set properly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testPaymentIds() throws Exception {
        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
        action.setPaymentIds(paymentIds);
        assertEquals("The paymentIds is incorrect.", paymentIds, action.getPaymentIds());
    }

    /**
     * Accuracy test for validate(). When the paymentIds is null.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testValidate_Null() throws Exception {
        action.setPaymentIds(null);
        action.validate();
    }

    /**
     * Accuracy test for validate(). When the paymentIds is null.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testValidate1() throws Exception {
        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier paymentId = new PaymentIdentifier();
        paymentId.setPaymentDetailId(null);
        paymentIds.add(paymentId);
        action.setPaymentIds(paymentIds);
        action.validate();
    }

    /**
     * Accuracy test for validate(). When the paymentIds is null.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testValidate2() throws Exception {
        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier paymentId = new PaymentIdentifier();
        paymentId.setContestId(1L);
        paymentId.setProjectInfoTypeId(2L);

        paymentIds.add(paymentId);
        action.setPaymentIds(paymentIds);
        action.validate();
    }

    /**
     * Accuracy test for validate(). When the paymentIds is null.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testValidate3() throws Exception {
        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier paymentId1 = new PaymentIdentifier();
        paymentId1.setPaymentDetailId(1L);
        paymentIds.add(paymentId1);

        PaymentIdentifier paymentId2 = new PaymentIdentifier();
        paymentId2.setPaymentDetailId(2L);
        paymentIds.add(paymentId2);

        action.setPaymentIds(paymentIds);

        action.validate();
    }

    /**
     * Accuracy test for execute(). When the paymentIds is null, the action should be executed properly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testExecute1() throws Exception {
        action.setPaymentIds(null);
        assertEquals("The execute is inocrrect.", "success", action.execute());

        List<AccountingAuditRecord> records = MockBillingCostAuditService.getRecords();
        assertEquals("The audit should be called.", 1, records.size());

        AccountingAuditRecord record = records.get(0);
        assertEquals("The audit should be called.", "accuracytest", record.getAction());
        assertTrue("The audit should be called.", (new Date().getTime() - record.getTimestamp().getTime()) < 1000000);
        assertEquals("The audit should be called.", null, record.getUserName());
    }

    /**
     * Accuracy test for execute(). When the paymentIds is not null, the action should be executed properly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testExecute2() throws Exception {
        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier paymentId1 = new PaymentIdentifier();
        paymentId1.setPaymentDetailId(1L);
        paymentIds.add(paymentId1);

        PaymentIdentifier paymentId2 = new PaymentIdentifier();
        paymentId2.setPaymentDetailId(2L);
        paymentIds.add(paymentId2);

        action.setPaymentIds(paymentIds);

        assertEquals("The execute is inocrrect.", "success", action.execute());

        assertEquals("The execute is inocrrect.", 1L, billingCostDataService.getPaymentAreaId());
        assertEquals("The execute is inocrrect.", paymentIds, billingCostDataService.getPaymentIds());
        assertNotNull("The execute is inocrrect.", billingCostDataService.getTcSubject());

        List<AccountingAuditRecord> records = MockBillingCostAuditService.getRecords();
        assertEquals("The audit should be called.", 1, records.size());

        AccountingAuditRecord record = records.get(0);
        assertEquals("The audit should be called.", "accuracytest", record.getAction());
        assertTrue("The audit should be called.", (new Date().getTime() - record.getTimestamp().getTime()) < 1000000);
        assertEquals("The audit should be called.", null, record.getUserName());
    }
}
