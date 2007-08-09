/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.result;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.ResultExecutionException;
import com.topcoder.web.frontcontroller.results.ForwardResult;
import com.topcoder.web.user.LoginHandler;
import com.orpheus.game.server.util.LogEvent;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * <p>A {@link ForwardResult} which collects the events triggered in the course of user's acitivity when playing the
 * games or visiting the web site. The details for such events are put to queue </p>
 *
 * @author isv
 * @version 1.0
 */
public class StatsCollectingResult extends LoggingForwardResult {

    /**
     * <p>An empty <code>String</code> array to be used to represent the empty message text format arguments list.</p>
     */
    public static final String[] EMPTY_ARRAY = new String[0];

    /**
     * <p>An empty <code>String</code> array to be used to represent the unknown parameter message text format arguments
     * list.</p>
     */
    public static final String[] UNKNOWN_PARAM_VALUE_ARRAY = new String[] {"???"};

    /**
     * <p>A <code>String</code> array providing the names of the request parameters to be used for building the log
     * message content.</p>
     */
    private final String[] paramNames;

    /**
     * <p>A <code>int</code> providing the code for the action which has triggered the message to be logged.</p>
     */
    private final int actionCode;

    /**
     * <p>Constructs new <code>StatsCollectingResult</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * <pre>
     *     &lt;result name=&quot;x&quot type=&quot;y&quot;&gt;
     *      &lt;log-message&gt;had viewed the Unlocked Sites list for game {0}&lt;/log-message&gt;
     *      &lt;parameters&gt;domain,gameId&lt;/parameters&gt;
     *      &lt;category&gt;WEBSITE&lt;/category&gt;
     *     &lt;/result&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public StatsCollectingResult(Element element) {
        super(element);
        this.actionCode = Integer.parseInt(getElement(element, "action-code", true));
        String parameters = getElement(element, "parameters", false);
        if (parameters != null) {
            this.paramNames = parameters.split(",");
        } else {
            this.paramNames = EMPTY_ARRAY;
        }
    }

    /**
     * <p>Executes this result.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @param actualLogger a <code>Log</code> to log the events to.
     * @param loggingLevel a <code>Level</code> specifying the logging level.
     * @throws ResultExecutionException if an unrecoverable error prevents the result from successful execution.
     * @throws IllegalArgumentException if specified <code>context</code> is <code>null</code>.
     */
    public void execute(ActionContext context, Log actualLogger, Level loggingLevel) throws ResultExecutionException {
        // Prepare the event and put it to queue for logging to DB table
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            final HttpServletRequest request = context.getRequest();
            final HttpSession session = request.getSession(false);
            // Determine the session ID, user handle and user ID
            final Object userId;
            final String sessionId;
            if (session != null) {
                sessionId = session.getId();
                final UserProfile user = LoginHandler.getAuthenticatedUser(session);
                if (user != null) {
                    userId = user.getIdentifier();
                } else {
                    userId = "-1";
                }
            } else {
                sessionId = "NO_SESSION";
                userId = "-1";
            }
            // Build the log message specific to executed action
            final Map params = new HashMap();
            for (int i = 0; i < this.paramNames.length; i++) {
                String[] paramValues = request.getParameterValues(this.paramNames[i]);
                if ((paramValues == null) || (paramValues.length == 0)) {
                    paramValues = UNKNOWN_PARAM_VALUE_ARRAY;
                }
                params.put(this.paramNames[i], paramValues);
            }
            ServletContext servletContext = getServletContext(context);
            Collection queue = (Collection) servletContext.getAttribute("LogEventQueue");
            queue.add(new LogEvent(userId, this.actionCode, new Date(), sessionId, params, request.getRemoteAddr()));
        } catch (Exception e) {
            // Any exception encountered while logging the event must be ignored in order not to interrupt the request
            // processing
            System.out.println("ERROR WHILE LOGGING THE EVENT : " + e.getMessage());
        }
        super.execute(context, actualLogger, loggingLevel);
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
     * <p>Gets the servlet context.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return a <code>ServletContext</code> associated with current environment.
     * @throws IllegalStateException if there is no active session associated with specified request.
     */
    private ServletContext getServletContext(ActionContext context) {
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getServletContext();
        } else {
            throw new IllegalStateException("There is no active session");
        }
    }
}
