/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.failuretests;

import static org.mockito.Mockito.mock;

import com.topcoder.accounting.action.AuditHistoryAction;
import com.topcoder.accounting.action.BaseAction;
import com.topcoder.accounting.action.BillingCostActionConfigurationException;
import com.topcoder.accounting.service.BillingCostAuditService;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class BaseActionFailureTests extends TestCase {

    /**
     * <p>
     * The BaseAction instance for testing.
     * </p>
     */
    private BaseAction instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new AuditHistoryAction();
        BillingCostAuditService billingCostAuditService = mock(BillingCostAuditService.class);
        instance.setBillingCostAuditService(billingCostAuditService);
        instance.setAction("action");
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseActionFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseAction#checkConfiguration() for failure.
     * It tests the case that when billingCostAuditService is null and expects BillingCostActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullBillingCostAuditService() {
        instance.setBillingCostAuditService(null);
        try {
            instance.checkConfiguration();
            fail("BillingCostActionConfigurationException expected.");
        } catch (BillingCostActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseAction#checkConfiguration() for failure.
     * It tests the case that when action is null and expects BillingCostActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_NullAction() {
        instance.setAction(null);
        try {
            instance.checkConfiguration();
            fail("BillingCostActionConfigurationException expected.");
        } catch (BillingCostActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseAction#checkConfiguration() for failure.
     * It tests the case that when action is empty and expects BillingCostActionConfigurationException.
     * </p>
     */
    public void testCheckConfiguration_EmptyAction() {
        instance.setAction(" ");
        try {
            instance.checkConfiguration();
            fail("BillingCostActionConfigurationException expected.");
        } catch (BillingCostActionConfigurationException e) {
            //good
        }
    }
}
