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

import com.topcoder.accounting.action.BillingCostExportDetailsAction;
import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostAuditService;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.PagedResult;

/**
 * Accuracy test for BillingCostExportDetailsAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BillingCostExportDetailsActionAccuracyTest {
    /**
     * Represents the instance of BillingCostExportDetailsAction used in test.
     */
    private BillingCostExportDetailsAction action;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() {
        action = new BillingCostExportDetailsAction();
        action.setBillingCostAuditService(new MockBillingCostAuditService());
        action.setServletRequest(new MockHttpServletRequest());
        action.setAction("accuracytest");
        action.setPageNumber(1);
        action.setPageSize(10);
        action.setBillingCostExportId(11);
    }

    /**
     * Accuracy test for getter and setter of BillingCostExportId.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testBillingCostExportId() throws Exception {
        action.setBillingCostExportId(12);
        assertEquals("The criteria is incorrect.", 12, action.getBillingCostExportId());
    }

    /**
     * Accuracy test for execute(). The action should be executed properly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testExecute() throws Exception {
        action.execute();

        List<AccountingAuditRecord> records = MockBillingCostAuditService.getRecords();
        assertEquals("The audit should be called.", 1, records.size());

        AccountingAuditRecord record = records.get(0);
        assertEquals("The audit should be called.", "accuracytest", record.getAction());
        assertTrue("The audit should be called.", (new Date().getTime() - record.getTimestamp().getTime()) < 1000000);
        assertEquals("The audit should be called.", null, record.getUserName());

        PagedResult<BillingCostExportDetail> result = action.getBillingCostExportDetails();
        assertEquals("The execute is incorrect.", 1, result.getPageNo());
        assertEquals("The execute is incorrect.", 10, result.getPageSize());
        assertEquals("The execute is incorrect.", 11, result.getTotalPages());
    }
}
