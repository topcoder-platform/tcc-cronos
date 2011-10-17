/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExport;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dao.PaymentArea;
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;
import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;
import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;
import com.topcoder.accounting.entities.dto.BillingCostReportEntry;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.PaymentIdentifier;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.accounting.service.BillingCostDataService;
import com.topcoder.accounting.service.LookupService;
import com.topcoder.accounting.service.impl.BaseUnitTests;
import com.topcoder.security.TCSubject;

/**
 * <p>
 * Demo tests.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class DemoTest extends BaseUnitTests {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DemoTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * The Demo API.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemoAPI() throws Exception {
        LookupService lookupService = (LookupService) APP_CONTEXT.getBean("lookupService");
        BillingCostAuditService billingCostAuditService = (BillingCostAuditService) APP_CONTEXT
            .getBean("billingCostAuditService");
        BillingCostDataService billingCostDataService = (BillingCostDataService) APP_CONTEXT
            .getBean("billingCostDataService");

        // Get all payment areas
        List<PaymentArea> paymentAreas = lookupService.getPaymentAreas();
        // The list would contain all payment areas, which in our case, would be 3.

        // Retrieve a billing cost report for a project for a specific stretch of time, getting the first page of
        // the results
        BillingCostReportCriteria billingCostReportCriteria = new BillingCostReportCriteria();
        billingCostReportCriteria.setProjectId(1L);
        Calendar calendar = Calendar.getInstance();
        // August 1, 2011
        calendar.set(2011, 7, 1);
        billingCostReportCriteria.setStartDate(calendar.getTime());
        // August 30, 2011
        calendar.set(2011, 7, 30);
        billingCostReportCriteria.setEndDate(calendar.getTime());
        PagedResult<BillingCostReportEntry> billingCostReportEntries = billingCostDataService
            .getBillingCostReport(billingCostReportCriteria, 1, 10);
        // This result would get the first 10 entries in the report for a specific project in the month of august,
        // as part of a monthly billing report

        // Export the above-retrieved entries
        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
        PaymentIdentifier pid = new PaymentIdentifier();
        pid.setContestId(1L);
        pid.setPaymentDetailId(2L);
        pid.setProjectInfoTypeId(3L);
        paymentIds.add(pid);
        // the current user
        TCSubject user = new TCSubject(3L);
        // assume this is the area of payment we want, such as studio
        long paymentAreaId = 1L;
        billingCostDataService.exportBillingCostData(paymentIds, paymentAreaId, user);
        // This action would result in the export of these entries, as identified by their PaymentIdentifiers

        // The following methods can be use to get export data, get an identifier, and to manage audits.

        // Get all exports for a given payment area for a specific date range, showing the first page
        BillingCostExportHistoryCriteria billingCostExportHistoryCriteria = new BillingCostExportHistoryCriteria();
        billingCostExportHistoryCriteria.setPaymentAreaId(1L);
        // August 1, 2011
        calendar.set(2011, 7, 1);
        billingCostExportHistoryCriteria.setStartDate(calendar.getTime());
        // August 30, 2011
        calendar.set(2011, 7, 30);
        billingCostExportHistoryCriteria.setEndDate(calendar.getTime());
        PagedResult<BillingCostExport> exports = billingCostAuditService.getBillingCostExportHistory(
            billingCostExportHistoryCriteria, 1, 10);
        // This result would get the first 10 entries in the report for a specific payment area in the month of
        // august, as part of a monthly export report. Note that these results may include the result we exported
        // above.

        // Gets all details for a single cost export, showing the first page
        // one of the above exports
        long billingCostExportId = 1;
        PagedResult<BillingCostExportDetail> details = billingCostAuditService.getBillingCostExportDetails(
            billingCostExportId, 1, 10);

        // Another way of searching details is to search for all that are now in quick books, showing the first
        // page
        PagedResult<BillingCostExportDetail> details2 = billingCostAuditService.getBillingCostExportDetails(true,
            1, 10);

        // This method creates a new audit record
        // new audit record with data
        AccountingAuditRecord accountingAuditRecord = new AccountingAuditRecord();
        accountingAuditRecord.setId(10);
        accountingAuditRecord.setAction("add");
        accountingAuditRecord.setUserName("admin");
        accountingAuditRecord.setTimestamp(new Date());
        billingCostAuditService.auditAccountingAction(accountingAuditRecord);

        // Gets all audits records for a given action for a specific date range, showing the first page
        AccountingAuditRecordCriteria accountingAuditRecordCriteria = new AccountingAuditRecordCriteria();
        accountingAuditRecordCriteria.setAction("updateBillingCostExportDetails");
        // August 1, 2011
        calendar.set(2011, 7, 1);
        accountingAuditRecordCriteria.setStartDate(calendar.getTime());
        // August 30, 2011
        calendar.set(2011, 7, 30);
        accountingAuditRecordCriteria.setEndDate(calendar.getTime());
        PagedResult<AccountingAuditRecord> auditRecords = billingCostAuditService.getAccountingAuditHistory(
            accountingAuditRecordCriteria, 1, 10);

        // Perform some updates
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setInvoiceNumber("55");
        update.setBillingCostExportDetailIds(new long[] {1, 2, 3});
        updates.add(update);
        billingCostAuditService.updateBillingCostExportDetails(updates);

        // Get the latest invoice number
        String latestInvoiceNumber = billingCostAuditService.getLatestInvoiceNumber();

    }
}
