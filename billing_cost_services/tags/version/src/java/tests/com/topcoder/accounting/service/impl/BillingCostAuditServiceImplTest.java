/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
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
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;
import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteria;
import com.topcoder.accounting.entities.dto.PagedResult;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.accounting.service.EntityNotFoundException;

/**
 * <p>
 * Unit tests for class <code>BillingCostAuditServiceImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BillingCostAuditServiceImplTest extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>BillingCostAuditServiceImpl</code> instance used to test against.
     * </p>
     */
    private BillingCostAuditServiceImpl impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostAuditServiceImplTest.class);
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
        impl = (BillingCostAuditServiceImpl) APP_CONTEXT.getBean("billingCostAuditService");
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
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BillingCostAuditServiceImpl</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof BillingCostAuditService);
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseService);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BillingCostAuditServiceImpl()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>getBillingCostExportHistory(BillingCostExportHistoryCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportHistory() throws Exception {
        PagedResult<BillingCostExport> result = impl.getBillingCostExportHistory(null, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 3, result.getTotalRecords());

        List<BillingCostExport> records = result.getRecords();
        assertEquals("The record size should be same as", 3, records.size());
        assertEquals("The record account name should be same as", "user1", records.get(0).getAccountantName());
        assertEquals("The record payment area id should be same as", 1, records.get(0).getPaymentArea().getId());
        assertEquals("The record payment area id should be same as", 2, records.get(0).getRecordsCount());
        assertEquals("The record account name should be same as", "user1", records.get(1).getAccountantName());
        assertEquals("The record payment area id should be same as", 2, records.get(1).getPaymentArea().getId());
        assertEquals("The record payment area id should be same as", 3, records.get(1).getRecordsCount());
        assertEquals("The record account name should be same as", "user3", records.get(2).getAccountantName());
        assertEquals("The record payment area id should be same as", 3, records.get(2).getPaymentArea().getId());
        assertEquals("The record payment area id should be same as", 4, records.get(2).getRecordsCount());
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>getBillingCostExportHistory(BillingCostExportHistoryCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportHistory2() throws Exception {
        BillingCostExportHistoryCriteria criteria = new BillingCostExportHistoryCriteria();
        criteria.setAccountantName("user1");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 11, 30);
        criteria.setEndDate(calendar.getTime());
        PagedResult<BillingCostExport> result = impl.getBillingCostExportHistory(criteria, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        List<BillingCostExport> records = result.getRecords();
        assertEquals("The record size should be same as", 2, records.size());
        assertEquals("The record account name should be same as", "user1", records.get(0).getAccountantName());
        assertEquals("The record payment area id should be same as", 1, records.get(0).getPaymentArea().getId());
        assertEquals("The record payment area id should be same as", 2, records.get(0).getRecordsCount());
        assertEquals("The record account name should be same as", "user1", records.get(1).getAccountantName());
        assertEquals("The record payment area id should be same as", 2, records.get(1).getPaymentArea().getId());
        assertEquals("The record payment area id should be same as", 3, records.get(1).getRecordsCount());
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>getBillingCostExportHistory(BillingCostExportHistoryCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportHistory3() throws Exception {
        BillingCostExportHistoryCriteria criteria = new BillingCostExportHistoryCriteria();
        criteria.setPaymentAreaId(2L);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 1, 1);
        criteria.setStartDate(calendar.getTime());
        PagedResult<BillingCostExport> result = impl.getBillingCostExportHistory(criteria, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 1, result.getTotalRecords());

        List<BillingCostExport> records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record account name should be same as", "user1", records.get(0).getAccountantName());
        assertEquals("The record payment area id should be same as", 2, records.get(0).getPaymentArea().getId());
        assertEquals("The record payment area id should be same as", 3, records.get(0).getRecordsCount());
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>getBillingCostExportHistory(BillingCostExportHistoryCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportHistory4() throws Exception {
        BillingCostExportHistoryCriteria criteria = new BillingCostExportHistoryCriteria();
        criteria.setAccountantName("user1");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 8, 1);
        criteria.setStartDate(calendar.getTime());
        calendar.set(2011, 8, 10);
        criteria.setEndDate(calendar.getTime());
        PagedResult<BillingCostExport> result = impl.getBillingCostExportHistory(criteria, 1, 1);
        assertEquals("The page no should be same as", 1, result.getPageNo());
        assertEquals("The page size should be same as", 1, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        List<BillingCostExport> records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record account name should be same as", "user1", records.get(0).getAccountantName());
        assertEquals("The record payment area id should be same as", 1, records.get(0).getPaymentArea().getId());
        assertEquals("The record payment area id should be same as", 2, records.get(0).getRecordsCount());

        result = impl.getBillingCostExportHistory(criteria, 2, 1);
        assertEquals("The page no should be same as", 2, result.getPageNo());
        assertEquals("The page size should be same as", 1, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record account name should be same as", "user1", records.get(0).getAccountantName());
        assertEquals("The record payment area id should be same as", 2, records.get(0).getPaymentArea().getId());
        assertEquals("The record payment area id should be same as", 3, records.get(0).getRecordsCount());

        result = impl.getBillingCostExportHistory(criteria, 3, 1);
        assertEquals("The page no should be same as", 3, result.getPageNo());
        assertEquals("The page size should be same as", 1, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 0, records.size());
    }

    /**
     * <p>
     * Failure test for the method
     * <code>getBillingCostExportHistory(BillingCostExportHistoryCriteria,int, int)</code>.<br>
     * The pageNo is zero.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportHistory_PagenoZero() throws Exception {
        impl.getBillingCostExportHistory(null, 0, 1);
    }

    /**
     * <p>
     * Failure test for the method
     * <code>getBillingCostExportHistory(BillingCostExportHistoryCriteria,int, int)</code>.<br>
     * The pageNo is negative.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportHistory_PagenoNegative() throws Exception {
        impl.getBillingCostExportHistory(null, -2, 1);
    }

    /**
     * <p>
     * Failure test for the method
     * <code>getBillingCostExportHistory(BillingCostExportHistoryCriteria,int, int)</code>.<br>
     * The pageSize is zero.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportHistory_PagesizeZero() throws Exception {
        impl.getBillingCostExportHistory(null, 1, 0);
    }

    /**
     * <p>
     * Failure test for the method
     * <code>getBillingCostExportHistory(BillingCostExportHistoryCriteria,int, int)</code>.<br>
     * The pageSize is negative.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportHistory_PagesizeNegative() throws Exception {
        impl.getBillingCostExportHistory(null, 1, -1);
    }

    /**
     * <p>
     * Failure test for the method
     * <code>getBillingCostExportHistory(BillingCostExportHistoryCriteria,int, int)</code>.<br>
     * Exception occurs while accessing to DB.<br>
     * Expect BillingCostServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostServiceException.class)
    public void testGetBillingCostExportHistory_BillingCostServiceExceptionOccurs() throws Exception {
        impl = (BillingCostAuditServiceImpl) APP_CONTEXT_INVALID.getBean("billingCostAuditService");
        impl.getBillingCostExportHistory(null, -1, -1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingCostExportDetails(long,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportDetails() throws Exception {
        PagedResult<BillingCostExportDetail> result = impl.getBillingCostExportDetails(1, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 3, result.getTotalRecords());

        List<BillingCostExportDetail> records = result.getRecords();
        assertEquals("The record size should be same as", 3, records.size());
        assertEquals("The record billing cost export id should be same as", 1, records.get(0)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "10", records.get(0).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(0).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer3", records.get(0).getCustomer());
        assertEquals("The record billing cost export id should be same as", 1, records.get(1)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "20", records.get(1).getInvoiceNumber());
        assertFalse("The record in quick books field should be false", records.get(1).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer2", records.get(1).getCustomer());
        assertEquals("The record billing cost export id should be same as", 1, records.get(2)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "30", records.get(2).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(2).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer1", records.get(2).getCustomer());

        result = impl.getBillingCostExportDetails(2, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 0, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 0, records.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingCostExportDetails(long,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportDetails2() throws Exception {
        PagedResult<BillingCostExportDetail> result = impl.getBillingCostExportDetails(1, 1, 2);
        assertEquals("The page no should be same as", 1, result.getPageNo());
        assertEquals("The page size should be same as", 2, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 3, result.getTotalRecords());

        List<BillingCostExportDetail> records = result.getRecords();
        assertEquals("The record size should be same as", 2, records.size());
        assertEquals("The record billing cost export id should be same as", 1, records.get(0)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "10", records.get(0).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(0).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer3", records.get(0).getCustomer());
        assertEquals("The record billing cost export id should be same as", 1, records.get(1)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "20", records.get(1).getInvoiceNumber());
        assertFalse("The record in quick books field should be false", records.get(1).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer2", records.get(1).getCustomer());

        result = impl.getBillingCostExportDetails(1, 2, 2);
        assertEquals("The page no should be same as", 2, result.getPageNo());
        assertEquals("The page size should be same as", 2, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 3, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record billing cost export id should be same as", 1, records.get(0)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "30", records.get(0).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(0).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer1", records.get(0).getCustomer());
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(long,int, int)</code>.<br>
     * The pageNo is zero.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportDetails_PagenoZero() throws Exception {
        impl.getBillingCostExportDetails(1, 0, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(long,int, int)</code>.<br>
     * The pageNo is negative.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportDetails_PagenoNegative() throws Exception {
        impl.getBillingCostExportDetails(1, -2, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(long,int, int)</code>.<br>
     * The pageSize is zero.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportDetails_PagesizeZero() throws Exception {
        impl.getBillingCostExportDetails(1, 1, 0);
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(long,int, int)</code>.<br>
     * The pageSize is negative.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportDetails_PagesizeNegative() throws Exception {
        impl.getBillingCostExportDetails(1, 1, -1);
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(long,int, int)</code>.<br>
     * Exception occurs while accessing to DB.<br>
     * Expect BillingCostServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostServiceException.class)
    public void testGetBillingCostExportDetails_BillingCostServiceExceptionOccurs() throws Exception {
        impl = (BillingCostAuditServiceImpl) APP_CONTEXT_INVALID.getBean("billingCostAuditService");
        impl.getBillingCostExportDetails(1, -1, 1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingCostExportDetails(boolean,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportDetailsBoolean() throws Exception {
        PagedResult<BillingCostExportDetail> result = impl.getBillingCostExportDetails(true, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        List<BillingCostExportDetail> records = result.getRecords();
        assertEquals("The record size should be same as", 2, records.size());
        assertEquals("The record billing cost export id should be same as", 1, records.get(0)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "30", records.get(0).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(0).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer1", records.get(0).getCustomer());
        assertEquals("The record billing cost export id should be same as", 1, records.get(1)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "10", records.get(1).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(1).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer3", records.get(1).getCustomer());

        result = impl.getBillingCostExportDetails(false, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 1, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record billing cost export id should be same as", 1, records.get(0)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "20", records.get(0).getInvoiceNumber());
        assertFalse("The record in quick books field should be false", records.get(0).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer2", records.get(0).getCustomer());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getBillingCostExportDetails(boolean,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostExportDetailsBoolean2() throws Exception {
        PagedResult<BillingCostExportDetail> result = impl.getBillingCostExportDetails(true, 1, 1);
        assertEquals("The page no should be same as", 1, result.getPageNo());
        assertEquals("The page size should be same as", 1, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        List<BillingCostExportDetail> records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record billing cost export id should be same as", 1, records.get(0)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "30", records.get(0).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(0).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer1", records.get(0).getCustomer());

        result = impl.getBillingCostExportDetails(true, 2, 1);
        assertEquals("The page no should be same as", 2, result.getPageNo());
        assertEquals("The page size should be same as", 1, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record billing cost export id should be same as", 1, records.get(0)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "10", records.get(0).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(0).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer3", records.get(0).getCustomer());

        result = impl.getBillingCostExportDetails(true, 3, 1);
        assertEquals("The page no should be same as", 3, result.getPageNo());
        assertEquals("The page size should be same as", 1, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 0, records.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(boolean,int, int)</code>.<br>
     * The pageNo is zero.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportDetailsBoolean_PagenoZero() throws Exception {
        impl.getBillingCostExportDetails(true, 0, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(boolean,int, int)</code>.<br>
     * The pageNo is negative.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportDetailsBoolean_PagenoNegative() throws Exception {
        impl.getBillingCostExportDetails(true, -2, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(boolean,int, int)</code>.<br>
     * The pageSize is zero.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportDetailsBoolean_PagesizeZero() throws Exception {
        impl.getBillingCostExportDetails(true, 1, 0);
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(boolean,int, int)</code>.<br>
     * The pageSize is negative.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBillingCostExportDetailsBoolean_PagesizeNegative() throws Exception {
        impl.getBillingCostExportDetails(true, 1, -1);
    }

    /**
     * <p>
     * Failure test for the method <code>getBillingCostExportDetails(boolean,int, int)</code>.<br>
     * Exception occurs while accessing to DB.<br>
     * Expect BillingCostServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostServiceException.class)
    public void testGetBillingCostExportDetailsBoolean_BillingCostServiceExceptionOccurs() throws Exception {
        impl = (BillingCostAuditServiceImpl) APP_CONTEXT_INVALID.getBean("billingCostAuditService");
        impl.getBillingCostExportDetails(true, -1, 1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>auditAccountingAction(AccountingAuditRecord)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testAuditAccountingAction() throws Exception {
        PagedResult<AccountingAuditRecord> result = impl.getAccountingAuditHistory(null, -1, -1);
        List<AccountingAuditRecord> records = result.getRecords();
        assertEquals("The record size should be same as", 3, records.size());

        AccountingAuditRecord record = new AccountingAuditRecord();
        record.setId(10);
        record.setAction("add");
        record.setUserName("admin");
        record.setTimestamp(new Date());
        impl.auditAccountingAction(record);

        result = impl.getAccountingAuditHistory(null, -1, -1);
        records = result.getRecords();
        assertEquals("The record size should be same as", 4, records.size());
    }

    /**
     * <p>
     * Failure test for the method <code>auditAccountingAction(AccountingAuditRecord)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAuditAccountingAction_ParamNull() throws Exception {
        impl.auditAccountingAction(null);
    }

    /**
     * <p>
     * Failure test for the method <code>auditAccountingAction(AccountingAuditRecord)</code>.<br>
     * The parameter's field is null.<br>
     * Expect BillingCostServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostServiceException.class)
    public void testAuditAccountingAction_FieldNull() throws Exception {
        AccountingAuditRecord record = new AccountingAuditRecord();
        record.setId(20);
        record.setAction("add");
        record.setTimestamp(new Date());
        impl.auditAccountingAction(record);
    }

    /**
     * <p>
     * Failure test for the method <code>auditAccountingAction(AccountingAuditRecord)</code>.<br>
     * The id has existed.<br>
     * Expect BillingCostServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostServiceException.class)
    public void testAuditAccountingAction_IdExist() throws Exception {
        AccountingAuditRecord record = new AccountingAuditRecord();
        record.setId(50);
        record.setAction("add");
        record.setUserName("admin");
        record.setTimestamp(new Date());
        impl.auditAccountingAction(record);

        impl.auditAccountingAction(record);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAccountingAuditHistory() throws Exception {
        PagedResult<AccountingAuditRecord> result = impl.getAccountingAuditHistory(null, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 3, result.getTotalRecords());

        List<AccountingAuditRecord> records = result.getRecords();
        assertEquals("The record size should be same as", 3, records.size());
        assertEquals("The record action should be same as", "add", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());
        assertEquals("The record action should be same as", "get", records.get(1).getAction());
        assertEquals("The record user should be same as", "user", records.get(1).getUserName());
        assertEquals("The record action should be same as", "del", records.get(2).getAction());
        assertEquals("The record user should be same as", "admin", records.get(2).getUserName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAccountingAuditHistory2() throws Exception {
        AccountingAuditRecordCriteria criteria = new AccountingAuditRecordCriteria();
        criteria.setUserName("admin");
        criteria.setAction("  ");
        PagedResult<AccountingAuditRecord> result = impl.getAccountingAuditHistory(criteria, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        List<AccountingAuditRecord> records = result.getRecords();
        assertEquals("The record size should be same as", 2, records.size());
        assertEquals("The record action should be same as", "add", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());
        assertEquals("The record action should be same as", "del", records.get(1).getAction());
        assertEquals("The record user should be same as", "admin", records.get(1).getUserName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAccountingAuditHistory3() throws Exception {
        AccountingAuditRecordCriteria criteria = new AccountingAuditRecordCriteria();
        criteria.setAction("del");
        PagedResult<AccountingAuditRecord> result = impl.getAccountingAuditHistory(criteria, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 1, result.getTotalRecords());

        List<AccountingAuditRecord> records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record action should be same as", "del", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAccountingAuditHistory4() throws Exception {
        AccountingAuditRecordCriteria criteria = new AccountingAuditRecordCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 8, 1);
        criteria.setStartDate(calendar.getTime());
        PagedResult<AccountingAuditRecord> result = impl.getAccountingAuditHistory(criteria, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 3, result.getTotalRecords());

        List<AccountingAuditRecord> records = result.getRecords();
        assertEquals("The record size should be same as", 3, records.size());
        assertEquals("The record action should be same as", "add", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());
        assertEquals("The record action should be same as", "get", records.get(1).getAction());
        assertEquals("The record user should be same as", "user", records.get(1).getUserName());
        assertEquals("The record action should be same as", "del", records.get(2).getAction());
        assertEquals("The record user should be same as", "admin", records.get(2).getUserName());

        calendar.set(2011, 8, 3);
        criteria.setEndDate(calendar.getTime());
        result = impl.getAccountingAuditHistory(criteria, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 2, records.size());
        assertEquals("The record action should be same as", "add", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());
        assertEquals("The record action should be same as", "get", records.get(1).getAction());
        assertEquals("The record user should be same as", "user", records.get(1).getUserName());

        criteria = new AccountingAuditRecordCriteria();
        calendar.set(2011, 8, 2);
        criteria.setEndDate(calendar.getTime());
        result = impl.getAccountingAuditHistory(criteria, -1, -1);
        assertEquals("The page no should be same as", -1, result.getPageNo());
        assertEquals("The page size should be same as", -1, result.getPageSize());
        assertEquals("The total page should be same as", 1, result.getTotalPages());
        assertEquals("The total records should be same as", 1, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record action should be same as", "add", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAccountingAuditHistory5() throws Exception {
        PagedResult<AccountingAuditRecord> result = impl.getAccountingAuditHistory(null, 1, 2);
        assertEquals("The page no should be same as", 1, result.getPageNo());
        assertEquals("The page size should be same as", 2, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 3, result.getTotalRecords());

        List<AccountingAuditRecord> records = result.getRecords();
        assertEquals("The record size should be same as", 2, records.size());
        assertEquals("The record action should be same as", "add", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());
        assertEquals("The record action should be same as", "get", records.get(1).getAction());
        assertEquals("The record user should be same as", "user", records.get(1).getUserName());

        result = impl.getAccountingAuditHistory(null, 2, 2);
        assertEquals("The page no should be same as", 2, result.getPageNo());
        assertEquals("The page size should be same as", 2, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 3, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record action should be same as", "del", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAccountingAuditHistory6() throws Exception {
        AccountingAuditRecordCriteria criteria = new AccountingAuditRecordCriteria();
        criteria.setUserName("admin");
        PagedResult<AccountingAuditRecord> result = impl.getAccountingAuditHistory(criteria, 1, 1);
        assertEquals("The page no should be same as", 1, result.getPageNo());
        assertEquals("The page size should be same as", 1, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        List<AccountingAuditRecord> records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record action should be same as", "add", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());

        result = impl.getAccountingAuditHistory(criteria, 2, 1);
        assertEquals("The page no should be same as", 2, result.getPageNo());
        assertEquals("The page size should be same as", 1, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 1, records.size());
        assertEquals("The record action should be same as", "del", records.get(0).getAction());
        assertEquals("The record user should be same as", "admin", records.get(0).getUserName());

        result = impl.getAccountingAuditHistory(criteria, 3, 1);
        assertEquals("The page no should be same as", 3, result.getPageNo());
        assertEquals("The page size should be same as", 1, result.getPageSize());
        assertEquals("The total page should be same as", 2, result.getTotalPages());
        assertEquals("The total records should be same as", 2, result.getTotalRecords());

        records = result.getRecords();
        assertEquals("The record size should be same as", 0, records.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * The pageNo is zero.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAccountingAuditHistory_PagenoZero() throws Exception {
        impl.getAccountingAuditHistory(null, 0, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * The pageNo is negative.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAccountingAuditHistory_PagenoNegative() throws Exception {
        impl.getAccountingAuditHistory(null, -2, 1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * The pageSize is zero.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAccountingAuditHistory_PagesizeZero() throws Exception {
        impl.getAccountingAuditHistory(null, 1, 0);
    }

    /**
     * <p>
     * Failure test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * The pageSize is negative.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAccountingAuditHistory_PagesizeNegative() throws Exception {
        impl.getAccountingAuditHistory(null, 1, -1);
    }

    /**
     * <p>
     * Failure test for the method <code>getAccountingAuditHistory(AccountingAuditRecordCriteria,int, int)</code>.<br>
     * Exception occurs while accessing to DB.<br>
     * Expect BillingCostServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostServiceException.class)
    public void testGetAccountingAuditHistory_BillingCostServiceExceptionOccurs() throws Exception {
        impl = (BillingCostAuditServiceImpl) APP_CONTEXT_INVALID.getBean("billingCostAuditService");
        impl.getAccountingAuditHistory(null, -1, 1);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testUpdateBillingCostExportDetails() throws Exception {
        PagedResult<BillingCostExportDetail> result = impl.getBillingCostExportDetails(1, -1, -1);
        List<BillingCostExportDetail> records = result.getRecords();
        assertEquals("The record size should be same as", 3, records.size());
        assertEquals("The record billing cost export id should be same as", 1, records.get(0)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "10", records.get(0).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(0).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer3", records.get(0).getCustomer());
        assertEquals("The record billing cost export id should be same as", 1, records.get(1)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "20", records.get(1).getInvoiceNumber());
        assertFalse("The record in quick books field should be false", records.get(1).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer2", records.get(1).getCustomer());
        assertEquals("The record billing cost export id should be same as", 1, records.get(2)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "30", records.get(2).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(2).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer1", records.get(2).getCustomer());

        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setInvoiceNumber("55");
        update.setBillingCostExportDetailIds(new long[] {1, 2, 3});
        updates.add(update);
        impl.updateBillingCostExportDetails(updates);

        result = impl.getBillingCostExportDetails(1, -1, -1);
        records = result.getRecords();
        assertEquals("The record size should be same as", 3, records.size());
        assertEquals("The record billing cost export id should be same as", 1, records.get(0)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "55", records.get(0).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(0).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer3", records.get(0).getCustomer());
        assertEquals("The record billing cost export id should be same as", 1, records.get(1)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "55", records.get(1).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(1).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer2", records.get(1).getCustomer());
        assertEquals("The record billing cost export id should be same as", 1, records.get(2)
            .getBillingCostExportId());
        assertEquals("The record invoice number should be same as", "55", records.get(2).getInvoiceNumber());
        assertTrue("The record in quick books field should be true", records.get(2).isInQuickBooks());
        assertEquals("The record customer should be same as", "customer1", records.get(2).getCustomer());
    }

    /**
     * <p>
     * Failure test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * The parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateBillingCostExportDetails_ParamNull() throws Exception {
        impl.updateBillingCostExportDetails(null);
    }

    /**
     * <p>
     * Failure test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * The parameter is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateBillingCostExportDetails_ParamEmpty() throws Exception {
        impl.updateBillingCostExportDetails(new ArrayList<QuickBooksImportUpdate>());
    }

    /**
     * <p>
     * Failure test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * The parameter contains null element.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateBillingCostExportDetails_NullElement() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        updates.add(null);
        impl.updateBillingCostExportDetails(updates);
    }

    /**
     * <p>
     * Failure test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * The billingCostExportDetailIds is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateBillingCostExportDetails_IdsNull() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setInvoiceNumber("55");
        updates.add(update);
        impl.updateBillingCostExportDetails(updates);
    }

    /**
     * <p>
     * Failure test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * The billingCostExportDetailIds is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateBillingCostExportDetails_IdsEmpty() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setInvoiceNumber("55");
        update.setBillingCostExportDetailIds(new long[0]);
        updates.add(update);
        impl.updateBillingCostExportDetails(updates);
    }

    /**
     * <p>
     * Failure test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * The invoiceNumber is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateBillingCostExportDetails_InvoiceNumberNull() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        // update.setInvoiceNumber("55");
        update.setBillingCostExportDetailIds(new long[] {1, 2, 3});
        updates.add(update);
        impl.updateBillingCostExportDetails(updates);
    }

    /**
     * <p>
     * Failure test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * The invoiceNumber is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateBillingCostExportDetails_InvoiceNumberEmpty() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setInvoiceNumber("   ");
        update.setBillingCostExportDetailIds(new long[] {1, 2, 3});
        updates.add(update);
        impl.updateBillingCostExportDetails(updates);
    }

    /**
     * <p>
     * Failure test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * Exception occurs while accessing to DB.<br>
     * Expect BillingCostServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostServiceException.class)
    public void testUpdateBillingCostExportDetails_BillingCostServiceExceptionOccurs() throws Exception {
        impl = (BillingCostAuditServiceImpl) APP_CONTEXT_INVALID.getBean("billingCostAuditService");
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setInvoiceNumber("55");
        update.setBillingCostExportDetailIds(new long[] {1, 2, 3});
        updates.add(update);
        impl.updateBillingCostExportDetails(updates);
    }

    /**
     * <p>
     * Failure test for the method <code>updateBillingCostExportDetails(List)</code>.<br>
     * Entity is not existed in DB.<br>
     * Expect EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EntityNotFoundException.class)
    public void testUpdateBillingCostExportDetails_EntityNotExist() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setInvoiceNumber("55");
        update.setBillingCostExportDetailIds(new long[] {1, 2, 3, 4});
        updates.add(update);
        impl.updateBillingCostExportDetails(updates);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLatestInvoiceNumber()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetLatestInvoiceNumber() throws Exception {
        assertEquals("The latest invoice number should be same as", "30", impl.getLatestInvoiceNumber());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLatestInvoiceNumber()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetLatestInvoiceNumber2() throws Exception {
        super.clearDB();
        SimpleDateFormat sdf = new SimpleDateFormat("Myy");
        String expect = sdf.format(new Date()) + "-101";
        assertEquals("The latest invoice number should be same as", expect, impl.getLatestInvoiceNumber());
    }

    /**
     * <p>
     * Failure test for the method <code>getLatestInvoiceNumber()</code>.<br>
     * Exception occurs while accessing to DB.<br>
     * Expect BillingCostServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostServiceException.class)
    public void testGetLatestInvoiceNumber_BillingCostServiceExceptionOccurs() throws Exception {
        impl = (BillingCostAuditServiceImpl) APP_CONTEXT_INVALID.getBean("billingCostAuditService");
        impl.getLatestInvoiceNumber();
    }

}
