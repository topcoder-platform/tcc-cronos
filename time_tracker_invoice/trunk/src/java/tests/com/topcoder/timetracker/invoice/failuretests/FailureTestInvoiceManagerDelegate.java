/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.failuretests;
import junit.framework.TestCase;

import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.delegate.InvoiceManagerDelegate;
/**
 * This class contains unit tests for <code>InvoiceManagerDelegate</code> class.
 *
 * @author enefem21
 * @version 1.0
 */
public class FailureTestInvoiceManagerDelegate extends TestCase {
    /**
     * Set Up the test environment before testing.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.removeNamespaces();
    }

    /**
     * Clean up the test environment after testing.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.removeNamespaces();
    }
    /**
     * Tests <code>InvoiceManagerDelegate(String namespace)</code> method
     * for failure with null Namespace.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInvoiceManagerDelegateNullNamespace() throws Exception {
        try {
        	new InvoiceManagerDelegate(null);
            fail("testInvoiceManagerDelegate_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>InvoiceManagerDelegate(String namespace)</code> method
     * for failure with empty Namespace,
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInvoiceManagerDelegateEmptyNamespace() throws Exception {
        try {
        	new InvoiceManagerDelegate(" ");
            fail("testInvoiceManagerDelegate_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>InvoiceManagerDelegate(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInvoiceManagerDelegateFail1() throws Exception {
        try {
        	new InvoiceManagerDelegate("InvoiceManagerDelegate.fail1");
            fail("testInvoiceManagerDelegateFail1 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
    
    /**
     * Tests <code>InvoiceManagerDelegate(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInvoiceManagerDelegateFail2() throws Exception {
        try {
        	new InvoiceManagerDelegate("InvoiceManagerDelegate.fail2");
            fail("testInvoiceManagerDelegateFail2 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
    
    /**
     * Tests <code>InvoiceManagerDelegate(String namespace)</code> method
     * for failure with invalid configuration,
     * InvoiceConfigurationException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testInvoiceManagerDelegateFail3() throws Exception {
        try {
        	new InvoiceManagerDelegate("InvoiceManagerDelegate.fail3");
            fail("testInvoiceManagerDelegateFail3 is failure.");
        } catch (InvoiceConfigurationException ice) {
            // pass
        }
    }
}