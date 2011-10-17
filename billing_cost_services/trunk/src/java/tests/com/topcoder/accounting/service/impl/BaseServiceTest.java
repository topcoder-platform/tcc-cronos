/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.topcoder.accounting.service.BillingCostConfigurationException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for class <code>BaseService</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class BaseServiceTest extends BaseUnitTests {

    /**
     * Represents the <code>BaseService</code> instance used to test against.
     */
    private BaseService impl;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(BaseServiceTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @Before
    public void setUp() throws Exception {
        impl = new MockBaseService();
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * Inheritance test, verifies <code>BaseService</code> subclasses should be correct.
     */
    @SuppressWarnings("cast")
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof InitializingBean);
    }

    /**
     * Accuracy test for the constructor <code>BaseService()</code>.<br>
     * . Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>afterPropertiesSet()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testAfterPropertiesSet() {
        Log logger = LogManager.getLog("temp");
        impl.setLogger(logger);
        HibernateTemplate hibernateTemplate = (HibernateTemplate) APP_CONTEXT.getBean("hibernateTemplate");
        impl.setHibernateTemplate(hibernateTemplate);

        impl.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
     * The logger is null.<br>
     * Expect BillingCostConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostConfigurationException.class)
    public void testAfterPropertiesSet_LoggerNull() throws Exception {
        HibernateTemplate hibernateTemplate = (HibernateTemplate) APP_CONTEXT.getBean("hibernateTemplate");
        impl.setHibernateTemplate(hibernateTemplate);

        impl.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
     * The hibernateTemplate is null.<br>
     * Expect BillingCostConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = BillingCostConfigurationException.class)
    public void testAfterPropertiesSet_HibernateTemplateNull() throws Exception {
        Log logger = LogManager.getLog("temp");
        impl.setLogger(logger);

        impl.afterPropertiesSet();
    }

    /**
     * Accuracy test for the method <code>getLogger()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetLogger() {
        assertNull("The initial value should be null", impl.getLogger());
        Log expect = LogManager.getLog("temp");
        impl.setLogger(expect);
        assertEquals("The result should be same as", expect, impl.getLogger());
    }

    /**
     * Accuracy test for the method <code>setLogger(Log)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetLogger() {
        assertNull("The initial value should be null", impl.getLogger());
        Log expect = LogManager.getLog("temp");
        impl.setLogger(expect);
        assertEquals("The result should be same as", expect, impl.getLogger());
    }

    /**
     * Accuracy test for the method <code>getHibernateTemplate()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetHibernateTemplate() {
        assertNull("The initial value should be null", impl.getHibernateTemplate());
        HibernateTemplate expect = (HibernateTemplate) APP_CONTEXT.getBean("hibernateTemplate");
        impl.setHibernateTemplate(expect);
        assertEquals("The result should be same as", expect, impl.getHibernateTemplate());
    }

    /**
     * Accuracy test for the method <code>setHibernateTemplate(HibernateTemplate)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetHibernateTemplate() {
        assertNull("The initial value should be null", impl.getHibernateTemplate());
        HibernateTemplate expect = (HibernateTemplate) APP_CONTEXT.getBean("hibernateTemplate");
        impl.setHibernateTemplate(expect);
        assertEquals("The result should be same as", expect, impl.getHibernateTemplate());
    }

    /**
     * The class for testing.
     *
     * @author stevenfrog
     * @version 1.0
     */
    class MockBaseService extends BaseService {
        // empty
    }

}
