/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.stresstests;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.topcoder.accounting.service.impl.BillingCostDataServiceImpl;

/**
 * <p>
 * Stress tests for class <code>BillingCostDataServiceImpl</code>.
 * </p>
 *
 * @author dingying131
 * @version 1.0
 */
public class BillingCostDataServiceImplStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents the <code>BillingCostDataServiceImpl</code> instance used to test against.
     * </p>
     */
    private BillingCostDataServiceImpl service;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BillingCostDataServiceImplStressTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        clearData();
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void before() throws Exception {
        loadData();
        service = (BillingCostDataServiceImpl) APP_CONTEXT.getBean("billingCostDataService");
        service.afterPropertiesSet();
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void after() throws Exception {
        clearData();
        service = null;
    }

    /**
     * <p>
     * Stress test for the method <code>getBillingCostReport()</code>.<br>
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetBillingCostReport() throws Exception {
        logEntrance("getBillingCostReport");
        for (int i = 0; i < TIMES; i++) {
            // service.getBillingCostReport(null, 1, 2);
        }
        logExit();
    }

}
