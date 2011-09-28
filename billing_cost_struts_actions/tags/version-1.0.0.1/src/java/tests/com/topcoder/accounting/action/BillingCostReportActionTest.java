/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.topcoder.accounting.entities.dto.BillingCostReportCriteria;

/**
 * <p>
 * Unit tests for the {@link BillingCostReportAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BillingCostReportActionTest {
    /** Represents the criteria used to test again. */
    private BillingCostReportCriteria criteriaValue;

    /** Represents the pageNumber used to test again. */
    private int pageNumberValue;

    /** Represents the pageSize used to test again. */
    private int pageSizeValue;

    /** Represents the instance used to test again. */
    private BillingCostReportAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new BillingCostReportAction();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportAction#BillingCostReportAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testBillingCostReportAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostReportAction#getCriteria()}
     * </p>
     * <p>
     * The value of <code>criteria</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getCriteria() throws Exception {
        assertNull("must be null", testInstance.getCriteria());
    }

    /**
     * <p>
     * Accuracy test
     * {@link BillingCostReportAction#setCriteria(BillingCostReportCriteria)}.
     * </p>
     * <p>
     * The value of <code>criteria</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCriteria() throws Exception {
        testInstance.setCriteria(criteriaValue);
        assertEquals("must be the same", criteriaValue, testInstance
                .getCriteria());
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostReportAction#getPageNumber()}
     * </p>
     * <p>
     * The value of <code>pageNumber</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPageNumber() throws Exception {
        assertEquals("must be 0", 0, testInstance.getPageNumber());
    }

    /**
     * <p>
     * Accuracy test {@link BillingCostReportAction#setPageNumber(int)}.
     * </p>
     * <p>
     * The value of <code>pageNumber</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPageNumber() throws Exception {
        pageNumberValue = 10;
        testInstance.setPageNumber(pageNumberValue);
        assertEquals("must be 10", pageNumberValue, testInstance
                .getPageNumber());
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostReportAction#getPageSize()}
     * </p>
     * <p>
     * The value of <code>pageSize</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPageSize() throws Exception {
        assertEquals("must be 0", 0, testInstance.getPageSize());
    }

    /**
     * <p>
     * Accuracy test {@link BillingCostReportAction#setPageSize(int)}.
     * </p>
     * <p>
     * The value of <code>pageSize</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPageSize() throws Exception {
        pageSizeValue = 20;
        testInstance.setPageSize(pageSizeValue);
        assertEquals("must be 20", pageSizeValue, testInstance.getPageSize());
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BillingCostReportAction</code>
     * subclasses <code>BaseBillingCostReportAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "BillingCostReportAction does not subclass BaseBillingCostReportAction.",
                BillingCostReportAction.class.getSuperclass() == BaseBillingCostReportAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostReportAction#checkConfiguration()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_checkConfiguration() throws Exception {
        MockBillingCostDataService billingCostDataService = new MockBillingCostDataService();
        testInstance.setBillingCostDataService(billingCostDataService);
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setAction("action");
        testInstance.setDefaultDuration(20);
        testInstance.checkConfiguration();
    }

    /**
     * <p>
     * Failure test for {@link BillingCostReportAction#checkConfiguration()}.
     * </p>
     * <p>
     * default duration should be non-negative.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = BillingCostActionConfigurationException.class)
    public void test_checkConfiguration_failure_1() throws Exception {
        MockBillingCostDataService billingCostDataService = new MockBillingCostDataService();
        testInstance.setBillingCostDataService(billingCostDataService);
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setAction("action");
        testInstance.setDefaultDuration(-20);
        testInstance.checkConfiguration();
    }

    /**
     * <p>
     * Accuracy test for
     * {@link BillingCostReportAction#getBillingCostReportEntries()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getBillingCostReportEntries() throws Exception {
        assertNull("must be null", testInstance.getBillingCostReportEntries());
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostReportAction#setDefaultDuration()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setDefaultDuration() throws Exception {
        testInstance.setDefaultDuration(20);
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostReportAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute1() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setPageNumber(0);
        testInstance
                .setBillingCostDataService(new MockBillingCostDataService());
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be 'success'", BillingCostReportAction.SUCCESS,
                testInstance.execute());
    }

    /**
     * <p>
     * Accuracy test for {@link BillingCostReportAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute2() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setPageNumber(0);
        BillingCostReportCriteria criteria = new BillingCostReportCriteria();
        Date now = new Date();
        criteria.setStartDate(now);
        criteria.setEndDate(new Date(now.getTime() + 1000));
        testInstance.setCriteria(criteria);
        testInstance
                .setBillingCostDataService(new MockBillingCostDataService());
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be 'success'", BillingCostReportAction.SUCCESS,
                testInstance.execute());
    }

    /**
     * <p>
     * Failure test for {@link BillingCostReportAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = Exception.class)
    public void test_execute_failure_1() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        testInstance.setPageNumber(-1);
        testInstance
                .setBillingCostDataService(new MockBillingCostDataService());
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        assertEquals("must be 'success'", BillingCostReportAction.SUCCESS,
                testInstance.execute());
    }
}
