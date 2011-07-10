/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.security.SimpleResource;
import com.topcoder.shared.security.User;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.HttpObjectFactory;
import com.topcoder.web.common.SecurityHelper;
import com.topcoder.web.common.SessionInfo;
import com.topcoder.web.common.TCRequest;
import com.topcoder.web.common.security.TCSAuthorization;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;

/**
 * <p>
 * This interceptor is responsible for checking if a user is authorized to access the requested URI. If the
 * user is anonymous and is not authorized, he will be redirected to the login page. Otherwise if the user is
 * not authorized, the user will be redirected to an authorization error page. It will try to get a
 * WebAuthentication from session, and if it's not present, it will create a BasicAuthentication and put it
 * into session. It will also put a SessionInfo into session.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because it's mutable. However, dedicated instance of struts
 * interceptor will be created by the struts framework to process the user request, so the struts interceptor
 * don't need to be thread-safe.
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class AuthorizationInterceptor extends BaseInterceptor {
    /**
     * <p>
     * The return value used when the user is not authorized. It's a constant. So it can only be that constant
     * value It is final and won't change once it is initialized as part of variable declaration to:
     * "authorizationError". It is used in intercept().
     * </p>
     */
    public static final String AUTHORIZATION_ERROR = "authorizationError";

    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = AuthorizationInterceptor.class.getName();

    /**
     * <p>
     * The Log object used for logging. It's a constant. So it can only be that constant value It is final and
     * won't change once it is initialized as part of variable declaration to:
     * LogManager.getLog(AuthorizationInterceptor.class.toString()). It is used throughout the class wherever
     * logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(AuthorizationInterceptor.class.toString());

    /**
     * <p>
     * The key that is used for setting SessionInfo into session. It is set through setter and doesn't have a
     * getter. It cannot be null or empty. (Note that the above statement applies only after the setter has
     * been called as part of the IoC initialization. This field's value has no restriction before the IoC
     * initialization) It can be changed after it is initialized as part of variable declaration to:
     * "sessionInfo". It is used in intercept(), setSessionInfoKey(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private String sessionInfoKey = "sessionInfo";

    /**
     * <p>
     * The session key for WebAuthentication that is put into session. It is set through setter and doesn't
     * have a getter. It cannot be null or empty. (Note that the above statement applies only after the setter
     * has been called as part of the IoC initialization. This field's value has no restriction before the IoC
     * initialization) It can be changed after it is initialized as part of variable declaration to:
     * "webAuthentication". It is used in intercept(), setWebAuthenticationSessionKey(). Its value legality is
     * checked in checkConfiguration() method.
     * </p>
     */
    private String webAuthenticationSessionKey = "webAuthentication";

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public AuthorizationInterceptor() {
        // Empty
    }

    /**
     * <p>
     * Perform authorization check.
     * </p>
     *
     * @param actionInvocation
     *            the action invocation to intercept
     * @return whatever the downstream action returns if the user is authorized, or LOGIN if the user is not
     *         authorized but is anonymous, or AUTHORIZATION_ERROR if the user is not authorized and is not
     *         anonymous
     * @throws IllegalArgumentException
     *             if actionInvocation is null
     * @throws AuthorizationInterceptorException
     *             if any error occurs
     */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws AuthorizationInterceptorException {
        // Log the entry
        final String signature = CLASS_NAME + ".intercept(ActionInvocation)";
        LoggingWrapperUtility.logEntrance(LOG, signature, new String[] {"actionInvocation"},
            new Object[] {actionInvocation});

        try {
            ParameterCheckUtility.checkNotNull(actionInvocation, "actionInvocation");

            HttpServletRequest servletRequest = ServletActionContext.getRequest();
            // Set the character encoding:
            servletRequest.setCharacterEncoding("utf-8");
            TCRequest tcRequest = HttpObjectFactory.createRequest(servletRequest);
            // Get the session from request:
            HttpSession session = servletRequest.getSession();

            WebAuthentication authentication;
            if (session.getAttribute(webAuthenticationSessionKey) != null) {
                authentication = (WebAuthentication) session.getAttribute(webAuthenticationSessionKey);
            } else {
                // Create a new BasicAuthentication:
                authentication = this.createWebAuthentication();
                // Set the BasicAuthentication into session:
                session.setAttribute(webAuthenticationSessionKey, authentication);
            }

            // Get the user:
            User user = authentication.getActiveUser();
            // Get the TCSubject:
            TCSubject tcSubject = SecurityHelper.getUserSubject((user.getId()));
            // Create a SessionInfo:
            SessionInfo info = new SessionInfo(tcRequest, authentication, tcSubject.getPrincipals());
            // Set the SessionInfo to tcRequest:
            tcRequest.setAttribute(sessionInfoKey, info);
            // Create a resource for the URI:
            SimpleResource resource = new SimpleResource(servletRequest.getRequestURI());
            // Create a TCSAuthorization:
            TCSAuthorization authorization = new TCSAuthorization(tcSubject);
            // If authorization.hasPermission(resource) then
            if (authorization.hasPermission(resource)) {
                // Invoke the downstream action:
                String result = actionInvocation.invoke();

                // Log the exit
                LoggingWrapperUtility.logExit(LOG, signature, new Object[] {result});

                return result;
            } else if (user.isAnonymous()) {
                // Log the exit
                LoggingWrapperUtility.logExit(LOG, signature, new Object[] {Action.LOGIN});

                return Action.LOGIN;
            } else {
                // Log the exit
                LoggingWrapperUtility.logExit(LOG, signature, new Object[] {AUTHORIZATION_ERROR});

                return AUTHORIZATION_ERROR;
            }
        } catch (IllegalArgumentException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, e);
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new AuthorizationInterceptorException(
                "Exception occurs", e));
        }
    }

    /**
     * <p>
     * Setter method for the sessionInfoKey, simply set the value to the namesake field.
     * </p>
     *
     * @param sessionInfoKey
     *            The key that is used for setting SessionInfo into session
     */
    public void setSessionInfoKey(String sessionInfoKey) {
        this.sessionInfoKey = sessionInfoKey;
    }

    /**
     * <p>
     * Setter method for the webAuthenticationSessionKey, simply set the value to the namesake field.
     * </p>
     *
     * @param webAuthenticationSessionKey
     *            The session key for WebAuthentication that is put into session
     */
    public void setWebAuthenticationSessionKey(String webAuthenticationSessionKey) {
        this.webAuthenticationSessionKey = webAuthenticationSessionKey;
    }

    /**
     * <p>
     * This method is called right after the dependency of this class is fully injected. It checks if the
     * injected values are valid.
     * </p>
     *
     * @throws BasicStrutsActionsConfigurationException
     *             if any of the injected values is invalid.
     *
     */
    public void checkConfiguration() {
        // Check the value of the following fields according to their legal value specification in the field
        // variable documentation:
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(sessionInfoKey, "sessionInfoKey",
            BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(webAuthenticationSessionKey,
            "webAuthenticationSessionKey", BasicStrutsActionsConfigurationException.class);
    }
}
