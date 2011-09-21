/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.accounting.action.BillingCostExportHistoryAction;
import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostAuditService;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExport;
import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;
import com.topcoder.accounting.entities.dto.PagedResult;

/**
 * Accuracy test for BillingCostExportHistoryAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BillingCostExportHistoryActionAccuracyTest {
    /**
     * Represents the instance of BillingCostExportHistoryAction used in test.
     */
    private BillingCostExportHistoryAction action;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() {
        action = new BillingCostExportHistoryAction();
        action.setBillingCostAuditService(new MockBillingCostAuditService());
        action.setServletRequest(new MockHttpServletRequest());
        action.setAction("accuracytest");
        action.setPageNumber(1);
        action.setPageSize(10);
    }

    /**
     * Accuracy test for getter and setter of Criteria.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testCriteria() throws Exception {
        BillingCostExportHistoryCriteria criteria = new BillingCostExportHistoryCriteria();
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
        action.setCriteria(null);
        action.execute();

        List<AccountingAuditRecord> records = MockBillingCostAuditService.getRecords();
        assertEquals("The audit should be called.", 1, records.size());

        AccountingAuditRecord record = records.get(0);
        assertEquals("The audit should be called.", "accuracytest", record.getAction());
        assertTrue("The audit should be called.", (new Date().getTime() - record.getTimestamp().getTime()) < 1000000);
        assertEquals("The audit should be called.", null, record.getUserName());

        PagedResult<BillingCostExport> result = action.getBillingCostExports();
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
        BillingCostExportHistoryCriteria criteria = new BillingCostExportHistoryCriteria();
        criteria.setAccountantName("test");
        action.setCriteria(criteria);
        action.execute();

        List<AccountingAuditRecord> records = MockBillingCostAuditService.getRecords();
        assertEquals("The audit should be called.", 1, records.size());

        AccountingAuditRecord record = records.get(0);
        assertEquals("The audit should be called.", "accuracytest", record.getAction());
        assertTrue("The audit should be called.", (new Date().getTime() - record.getTimestamp().getTime()) < 1000000);
        assertEquals("The audit should be called.", null, record.getUserName());

        PagedResult<BillingCostExport> result = action.getBillingCostExports();
        assertEquals("The execute is incorrect.", 1, result.getPageNo());
        assertEquals("The execute is incorrect.", 10, result.getPageSize());
        assertEquals("The execute is incorrect.", 4, result.getTotalPages());
    }
}
