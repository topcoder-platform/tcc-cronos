/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.accounting.action.accuracytests.AccuracyTestHelper;
import com.topcoder.accounting.action.accuracytests.mock.MockBillingCostAuditService;
import com.topcoder.accounting.service.BillingCostAuditService;

/**
 * Accuracy test for BaseAction class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseActionAccuracyTest {
    /**
     * Represents the instance of BaseAction used in test.
     */
    private BaseAction action;

    /**
     * Set up for each test.
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() {
        action = new BaseAction() {
        };
        action.setBillingCostAuditService(new MockBillingCostAuditService());
        action.setServletRequest(new MockHttpServletRequest());
        action.setAction("accuracytest");
    }

    /**
     * Accuracy test for getBillingCostAuditService(). The billingCostAuditService should be returned.
     */
    @Test
    public void testGetBillingCostAuditService() {
        BillingCostAuditService service = new MockBillingCostAuditService();
        action.setBillingCostAuditService(service);
        assertEquals("The billingCostAuditService is incorrect.", service, action.getBillingCostAuditService());
    }

    /**
     * Accuracy test for getServletRequest(). The request should be returned.
     */
    @Test
    public void testGetServletRequest() {
        HttpServletRequest request = new MockHttpServletRequest();
        action.setServletRequest(request);
        assertEquals("The getServletRequest is incorrect.", request, action.getServletRequest());
    }

    /**
     * Accuracy test for setBillingCostAuditService(BillingCostAuditService billingCostAuditService). The
     * billingCostAuditService should be set correctly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testSetBillingCostAuditService() throws Exception {
        BillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        action.setBillingCostAuditService(billingCostAuditService);
        assertEquals("The billingCostAuditService is incorrect.", billingCostAuditService,
            AccuracyTestHelper.getFieldObj(BaseAction.class, action, "billingCostAuditService"));
    }

    /**
     * Accuracy test for setServletRequest(HttpServletRequest servletRequest). The servletRequest should be set
     * correctly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testSetServletRequest() throws Exception {
        HttpServletRequest servletRequest = new MockHttpServletRequest();
        action.setServletRequest(servletRequest);
        assertEquals("The setServletRequest is incorrect.", servletRequest,
            AccuracyTestHelper.getFieldObj(BaseAction.class, action, "servletRequest"));
    }

    /**
     * Accuracy test for setAction(String action). The action should be set correctly.
     *
     * @throws Exception
     *             to jUnit.
     */
    @Test
    public void testSetAction() throws Exception {
        action.setAction("test");
        assertEquals("The setAction is incorrect.", "test",
            AccuracyTestHelper.getFieldObj(BaseAction.class, action, "action"));
    }

    /**
     * Accuracy test for checkConfiguration(). No exception thrown.
     */
    @Test
    public void testCheckConfiguration() {
        action.checkConfiguration();
    }
}
