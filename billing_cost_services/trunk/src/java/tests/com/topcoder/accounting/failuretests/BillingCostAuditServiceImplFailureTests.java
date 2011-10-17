/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.failuretests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdate;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.accounting.service.impl.BillingCostAuditServiceImpl;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BillingCostAuditServiceImpl.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class BillingCostAuditServiceImplFailureTests extends TestCase {
    /**
     * <p>
     * Represents the BillingCostAuditServiceImpl instance used to test.
     * </p>
     */
    private BillingCostAuditServiceImpl instance;

    /**
     * <p>
     * Represents the ApplicationContext instance used to test.
     * </p>
     */
    private ApplicationContext invalid_context;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        invalid_context = new ClassPathXmlApplicationContext("failure" + File.separator + "invalidBeans.xml");
        instance = (BillingCostAuditServiceImpl) invalid_context.getBean("billingCostAuditService");
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BillingCostAuditServiceImplFailureTests.class);
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportHistory(BillingCostExportHistoryCriteria,int,int) for failure.
     * It tests the case that when pageNo is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostExportHistory_ZeroPageNo() throws Exception {
        try {
            instance.getBillingCostExportHistory(null, 0, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportHistory(BillingCostExportHistoryCriteria,int,int) for failure.
     * It tests the case that when pageNo is negative and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostExportHistory_NegativePageNo() throws Exception {
        try {
            instance.getBillingCostExportHistory(null, -2, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportHistory(BillingCostExportHistoryCriteria,int,int) for failure.
     * It tests the case that when pageSize is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostExportHistory_ZeroPageSize() throws Exception {
        try {
            instance.getBillingCostExportHistory(null, 1, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportHistory(BillingCostExportHistoryCriteria,int,int) for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
    public void testGetBillingCostExportHistory_BillingCostServiceException() {
        try {
            instance.getBillingCostExportHistory(null, 1, 1);
            fail("BillingCostServiceException expected.");
        } catch (BillingCostServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportDetails(long,int,int) for failure.
     * It tests the case that when pageNo is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostExportDetails1_ZeroPageNo() throws Exception {
        try {
            instance.getBillingCostExportDetails(1L, 0, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportDetails(long,int,int) for failure.
     * It tests the case that when pageNo is negative and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostExportDetails1_NegativePageNo() throws Exception {
        try {
            instance.getBillingCostExportDetails(1L, -2, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportDetails(long,int,int) for failure.
     * It tests the case that when pageSize is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostExportDetails1_ZeroPageSize() throws Exception {
        try {
            instance.getBillingCostExportDetails(1L, 1, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportDetails(long,int,int) for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
    public void testGetBillingCostExportDetails1_BillingCostServiceException() {
        try {
            instance.getBillingCostExportDetails(1L, -1, -1);
            fail("BillingCostServiceException expected.");
        } catch (BillingCostServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportDetails(boolean,int,int) for failure.
     * It tests the case that when pageNo is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostExportDetails2_ZeroPageNo() throws Exception {
        try {
            instance.getBillingCostExportDetails(true, 0, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportDetails(boolean,int,int) for failure.
     * It tests the case that when pageNo is negative and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostExportDetails2_NegativePageNo() throws Exception {
        try {
            instance.getBillingCostExportDetails(true, -2, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportDetails(boolean,int,int) for failure.
     * It tests the case that when pageSize is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostExportDetails2_ZeroPageSize() throws Exception {
        try {
            instance.getBillingCostExportDetails(true, 1, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getBillingCostExportDetails(boolean,int,int) for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
    public void testGetBillingCostExportDetails2_BillingCostServiceException() {
        try {
            instance.getBillingCostExportDetails(true, -1, -1);
            fail("BillingCostServiceException expected.");
        } catch (BillingCostServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#auditAccountingAction(AccountingAuditRecord) for failure.
     * It tests the case that when record is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testAuditAccountingAction_NullRecord() throws Exception {
        try {
            instance.auditAccountingAction(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#auditAccountingAction(AccountingAuditRecord) for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
    public void testAuditAccountingAction_BillingCostServiceException() {
        try {
            instance.auditAccountingAction(new AccountingAuditRecord());
            fail("BillingCostServiceException expected.");
        } catch (BillingCostServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getAccountingAuditHistory(AccountingAuditRecordCriteria,int,int) for failure.
     * It tests the case that when pageNo is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAccountingAuditHistory_ZeroPageNo() throws Exception {
        try {
            instance.getAccountingAuditHistory(null, 0, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getAccountingAuditHistory(AccountingAuditRecordCriteria,int,int) for failure.
     * It tests the case that when pageNo is negative and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAccountingAuditHistory_NegativePageNo() throws Exception {
        try {
            instance.getAccountingAuditHistory(null, -2, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getAccountingAuditHistory(AccountingAuditRecordCriteria,int,int) for failure.
     * It tests the case that when pageSize is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetAccountingAuditHistory_ZeroPageSize() throws Exception {
        try {
            instance.getAccountingAuditHistory(null, 1, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getAccountingAuditHistory(AccountingAuditRecordCriteria,int,int) for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
    public void testGetAccountingAuditHistory_BillingCostServiceException() {
        try {
            instance.getAccountingAuditHistory(null, -1, -1);
            fail("BillingCostServiceException expected.");
        } catch (BillingCostServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#updateBillingCostExportDetails(List) for failure.
     * It tests the case that when updates is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateBillingCostExportDetails_NullUpdates() throws Exception {
        try {
            instance.updateBillingCostExportDetails(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#updateBillingCostExportDetails(List) for failure.
     * It tests the case that when updates is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateBillingCostExportDetails_EmptyUpdates() throws Exception {
        try {
            instance.updateBillingCostExportDetails(new ArrayList<QuickBooksImportUpdate>());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#updateBillingCostExportDetails(List) for failure.
     * It tests the case that when updates contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateBillingCostExportDetails_NullInUpdates() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        updates.add(null);
        try {
            instance.updateBillingCostExportDetails(updates);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#updateBillingCostExportDetails(List) for failure.
     * It tests the case that when BillingCostExportDetailIds is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateBillingCostExportDetails_NullBillingCostExportDetailIds() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setInvoiceNumber("1");
        updates.add(update);
        try {
            instance.updateBillingCostExportDetails(updates);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#updateBillingCostExportDetails(List) for failure.
     * It tests the case that when BillingCostExportDetailIds is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateBillingCostExportDetails_EmptyBillingCostExportDetailIds() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setBillingCostExportDetailIds(new long[] {});
        update.setInvoiceNumber("1");
        updates.add(update);
        try {
            instance.updateBillingCostExportDetails(updates);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#updateBillingCostExportDetails(List) for failure.
     * It tests the case that when invoiceNumber is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateBillingCostExportDetails_NullInvoiceNumber() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setBillingCostExportDetailIds(new long[] {1L});
        updates.add(update);
        try {
            instance.updateBillingCostExportDetails(updates);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#updateBillingCostExportDetails(List) for failure.
     * It tests the case that when invoiceNumber is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateBillingCostExportDetails_EmptyInvoiceNumber() throws Exception {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setBillingCostExportDetailIds(new long[] {1L});
        update.setInvoiceNumber(" ");
        updates.add(update);
        try {
            instance.updateBillingCostExportDetails(updates);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#updateBillingCostExportDetails(List) for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
    public void testUpdateBillingCostExportDetails_BillingCostServiceException() {
        List<QuickBooksImportUpdate> updates = new ArrayList<QuickBooksImportUpdate>();
        QuickBooksImportUpdate update = new QuickBooksImportUpdate();
        update.setBillingCostExportDetailIds(new long[] {1L});
        update.setInvoiceNumber("1");
        updates.add(update);
        try {
            instance.updateBillingCostExportDetails(updates);
            fail("BillingCostServiceException expected.");
        } catch (BillingCostServiceException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostAuditServiceImpl#getLatestInvoiceNumber() for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
    public void testGetLatestInvoiceNumber_BillingCostServiceException() {
        try {
            instance.getLatestInvoiceNumber();
            fail("BillingCostServiceException expected.");
        } catch (BillingCostServiceException e) {
            //good
        }
    }
}