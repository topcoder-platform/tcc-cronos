/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import com.topcoder.service.user.UserServiceRemote;
import com.topcoder.service.user.UserServiceLocal;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * It provides CRUD on user object.
 * </p>
 * 
 * @author snow01
 * @since Cockpit Release Assembly for Receipts
 * @version 1.0
 */
@RunAs("Cockpit Administrator")
@RolesAllowed("Cockpit User")
@DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class UserServiceBean implements UserServiceRemote, UserServiceLocal {
    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * This field represents the persistence unit name to lookup the <code>EntityManager</code> from the
     * <code>SessionContext</code>. It is initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    @Resource(name = "unitName")
    private String unitName;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log logger;

    /**
     * A default empty constructor.
     */
    public UserServiceBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this point all the bean's resources will be
     * ready.
     * </p>
     */
    @PostConstruct
    public void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException("logName parameter not supposed to be empty.");
            }

            logger = LogManager.getLog(logName);
        }

        // first record in logger
        logExit("init");
    }

    /**
     * <p>
     * This method retrieve the email address for given user id.
     * </p>
     * 
     * @param userid
     *            user id to look for
     * 
     * @return the email address
     * 
     * @throws IllegalArgumentWSException
     *             if the argument is invalid
     * @throws UserServiceException
     *             if any error occurs when getting permissions.
     * 
     * @since Cockpit Release Assembly for Receipts
     */
    public String getEmailAddress(long userid) throws UserServiceException {
        try {
            logEnter("getEmailAddress(userid)");
            logOneParameter(userid);

            EntityManager em = getEntityManager();
            Query query = em.createNativeQuery("select max(address) from email where user_id = " + userid);
            Object result = query.getSingleResult();
            if (result != null) {
                return result.toString();
            }

            return null;
        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapUserServiceException(e, "There are errors while retrieving the user's email address.");
        } finally {
            logExit("getEmailAddress(userid)");
        }
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     * 
     * @return the EntityManager looked up from the session context
     * @throws ContestManagementException
     *             if fail to get the EntityManager from the sessionContext.
     */
    private EntityManager getEntityManager() throws UserServiceException {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw wrapUserServiceException("The object for jndi name '" + unitName + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapUserServiceException(e, "The jndi name for '" + unitName + "' should be EntityManager instance.");
        }
    }

    /**
     * <p>
     * Log the entrance of a method.
     * </p>
     * 
     * @param methodName
     *            the method name
     */
    private void logEnter(String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[Enter method: UserServiceBean." + methodName + "]");
        }
    }

    /**
     * <p>
     * Log the exit of a method.
     * </p>
     * 
     * @param methodName
     *            the method name
     */
    private void logExit(String methodName) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[Exit method: " + methodName + "]");
        }
    }

    /**
     * <p>
     * Log the parameter.
     * </p>
     * 
     * @param param
     *            the parameter value
     */
    private void logOneParameter(Object param) {
        if (logger != null) {
            logger.log(Level.DEBUG, "[param1: {0}]", param);
        }
    }

    /**
     * <p>
     * Log the exception.
     * </p>
     * 
     * @param e
     *            the exception to log
     * @param message
     *            the string message
     */
    private void logException(Throwable e, String message) {
        if (logger != null) {
            // This will log the message and StackTrace of the exception.
            logger.log(Level.ERROR, e, message);

            while (e != null) {
                logger.log(Level.ERROR, "INNER: " + e.getMessage());
                e = e.getCause();
            }
        }
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     * 
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * @return the created exception
     */
    private UserServiceException wrapUserServiceException(Exception e, String message) {
        UserServiceException ce = new UserServiceException(message, e);
        logException(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     * 
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     * @return the created exception
     */
    private UserServiceException wrapUserServiceException(String message) {
        UserServiceException ce = new UserServiceException(message);
        logException(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }
}
