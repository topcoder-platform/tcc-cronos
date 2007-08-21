/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.server.OrpheusFunctions;
import com.orpheus.game.server.util.AuthenticationSupport;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>A custom {@link Handler} implementation to be used in conjunction with <code>LoginHandler</code> to perform the
 * post-handling of <code>Login</code> request. Stores a login cookie for the user. </p>
 *
 * @author mtong
 * @version 1.0
 */
public class LoginPostHandler extends AbstractGameServerHandler implements Handler {

    private static final String USER_COOKIE_NAME = "user_id";
    
    /**
     * <p>Constructs new <code>LoginPreHandler</code> instance initialized based on the configuration parameters
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
    public LoginPostHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
    }

    /**
     * <p>Processes the incoming request. If a user attempting to login to application is currently already logged in
     * then the request is forwarded to a page notifying on that and suggesting to logout first. Note that the test is
     * performed for users other than ones assigned a <code>player</code> role.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        System.out.println("*** in LoginPostHandler.execute()");
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            HttpServletRequest request = context.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                System.out.println("*** session is non-null");
                UserProfile authenticatedUser = LoginHandler.getAuthenticatedUser(session);
                if (authenticatedUser != null) {
                    long uid = ((Long)authenticatedUser.getIdentifier()).longValue();
                    String handle = OrpheusFunctions.getHandle(authenticatedUser);
                    String password = (String) authenticatedUser.getProperty(UserConstants.CREDENTIALS_PASSWORD);
                    
                    String hash = AuthenticationSupport.hashPassword(uid + password);
                    Cookie c = new Cookie("Ball_" + USER_COOKIE_NAME, "" + uid + "|" + handle + "|" + hash);
                    System.out.println("*** Saving cookie with value: " + c.getValue());
                    c.setPath("/");
                    c.setMaxAge(Integer.MAX_VALUE);  // if the user's "Remember Me" option is not enabled, this should be -1
                    context.getResponse().addCookie(c);
                }
            }
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not perform Login request post-processing", e);
        }
    }
}

