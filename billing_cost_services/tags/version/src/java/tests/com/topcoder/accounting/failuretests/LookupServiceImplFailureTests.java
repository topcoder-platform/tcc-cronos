/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.failuretests;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.accounting.service.impl.LookupServiceImpl;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for LookupServiceImpl.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class LookupServiceImplFailureTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(LookupServiceImplFailureTests.class);
    }

    /**
     * <p>
     * Tests LookupServiceImpl#getPaymentAreas() for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
    public void testGetPaymentAreas_BillingCostServiceException() {
        ApplicationContext invalid_context = new ClassPathXmlApplicationContext("failure" + File.separator
            + "invalidBeans.xml");
        LookupServiceImpl instance = (LookupServiceImpl) invalid_context.getBean("lookupService");

        try {
            instance.getPaymentAreas();
            fail("BillingCostServiceException expected.");
        } catch (BillingCostServiceException e) {
            //good
        }
    }
}