/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.failuretests;

import static org.mockito.Mockito.mock;

import com.topcoder.accounting.action.BaseBillingCostReportAction;
import com.topcoder.accounting.action.BillingCostActionConfigurationException;
import com.topcoder.accounting.action.BillingCostReportAction;
import com.topcoder.accounting.service.BillingCostAuditService;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseBillingCostReportAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BaseBillingCostReportActionFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseBillingCostReportActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseBillingCostReportAction#checkConfiguration() for failure.
     * It tests the case that when billingCostDataService is null and expects BillingCostActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullBillingCostDataService() {
        BaseBillingCostReportAction instance = new BillingCostReportAction();
        BillingCostAuditService billingCostAuditService = mock(BillingCostAuditService.class);
        instance.setBillingCostAuditService(billingCostAuditService);
        instance.setAction("action");
        try {
            instance.checkConfiguration();
            fail("BillingCostActionConfigurationException expected.");
        } catch (BillingCostActionConfigurationException e) {
            //good
        }
    }

}
