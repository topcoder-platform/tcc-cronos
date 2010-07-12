/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */



package com.cronos.onlinereview.login;

import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.AuthenticationFactory;
import com.topcoder.security.authenticationfactory.Authenticator;
import com.topcoder.security.authenticationfactory.InvalidPrincipalException;
import com.topcoder.security.authenticationfactory.MissingPrincipalKeyException;
import com.topcoder.security.authenticationfactory.Principal;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <code>LoginActions</code> class defines login and logout actions for Online Review Login component.
 * <p>
 * <em>Login</em> action is responsible for logging in the user to the application, and <em>Logout</em> action is
 * responsible for logging out the user.
 * </p>
 * <p>
 * NOTE: If logger is configured, it will be employed to log any thrown exception stack trace.
 * </p>
 * <p>
 * Changes in 1.1: Added support for "Remember me" checkbox and usage of AuthCookieManager.
 * </p>
 * <p>
 * <b>Thread Safety:</b>This class is thread safe since it does not contain any mutable inner states.
 * </p>
 *
 * @author woodjhon, maone, saarixx, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class LoginActions extends DispatchAction {

    /**
     * Represents the default namespace used in constructor.
     */
    private static final String DEFAULT_NAMESPACE = LoginActions.class.getName();

    /**
     * The manager used by this class for managing the authentication cookies. Is initialized in the constructor and
     * never changed after that. Cannot be null. Is used in login() and logout().
     *
     * @since 1.1
     */
    private AuthCookieManager authCookieManager;

    /**
     * The <code>AuthResponseParser</code> to set/unset the login state for the user.
     * <p>
     * It's set in the constructor, non-null.
     * </p>
     */
    private final AuthResponseParser authResponseParser;

    /**
     * The <code>Authenticator</code> used to authenticate user in the login method.
     * <p>
     * It's set in the constructor, non-null.
     * </p>
     */
    private final Authenticator authenticator;

    /**
     * The <code>Log</code> instance used to log the exception caught in the login/logout methods.
     * <p>
     * It's set in the constructor, optional, possibly null.
     * </p>
     */
    private final Log logger;

    /**
     * Create <code>LoginActions</code> from default namespace. The default namespace is the fully qualified class name
     * of this action.
     * <p>
     * <em>authenticator_name</em> property will be used to retrieve <code>Authenticator</code> instance from
     * authenticator factory. And <code>AuthResponseParser</code> will be created from the
     * <em>auth_response_parser.class</em> and <em>auth_response_parser.namespace</em> property. And if
     * <em>logger_name</em> is configured, it will be used to get <code>Log</code> instance form logging factory. For
     * more details, please see the Component Specification.
     * </p>
     * <p>
     * Changes in 1.1: Added steps for instantiating AuthCookieManager instance
     * </p>
     *
     * @throws ConfigurationException
     *             if failed to create the action from the default namespace
     * @version 1.1
     * @since 1.0
     */
    public LoginActions() throws ConfigurationException {

        // create authResponseParser instance
        String className = Util.getRequiredPropertyString(DEFAULT_NAMESPACE, "auth_response_parser.class");
        String namespace = Util.getOptionalPropertyString(DEFAULT_NAMESPACE, "auth_response_parser.namespace");

        if ((namespace == null) || (namespace.trim().length() == 0)) {
            authResponseParser = (AuthResponseParser) Util.creatObject(className, new Class[] {}, new Object[] {},
                    AuthResponseParser.class);
        } else {
            authResponseParser = (AuthResponseParser) Util.creatObject(className, new Class[] { String.class },
                    new Object[] { namespace }, AuthResponseParser.class);
        }

        // instantiate the cookie manager instance via reflection
        className = Util.getRequiredPropertyString(DEFAULT_NAMESPACE, "auth_cookie_manager.class");
        namespace = Util.getOptionalPropertyString(DEFAULT_NAMESPACE, "auth_cookie_manager.namespace");

        if ((namespace == null) || (namespace.trim().length() == 0)) {
            this.authCookieManager = (AuthCookieManager) Util.creatObject(className, new Class[] {}, new Object[] {},
                    AuthCookieManager.class);
        } else {
            this.authCookieManager = (AuthCookieManager) Util.creatObject(className, new Class[] { String.class },
                    new Object[] { namespace }, AuthCookieManager.class);
        }

        // create the authenticator instance
        String authenticatorName = Util.getRequiredPropertyString(DEFAULT_NAMESPACE, "authenticator_name");

        try {
            authenticator = AuthenticationFactory.getInstance().getAuthenticator(authenticatorName);
        } catch (com.topcoder.security.authenticationfactory.ConfigurationException e) {
            throw new ConfigurationException("Cannot create authenticator.", e);
        }

        if (authenticator == null) {
            throw new ConfigurationException("Cannot create authenticator: " + authenticatorName);
        }

        // create the logger instance (if exists)
        String loggerName = Util.getOptionalPropertyString(DEFAULT_NAMESPACE, "logger_name");

        if ((loggerName != null) && (loggerName.trim().length() != 0)) {
            logger = LogFactory.getLog(loggerName);
        } else {
            logger = null;
        }
    }

    /**
     * This method tries to log in the user.
     * <p>
     * It will first use the authenticator to authenticate the user name and password extracted from the form. If
     * authentication succeeded, it will set the login state and forward the request and response to
     * <code>success</code> page. If failed, it will forward the request and response to the <code>failure</code> page.
     * <p>
     * NOTE: If any exception occurs in it, resource key will be added to the ActionMessages, which will be saved. If
     * logger is configured, it will be employed to log any thrown exception stacktrace.
     * </p>
     * <p>
     * Added support for "rememberMe" property and authCookieManager
     * </p>
     *
     * @return the forward result based on the authentication response
     * @param mapping
     *            action mapping.
     * @param form
     *            action form.
     * @param request
     *            the http request
     * @param response
     *            the http response
     * @throws MissingPrincipalKeyException
     *             if any principal key is missing.
     * @throws InvalidPrincipalException
     *             if principal is not accepted by authenticator.
     * @throws AuthenticateException
     *             if any other error occurred during authentication.
     * @throws AuthResponseParsingException
     *             if authResponseParser failed to set the login state.
     * @throws AuthCookieManagementException
     *             if some error occurred when setting the authentication cookie
     * @version 1.1
     * @since 1.0
     */
    public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                               HttpServletResponse response)
            throws AuthenticateException, AuthResponseParsingException, AuthCookieManagementException {
        try {

            // retrieve username and password from the form
            DynaValidatorForm validatorForm = (DynaValidatorForm) form;
            String userName = getFormProperty(validatorForm, Util.USERNAME);
            String password = getFormProperty(validatorForm, Util.PASSWORD);
            String forwardUrl = request.getParameter(Util.FOWARD_URL);

            // create a principal with user name and password
            Principal principal = new Principal("dummy");

            principal.addMapping(Util.USERNAME, userName);
            principal.addMapping(Util.PASSWORD, password);

            // authenticate the user
            Response authResponse = authenticator.authenticate(principal);

            authResponseParser.setLoginState(principal, authResponse, request, response);

            if (authResponse.isSuccessful()) {
                // Save the cookie with user details if Remember Me feature is requested. But if impersonated login
                // is used then do not persist such cookie and erase existing one
                boolean isImpersonationUsed = (userName.indexOf("/") >= 0);
                if (isImpersonationUsed) {
                    this.authCookieManager.removeAuthCookie(request, response);
                } else {
                    // Extract the value of "Remember me" flag from the form
                    if ("on".equals(validatorForm.get("rememberMe"))) {
                        // Set auth cookie with cookie manager
                        this.authCookieManager.setAuthCookie(principal, request, response);
                    }
                }

                request.getSession().removeAttribute("redirectBackUrl");

                if ((forwardUrl != null) && (forwardUrl.trim().length() != 0)) {
                    return constructNewForward(mapping.findForward("success"), forwardUrl);
                } else {
                    return mapping.findForward("success");
                }
            } else {

                // Actually in case of wrong login attempt, an attribute should be place into the request
                // This is to let use the same page in the application for the first login
                // and for every subsequent incorrect login attempt
                return mapping.findForward("failure");
            }
        } catch (MissingPrincipalKeyException e) {
            recordException(e);

            throw e;
        } catch (InvalidPrincipalException e) {
            recordException(e);

            throw e;
        } catch (AuthenticateException e) {
            recordException(e);

            throw e;
        } catch (AuthResponseParsingException e) {
            recordException(e);

            throw e;
        } catch (AuthCookieManagementException e) {
            recordException(e);

            throw e;
        }
    }

    /**
     * This static method creates a new action forward based on the forward specified by <code>basedOn</code> parameter
     * (only module is considered). The newly created forward will be pointing to the URL specified by
     * <code>forwardUrl</code> parameter, have &quotsomeNewForward;&quot; name, and always use 'redirect' technique
     * (i.e. the browser will display the path of this forward in its Address field).
     *
     * @return a newly created action forward that points to URL specified by <code>forwardUrl</code> parameter.
     * @param basedOn
     *            a forward to base newly created forward on.
     * @param forwardUrl
     *            an URL string for the new forward.
     */
    private static ActionForward constructNewForward(ActionForward basedOn, String forwardUrl) {

        // Create new ActionForward object
        ActionForward clonedForward = new ActionForward();

        // Clone (copy) the fields
        clonedForward.setModule(basedOn.getModule());
        clonedForward.setName("someNewForward");
        clonedForward.setRedirect(true);

        // Append string argument
        clonedForward.setPath(forwardUrl);

        // Return the newly-created action forward
        return clonedForward;
    }

    /**
     * Get property value for given <code>key</code> from <code>DynaValidatorForm</code>.
     *
     * @param form
     *            the form to retrieve value from
     * @param key
     *            the key associated with the value
     * @return the String value
     * @throws MissingPrincipalKeyException
     *             if there no value associated with the key
     * @throws InvalidPrincipalException
     *             if the value is not type of String
     */
    private static String getFormProperty(DynaValidatorForm form, String key) {
        try {
            Object value = form.get(key);

            if (!(value instanceof String)) {
                throw new InvalidPrincipalException(key + " should be String.");
            }

            return (String) value;
        } catch (IllegalArgumentException e) {
            throw new MissingPrincipalKeyException(key);
        }
    }

    /**
     * This method tries to log the user out.
     * <p>
     * It will use <code>AuthResponseParser</code> to unset the login state. If the operation succeeds, it will forward
     * to <em>logout</em>.
     * </p>
     * <p>
     * Added support for authCookieManager.
     * </p>
     *
     * @return forward to <code>logout</code>.
     * @param mapping
     *            action mapping.
     * @param form
     *            action form.
     * @param request
     *            the http request
     * @param response
     *            the http response
     * @throws AuthResponseParsingException
     *             if authResponseParser failed to unset the login state.
     * @throws AuthCookieManagementException
     *             if some error occurred when removing the authentication cookie
     * @version 1.1
     * @since 1.0
     */
    public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response)
            throws AuthResponseParsingException, AuthCookieManagementException {
        try {

            // Remove auth cookie with the manager
            authCookieManager.removeAuthCookie(request, response);

            // unset login state
            authResponseParser.unsetLoginState(request, response);

            // forward to logout
            return mapping.findForward("logout");
        } catch (AuthResponseParsingException e) {
            recordException(e);

            throw e;
        } catch (AuthCookieManagementException e) {
            recordException(e);

            throw e;
        }
    }

    /**
     * Record the exception to <code>logger</code>(if exists).
     *
     * @param e
     *            the exception instance
     */
    private void recordException(Exception e) {
        // record to logger
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        e.printStackTrace(out);
        if (logger != null) {
            logger.log(Level.ERROR, sw.getBuffer());
        }
    }
}
