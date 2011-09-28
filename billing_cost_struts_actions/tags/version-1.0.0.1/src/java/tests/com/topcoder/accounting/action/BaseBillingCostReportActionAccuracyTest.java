/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostAuditService;
import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostDataService;
import com.topcoder.accounting.service.BillingCostDataService;

/**
 * Accuracy test for BaseBillingCostReportAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseBillingCostReportActionAccuracyTest {
    /**
     * Represents the instance of BaseBillingCostReportAction used in test.
     */
    private BaseBillingCostReportAction action;

    /**
     * Set up for each test.
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() {
        action = new BaseBillingCostReportAction() {
        };
        action.setBillingCostAuditService(new MockBillingCostAuditService());
        action.setServletRequest(new MockHttpServletRequest());
        action.setAction("accuracytest");

        BillingCostDataService billingCostDataService = new MockBillingCostDataService();
        action.setBillingCostDataService(billingCostDataService);
    }

    /**
     * Accuracy test for getter and setter of BillingCostDataService.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testBillingCostDataService() throws Exception {
        BillingCostDataService billingCostDataService = new MockBillingCostDataService();
        action.setBillingCostDataService(billingCostDataService);

        assertEquals("The billingCostDataService is incorrect.", billingCostDataService,
            action.getBillingCostDataService());
    }

    /**
     * Accuracy test for checkConfiguration(). No exception thrown.
     */
    @Test
    public void testCheckConfiguration() {
        action.checkConfiguration();
    }
}
