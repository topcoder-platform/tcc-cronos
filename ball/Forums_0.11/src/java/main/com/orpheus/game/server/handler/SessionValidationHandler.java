/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import org.w3c.dom.Element;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.user.LoginHandler;
import com.topcoder.user.profile.UserProfile;
import com.orpheus.game.server.OrpheusOnlineStatusTracker;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>A custom handler which is intended to verify if the current session hasn't expired. If so the handler will
 * redirect the request to <code>Login</code> page. This handler is expected to be used in conjunction with any other
 * handlers which require the user's session to be active.</p>
 *
 * @author isv
 * @version 1.0
 */
public class SessionValidationHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name of configuration parameter referencing the login page.</p>
     */
    public static final String LOGIN_PAGE_RESULT_CONFIG = "login_page_result";

    /**
     * <p>Constructs new <code>SessionValidationHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;login_page_result&gt;login&lt;/login_page_result&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or any of required configuration parameters
     *         is missing.
     */
    public SessionValidationHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, LOGIN_PAGE_RESULT_CONFIG, true);
    }

    /**
     * <p>Executes this handler when servicing the specified request. Analyzes the current session and if the session
     * has expired or the current user is not logged to application then forwards the request to <code>Login</code>
     * page (which is configurable).</p> 
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if a handler execution succeeds; otherwise an exception will be thrown.
     * @throws HandlerExecutionException if an unrecoverable error prevenst the handler from successful execution.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserProfile user = LoginHandler.getAuthenticatedUser(session);
            if (user != null) {
                // Session is active and user is logged to application, track the request if it is not already tracked
                Object requestTrackedFlag = request.getAttribute("REQUEST_TRACKED");
                if (requestTrackedFlag == null) {
                    OrpheusOnlineStatusTracker tracker = getOnlineStatusTracker(session);
                    tracker.requestArrived(user, session);
                    request.setAttribute("REQUEST_TRACKED", Boolean.TRUE);
                }
                return null;
            }
        }
        // Either session has expired or there is no user logged to application
        return getString(LOGIN_PAGE_RESULT_CONFIG);
    }
}
