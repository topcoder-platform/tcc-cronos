/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import java.lang.reflect.Field;

import hr.leads.services.JPATestBase;
/**
 * <p>
 * All tests for <code>BaseReportConfigurationServiceBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseReportConfigurationServiceBeanTest extends JPATestBase {

    /**
     * <p>
     * Represents the instance for unit tests.
     * </p>
     */
    private BaseReportConfigurationServiceBean baseReportConfigurationServiceBean = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        baseReportConfigurationServiceBean = (BaseReportConfigurationServiceBean)
            lookupForTest("reportService");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Tests constructor: {@link BaseReportConfigurationServiceBean#BaseReportConfigurationServiceBean()}.
     * </p>
     *
     * <p>
     * Tests if the instance can be successfully created.
     * </p>
     */
    public void testBaseReportConfigurationServiceBean() {
        assertNotNull(baseReportConfigurationServiceBean);
        assertTrue("should be instance of BaseReportConfigurationServiceBean",
                baseReportConfigurationServiceBean instanceof BaseReportConfigurationServiceBean);
    }

    /**
     * <p>
     * Tests method: {@link BaseReportConfigurationServiceBean#getLoggerName()}.
     * </p>
     *
     * <p>
     * Checks if the logger name is properly injected.
     * </p>
     */
    public void testGetLoggerName() {
        assertEquals("incorrect logger name.",
                ReportServiceBean.class.getName(), baseReportConfigurationServiceBean.getLoggerName());
    }

    /**
     * <p>
     * Tests method: {@link BaseReportConfigurationServiceBean#getLogger()}.
     * </p>
     *
     * <p>
     * Checks if the logger is properly injected.
     * </p>
     */
    public void testGetLogger() {
        baseReportConfigurationServiceBean.afterBeanInitialized();
        assertEquals("incorrect logger name.",
                ReportServiceBean.class.getName(), baseReportConfigurationServiceBean.getLogger().getName());
    }

    /**
     * <p>
     * Tests method: {@link BaseReportConfigurationServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * Checks if the logger is logger name is not null.
     * </p>
     */
    public void testAfterBeanInitialized() {
        baseReportConfigurationServiceBean.afterBeanInitialized();
        assertEquals("incorrect logger name.",
                ReportServiceBean.class.getName(), baseReportConfigurationServiceBean.getLogger().getName());
    }

    /**
     * <p>
     * Tests method: {@link BaseReportConfigurationServiceBean#afterBeanInitialized()}.
     * </p>
     *
     * <p>
     * Checks if the logger is logger name is null.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testAfterBeanInitializedLoggerNameNull() throws Exception {
        // inject null to the loggerName
        Field field = baseReportConfigurationServiceBean.getClass().getSuperclass().getDeclaredField("loggerName");
        field.setAccessible(true);
        field.set(baseReportConfigurationServiceBean, null);
        baseReportConfigurationServiceBean.afterBeanInitialized();
        assertEquals("incorrect logger name.",
                ReportServiceBean.class.getName(),
                baseReportConfigurationServiceBean.getLogger().getName());
    }

}
