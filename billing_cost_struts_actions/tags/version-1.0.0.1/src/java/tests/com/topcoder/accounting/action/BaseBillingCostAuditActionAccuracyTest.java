/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostAuditService;

/**
 * Accuracy test for BaseBillingCostAuditAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseBillingCostAuditActionAccuracyTest {
    /**
     * Represents the instance of BaseBillingCostAuditAction used in test.
     */
    private BaseBillingCostAuditAction action;

    /**
     * Set up for each test.
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() {
        action = new BaseBillingCostAuditAction() {
        };
        action.setBillingCostAuditService(new MockBillingCostAuditService());
        action.setServletRequest(new MockHttpServletRequest());
        action.setAction("accuracytest");
        action.setPageNumber(1);
        action.setPageSize(2);
    }

    /**
     * Accuracy test for getter and setter of pageNumber.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testPageNumber() throws Exception {
        action.setPageNumber(12);

        assertEquals("The pageNumber is incorrect.", 12, action.getPageNumber());
    }

    /**
     * Accuracy test for getter and setter of pageSize.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testPageSize() throws Exception {
        action.setPageSize(11);

        assertEquals("The pageSize is incorrect.", 11, action.getPageSize());
    }

    /**
     * Accuracy test for checkConfiguration(). No exception thrown.
     */
    @Test
    public void testCheckConfiguration() {
        action.checkConfiguration();
    }
}
