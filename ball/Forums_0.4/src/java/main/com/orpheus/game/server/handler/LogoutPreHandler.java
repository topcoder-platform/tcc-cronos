/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.Cookie;

/**
 * <p>A custom {@link Handler} implementation to be used in conjunction with <code>LogoutHandler</code> to perform the
 * post-handling of <code>Logout</code> request. Removes the user's login cookie. </p>
 *
 * @author mtong
 * @version 1.0
 */
public class LogoutPreHandler extends AbstractGameServerHandler implements Handler {

    private static final String USER_COOKIE_NAME = "user_id";
    
    /**
     * <p>Constructs new <code>LogoutPreHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public LogoutPreHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
    }

    /**
     * <p>Processes the incoming request.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        System.out.println("*** in LogoutPreHandler.execute()");
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            System.out.println("*** removing cookie....");
            Cookie c = new Cookie("Ball_" + USER_COOKIE_NAME, "");
            c.setPath("/");
            c.setMaxAge(0);
            context.getResponse().addCookie(c);            
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not perform Logout request post-processing", e);
        }
    }
}

