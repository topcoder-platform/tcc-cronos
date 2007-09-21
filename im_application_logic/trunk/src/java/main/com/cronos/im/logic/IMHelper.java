/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.cronos.im.messenger.Messenger;

import com.topcoder.chat.message.pool.Message;
import com.topcoder.chat.session.ChatSession;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.log.Level;

/**
 * <p>
 * Helper class for the IM Application Logic component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class IMHelper {

    /**
     * Id of the online status of user.
     */
    static final long USER_STATUS_ONLINE = 101;

    /**
     * Id of the offline status of user.
     */
    static final long USER_STATUS_OFFLINE = 103;

    /**
     * Id of the open status of session.
     */
    static final long SESSION_STATUS_OPEN = 203;

    /**
     * Id of the close status of session.
     */
    static final long SESSION_STATUS_CLOSE = 204;

    /**
     * Id of the user entity.
     */
    static final long ENTITY_USER = 1;

    /**
     * Id of the session entity.
     */
    static final long ENTITY_SESSION = 2;

    /**
     * <p>
     * This private constructor prevents to create a new instance.
     * </p>
     */
    private IMHelper() {
        // empty
    }

    /**
     * <p>
     * Check whether the given Object is null.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     *
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    static void checkNull(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * <p>
     * Check whether the given String is null or empty.
     * </p>
     *
     * @param arg
     *            the String to check
     * @param name
     *            the name of the String argument to check
     *
     * @throws IllegalArgumentException
     *             if the given string is null or empty
     */
    static void checkString(String arg, String name) {
        checkNull(arg, name);

        if (arg.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }
    }

    /**
     * Get optional configuration string from given namespace and property.
     *
     * @param ns
     *            the namespace to get configuration string from
     * @param prop
     *            the property name of the configuration value to get
     * @return the configuration value of the given property in the given namespace
     * @throws IMConfigurationException
     *             if the namespace is unknown or the property is empty string
     */
    static String getOptionalConfigString(String ns, String prop) throws IMConfigurationException {
        String s;
        // get configuration string from config manager
        try {
            s = ConfigManager.getInstance().getString(ns, prop);
        } catch (UnknownNamespaceException e) {
            throw new IMConfigurationException("The namespace " + ns + " is unknown.", e);
        }
        // check whether it is empty
        if (s != null && s.trim().length() == 0) {
            throw new IMConfigurationException("The property " + prop + " is namespace " + ns + " is empty.");
        }
        return s;
    }

    /**
     * Get required configuration string from given namespace and property.
     *
     * @param ns
     *            the namespace to get configuration string from
     * @param prop
     *            the property name of the configuration value to get
     * @return the configuration value of the given property in the given namespace
     * @throws IMConfigurationException
     *             if the namespace is unknown or the property is missing or empty string
     */
    static String getRequiredConfigString(String ns, String prop) throws IMConfigurationException {
        String s = getOptionalConfigString(ns, prop);
        // check whether it is missing
        if (s == null) {
            throw new IMConfigurationException("The property " + prop + " is namespace " + ns
                    + " is missing.");
        }
        return s;
    }

    /**
     * Checks if the given id is contained in the ids array.
     *
     * @param ids
     *            the ids array
     * @param id
     *            the id the check
     * @return whether the id in contained in the ids array
     */
    static boolean contains(long[] ids, long id) {
        if (ids == null) {
            return false;
        }
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Post message to user message pool. If any error occurs, the error details will be logged.
     *
     * @param messenger the messenger used to post message.
     * @param msg the message to post.
     * @param userId the user id of the user pool.
     * @param logger the logger to log the exception.
     */
    static void postMessage(Messenger messenger, Message msg, long userId, IMLogger logger) {
        try {
            messenger.postMessage(msg, userId);
        } catch (Exception e) {
            if (logger != null) {
                logger.log(Level.WARN, e.toString());
            }
        }
    }

    /**
     * Post message to user session message pool. If any error occurs, the error details will be logged.
     *
     * @param messenger the messenger used to post message.
     * @param msg the message to post.
     * @param userId the user id of the user session pool.
     * @param sessionId the session id of the user session pool.
     * @param logger the logger to log the exception.
     */
    static void postMessage(Messenger messenger, Message msg, long userId, long sessionId, IMLogger logger) {
        try {
            messenger.postMessage(msg, userId, sessionId);
        } catch (Exception e) {
            if (logger != null) {
                logger.log(Level.WARN, e.toString());
            }
        }
    }

    /**
     * Post message to other users of a session (not the message sender). If any error occurs, the error
     * details will be logged.
     *
     * @param messenger the messenger used to post message.
     * @param msg the message to post.
     * @param session the chat session containing the other users.
     * @param logger the logger to log the exception.
     */
    static void postMessageToOthers(Messenger messenger, Message msg, ChatSession session, IMLogger logger) {
        try {
            messenger.postMessageToOthers(msg, session);
        } catch (Exception e) {
            if (logger != null) {
                logger.log(Level.WARN, e.toString());
            }
        }
    }
}
