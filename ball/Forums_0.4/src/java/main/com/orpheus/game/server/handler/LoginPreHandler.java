/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.server.OrpheusFunctions;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.user.LoginHandler;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>A custom {@link Handler} implementation to be used in conjunction with <code>LoginHandler</code> to perform the
 * pre-handling of <code>Login</code> request. Verifies that a user attempting to login to application is not currently
 * logged in. In latter case forwards the request to a page which informs a user on existing session.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LoginPreHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of result referencing a page notifying the user on existing session.</p>
     */
    public static final String WARNING_RESULT_NAME_CONFIG = "warning_result";

    /**
     * <p>Constructs new <code>LoginPreHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;warning_result&gt;login_warning_result&lt;/warning_result&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public LoginPreHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, WARNING_RESULT_NAME_CONFIG, true);
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
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            HttpServletRequest request = context.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                UserProfile authenticatedUser = LoginHandler.getAuthenticatedUser(session);
                if (authenticatedUser != null) {
                    String currentProfileType = OrpheusFunctions.getUserProfileType(request);
                    if (!"player".equals(currentProfileType)) {
                        return getString(WARNING_RESULT_NAME_CONFIG);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not perform Login request pre-processing", e);
        }
    }
}

