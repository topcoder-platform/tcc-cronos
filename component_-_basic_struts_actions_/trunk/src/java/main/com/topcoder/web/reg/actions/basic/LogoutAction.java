/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import javax.servlet.http.HttpSession;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.shared.security.User;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.security.WebAuthentication;

/**
 * <p>
 * This action handles user logout. It simply logs the user out with the WebAuthentication from session, and
 * invalidates the session.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because it's mutable. However, dedicated instance of struts
 * action will be created by the struts framework to process the user request, so the struts actions don't
 * need to be thread-safe.
 * </p>
 * <b>Usage</b>
 * <p>
 *
 * <pre>
 * &lt;action name="logout" class="logoutAction"&gt;
 *             &lt;result name="success"&gt;
 *                 &lt;!--this is up to configuration.
 *                  It's the initial homepage on which the user can login again--&gt;
 *                 /initialHomePage.jsp
 *             &lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class LogoutAction extends BaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = LogoutAction.class.getName();

    /**
     * <p>
     * The Log object used for logging. LegalValue: It's a constant. So it can only be that constant value
     * Initialization and Mutability: It is final and won't change once it is initialized as part of variable
     * declaration to: LogManager.getLog(LogoutAction.class.toString()). Usage: It is used throughout the
     * class wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(LogoutAction.class.toString());

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public LogoutAction() {
        // Empty
    }

    /**
     * <p>
     * Execute the action logic of logout.
     * </p>
     *
     * @return SUCCESS if no error occurs
     * @throws BasicActionException
     *             if any error occurs
     */
    @Override
    public String execute() throws BasicActionException {
        // Log the entry
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(LOG, signature, null, null);

        try {
            // Get the authentication from session:
            WebAuthentication authentication = getWebAuthenticationFromSession();
            // Get the user:
            User u = authentication.getActiveUser();
            // Audit this action:
            audit(u.getUserName(), null, null);
            // Logs out the user:
            authentication.logout();
            // Get the http session:
            HttpSession httpSession = this.getServletRequest().getSession();
            // Invalidate the session:
            httpSession.invalidate();

            // Log the exit
            LoggingWrapperUtility.logExit(LOG, signature, new Object[] {SUCCESS});

            return SUCCESS;
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "IllegalArgumentException occurs while auditing", e));
        } catch (IllegalStateException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "IllegalStateException occurs while accessing httpSession", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "RuntimeException occurs while logging out", e));
        }
    }
}
