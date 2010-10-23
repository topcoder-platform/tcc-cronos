/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;

/**
 * <p>
 * This is the base class for all EJBs defined in this component.
 * </p>
 * <p>
 * It provides Logger instance created according to injected logger name.
 * </p>
 * <p>
 * <b> Thread Safety: </b> This class is mutable and not thread safe. But it is
 * always used in thread safe manner in EJB container because its state doesn't
 * change after initialization.
 * </p>
 *
 * @author semi_sleep, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseReportConfigurationServiceBean {

    /**
     * <p>
     * Represents the logger name used to retrieve logger.
     * </p>
     * <p>
     * It can be any value.
     * </p>
     * <p>
     * It can be modified with EJB container injection, will be set to current
     * class name in afterBeanInitialized() method if it's null / empty.
     * </p>
     * <p>
     * It is used in afterBeanInitialized() method, it has s a protected getter
     * to expose it to subclasses.
     * </p>
     */
    @Resource
    private String loggerName;

    /**
     * <p>
     * Represents the logger used by this class and subclasses to write log.
     * </p>
     * <p>
     * It cannot be null after initialization.
     * </p>
     * <p>
     * It is initialized in the afterBeanInitialized() method.
     * </p>
     * <p>
     * It has a protected getter to expose it to subclasses.
     * </p>
    */
    private Logger logger;

    /**
     * <p>
     * Creates an instance of BaseReportConfigurationServiceBean.
     * </p>
     *
     * <p>
     * It is a default constructor.
     * </p>
    */
    protected BaseReportConfigurationServiceBean() {
        // do nothing
    }

    /**
     * <p>
     * Gets the loggerName field, retrieves the loggerName to be used by subclasses if necessary.
     * </p>
     *
     * @return the loggerName to be used by subclasses.
    */
    protected String getLoggerName() {
        return loggerName;
    }

    /**
     * <p>
     * Gets the logger field, retrieves the Logger instance to be used by subclasses.
     * </p>
     *
     * @return the Logger instance to be used by subclasses.
    */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * <p>
     * Initializes the logger field using the injected logger name.
     * </p>
     *
     * <p>
     * Note, if the loggerName is not injected(or null), the loggerName of the getClass().getName()
     * will be used.
     * </p>
     */
    @PostConstruct
    protected void afterBeanInitialized() {
        if (loggerName == null || loggerName.length() == 0) {
            loggerName = getClass().getName();
        }
        logger = Logger.getLogger(loggerName);
    }
}
