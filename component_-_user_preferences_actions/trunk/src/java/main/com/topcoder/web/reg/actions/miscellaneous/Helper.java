/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import com.jivesoftware.base.UserNotFoundException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This class contains helper methods and constants.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {

    /**
     * <p>
     * Represents discard action name.
     * </p>
     */
    static final String DISCARD_ACTION = "discard";

    /**
     * <p>
     * Represents submit action name.
     * </p>
     */
    static final String SUBMIT_ACTION = "submit";

    /**
     * <p>
     * Prevents from instantiating.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Wraps given exception and provides logging if needed.
     * </p>
     * @param logger the logger to provide logging
     * @param methodSignature the method signature, can be null
     * @param errorMessage the error message
     * @param e the exception that occurs, can be null
     * @throws UserPreferencesActionExecutionException throws wrapped exception
     */
    private static void wrapAndLogException(Log logger, String methodSignature, String errorMessage, Throwable e)
        throws UserPreferencesActionExecutionException {
        if (methodSignature != null) {
            throw Helper.logAndWrapException(logger, methodSignature, errorMessage, e,
                    UserPreferencesActionExecutionException.class);
        } else {
            throw new UserPreferencesActionExecutionException("User not found exception occurred.", e);
        }
    }

    /**
     * <p>
     * Checks whether given fieldValue less than given value.
     * </p>
     * @param logger the logger to provide logging
     * @param methodSignature the method signature
     * @param action the action to be populated with field errors
     * @param fieldValue the field value to check
     * @param fieldName the field name
     * @param value the that fieldValue should be greater
     */
    static void checkIntegerFieldGreater(Log logger, String methodSignature, ActionSupport action, int fieldValue,
            String fieldName, int value) {
        if (fieldValue < value) {
            String errorMessage = fieldName + " should be greater than " + value + ".";
            action.addFieldError(fieldName, errorMessage);
            logger.log(Level.WARN, "Validation error in method " + methodSignature + ". " + errorMessage);
        }
    }

    /**
     * <p>
     * Checks whether given fieldValue is null or empty and adds to given action field error.
     * </p>
     * @param logger the logger to provide logging
     * @param methodSignature the method signature
     * @param action the action to be populated with field errors
     * @param fieldValue the field value to check
     * @param fieldName the field name
     */
    static void checkNotNullNorEmpty(Log logger, String methodSignature, ActionSupport action, String fieldValue,
            String fieldName) {
        if (fieldValue == null || fieldValue.trim().length() == 0) {
            String errorMessage = fieldName + " should not be null or empty.";
            action.addFieldError(fieldName, errorMessage);
            logger.log(Level.WARN, "Validation error in method " + methodSignature + ". " + errorMessage);
        }
    }

    /**
     * <p>
     * Retrieves logged in forum user.
     * </p>
     * @param action the action get logged user
     * @param methodSignature the method signature for logging, is null then logging is not performed
     * @return retrieved logged in forum user
     * @throws UserPreferencesActionExecutionException if user not found exception occurred
     */
    static com.jivesoftware.base.User getLoggedInForumUser(BaseForumAction action, String methodSignature)
        throws UserPreferencesActionExecutionException {
        try {
            com.jivesoftware.base.User user =
                    action.getForumFactory().getUserManager().getUser(action.getLoggedInUser().getId());
            // check if user exists in forum database
            if (user == null) {
                wrapAndLogException(action.getLogger(), methodSignature, "User is not present in forum database.",
                        null);
            }
            return user;
        } catch (UserNotFoundException e) {
            // check if logging is required
            if (methodSignature != null) {
                throw Helper.logAndWrapException(action.getLogger(), methodSignature,
                        "User not found exception occurred.", e, UserPreferencesActionExecutionException.class);
            } else {
                throw new UserPreferencesActionExecutionException("User not found exception occurred.", e);
            }
        }
    }

    /**
     * <p>
     * Creates User properties string representation.
     * </p>
     * @param user the user to get properties string representation
     * @return String representation of user's properties
     */
    static String getUserPropertiesStringRepresentation(com.jivesoftware.base.User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Iterator iterator = user.getPropertyNames(); iterator.hasNext();) {
            String propertyName = (String) iterator.next();
            String propertyValue = user.getProperty(propertyName);
            sb.append("{").append(propertyName).append("=").append(propertyValue).append("},");
        }
        // replace last comma
        sb.replace(sb.length() - 1, sb.length(), "");
        sb.append("]");
        return sb.toString();
    }

    /**
     * <p>
     * Handles action execute method with action discard.
     * </p>
     * @param action the action to handle discard action
     * @param methodSignature the method signature for logging
     * @throws PreferencesActionDiscardException if backed up user is not present in session
     */
    static void handleDiscardAction(BasePreferencesAction action, String methodSignature)
        throws PreferencesActionDiscardException {
        User backupUser = (User) ActionContext.getContext().getSession().get(action.getBackupSessionKey());
        if (backupUser == null) {
            throw Helper.logAndWrapException(action.getLogger(), methodSignature,
                    "There is no user in session for backupSessionKey " + action.getBackupSessionKey(), null,
                    PreferencesActionDiscardException.class);
        }
        action.getUserDao().saveOrUpdate(backupUser);
    }

    /**
     * <p>
     * Handles action execute forum preferences method with action discard.
     * </p>
     * @param action the action to handle discard action
     * @param methodSignature the method signature for logging
     * @throws PreferencesActionDiscardException if backed up user is not present in session
     */
    static void handleDiscardForumAction(BaseForumAction action, String methodSignature)
        throws PreferencesActionDiscardException {
        com.jivesoftware.base.User backupUser =
                (com.jivesoftware.base.User) ActionContext.getContext().getSession().get(action.getBackupSessionKey());
        if (backupUser == null) {
            throw Helper.logAndWrapException(action.getLogger(), methodSignature,
                    "There is no user in session for backupSessionKey " + action.getBackupSessionKey(), null,
                    PreferencesActionDiscardException.class);
        }
        // update user
    }

    /**
     * <p>
     * Logs and returns wrapped exception.
     * </p>
     * @param <T> the type of exception
     * @param logger the logger to provide logging
     * @param methodSignature the method signature
     * @param errorMessage the error message
     * @param e the exception that occurs, can be null
     * @param exceptionClass the class of expected exception
     * @return wrapped given exception into expected exception class
     */
    static < T extends Throwable > T logAndWrapException(Log logger, String methodSignature, String errorMessage,
            Throwable e, Class < T > exceptionClass) {
        Throwable exceptionToLog = e;
        T result = null;
        try {
            if (e != null) {
                result = exceptionClass.getConstructor(String.class, Throwable.class).newInstance(errorMessage, e);
            } else {
                result = exceptionClass.getConstructor(String.class).newInstance(errorMessage);
                exceptionToLog = result;
            }
        } catch (IllegalArgumentException e1) {
            // never happened
        } catch (SecurityException e1) {
            // never happened
        } catch (InstantiationException e1) {
            // never happened
        } catch (IllegalAccessException e1) {
            // never happened
        } catch (InvocationTargetException e1) {
            // never happened
        } catch (NoSuchMethodException e1) {
            // never happened
        }
        LoggingWrapperUtility.logException(logger, methodSignature, exceptionToLog);
        return result;
    }
}
