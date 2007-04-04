/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit.accuracytests;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.audit.ApplicationArea;

/**
 * Accuracy test for <code>{@link com.topcoder.timetracker.audit.ApplicationArea}</code> class.
 *
 * @author mittu
 * @version 1.0
 */
public class ApplicationAreaTest extends TestCase {
    /**
     * Accuracy test for <code>{@link ApplicationArea#toString()}</code>.
     */
     public void testMethodToString() {
         assertEquals("Expense - string mismatch.", "Expense", ApplicationArea.TT_EXPENSE.toString());
         assertEquals("Fixed Billing - string mismatch.", "Fixed Billing", ApplicationArea.TT_FIXED_BILLING.toString());
         assertEquals("Time - string mismatch.", "Time", ApplicationArea.TT_TIME.toString());
         assertEquals("Client - string mismatch.", "Client", ApplicationArea.TT_CLIENT.toString());
         assertEquals("Company - string mismatch.", "Company", ApplicationArea.TT_COMPANY.toString());
         assertEquals("Project - string mismatch.", "Project", ApplicationArea.TT_PROJECT.toString());
         assertEquals("User - string mismatch.", "User", ApplicationArea.TT_USER.toString());
         assertEquals("Invoice - string mismatch.", "Invoice", ApplicationArea.TT_INVOICE.toString());
         assertEquals("Notification - string mismatch.", "Notification", ApplicationArea.TT_NOTIFICATION.toString());
         assertEquals("Configuration - string mismatch.", "Configuration", ApplicationArea.TT_CONFIGURATION.toString());
     }

    /**
     * Accuracy test for <code>{@link ApplicationArea#getId()}</code>.
     */
     public void testMethodGetId() {
         assertEquals("Expense - id mismatch.", 1, ApplicationArea.TT_EXPENSE.getId());
         assertEquals("Fixed Billing - id mismatch.", 2, ApplicationArea.TT_FIXED_BILLING.getId());
         assertEquals("Time - id mismatch.", 3, ApplicationArea.TT_TIME.getId());
         assertEquals("Client - id mismatch.", 4, ApplicationArea.TT_CLIENT.getId());
         assertEquals("Company - id mismatch.", 5, ApplicationArea.TT_COMPANY.getId());
         assertEquals("Project - id mismatch.", 6, ApplicationArea.TT_PROJECT.getId());
         assertEquals("User - id mismatch.", 7, ApplicationArea.TT_USER.getId());
         assertEquals("Invoice - id mismatch.", 8, ApplicationArea.TT_INVOICE.getId());
         assertEquals("Notification - id mismatch.", 9, ApplicationArea.TT_NOTIFICATION.getId());
         assertEquals("Configuration - id mismatch.", 10, ApplicationArea.TT_CONFIGURATION.getId());
     }

    /**
     * Accuracy test for <code>{@link ApplicationArea#getName()}</code>.
     */
     public void testMethodGetName() {
         assertEquals("Expense - name mismatch.", "Expense", ApplicationArea.TT_EXPENSE.getName());
         assertEquals("Fixed Billing - name mismatch.", "Fixed Billing", ApplicationArea.TT_FIXED_BILLING.getName());
         assertEquals("Time - name mismatch.", "Time", ApplicationArea.TT_TIME.getName());
         assertEquals("Client - name mismatch.", "Client", ApplicationArea.TT_CLIENT.getName());
         assertEquals("Company - name mismatch.", "Company", ApplicationArea.TT_COMPANY.getName());
         assertEquals("Project - name mismatch.", "Project", ApplicationArea.TT_PROJECT.getName());
         assertEquals("User - name mismatch.", "User", ApplicationArea.TT_USER.getName());
         assertEquals("Invoice - name mismatch.", "Invoice", ApplicationArea.TT_INVOICE.getName());
         assertEquals("Notification - name mismatch.", "Notification", ApplicationArea.TT_NOTIFICATION.getName());
         assertEquals("Configuration - name mismatch.", "Configuration", ApplicationArea.TT_CONFIGURATION.getName());
     }

    /**
     * Returns all tests.
     *
     * @return all tests
     */
     public static Test suite() {
        return new TestSuite(ApplicationAreaTest.class);
     }
}
