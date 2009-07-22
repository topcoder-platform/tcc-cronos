/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.user.ejb;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.topcoder.service.user.Helper;
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
 * <p>
 * Updated for Jira and Confluence User Sync Widget 1.0
 *  The mock implementation of getEmailAddress and isAdmin has been moved from user_sync_service UserServiceBean class.
 * </p>
 * 
 * <p>
 * The mock implementation for getEmailAddress(..) returns <userHandle>@topcoder.com email address for any user handle
 * that starts with alphabets and just has allowed character sets [A-Z], [a-z], [0-9], _ (a underscore). Other wise it
 * returns null.
 * 
 * The mock implementation for isAdmin(..) returns true for 'user' handle or all those handles that has only Upper case
 * alphabets.
 * </p>
 * 
 * @author snow01, TCSASSEMBLER
 * @since Cockpit Release Assembly for Receipts
 * @version 1.0
 */
@RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
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
     * Static instance of Pattern that validates user handle for the mock implementation.
     */
    private static final Pattern validUserHandlePattern = Pattern.compile("[A-Za-z][_A-Za-z0-9]*");

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
     * This method retrieve the email address for given user handle.
     * 
     * This mock implementation returns <userHandle>@topcoder.com email address for any user handle that starts with
     * alphabets and just has allowed character sets [A-Z], [a-z], [0-9], _ (a underscore). Other wise it returns null.
     * </p>
     * 
     * @param userHandle
     *            user handle to look for
     * 
     * @return the email address
     * 
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     * @since Jira & Confluence User Sync Service             
     */
    public String getEmailAddress(String userHandle) throws UserServiceException {
        String ret = null;
        try {
            logEnter("getEmailAddress(userHandle)", userHandle);
            Helper.checkNull(userHandle, "userHandle");
            Helper.checkEmpty(userHandle, "userHandle");

            Matcher matcher = validUserHandlePattern.matcher(userHandle);
            if (matcher.matches()) {
                ret = userHandle + "@topcoder.com";
            } else {
                ret = null;
            }
        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "IllegalStateException.");
        } finally {
            logExit("getEmailAddress(userHandle)", ret);
        }

        return ret;
    }

    /**
     * <p>
     * This method returns true if given user handle is admin otherwise it returns false.
     * 
     * This mock implementation returns true for 'user' handle or all those handles that has only Upper case alphabets.
     * </p>
     * 
     * @param userHandle
     *            user handle to look for
     * 
     * @return returns true if given user handle is admin otherwise it returns false.
     * 
     * @throws UserServiceException
     *             if any error occurs when getting user details.
     * @since Jira & Confluence User Sync Service
     */
    public boolean isAdmin(String userHandle) throws UserServiceException {
        boolean ret = false;
        try {
            logEnter("isAdmin(userHandle)", userHandle);
            Helper.checkNull(userHandle, "userHandle");
            Helper.checkEmpty(userHandle, "userHandle");

            ret = userHandle.equals("user") || (userHandle.toUpperCase().equals(userHandle));
        } catch (IllegalStateException e) {
            throw wrapUserServiceException(e, "IllegalStateException.");
        } finally {
            logExit("isAdmin(userHandle)", ret);
        }

        return ret;
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
     * This method used to log enter in method. It will persist both method name and it's parameters if any.
     * </p>
     * 
     * @param method
     *            name of the entered method
     * @param params
     *            array containing parameters used to invoke method
     */
    private void logEnter(String method, Object... params) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Enter method UserServiceBean.{0} with parameters {1}.", method, Arrays
                    .deepToString(params));
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     * 
     * @param method
     *            name of the leaved method
     * @param returnValue
     *            value returned from the method
     */
    private void logExit(String method, Object returnValue) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0} with return value {1}.", method, returnValue);
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
