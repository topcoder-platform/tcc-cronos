/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.topcoder.accounting.action.AuditHistoryAction;
import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostAuditService;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.service.BillingCostAuditService;

/**
 * Accuracy test for AuditHistoryAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuditHistoryActionAccuracyTest {
    /**
     * Represents the instance of AuditHistoryAction used in test.
     */
    private AuditHistoryAction action;

    /**
     * Represents the instance of HttpServletRequest used in test.
     */
    private MockHttpServletRequest servletRequest;

    /**
     * Represents the instance of BillingCostAuditService used in test.
     */
    private BillingCostAuditService billingCostAuditService;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() {
        action = new AuditHistoryAction();
        servletRequest = new MockHttpServletRequest();
        servletRequest.setSession(new MockHttpSession());

        action.setServletRequest(servletRequest);
        action.setPageNumber(1);
        action.setPageSize(10);
        action.setAction("accuracytest");

        billingCostAuditService = new MockBillingCostAuditService();
        action.setBillingCostAuditService(billingCostAuditService);
    }

    /**
     * Test the constructor AuditHistoryAction(). The instance should be created.
     */
    @Test
    public void testCtor() {
        assertNotNull("The instance should be created.", action);
    }

    /**
     * Accuracy test for getter and setter of Criteria. The criteria should be set and get properly.
     */
    @Test
    public void testCriteria() {
        AccountingAuditRecordCriteria criteria = new AccountingAuditRecordCriteria();
        action.setCriteria(criteria);
        assertEquals("The criteria is incorrect.", criteria, action.getCriteria());
    }

    /**
     * Accuracy test for execute(). When the criteria is null, the action should be executed properly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testExecute1() throws Exception {
        action.execute();

        List<AccountingAuditRecord> records = MockBillingCostAuditService.getRecords();
        assertEquals("The audit should be called.", 1, records.size());

        AccountingAuditRecord record = records.get(0);
        assertEquals("The audit should be called.", "accuracytest", record.getAction());
        assertTrue("The audit should be called.", (new Date().getTime() - record.getTimestamp().getTime()) < 1000000);
        assertEquals("The audit should be called.", null, record.getUserName());

        PagedResult<AccountingAuditRecord> result = action.getAccountingAuditRecords();
        assertEquals("The execute is incorrect.", 1, result.getPageNo());
        assertEquals("The execute is incorrect.", 10, result.getPageSize());
        assertEquals("The execute is incorrect.", 0, result.getTotalPages());
    }

    /**
     * Accuracy test for execute(). When the criteria is not null, the action should be executed properly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testExecute2() throws Exception {
        AccountingAuditRecordCriteria criteria = new AccountingAuditRecordCriteria();
        criteria.setAction("test");
        action.setCriteria(criteria);

        action.execute();

        List<AccountingAuditRecord> records = MockBillingCostAuditService.getRecords();
        assertEquals("The audit should be called.", 1, records.size());

        AccountingAuditRecord record = records.get(0);
        assertEquals("The audit should be called.", "accuracytest", record.getAction());
        assertTrue("The audit should be called.", (new Date().getTime() - record.getTimestamp().getTime()) < 1000000);
        assertEquals("The audit should be called.", null, record.getUserName());

        PagedResult<AccountingAuditRecord> result = action.getAccountingAuditRecords();
        assertEquals("The execute is incorrect.", 1, result.getPageNo());
        assertEquals("The execute is incorrect.", 10, result.getPageSize());
        assertEquals("The execute is incorrect.", 4, result.getTotalPages());
    }
}
