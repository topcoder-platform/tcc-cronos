/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.orpheus.game.server.OrpheusFunctions;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.FrontControllerLogger;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * <p>A helper class providing the functionality for logging the pre-configured messages which are customized based on
 * the provided context.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LogHelper {

    /**
     * <p>An empty <code>String</code> array to be used to represent the empty message text format arguments list.</p>
     */
    public static final String[] EMPTY_ARRAY = new String[0];

    /**
     * <p>A <code>String</code> providing the template for the log message.</p>
     */
    private final static String LOG_MESSAGE_FORMAT
        = "THE BALL LOG : {0} : {1} : User ({2}, {3}, {4}, {5}) : Response to {6} : {7}";

    /**
     * <p>A <code>String</code> providing the template for the log message.</p>
     */
    private final static String LOG_INFO_MESSAGE_FORMAT
        = "THE BALL LOG : {0} : {1} : User ({2}, {3}, {4}, {5}) : Info for {6} : {7}";

    /**
     * <p>A <code>String</code> providing the tempplate for the request log message.</p>
     */
    private final static String LOG_REQUEST_FORMAT
        = "THE BALL LOG : {0} : {1} : User ({2}, {3}, {4}, {5}, {6}) : Request {7} : {8}";

    /**
     * <p>A <code>String</code> providing the default category for log messages.</p>
     */
    private final static String DEFAULT_LOG_CATEGORY = "PLUGIN";

    /**
     * <p>A <code>String</code> array providing the names of the request parameters to be used for building the log
     * message content.</p>
     */
    private final String[] paramNames;

    /**
     * <p>A <code>String</code> providing the template for the logged message specific to performed action.</p>
     */
    private final String pattern;

    /**
     * <p>A <code>String</code> providing the category for the logged message specific to performed action.</p>
     */
    private final String category;

    /**
     * <p>Constructs new <code>LogHelper</code> instance with specified message template, parameter names and category.
     * </p>
     *
     * @param pattern a <code>String</code> providing the log message template.
     * @param paramNames a <code>String</code> array prpviding the names of request parameters to be used for
     *        customizing the message content based on current context.
     * @param category a <code>String</code> providing the category of the log message.
     */
    public LogHelper(String pattern, String[] paramNames, String category) {
        if (pattern == null) {
            throw new IllegalArgumentException("The parameter [pattern] is NULL");
        }
        if (paramNames == null) {
            throw new IllegalArgumentException("The parameter [paramNames] is NULL");
        }
        if (category == null) {
            throw new IllegalArgumentException("The parameter [category] is NULL");
        }
        this.paramNames = paramNames;
        this.pattern = pattern;
        this.category = category;
    }

    /**
     * <p>Constructs new <code>LogHelper</code> instance initialized based on the configuration parameters provided
     * by the specified <code>XML</code> element.</p>
     *
     * <p>
     * <pre>
     *     &lt;WWWWWW name=&quot;x&quot type=&quot;y&quot;&gt;
     *      &lt;log-message&gt;had viewed the Unlocked Sites list for game {0}&lt;/log-message&gt;
     *      &lt;parameters&gt;domain,gameId&lt;/parameters&gt;
     *      &lt;category&gt;WEBSITE&lt;/category&gt;
     *      &lt;action-code&gt;A01&lt;/action-code&gt;
     *      &lt;action-status&gt;F01&lt;/action-code&gt;
     *     &lt;/WWWWW&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public LogHelper(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        this.pattern = getElement(element, "log-message", true);
        String parameters = getElement(element, "parameters", false);
        if (parameters != null) {
            this.paramNames = parameters.split(",");
        } else {
            this.paramNames = EMPTY_ARRAY;
        }
        String category = getElement(element, "category", false);
        if (category == null) {
            this.category = DEFAULT_LOG_CATEGORY;
        } else {
            this.category = category;
        }
    }

    /**
     * <p>Logs the pre-configured message initialized based on the specified context.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param actualLogger a <code>Log</code> to log the events to.
     * @param loggingLevel a <code>Level</code> specifying the logging level.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public void log(ActionContext context, Log actualLogger, Level loggingLevel) {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            final HttpServletRequest request = context.getRequest();
            final HttpSession session = request.getSession(false);
            // Determine the session ID, user handle and user ID
            final String userHandle;
            final Object userId;
            final String sessionId;
            if (session != null) {
                sessionId = session.getId();
                final UserProfile user = LoginHandler.getAuthenticatedUser(session);
                if (user != null) {
                    userId = user.getIdentifier();
                    userHandle = OrpheusFunctions.getHandle(user);
                } else {
                    userId = "-1";
                    userHandle = "ANONYMOUS";
                }
            } else {
                sessionId = "NO_SESSION";
                userId = "-1";
                userHandle = "ANONYMOUS";
            }
            // Build the log message specific to executed action
            final Object[] messageFormatArgs = new Object[this.paramNames.length];
            for (int i = 0; i < messageFormatArgs.length; i++) {
                String[] paramValues = request.getParameterValues(this.paramNames[i]);
                if ((paramValues != null) && (paramValues.length > 0)) {
                    StringBuffer buf = new StringBuffer();
                    for (int j = 0; j < paramValues.length; j++) {
                        if (j > 0) {
                            buf.append(",");
                        }
                        buf.append(paramValues[j]);
                    }
                    messageFormatArgs[i] = buf.toString();
                } else {
                    messageFormatArgs[i] = "";
                }
            }
            // Build entire log message
            final String requestId = getRequestId(request);
            final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            final Object[] globalFormatArgs
                = new Object[] {dateFormatter.format(new Date()), this.category, userHandle, userId,
                                request.getRemoteAddr(), sessionId, requestId,
                MessageFormat.format(this.pattern, messageFormatArgs)};
            final Object[] globalRequestFormatArgs
                = new Object[] {dateFormatter.format(new Date()), this.category, userHandle, userId,
                                request.getRemoteAddr(), sessionId, request.getHeader("User-Agent"), requestId,
                                getFormattedRequest(request)};
            final String logMessage = MessageFormat.format(LOG_MESSAGE_FORMAT, globalFormatArgs);
            final String logRequestMessage = MessageFormat.format(LOG_REQUEST_FORMAT, globalRequestFormatArgs);
            FrontControllerLogger logger
                = new FrontControllerLogger(this.getClass().getName(), actualLogger, loggingLevel);
            logger.info(logRequestMessage);
            logger.info(logMessage);
        } catch (Exception e) {
            // Any exception encountered while logging the event must be ignored in order not to interrupt the request
            // processing
            System.out.println("ERROR WHILE LOGGING THE EVENT : " + e.getMessage());
        }
    }

    /**
     * <p>Logs the pre-configured message initialized based on the specified context.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param message a <code>String</code> providing the message to be logged.
     * @param actualLogger a <code>Log</code> to log the events to.
     * @param loggingLevel a <code>Level</code> specifying the logging level.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public void log(ActionContext context, String message, Log actualLogger, Level loggingLevel) {
        if (message == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            final HttpServletRequest request = context.getRequest();
            final HttpSession session = request.getSession(false);
            // Determine the session ID, user handle and user ID
            final String userHandle;
            final Object userId;
            final String sessionId;
            if (session != null) {
                sessionId = session.getId();
                final UserProfile user = LoginHandler.getAuthenticatedUser(session);
                if (user != null) {
                    userId = user.getIdentifier();
                    userHandle = OrpheusFunctions.getHandle(user);
                } else {
                    userId = "-1";
                    userHandle = "ANONYMOUS";
                }
            } else {
                sessionId = "NO_SESSION";
                userId = "-1";
                userHandle = "ANONYMOUS";
            }
            // Build entire log message
            final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            final Object[] globalFormatArgs
                = new Object[] {dateFormatter.format(new Date()), this.category, userHandle, userId,
                                request.getRemoteAddr(), sessionId, getRequestId(request), message};
            final String logMessage = MessageFormat.format(LOG_INFO_MESSAGE_FORMAT, globalFormatArgs);
            FrontControllerLogger logger
                = new FrontControllerLogger(this.getClass().getName(), actualLogger, loggingLevel);
            logger.info(logMessage);
        } catch (Exception e) {
            // Any exception encountered while logging the event must be ignored in order not to interrupt the request
            // processing
            System.out.println("ERROR WHILE LOGGING THE EVENT : " + e.getMessage());
        }
    }

    /**
     * <p>Gets the list of all exceptions from the specified exception chain.</p>
     *
     * @param e a <code>Throwable</code> providing the thrown exception.  
     * @return a <code>String</code> collecting the exception causes.
     */
    public static String getExceptionMessages(Throwable e) {
        StringBuffer b = new StringBuffer();
        Throwable current = e;
        while (current != null) {
            String message = current.getMessage();
            if (message != null) {
                if (b.length() > 0) {
                    b.append(", ");
                }
                b.append("caused by : ");
                b.append(message);
            }
            current = current.getCause();
        }
        return b.toString();
    }

    /**
     * <p>Gets the text value of the specified node (key) from the given element.</p>
     *
     * @param element an <code>Element</code> providing the element to be used.
     * @param key a <code>String</code> providing the tag name for element to be extracted.
     * @param required <code>true</code> if specified key is required; <code>false</code> otherwise.
     * @return a <code>String</code> providing the text value of the given tag.
     * @throws IllegalArgumentException if the given <code>key</code> is not present or has empty value.
     */
    private static String getElement(Element element, String key, boolean required) {
        NodeList nodeList = element.getElementsByTagName(key);
        if (nodeList.getLength() == 0) {
            if (required) {
                throw new IllegalArgumentException("Key '" + key + "' is missing in the element");
            } else {
                return null;
            }
        }
        Node node = nodeList.item(0).getFirstChild();
        if (node == null && required) {
            throw new IllegalArgumentException("The [" + key + "] element is not found");
        } else if (node != null) {
            String value = node.getNodeValue();
            if (((value == null) || (value.trim().length() == 0)) && required) {
                throw new IllegalArgumentException("The [" + key + "] element is empty");
            }
            return value;
        }
        return null;
    }

    /**
     * <p>Gets the log message providing details for specified request.</p>
     *
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @return a <code>String</code> providing the formatted log message for request.
     */
    private static String getFormattedRequest(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("URL=[");
        buffer.append(request.getRequestURL());
        buffer.append("] ");
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (!name.equalsIgnoreCase("password")) {
                String[] values = request.getParameterValues(name);
                buffer.append(name);
                buffer.append("=[");
                for (int i = 0; i < values.length; i++) {
                    if (i > 0) {
                        buffer.append(',');
                    }
                    buffer.append(values[i]);
                }
                buffer.append("]");
                if (names.hasMoreElements()) {
                    buffer.append(";");
                }
            }
        }
        return buffer.toString();
    }

    /**
     * <p>Gets the ID which could be used for referencing the specified request.</p>
     *
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @return a <code>String</code> providing the ID for request.
     */
    private static String getRequestId(HttpServletRequest request) {
        String requestString = request.toString();
        int pos = requestString.indexOf('@');
        if (pos > 0) {
            requestString = requestString.substring(pos + 1);
        }
        return requestString;
    }
}
