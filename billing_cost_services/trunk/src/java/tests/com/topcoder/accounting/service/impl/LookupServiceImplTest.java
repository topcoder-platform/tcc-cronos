/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.accounting.entities.dao.PaymentArea;
import com.topcoder.accounting.service.BillingCostServiceException;
import com.topcoder.accounting.service.LookupService;

/**
 * <p>
 * Unit tests for class <code>LookupServiceImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class LookupServiceImplTest extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>LookupServiceImpl</code> instance used to test against.
     * </p>
     */
    private LookupServiceImpl impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(LookupServiceImplTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        impl = (LookupServiceImpl) APP_CONTEXT.getBean("lookupService");
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        impl = null;
    }

    /**
     * <p>
     * Inheritance test, verifies <code>LookupServiceImpl</code> subclasses should be correct.
     * </p>
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof LookupService);
        assertTrue("The instance's subclass is not correct.", impl instanceof BaseService);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>LookupServiceImpl()</code>.<br>
     * Instance should be created successfully.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPaymentAreas()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetPaymentAreas() throws Exception {
        List<PaymentArea> result = impl.getPaymentAreas();
        assertEquals("The result size should be same as", 3, result.size());
        assertEquals("The payment area id should be same as", 1, result.get(0).getId());
        assertEquals("The payment area name should be same as", "payarea1", result.get(0).getName());
        assertEquals("The payment area id should be same as", 2, result.get(1).getId());
        assertEquals("The payment area name should be same as", "payarea2", result.get(1).getName());
        assertEquals("The payment area id should be same as", 3, result.get(2).getId());
        assertEquals("The payment area name should be same as", "payarea3", result.get(2).getName());
    }

    /**
     * <p>
     * Failure test for the method <code>getPaymentAreas()</code>.<br>
     * Exception occurs while accessing to DB.<br>
     * Expect BillingCostServiceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostServiceException.class)
    public void testGetPaymentAreas_BillingCostServiceExceptionOccurs() throws Exception {
        impl = (LookupServiceImpl) APP_CONTEXT_INVALID.getBean("lookupService");
        impl.getPaymentAreas();
    }

}
