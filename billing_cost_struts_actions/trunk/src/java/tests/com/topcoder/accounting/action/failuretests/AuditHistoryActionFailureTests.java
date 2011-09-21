/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.failuretests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.topcoder.accounting.action.AuditHistoryAction;
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteria;
import com.topcoder.accounting.service.BillingCostAuditService;
import com.topcoder.accounting.service.BillingCostServiceException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for AuditHistoryAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class AuditHistoryActionFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AuditHistoryActionFailureTests.class);
    }

    /**
     * <p>
     * Tests AuditHistoryAction#execute() for failure.
     * Expects Exception.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecute_Exception() throws Exception {
        AuditHistoryAction action = new AuditHistoryAction();
        BillingCostAuditService billingCostAuditService = mock(BillingCostAuditService.class);
        action.setBillingCostAuditService(billingCostAuditService);
        when(billingCostAuditService.getAccoutingAuditHistory(new AccountingAuditRecordCriteria(), 0, 0)).thenThrow(
            new BillingCostServiceException("error"));
        try {
            action.execute();
            fail("Exception expected.");
        } catch (Exception e) {
            //good
        }
    }
}
