/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.dao.implementations;

import javax.ejb.SessionContext;
import javax.persistence.EntityManager;

import com.topcoder.service.digitalrun.points.DigitalRunPointsManagerPersistenceException;
import com.topcoder.service.digitalrun.points.Helper;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This abstract class holds the common field of all the five concrete DAO implementations. It has a
 * SessionContext and an unit name used to obtain an EntityManager instance and a Log instance used
 * to perform logging if logging is desired; if logging is not desired this field will remain
 * null.It provides a method that obtains an EntityManager instance.
 * </p>
 * <p>
 * Thread Safety: It is not thread safe because it is mutable. Anyway, setters are meant to be
 * called only once so there will be no thread safety issues.
 * </p>
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public abstract class AbstractDAO {
    /**
     * The unit name used to obtain an EntityManager instance. It has a setter; cannot be set to
     * null or empty string.
     */
    private String unitName;
    /**
     * Logger used to perform logging. If not set logging is disabled. It has a setter; cannot be
     * set to null.
     */
    private Log logger;
    /**
     * The session context used to obtain an EntityManager instance. It has a setter; cannot be set
     * to null.
     */
    private SessionContext sessionContext;

    /**
     * Empty constructor.
     */
    protected AbstractDAO() {
        // Empty
    }

    /**
     * Creates an EntityManager instance and returns it.
     * @throws DigitalRunPointsManagerPersistenceException
     *             if the retrieval of the EntityManager failed
     * @return an EntityManager instance
     */
    protected EntityManager getEntityManager() throws DigitalRunPointsManagerPersistenceException {
        String methodName = "com.topcoder.service.digitalrun.points.dao.implementations.AbstractDAO.getEntityManager()";
        // Log the entrance
        Helper.logEntranceInfo(logger, methodName);

        // Check whether the sessionContext is null
        if (sessionContext == null) {
            Helper.throwPersitenceExceptionWithLog("The sessionContext is null.", null, logger, methodName);
        }

        Object res = sessionContext.lookup(this.unitName);

        // Check whether the res is null or is not instance of EntityManager
        if (res == null || !(res instanceof EntityManager)) {
            Helper.throwPersitenceExceptionWithLog("The retrieval of the EntityManager failed."
                    + " The result is null or is not instanceof EntityManager", null, logger, methodName);
        }

        // Log the exit
        Helper.logExitInfo(logger, methodName);
        return (EntityManager) res;
    }

    /**
     * Retrieves the logger.
     * @return the logger field
     */
    protected Log getLogger() {
        return this.logger;
    }

    /**
     * Retrieves the unit name.
     * @return the unit name field
     */
    protected String getUnitName() {
        return this.unitName;
    }

    /**
     * Retrieves the session context.
     * @return the session context field
     */
    protected SessionContext getSessionContext() {
        return this.sessionContext;
    }

    /**
     * Setter for the namesake field. Simply set the field.
     * @throws IllegalArgumentException
     *             if argument is null
     * @param logger
     *            the logger
     */
    public void setLogger(Log logger) {
        Helper.checkNull(logger, "logger");

        this.logger = logger;
    }

    /**
     * Setter for the namesake field. Simply set the field.
     * @throws IllegalArgumentException
     *             if argument is null
     * @param sessionContext
     *            the session context
     */
    public void setSessionContext(SessionContext sessionContext) {
        Helper.checkNull(sessionContext, "sessionContext");

        this.sessionContext = sessionContext;
    }

    /**
     * Setter for the namesake field. Simply set the field.
     * @throws IllegalArgumentException
     *             if argument is null or empty string
     * @param unitName
     *            the unit name
     */
    public void setUnitName(String unitName) {
        Helper.checkNullAndEmpty(unitName, "unitName");

        this.unitName = unitName;
    }
}
