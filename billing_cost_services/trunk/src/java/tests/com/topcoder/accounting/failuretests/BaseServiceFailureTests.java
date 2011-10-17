/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.failuretests;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.accounting.service.BillingCostConfigurationException;
import com.topcoder.accounting.service.impl.BaseService;
import com.topcoder.accounting.service.impl.BillingCostAuditServiceImpl;
import com.topcoder.util.log.LogManager;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BaseService.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class BaseServiceFailureTests extends TestCase {

    /**
     * <p>
     * Represents the BillingCostAuditServiceImpl instance used to test.
     * </p>
     */
    private BaseService instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new BillingCostAuditServiceImpl();
        instance.setHibernateTemplate(new HibernateTemplate());
        instance.setLogger(LogManager.getLog());
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BaseServiceFailureTests.class);
    }

    /**
     * <p>
     * Tests BaseService#afterPropertiesSet() for failure.
     * It tests the case that when logger is null and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_NullLogger() {
        instance.setLogger(null);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BaseService#afterPropertiesSet() for failure.
     * It tests the case that when hibernateTemplate is null and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_NullHibernateTemplate() {
        instance.setHibernateTemplate(null);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

}