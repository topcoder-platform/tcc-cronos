/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server;

import com.topcoder.web.user.LoginHandler;
import com.topcoder.user.profile.UserProfile;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.ServletContext;

/**
 * <p>A listener for HTTP session events. The main purpose of this listener is to track the users login/logout actions
 * and notify the user online status tracker on them.</p>
 *
 * <p><b>Thread safety:</b> This class is thread-safe.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusSessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    /**
     * <p>Constructs new <code>OrpheusSessionListener</code> instance. This implementation does nothing.</p>
     */
    public OrpheusSessionListener() {
    }

    /**
     * <p>Notifies this listener on a new session created. This implementation does nothing.</p>
     *
     * @param event a <code>HttpSessionEvent</code> providing the details for the event.
     */
    public void sessionCreated(HttpSessionEvent event) {
    }

    /**
     * <p>Notifies this listener on specified session which is going to be invalidated.</p>
     *
     * <p>Notifies the user online status tracker on a user logged out of a session (in case the specified session is
     * associated with any user).</p>
     *
     * @param event a <code>HttpSessionEvent</code> providing the details for the event.
     */
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        UserProfile user = LoginHandler.getAuthenticatedUser(session);
        if (user != null) {
            ServletContext context = session.getServletContext();
            OrpheusOnlineStatusTracker tracker
                = (OrpheusOnlineStatusTracker) context.getAttribute(OrpheusOnlineStatusTracker.CONTEXT_ATTRIBUTE_NAME);
            tracker.userLoggedOut(user, session);
        }
    }

    /**
     * <p>Notifies this listener on session attribute addition. If a user profile is added to session then the listener
     * notifies the user online status tracker on new user logged to server.</p>
     *
     * @param event a <code>HttpSessionBindingEvent</code> providing the details for the event.
     */
    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        if ("user_profile".equals(event.getName())) {
            UserProfile user = (UserProfile) event.getValue();
            ServletContext context = session.getServletContext();
            OrpheusOnlineStatusTracker tracker
                = (OrpheusOnlineStatusTracker) context.getAttribute(OrpheusOnlineStatusTracker.CONTEXT_ATTRIBUTE_NAME);
            tracker.userLoggedIn(user, session);
        }
    }

    /**
     * <p>Notifies this listener on session attribute removal. This implementation does nothing.</p>
     *
     * @param event a <code>HttpSessionBindingEvent</code> providing the details for the event.
     */
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    /**
     * <p>Notifies this listener on session attribute replacement. This implementation does nothing.</p>
     *
     * @param event a <code>HttpSessionBindingEvent</code> providing the details for the event.
     */
    public void attributeReplaced(HttpSessionBindingEvent event) {
    }
}
