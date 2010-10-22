/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import hr.leads.services.ejb.BaseReportConfigurationServiceBean;

/**
 * <p>
 * Mock class for accuracy test.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class MockBaseReportConfigurationServiceBean extends BaseReportConfigurationServiceBean {
    /**
     * <p>
     * Gets the loggerName field, retrieves the loggerName to be used by subclasses if
     * necessary.
     * </p>
     *
     * @return the loggerName to be used by subclasses.
     */
    public String getLoggerName() {
        return super.getLoggerName();
    }

    /**
     * <p>
     * Gets the logger field, retrieves the Logger instance to be used by subclasses.
     * </p>
     *
     * @return the Logger instance to be used by subclasses.
     */
    public Logger getLogger() {
        return super.getLogger();
    }

    /**
     * <p>
     * Initializes the logger field using the injected logger name.
     * </p>
     *
     * <p>
     * Note, if the loggerName is not injected(or null), the loggerName of the
     * getClass().getName() will be used.
     * </p>
     */
    @PostConstruct
    public void afterBeanInitialized() {
        super.afterBeanInitialized();
    }
}
