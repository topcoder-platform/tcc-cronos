/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;

/**
 * <p>
 * Accuracy Unit test cases for Invoice.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class InvoiceAccuracyTests extends TestCase {
    /**
     * <p>
     * Invoice instance for testing.
     * </p>
     */
    private Invoice instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new Invoice();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InvoiceAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor Invoice#Invoice() for accuracy.
     * </p>
     */
    public void testCtor1() {
        assertNotNull("Failed to create Invoice instance.", instance);
    }

    /**
     * <p>
     * Tests Invoice#getCompanyId() for accuracy.
     * </p>
     */
    public void testGetCompanyId() {
        instance.setCompanyId(8);
        assertEquals("Failed to get the company id.", 8, instance.getCompanyId());
    }

    /**
     * <p>
     * Tests Invoice#setCompanyId(long) for accuracy.
     * </p>
     */
    public void testSetCompanyId() {
        instance.setCompanyId(8);
        assertEquals("Failed to set the company id.", 8, instance.getCompanyId());
    }

    /**
     * <p>
     * Tests Invoice#getDueDate() for accuracy.
     * </p>
     */
    public void testGetDueDate() {
        Date date = new Date();
        instance.setDueDate(date);
        assertSame("Failed to get the due date.", date, instance.getDueDate());
    }

    /**
     * <p>
     * Tests Invoice#setDueDate(Date) for accuracy.
     * </p>
     */
    public void testSetDueDate() {
        Date date = new Date();
        instance.setDueDate(date);
        assertSame("Failed to set the due date.", date, instance.getDueDate());
    }

    /**
     * <p>
     * Tests Invoice#getExpenseEntries() for accuracy.
     * </p>
     */
    public void testGetExpenseEntries() {
        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        instance.setExpenseEntries(expenseEntries);
        assertSame("Failed to get the expense entries.", entry, instance.getExpenseEntries()[0]);
    }

    /**
     * <p>
     * Tests Invoice#setExpenseEntries(ExpenseEntry[]) for accuracy.
     * </p>
     */
    public void testSetExpenseEntries() {
        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal(5));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        instance.setExpenseEntries(expenseEntries);
        assertSame("Failed to set the expense entries.", entry, instance.getExpenseEntries()[0]);
    }

    /**
     * <p>
     * Tests Invoice#getExpenseSubTotal() for accuracy.
     * </p>
     */
    public void testGetExpenseSubTotal() {
        ExpenseEntry entry = new ExpenseEntry();
        entry.setAmount(new BigDecimal("5.0"));
        ExpenseEntry[] expenseEntries = new ExpenseEntry[] {entry};
        instance.setExpenseEntries(expenseEntries);
        assertEquals("Failed to get the expense sub total.", 5.0, instance.getExpenseSubTotal().doubleValue(), 0.01);
    }

    /**
     * <p>
     * Tests Invoice#getInvoiceNumber() for accuracy.
     * </p>
     */
    public void testGetInvoiceNumber() {
        instance.setInvoiceNumber("invoiceNumber");
        assertEquals("Failed to get the invoice number.", "invoiceNumber", instance.getInvoiceNumber());
    }

    /**
     * <p>
     * Tests Invoice#setInvoiceNumber(String) for accuracy.
     * </p>
     */
    public void testSetInvoiceNumber() {
        instance.setInvoiceNumber("invoiceNumber");
        assertEquals("Failed to set the invoice number.", "invoiceNumber", instance.getInvoiceNumber());
    }

    /**
     * <p>
     * Tests Invoice#getServiceDetails() for accuracy.
     * </p>
     */
    public void testGetServiceDetails() {
        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        instance.setServiceDetails(serviceDetails);
        assertSame("Failed to get the service details.", detail, instance.getServiceDetails()[0]);
    }

    /**
     * <p>
     * Tests Invoice#setServiceDetails(ServiceDetail[]) for accuracy.
     * </p>
     */
    public void testSetServiceDetails() {
        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(new BigDecimal(8));
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        instance.setServiceDetails(serviceDetails);
        assertSame("Failed to set the service details.", detail, instance.getServiceDetails()[0]);
    }

    /**
     * <p>
     * Tests Invoice#getInvoiceStatus() for accuracy.
     * </p>
     */
    public void testGetInvoiceStatus() {
        InvoiceStatus invoiceStatus = new InvoiceStatus(8, "description", "user", "user", new Date(), new Date());
        instance.setInvoiceStatus(invoiceStatus);
        assertSame("Failed to get the invoice status.", invoiceStatus, instance.getInvoiceStatus());
    }

    /**
     * <p>
     * Tests Invoice#setInvoiceStatus(InvoiceStatus) for accuracy.
     * </p>
     */
    public void testSetInvoiceStatus() {
        InvoiceStatus invoiceStatus = new InvoiceStatus(8, "description", "user", "user", new Date(), new Date());
        instance.setInvoiceStatus(invoiceStatus);
        assertSame("Failed to set the invoice status.", invoiceStatus, instance.getInvoiceStatus());
    }

    /**
     * <p>
     * Tests Invoice#isPaid() for accuracy.
     * </p>
     */
    public void testIsPaid() {
        instance.setPaid(true);
        assertTrue("Failed to get the paid.", instance.isPaid());
    }

    /**
     * <p>
     * Tests Invoice#setPaid(boolean) for accuracy.
     * </p>
     */
    public void testSetPaid() {
        instance.setPaid(true);
        assertTrue("Failed to set the paid.", instance.isPaid());
    }

    /**
     * <p>
     * Tests Invoice#getPaymentTerm() for accuracy.
     * </p>
     */
    public void testGetPaymentTerm() {
        PaymentTerm paymentTerm = new PaymentTerm();
        instance.setPaymentTerm(paymentTerm);
        assertSame("Failed to get the payment term.", paymentTerm, instance.getPaymentTerm());
    }

    /**
     * <p>
     * Tests Invoice#setPaymentTerm(PaymentTerm) for accuracy.
     * </p>
     */
    public void testSetPaymentTerm() {
        PaymentTerm paymentTerm = new PaymentTerm();
        instance.setPaymentTerm(paymentTerm);
        assertSame("Failed to set the payment term.", paymentTerm, instance.getPaymentTerm());
    }

    /**
     * <p>
     * Tests Invoice#getProjectId() for accuracy.
     * </p>
     */
    public void testGetProjectId() {
        instance.setProjectId(8);
        assertEquals("Failed to get the project id.", 8, instance.getProjectId());
    }

    /**
     * <p>
     * Tests Invoice#setProjectId(long) for accuracy.
     * </p>
     */
    public void testSetProjectId() {
        instance.setProjectId(8);
        assertEquals("Failed to set the project id.", 8, instance.getProjectId());
    }

    /**
     * <p>
     * Tests Invoice#getPurchaseOrderNumber() for accuracy.
     * </p>
     */
    public void testGetPurchaseOrderNumber() {
        instance.setPurchaseOrderNumber("purchaseOrderNumber");
        assertEquals("Failed to get the purchase order number.", "purchaseOrderNumber",
            instance.getPurchaseOrderNumber());
    }

    /**
     * <p>
     * Tests Invoice#setPurchaseOrderNumber(String) for accuracy.
     * </p>
     */
    public void testSetPurchaseOrderNumber() {
        instance.setPurchaseOrderNumber("purchaseOrderNumber");
        assertEquals("Failed to set the purchase order number.", "purchaseOrderNumber",
            instance.getPurchaseOrderNumber());
    }

    /**
     * <p>
     * Tests Invoice#getSalesTax() for accuracy.
     * </p>
     */
    public void testGetSalesTax() {
        BigDecimal salesTax = new BigDecimal(5);
        instance.setSalesTax(salesTax);
        assertSame("Failed to get the sales tax.", salesTax, instance.getSalesTax());
    }

    /**
     * <p>
     * Tests Invoice#setSalesTax(BigDecimal) for accuracy.
     * </p>
     */
    public void testSetSalesTax() {
        BigDecimal salesTax = new BigDecimal(5);
        instance.setSalesTax(salesTax);
        assertSame("Failed to set the sales tax.", salesTax, instance.getSalesTax());
    }

    /**
     * <p>
     * Tests Invoice#getServicesSubTotal() for accuracy.
     * </p>
     */
    public void testGetServicesSubTotal() {
        BigDecimal bigDecimal = new BigDecimal("8.0");
        InvoiceServiceDetail detail = new InvoiceServiceDetail();
        detail.setAmount(bigDecimal);
        InvoiceServiceDetail[] serviceDetails = new InvoiceServiceDetail[] {detail};
        instance.setServiceDetails(serviceDetails);
        assertEquals("Failed to set the service sub total.", 8.0, instance.getServicesSubTotal().doubleValue(), 0.01);
    }

    /**
     * <p>
     * Tests Invoice#getFixedBillingEntries() for accuracy.
     * </p>
     */
    public void testGetFixedBillingEntries() {
        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        instance.setFixedBillingEntries(fixedBillingEntries);
        assertSame("Failed to get the fixedBilling entries.", fixedBillingEntry, instance.getFixedBillingEntries()[0]);
    }

    /**
     * <p>
     * Tests Invoice#setFixedBillingEntries(FixedBillingEntry[]) for accuracy.
     * </p>
     */
    public void testSetFixedBillingEntries() {
        FixedBillingEntry fixedBillingEntry = new FixedBillingEntry();
        FixedBillingEntry[] fixedBillingEntries = new FixedBillingEntry[] {fixedBillingEntry};
        instance.setFixedBillingEntries(fixedBillingEntries);
        assertSame("Failed to set the fixedBilling entries.", fixedBillingEntry, instance.getFixedBillingEntries()[0]);
    }

    /**
     * <p>
     * Tests Invoice#getInvoiceDate() for accuracy.
     * </p>
     */
    public void testGetInvoiceDate() {
        Date invoiceDate = new Date();
        instance.setInvoiceDate(invoiceDate);
        assertSame("Failed to get the invoice date.", invoiceDate, instance.getInvoiceDate());
    }

    /**
     * <p>
     * Tests Invoice#setInvoiceDate(Date) for accuracy.
     * </p>
     */
    public void testSetInvoiceDate() {
        Date invoiceDate = new Date();
        instance.setInvoiceDate(invoiceDate);
        assertSame("Failed to set the invoice date.", invoiceDate, instance.getInvoiceDate());
    }

}