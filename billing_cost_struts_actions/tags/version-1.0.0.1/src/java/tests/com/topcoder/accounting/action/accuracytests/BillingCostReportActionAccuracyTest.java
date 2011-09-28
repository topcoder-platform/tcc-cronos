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

import com.topcoder.accounting.action.BillingCostReportAction;
import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostAuditService;
import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostDataService;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;
import com.topcoder.accounting.entities.dto.BillingCostReportEntry;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.service.BillingCostDataService;

/**
 * Accuracy test for BillingCostReportAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BillingCostReportActionAccuracyTest {
    /**
     * Represents the instance of BillingCostReportAction used in test.
     */
    private BillingCostReportAction action;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() {
        action = new BillingCostReportAction();
        action.setBillingCostAuditService(new MockBillingCostAuditService());
        action.setServletRequest(new MockHttpServletRequest());
        action.setAction("accuracytest");
        action.setDefaultDuration(100);
        action.setCriteria(new BillingCostReportCriteria());
        action.setPageNumber(1);
        action.setPageSize(10);

        BillingCostDataService billingCostDataService = new MockBillingCostDataService();
        action.setBillingCostDataService(billingCostDataService);
    }

    /**
     * Accuracy test for setCriteria(BillingCostReportCriteria criteria). The criteria should be set.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testSetCriteria() throws Exception {
        BillingCostReportCriteria criteria = new BillingCostReportCriteria();
        action.setCriteria(criteria);
        assertEquals("The criteria is incorrect.", criteria, action.getCriteria());
    }

    /**
     * Accuracy test for setter and getter of pageNumber.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testPageNumber() throws Exception {
        action.setPageNumber(2);
        assertEquals("The pageNumber is incorrect.", 2, action.getPageNumber());
    }

    /**
     * Accuracy test for setter and getter of pageSize.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testPageSize() throws Exception {
        action.setPageSize(11);
        assertEquals("The pageSize is incorrect.", 11, action.getPageSize());
    }

    /**
     * Accuracy test for setDefaultDuration(long defaultDuration). The defaultDuration should be set properly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testBillingCostDataService() throws Exception {
        action.setDefaultDuration(101);
        assertEquals("The setDefaultDuration is incorrect.", 101L,
            AccuracyTestHelper.getFieldObj(BillingCostReportAction.class, action, "defaultDuration"));
    }

    /**
     * Accuracy test for checkConfiguration(). No exception thrown.
     */
    @Test
    public void testCheckConfiguration() {
        action.checkConfiguration();
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

        PagedResult<BillingCostReportEntry> result = action.getBillingCostReportEntries();
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
        BillingCostReportCriteria criteria = new BillingCostReportCriteria();
        criteria.setProjectId(3L);
        action.setCriteria(criteria);

        action.execute();

        List<AccountingAuditRecord> records = MockBillingCostAuditService.getRecords();
        assertEquals("The audit should be called.", 1, records.size());

        AccountingAuditRecord record = records.get(0);
        assertEquals("The audit should be called.", "accuracytest", record.getAction());
        assertTrue("The audit should be called.", (new Date().getTime() - record.getTimestamp().getTime()) < 1000000);
        assertEquals("The audit should be called.", null, record.getUserName());

        PagedResult<BillingCostReportEntry> result = action.getBillingCostReportEntries();
        assertEquals("The execute is incorrect.", 1, result.getPageNo());
        assertEquals("The execute is incorrect.", 10, result.getPageSize());
        assertEquals("The execute is incorrect.", 3, result.getTotalPages());
    }
}
