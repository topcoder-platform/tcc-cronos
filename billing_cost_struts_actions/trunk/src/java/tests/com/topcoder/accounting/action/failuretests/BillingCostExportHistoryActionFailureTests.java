/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.failuretests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.accounting.action.BillingCostExportHistoryAction;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.accounting.service.BillingCostServiceException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BillingCostExportHistoryAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BillingCostExportHistoryActionFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BillingCostExportHistoryActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BillingCostExportHistoryAction#execute() for failure.
     * Expects Exception.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecute_Exception() throws Exception {
        BillingCostExportHistoryAction action = new BillingCostExportHistoryAction();
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        action.setServletRequest(servletRequest);
        BillingCostAuditService billingCostAuditService = mock(BillingCostAuditService.class);
        action.setBillingCostAuditService(billingCostAuditService);
        doThrow(new BillingCostServiceException("error")).when(billingCostAuditService).auditAccountingAction(
            any(AccountingAuditRecord.class));
        try {
            action.execute();
            fail("Exception expected.");
        } catch (Exception e) {
            //good
        }
    }
}
