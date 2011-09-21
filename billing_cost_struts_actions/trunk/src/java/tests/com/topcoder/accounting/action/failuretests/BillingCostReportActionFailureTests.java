/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.failuretests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.accounting.action.BillingCostActionConfigurationException;
import com.topcoder.accounting.action.BillingCostReportAction;
import com.topcoder.accounting.entities.dao.AccountingAuditRecord;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.accounting.service.BillingCostServiceException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BillingCostReportAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BillingCostReportActionFailureTests extends TestCase {
    /**
     * <p>
     * The BillingCostReportAction instance for testing.
     * </p>
     */
    private BillingCostReportAction action;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        action = new BillingCostReportAction();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BillingCostReportActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BillingCostReportAction#checkConfiguration() for failure.
     * It tests the case that when defaultDuration is negative and expects BillingCostActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NegativeDefaultDuration() {
        action.setDefaultDuration(-5);
        try {
            action.checkConfiguration();
            fail("BillingCostActionConfigurationException expected.");
        } catch (BillingCostActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostReportAction#execute() for failure.
     * Expects Exception.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecute_Exception() throws Exception {
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
