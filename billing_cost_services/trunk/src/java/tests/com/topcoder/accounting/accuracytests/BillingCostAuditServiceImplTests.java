/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dao.BillingCostExport;
import com.topcoder.accounting.entities.dao.BillingCostExportDetail;
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;
import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.service.impl.BillingCostAuditServiceImpl;


/**
 * Failure tests of BillingCostAuditServiceImpl.
 *
 * @author gjw99
 * @version 1.0
 */
public class BillingCostAuditServiceImplTests {
    /**
     * <p>Represents the ApplicationContext used in tests.</p>
     */
    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext(
            "accuracy/beans.xml");

    /**
     * <p>Represents the BillingCostAuditServiceImpl used in tests.</p>
     */
    private BillingCostAuditServiceImpl instance;

    /**
     * <p>Represents the Connection used in tests.</p>
     */
    private Connection conn;

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = (BillingCostAuditServiceImpl) CONTEXT.getBean("billingCostAuditService");
        conn = TestsHelper.getConnection(CONTEXT);
        TestsHelper.clearDB(conn);
        TestsHelper.loadDB(conn);
    }

    /**
     * <p>Cleans up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @After
    public void tearDown() throws Exception {
    	TestsHelper.clearDB(conn);
    	TestsHelper.closeConnection(conn);
        instance = null;
    }

    /**
     * <p>Accuracy test the getBillingCostExportHistory method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportHistory1() throws Exception {
    	PagedResult<BillingCostExport> result = instance.getBillingCostExportHistory(null, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 3, result.getTotalRecords());
    }

    /**
     * <p>Accuracy test the getBillingCostExportHistory method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportHistory2() throws Exception {
    	PagedResult<BillingCostExport> result = instance.getBillingCostExportHistory(null, 1, 2);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 2, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportHistory method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportHistory3() throws Exception {
    	PagedResult<BillingCostExport> result = instance.getBillingCostExportHistory(null, 2, 2);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 1, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportHistory method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportHistory4() throws Exception {
    	BillingCostExportHistoryCriteria criteria = new BillingCostExportHistoryCriteria();
    	criteria.setPaymentAreaId(1l);
    	PagedResult<BillingCostExport> result = instance.getBillingCostExportHistory(criteria, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 2, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportHistory method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportHistory5() throws Exception {
    	BillingCostExportHistoryCriteria criteria = new BillingCostExportHistoryCriteria();
    	Calendar cal = Calendar.getInstance();
    	cal.set(2011, Calendar.SEPTEMBER, 16, 10, 10, 10);
    	criteria.setEndDate(cal.getTime());
    	PagedResult<BillingCostExport> result = instance.getBillingCostExportHistory(criteria, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 2, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportHistory method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportHistory6() throws Exception {
    	BillingCostExportHistoryCriteria criteria = new BillingCostExportHistoryCriteria();
    	Calendar cal = Calendar.getInstance();
    	cal.set(2011, Calendar.SEPTEMBER, 16, 10, 10, 10);
    	criteria.setStartDate(cal.getTime());
    	PagedResult<BillingCostExport> result = instance.getBillingCostExportHistory(criteria, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 1, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportDetails method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportDetails1() throws Exception {
    	PagedResult<BillingCostExportDetail> result = instance.getBillingCostExportDetails(1, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 2, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportDetails method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportDetails2() throws Exception {
    	PagedResult<BillingCostExportDetail> result = instance.getBillingCostExportDetails(3, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 1, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportDetails method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportDetails3() throws Exception {
    	PagedResult<BillingCostExportDetail> result = instance.getBillingCostExportDetails(2, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 0, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportDetails method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportDetails_1() throws Exception {
    	PagedResult<BillingCostExportDetail> result = instance.getBillingCostExportDetails(true, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 2, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportDetails method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportDetails_2() throws Exception {
    	PagedResult<BillingCostExportDetail> result = instance.getBillingCostExportDetails(false, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 1, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getBillingCostExportDetails method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getBillingCostExportDetails_3() throws Exception {
    	PagedResult<BillingCostExportDetail> result = instance.getBillingCostExportDetails(true, 2, 2);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 0, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the auditAccountingAction and getAccountingAuditHistory method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_auditAccountingAction() throws Exception {

    	AccountingAuditRecord record = new AccountingAuditRecord();
        record.setAction("action");
        record.setId(2L);

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 10, 10, 10, 10, 10);
        record.setTimestamp(cal.getTime());
        record.setUserName("user");
    	instance.auditAccountingAction(record);
    	PagedResult<AccountingAuditRecord> result = instance.getAccountingAuditHistory(null, -1, -1);
    	assertNotNull("return result is invalid", result);
    	assertEquals("return result is invalid", 1, result.getRecords().size());
    	result = instance.getAccountingAuditHistory(null, 2, 2);
     	assertNotNull("return result is invalid", result);
     	assertEquals("return result is invalid", 0, result.getRecords().size());
     	AccountingAuditRecordCriteria criteria = new AccountingAuditRecordCriteria();
     	criteria.setAction("other action");
    	result = instance.getAccountingAuditHistory(criteria, -1, -1);
     	assertNotNull("return result is invalid", result);
     	assertEquals("return result is invalid", 0, result.getRecords().size());
    }

    /**
     * <p>Accuracy test the getLatestInvoiceNumber method.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getLatestInvoiceNumber() throws Exception {
    	String result = instance.getLatestInvoiceNumber();
    	assertEquals("return result is invalid", "invoice4", result);
    }
}
