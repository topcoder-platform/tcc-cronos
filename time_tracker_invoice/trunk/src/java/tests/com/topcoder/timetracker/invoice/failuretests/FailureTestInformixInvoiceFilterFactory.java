/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.failuretests;
import junit.framework.TestCase;

import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactory;
/**
 * This class contains unit tests for <code>InformixInvoiceFilterFactory</code> class.
 *
 * @author enefem21
 * @version 1.0
 */
public class FailureTestInformixInvoiceFilterFactory extends TestCase {
    /**
     * Tests <code>createCompanyIdFilter(long companyId)</code> method
     * for failure with Negative CompanyId.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateCompanyIdFilterNegativeCompanyId() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createCompanyIdFilter(-1);
            fail("testCreateCompanyIdFilter_NegativeCompanyId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>createProjectIdFilter(long projectId)</code> method
     * for failure with Negative ProjectId.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateProjectIdFilterNegativeProjectId() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createProjectIdFilter(-1);
            fail("testCreateProjectIdFilter_NegativeProjectId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>createClientIdFilter(long clientId)</code> method
     * for failure with Negative ClientId.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateClientIdFilterNegativeClientId() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createClientIdFilter(-1);
            fail("testCreateClientIdFilter_NegativeClientId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>createInvoiceStatusIdFilter(long invoiceStatusId)</code> method
     * for failure with Negative InvoiceStatusId.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateInvoiceStatusIdFilterNegativeInvoiceStatusId() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createInvoiceStatusIdFilter(-1);
            fail("testCreateInvoiceStatusIdFilter_NegativeInvoiceStatusId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>createInvoiceNumberFilter(String invoiceNumber)</code> method
     * for failure with null InvoiceNumber.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateInvoiceNumberFilterNullInvoiceNumber() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createInvoiceNumberFilter(null);
            fail("testCreateInvoiceNumberFilter_NullInvoiceNumber is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>createInvoiceNumberFilter(String invoiceNumber)</code> method
     * for failure with empty InvoiceNumber,
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateInvoiceNumberFilterEmptyInvoiceNumber() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createInvoiceNumberFilter(" ");
            fail("testCreateInvoiceNumberFilter_EmptyInvoiceNumber is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>createInvoiceDateFilter(Date from, Date to)</code> method
     * for failure with null params.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateInvoiceDateFilterNullParams() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createCreationDateFilter(null, null);
            fail("testCreateInvoiceDateFilter_NullParams is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>createCreationDateFilter(Date from, Date to)</code> method
     * for failure with null Params.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateCreationDateFilterNullParams() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createCreationDateFilter(null, null);
            fail("testCreateCreationDateFilter_NullParams is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>createDueDateFilter(Date from, Date to)</code> method
     * for failure with null Params.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateDueDateFilterNullParams() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createDueDateFilter(null, null);
            fail("testCreateDueDateFilter_NullParams is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>createModificationDateFilter(Date from, Date to)</code> method
     * for failure with null Params.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateModificationDateFilterNullParams() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createModificationDateFilter(null, null);
            fail("testCreateModificationDateFilter_NullParams is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }

    /**
     * Tests <code>createCreationUsernameFilter(String creationUsername)</code> method
     * for failure with null CreationUsername.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateCreationUsernameFilterNullCreationUsername() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createCreationUsernameFilter(null);
            fail("testCreateCreationUsernameFilter_NullCreationUsername is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>createCreationUsernameFilter(String creationUsername)</code> method
     * for failure with empty CreationUsername,
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateCreationUsernameFilterEmptyCreationUsername() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createCreationUsernameFilter(" ");
            fail("testCreateCreationUsernameFilter_EmptyCreationUsername is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>createModificationUsernameFilter(String modificationUsername)</code> method
     * for failure with null ModificationUsername.
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateModificationUsernameFilterNullModificationUsername() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createModificationUsernameFilter(null);
            fail("testCreateModificationUsernameFilter_NullModificationUsername is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
    /**
     * Tests <code>createModificationUsernameFilter(String modificationUsername)</code> method
     * for failure with empty ModificationUsername,
     * IllegalArgumentException should be thrown.
     * @throws Exception to JUnit.
     */
    public void testCreateModificationUsernameFilterEmptyModificationUsername() throws Exception {
        try {
        	InformixInvoiceFilterFactory.createModificationUsernameFilter(" ");
            fail("testCreateModificationUsernameFilter_EmptyModificationUsername is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        }
    }
}