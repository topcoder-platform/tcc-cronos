/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality of <code>ApplicationArea</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ApplicationAreaUnitTest extends TestCase {
    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_EXPENSE.
     * </p>
     */
    public void testTT_EXPENSE_GetId_Accuracy() {
        assertEquals("The id should be correct.", 1, ApplicationArea.TT_EXPENSE.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_EXPENSE.
     * </p>
     */
    public void testTT_EXPENSE_GetName_Accuracy() {
        assertEquals("The name should be correct.", "Expense", ApplicationArea.TT_EXPENSE.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_EXPENSE.
     * </p>
     */
    public void testTT_EXPENSE_ToString_Accuracy() {
        assertEquals("The name should be correct.", "Expense", ApplicationArea.TT_EXPENSE.toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_FIXED_BILLING.
     * </p>
     */
    public void testTT_FIXED_BILLING_GetId_Accuracy() {
        assertEquals("The id should be correct.", 2, ApplicationArea.TT_FIXED_BILLING.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_FIXED_BILLING.
     * </p>
     */
    public void testTT_FIXED_BILLING_GetName_Accuracy() {
        assertEquals("The name should be correct.", "Fixed Billing", ApplicationArea.TT_FIXED_BILLING.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_FIXED_BILLING.
     * </p>
     */
    public void testTT_FIXED_BILLING_ToString_Accuracy() {
        assertEquals("The name should be correct.", "Fixed Billing", ApplicationArea.TT_FIXED_BILLING.toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_TIME.
     * </p>
     */
    public void testTT_TIME_GetId_Accuracy() {
        assertEquals("The id should be correct.", 3, ApplicationArea.TT_TIME.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_TIME.
     * </p>
     */
    public void testTT_TIME_GetName_Accuracy() {
        assertEquals("The name should be correct.", "Time", ApplicationArea.TT_TIME.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_TIME.
     * </p>
     */
    public void testTT_TIME_ToString_Accuracy() {
        assertEquals("The name should be correct.", "Time", ApplicationArea.TT_TIME.toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_CLIENT.
     * </p>
     */
    public void testTT_CLIENT_GetId_Accuracy() {
        assertEquals("The id should be correct.", 4, ApplicationArea.TT_CLIENT.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_CLIENT.
     * </p>
     */
    public void testTT_CLIENT_GetName_Accuracy() {
        assertEquals("The name should be correct.", "Client", ApplicationArea.TT_CLIENT.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_CLIENT.
     * </p>
     */
    public void testTT_CLIENT_ToString_Accuracy() {
        assertEquals("The name should be correct.", "Client", ApplicationArea.TT_CLIENT.toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_COMPANY.
     * </p>
     */
    public void testTT_COMPANY_GetId_Accuracy() {
        assertEquals("The id should be correct.", 5, ApplicationArea.TT_COMPANY.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_COMPANY.
     * </p>
     */
    public void testTT_COMPANY_GetName_Accuracy() {
        assertEquals("The name should be correct.", "Company", ApplicationArea.TT_COMPANY.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_COMPANY.
     * </p>
     */
    public void testTT_COMPANY_ToString_Accuracy() {
        assertEquals("The name should be correct.", "Company", ApplicationArea.TT_COMPANY.toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_PROJECT.
     * </p>
     */
    public void testTT_PROJECT_GetId_Accuracy() {
        assertEquals("The id should be correct.", 6, ApplicationArea.TT_PROJECT.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_PROJECT.
     * </p>
     */
    public void testTT_PROJECT_GetName_Accuracy() {
        assertEquals("The name should be correct.", "Project", ApplicationArea.TT_PROJECT.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_PROJECT.
     * </p>
     */
    public void testTT_PROJECT_ToString_Accuracy() {
        assertEquals("The name should be correct.", "Project", ApplicationArea.TT_PROJECT.toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_USER.
     * </p>
     */
    public void testTT_USER_GetId_Accuracy() {
        assertEquals("The id should be correct.", 7, ApplicationArea.TT_USER.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_USER.
     * </p>
     */
    public void testTT_USER_GetName_Accuracy() {
        assertEquals("The name should be correct.", "User", ApplicationArea.TT_USER.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_USER.
     * </p>
     */
    public void testTT_USER_ToString_Accuracy() {
        assertEquals("The name should be correct.", "User", ApplicationArea.TT_USER.toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_INVOICE.
     * </p>
     */
    public void testTT_INVOICE_GetId_Accuracy() {
        assertEquals("The id should be correct.", 8, ApplicationArea.TT_INVOICE.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_INVOICE.
     * </p>
     */
    public void testTT_INVOICE_GetName_Accuracy() {
        assertEquals("The name should be correct.", "Invoice", ApplicationArea.TT_INVOICE.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_INVOICE.
     * </p>
     */
    public void testTT_INVOICE_ToString_Accuracy() {
        assertEquals("The name should be correct.", "Invoice", ApplicationArea.TT_INVOICE.toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_NOTIFICATION.
     * </p>
     */
    public void testTT_NOTIFICATION_GetId_Accuracy() {
        assertEquals("The id should be correct.", 9, ApplicationArea.TT_NOTIFICATION.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_NOTIFICATION.
     * </p>
     */
    public void testTT_NOTIFICATION_GetName_Accuracy() {
        assertEquals("The name should be correct.", "Notification", ApplicationArea.TT_NOTIFICATION.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_NOTIFICATION.
     * </p>
     */
    public void testTT_NOTIFICATION_ToString_Accuracy() {
        assertEquals("The name should be correct.", "Notification", ApplicationArea.TT_NOTIFICATION.toString());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getId()</code> of ApplicationArea.TT_CONFIGURATION.
     * </p>
     */
    public void testTT_CONFIGURATION_GetId_Accuracy() {
        assertEquals("The id should be correct.", 10, ApplicationArea.TT_CONFIGURATION.getId());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>getName()</code> of ApplicationArea.TT_CONFIGURATION.
     * </p>
     */
    public void testTT_CONFIGURATION_GetName_Accuracy() {
        assertEquals("The name should be correct.", "Configuration", ApplicationArea.TT_CONFIGURATION.getName());
    }

    /**
     * <p>
     * Tests the accuracy of method <code>toString()</code> of ApplicationArea.TT_CONFIGURATION.
     * </p>
     */
    public void testTT_CONFIGURATION_ToString_Accuracy() {
        assertEquals("The name should be correct.", "Configuration", ApplicationArea.TT_CONFIGURATION.toString());
    }
}
