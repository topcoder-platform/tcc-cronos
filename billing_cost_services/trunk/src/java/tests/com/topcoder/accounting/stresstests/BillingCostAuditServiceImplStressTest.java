/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.stresstests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.service.impl.BillingCostAuditServiceImpl;

/**
 * <p>
 * Stress tests for class <code>BillingCostAuditServiceImpl</code>.
 * </p>
 *
 * @author dingying131
 * @version 1.0
 */
public class BillingCostAuditServiceImplStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents the <code>BillingCostAuditServiceImpl</code> instance used to test against.
     * </p>
     */
    private BillingCostAuditServiceImpl service;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostAuditServiceImplStressTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        clearData();
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void before() throws Exception {
        loadData();
        service = (BillingCostAuditServiceImpl) APP_CONTEXT.getBean("billingCostAuditService");
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void after() throws Exception {
        clearData();
    }

    /**
     * <p>
     * Stress test for the method <code>getBillingCostExportHistory()</code>.<br>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportHistory() throws Exception {
        logEntrance("getBillingCostExportHistory");
        for (int i = 0; i < TIMES; i++) {
            service.getBillingCostExportHistory(null, 1, 2);
        }
        logExit();
    }

    /**
     * <p>
     * Stress test for the method <code>getBillingCostExportDetails()</code>.<br>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportDetails1() throws Exception {
        logEntrance("getBillingCostExportDetails");
        for (int i = 0; i < TIMES; i++) {
            service.getBillingCostExportDetails(1, 1, 2);
        }
        logExit();
    }

    /**
     * <p>
     * Stress test for the method <code>getBillingCostExportDetails()</code>.<br>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportDetails2() throws Exception {
        logEntrance("getBillingCostExportDetails");
        for (int i = 0; i < TIMES; i++) {
            service.getBillingCostExportDetails(true, 1, 2);
        }
        logExit();
    }

    /**
     * <p>
     * Stress test for the method <code>auditAccountingAction()</code>.<br>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testAuditAccountingAction() throws Exception {

        AccountingAuditRecord record = new AccountingAuditRecord();
        record.setAction("test");
        record.setTimestamp(new Date());
        record.setUserName("a");

        logEntrance("auditAccountingAction");
        for (int i = 0; i < TIMES; i++) {
            service.auditAccountingAction(record);
        }
        logExit();
    }

    /**
     * <p>
     * Stress test for the method <code>getAccountingAuditHistory()</code>.<br>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAccountingAuditHistory() throws Exception {

        logEntrance("getAccountingAuditHistory");
        for (int i = 0; i < TIMES; i++) {
            service.getAccountingAuditHistory(null, 1, 1);
        }
        logExit();
    }

    /**
     * <p>
     * Stress test for the method <code>updateBillingCostExportDetails()</code>.<br>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testUpdateBillingCostExportDetails() throws Exception {

        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setBillingCostExportDetailIds(new long[] {1L});
        update.setInvoiceNumber("100");
        updates.add(update);

        logEntrance("getAccountingAuditHistory");
        for (int i = 0; i < TIMES; i++) {
            service.updateBillingCostExportDetails(updates);
        }
        logExit();
    }

    /**
     * <p>
     * Stress test for the method <code>getLatestInvoiceNumber()</code>.<br>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetLatestInvoiceNumber() throws Exception {

        logEntrance("getLatestInvoiceNumber");
        for (int i = 0; i < TIMES; i++) {
            service.getLatestInvoiceNumber();
        }
        logExit();
    }
}
