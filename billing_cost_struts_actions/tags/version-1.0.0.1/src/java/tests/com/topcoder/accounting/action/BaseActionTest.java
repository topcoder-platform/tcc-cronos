/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;


/**
 * <p>
 * Unit tests for the {@link BaseAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseActionTest {
    /** Represents the instance used to test again. */
    private BaseAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() throws Exception {
        testInstance = new BaseAction() {
        };
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
     * Accuracy test for {@link BaseAction#BaseAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testBaseAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>BaseAction</code> subclasses
     * <code>ActionSupport</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("BaseAction does not subclass ActionSupport.",
                BaseAction.class.getSuperclass() == ActionSupport.class);
    }

    /**
     * <p>
     * Accuracy test for {@link BaseAction#audit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_audit() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        testInstance.setBillingCostAuditService(billingCostAuditService);
        billingCostAuditService.setFlag(false);
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        testInstance.audit();
    }

    /**
     * <p>
     * Failure test for {@link BaseAction#audit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = Exception.class)
    public void test_audit_fail() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        testInstance.setBillingCostAuditService(billingCostAuditService);
        billingCostAuditService.setFlag(true);
        TestHelper.setField(testInstance, "servletRequest",
                new MockHttpServletRequest());
        testInstance.audit();
    }

    /**
     * <p>
     * Accuracy test for {@link BaseAction#getBillingCostAuditService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getBillingCostAuditService() throws Exception {
        testInstance.setBillingCostAuditService(null);
        assertNull("must be null", testInstance.getBillingCostAuditService());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseAction#getServletRequest()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getServletRequest() throws Exception {
        testInstance.setServletRequest(null);
        assertNull("must be null", testInstance.getServletRequest());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseAction#setBillingCostAuditService()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setBillingCostAuditService() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        billingCostAuditService.setFlag(false);
        testInstance.setBillingCostAuditService(billingCostAuditService);
        assertEquals("fail to set audit service", billingCostAuditService,
                testInstance.getBillingCostAuditService());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseAction#setServletRequest()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setServletRequest() throws Exception {
        MockHttpServletRequest mock = new MockHttpServletRequest();
        testInstance.setServletRequest(mock);
        assertEquals("fail to set servlet request", mock, testInstance
                .getServletRequest());
    }

    /**
     * <p>
     * Accuracy test for {@link BaseAction#checkConfiguration()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_checkConfiguration() throws Exception {
        MockBillingCostAuditService billingCostAuditService = new MockBillingCostAuditService();
        testInstance.setBillingCostAuditService(billingCostAuditService);
        billingCostAuditService.setFlag(false);
        testInstance.setAction("action");
        testInstance.checkConfiguration();
    }

    /**
     * <p>
     * Accuracy test for {@link BaseAction#setAction()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setAction() throws Exception {
        testInstance.setAction("");
        Field field = testInstance.getClass().getSuperclass().getDeclaredField("action");
        field.setAccessible(true);
        String str = (String) field.get(testInstance);
        assertTrue("should be empty string.", str.length() == 0);
    }

}
