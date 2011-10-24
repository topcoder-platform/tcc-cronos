/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.failuretests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.accounting.entities.dto.PaymentIdentifier;
import com.topcoder.accounting.service.BillingCostConfigurationException;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.accounting.service.impl.BillingCostDataServiceImpl;
import com.topcoder.security.TCSubject;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for BillingCostDataServiceImpl.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class BillingCostDataServiceImplFailureTests extends TestCase {
    /**
     * <p>
     * Represents the BillingCostDataServiceImpl instance used to test.
     * </p>
     */
    private BillingCostDataServiceImpl instance;

    /**
     * <p>
     * Represents the ApplicationContext instance used to test.
     * </p>
     */
    private ApplicationContext invalid_context;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        invalid_context = new ClassPathXmlApplicationContext("failure" + File.separator + "invalidBeans.xml");
        instance = (BillingCostDataServiceImpl) invalid_context.getBean("billingCostDataService");
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(BillingCostDataServiceImplFailureTests.class);
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#getBillingCostReport(BillingCostReportCriteria,int,int) for failure.
     * It tests the case that when pageNo is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostReport_ZeroPageNo() throws Exception {
        try {
            instance.getBillingCostReport(null, 0, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#getBillingCostReport(BillingCostReportCriteria,int,int) for failure.
     * It tests the case that when pageNo is negative and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostReport_NegativePageNo() throws Exception {
        try {
            instance.getBillingCostReport(null, -2, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#getBillingCostReport(BillingCostReportCriteria,int,int) for failure.
     * It tests the case that when pageSize is zero and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetBillingCostReport_ZeroPageSize() throws Exception {
        try {
            instance.getBillingCostReport(null, 1, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#getBillingCostReport(BillingCostReportCriteria,int,int) for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
//    public void testGetBillingCostReport_BillingCostServiceException() {
//        try {
//            instance.getBillingCostReport(null, -1, -1);
//            fail("BillingCostServiceException expected.");
//        } catch (BillingCostServiceException e) {
//            //good
//        }
//    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#exportBillingCostData(List,long,TCSubject) for failure.
     * It tests the case that when paymentIds is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExportBillingCostData_NullPaymentIds() throws Exception {
        try {
            instance.exportBillingCostData(null, 1L, new TCSubject(1L));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#exportBillingCostData(List,long,TCSubject) for failure.
     * It tests the case that when paymentIds is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExportBillingCostData_EmptyPaymentIds() throws Exception {
        try {
            instance.exportBillingCostData(new ArrayList<PaymentIdentifier>(), 1L, new TCSubject(1L));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#exportBillingCostData(List,long,TCSubject) for failure.
     * It tests the case that when paymentIds contains null element and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExportBillingCostData_NullInPaymentIds() throws Exception {
        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
        paymentIds.add(null);
        try {
            instance.exportBillingCostData(paymentIds, 1L, new TCSubject(1L));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#exportBillingCostData(List,long,TCSubject) for failure.
     * It tests the case that when user is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUNit
     */
    public void testExportBillingCostData_NullUser() throws Exception {
        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
        paymentIds.add(new PaymentIdentifier());
        try {
            instance.exportBillingCostData(paymentIds, 1L, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#exportBillingCostData(List,long,TCSubject) for failure.
     * Expects BillingCostServiceException.
     * </p>
     */
//    public void testExportBillingCostData_BillingCostServiceException() {
//        List<PaymentIdentifier> paymentIds = new ArrayList<PaymentIdentifier>();
//        paymentIds.add(new PaymentIdentifier());
//        try {
//            instance.exportBillingCostData(paymentIds, 1L, new TCSubject(1L));
//            fail("BillingCostServiceException expected.");
//        } catch (BillingCostServiceException e) {
//            //good
//        }
//    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when projectCategoryIds is null and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_NullProjectCategoryIds() {
        instance.setProjectCategoryIds(null);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when projectCategoryIds is empty and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_EmptyProjectCategoryIds() {
        instance.setProjectCategoryIds(new ArrayList<Long>());
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when projectCategoryIds contains null and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_NullInProjectCategoryIds() {
        List<Long> projectCategoryIds = new ArrayList<Long>();
        projectCategoryIds.add(null);
        instance.setProjectCategoryIds(projectCategoryIds);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when studioProjectCategoryIds is null and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_NullStudioProjectCategoryIds() {
        instance.setStudioProjectCategoryIds(null);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when studioProjectCategoryIds is empty and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_EmptyStudioProjectCategoryIds() {
        instance.setStudioProjectCategoryIds(new ArrayList<Long>());
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when studioProjectCategoryIds contains null and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_NullInStudioProjectCategoryIds() {
        List<Long> studioProjectCategoryIds = new ArrayList<Long>();
        studioProjectCategoryIds.add(null);
        instance.setStudioProjectCategoryIds(studioProjectCategoryIds);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when statusMapping is null and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_NullStatusMapping() {
        instance.setStatusMapping(null);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when statusMapping is empty and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_EmptyStatusMapping() {
        instance.setStatusMapping(new HashMap<String, Long>());
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when statusMapping contains null key and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_NullKeyInStatusMapping() {
        Map<String, Long> statusMapping = new HashMap<String, Long>();
        statusMapping.put(null, 1L);
        instance.setStatusMapping(statusMapping);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when statusMapping contains empty key and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_EmptyKeyInStatusMapping() {
        Map<String, Long> statusMapping = new HashMap<String, Long>();
        statusMapping.put(" ", 1L);
        instance.setStatusMapping(statusMapping);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests BillingCostDataServiceImpl#afterPropertiesSet() for failure.
     * It tests the case that when statusMapping contains null value and expects BillingCostConfigurationException.
     * </p>
     */
    public void testAfterPropertiesSet_NullValueInStatusMapping() {
        Map<String, Long> statusMapping = new HashMap<String, Long>();
        statusMapping.put("key", null);
        instance.setStatusMapping(statusMapping);
        try {
            instance.afterPropertiesSet();
            fail("BillingCostConfigurationException expected.");
        } catch (BillingCostConfigurationException e) {
            //good
        }
    }
}