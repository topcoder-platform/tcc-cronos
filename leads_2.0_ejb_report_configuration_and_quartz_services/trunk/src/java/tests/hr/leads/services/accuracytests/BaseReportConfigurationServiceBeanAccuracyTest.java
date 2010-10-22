/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import hr.leads.services.ejb.BaseReportConfigurationServiceBean;

import org.apache.log4j.Logger;

/**
 * <p>
 * This class is the accuracy tests for class <code>BaseReportConfigurationServiceBean</code>.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class BaseReportConfigurationServiceBeanAccuracyTest extends AccuracyBaseTest {
    /**
     * Represents the MockBaseReportConfigurationServiceBean instance for accuracy test.
     */
    private static MockBaseReportConfigurationServiceBean bean;

    /**
     * Represents the logger name.
     */
    private static final String NAME = "name";

    /**
     * Represents the logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger(NAME);

    /**
     * Sets up the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        bean = new MockBaseReportConfigurationServiceBean();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void tearDown() throws Exception {
        bean = null;
    }

    /**
     * <p>
     * Accuracy tests for the constructor.
     * </p>
     */
    public void testCtor() {
        MockBaseReportConfigurationServiceBean local = new MockBaseReportConfigurationServiceBean();
        assertNotNull(local);
        assertTrue(local instanceof BaseReportConfigurationServiceBean);
    }

    /**
     * <p>
     * Accuracy tests for the method <code>getLoggerName()</code>.
     * </p>
     *
     * @throws Exception if any exception occurred
     */
    public void testGetLoggerNameAccuracy() throws Exception {
        setField(BaseReportConfigurationServiceBean.class, bean, "loggerName", NAME);
        assertEquals(NAME, bean.getLoggerName());
    }

    /**
     * <p>
     *
     * Accuracy tests for the method <code>getLogger()</code>.
     * </p>
     *
     * @throws Exception if any exception occurred
     */
    public void testGetLoggerAccuracy() throws Exception {
        setField(BaseReportConfigurationServiceBean.class, bean, "logger", LOGGER);
        assertEquals(LOGGER, bean.getLogger());
    }

    /**
     * <p>
     * Accuracy tests for the method <code>afterBeanInitialized()</code>.
     * </p>
     */
    public void testAfterBeanInitializedAccuracy() {
        assertNull(bean.getLoggerName());
        assertNull(bean.getLogger());
        bean.afterBeanInitialized();
        assertNotNull(bean.getLoggerName());
        assertNotNull(bean.getLogger());
    }
}
