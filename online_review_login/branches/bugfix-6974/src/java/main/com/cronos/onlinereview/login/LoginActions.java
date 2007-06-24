/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.login;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.DynaValidatorForm;

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

/**
 * <code>LoginActions</code> class defines login and logout actions for Online Review
 * Login component.
 * <p>
 * <em>Login</em> action is responsible for logging in the user to the application, and
 * <em>Logout</em> action is responsible for logging out the user.
 * </p>
 * <p>
 * NOTE: If logger is configured, it will be employed to log any thrown exception stacktrace.
 * <p>
 * This class is thread safe since it does not contain any mutable inner states.
 * </p>
 *
 * @author woodjhon, maone
 * @version 1.0
 */
public class LoginActions extends DispatchAction {
    /**
     * Represents the default namespace used in constructor.
     */
    private static final String DEFAULT_NAMESPACE = LoginActions.class.getName();

    /**
     * The <code>Authenticator</code> used to authenticate user in the login method.
     * <p>
     * It's set in the constructor, non-null.
     * </p>
     */
    private final Authenticator authenticator;

    /**
     * The <code>AuthResponseParser</code> to set/unset the login state for the user.
     * <p>
     * It's set in the constructor, non-null.
     * </p>
     */
    private final AuthResponseParser authResponseParser;

    /**
     * The <code>Log</code> instance used to log the exception caught in the
     * login/logout methods.
     * <p>
     * It's set in the constructor, optional, possibly null.
     * </p>
     */
    private final Log logger;

    /**
     * Create <code>LoginActions</code> from default namespace. The default namespace is
     * the fully qualified class name of this action.
     * <p>
     * <em>authenticator_name</em> property will be used to retrieve
     * <code>Authenticator</code> instance from authenticator factory. And
     * <code>AuthResponseParser</code> will be created from the
     * <em>auth_response_parser.class</em> and <em>auth_response_parser.namespace</em>
     * property. And if <em>logger_name</em> is configured, it will be used to get
     * <code>Log</code> instance form logging factory. For more details, please see the
     * Component Specification.
     * </p>
     *
     * @throws ConfigurationException
     *             if failed to create the action from the default namespace
     */
    public LoginActions() throws ConfigurationException {
        // create authResponseParser instance
        authResponseParser = createAuthResponseParser(DEFAULT_NAMESPACE);

        // create the authenticator instance
        String authenticatorName = Util.getRequiredPropertyString(DEFAULT_NAMESPACE,
                "authenticator_name");
        try {
            authenticator = AuthenticationFactory.getInstance().getAuthenticator(
                    authenticatorName);
        } catch (com.topcoder.security.authenticationfactory.ConfigurationException e) {
            throw new ConfigurationException("Cannot create authenticator.", e);
        }
        if (authenticator == null) {
            throw new ConfigurationException("Cannot create authenticator: " + authenticatorName);
        }

        // create the logger instance (if exists)
        String loggerName = Util.getOptionalPropertyString(DEFAULT_NAMESPACE,
                "logger_name");
        if (loggerName != null && loggerName.trim().length() != 0) {
            logger = LogFactory.getLog(loggerName);
        } else {
            logger = null;
        }
    }

    /**
     * Create a <code>AuthResponseParser</code> instance from the <code>namespace</code>.
     * <p>
     * The class name will be retrieved from <em>auth_response_parser.class</em>
     * property. And if <em>auth_response_parser.namespace</em> exists, it will be
     * passed to the constructor. Otherwise, the default constructor will be used.
     * </p>
     *
     * @param namespace
     *            the configuration namespace
     * @return the created AuthResponseParser instance
     * @throws ConfigurationException
     *             if any error occurs.
     */
    private AuthResponseParser createAuthResponseParser(String namespace)
        throws ConfigurationException {
        try {
            // get class name from configuration
            String className = Util.getRequiredPropertyString(namespace,
                    "auth_response_parser.class");
            Class authClass = Class.forName(className);

            // get namespace from configuration
            String parserNamespace = Util.getOptionalPropertyString(namespace,
                    "auth_response_parser.namespace");

            // instantiate the parser instance via reflection
            Object parserObj = null;
            if (parserNamespace == null || parserNamespace.trim().length() == 0) {
                parserObj = authClass.newInstance();
            } else {
                Constructor authCtor = authClass
                        .getConstructor(new Class[] {String.class});
                parserObj = authCtor.newInstance(new Object[] {parserNamespace});
            }

            // validate and return the parser instance
            if (!(parserObj instanceof AuthResponseParser)) {
                throw new ConfigurationException("The created parser is not instanceof "
                        + AuthResponseParser.class);
            } else {
                return (AuthResponseParser) parserObj;
            }
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException(
                    "Invalid class name for AuthResponseParser.", e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException(
                    "No appropriate constructor for AuthResponseParser.", e);
        } catch (InstantiationException e) {
            throw new ConfigurationException("Failed to create AuthResponseParser.", e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Failed to create AuthResponseParser.", e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("Failed to create AuthResponseParser.", e);
        }
    }

    /**
     * This method tries to log in the user.
     * <p>
     * It will first use the authenticator to authenticate the user name and password
     * extracted from the form. If authentication succeeded, it will set the login state
     * and forward the request and response to <code>success</code> page. If failed, it
     * will forward the request and response to the <code>failure</code> page.
     * <p>
     * NOTE: If any exception occurs in it, resource key will be added to the
     * ActionMessages, which will be saved. If logger is configured, it will be employed
     * to log any thrown exception stacktrace.
     * </p>
     *
     *
     * @return the forward result based on the authenticaiton response
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
     */
    public ActionForward login(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
        throws AuthenticateException, AuthResponseParsingException {
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
                request.getSession().removeAttribute("redirectBackUrl");
                if (forwardUrl != null && forwardUrl.trim().length() != 0) {
                    return constructNewForward(mapping.findForward("success"), forwardUrl);
                } else {
                    return mapping.findForward("success");
                }
            } else {
            	if ( logger != null) {
            		logger.log(Level.WARN, "Login failure with username:" + userName);
            	}
                // TODO: Actually in case of wrong login attempt, an attribute should be place into the request
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
        }
    }

    /**
     * This static method creates a new action forward based on the foward specified by
     * <code>basedOn</code> parameter (only module is considered). The newly created forward will
     * be pointing to the URL specified by <code>forwardUrl</code> parameter, have
     * &quotsomeNewForward;&quot; name, and always use 'redirect' thechique (i.e. the browser will
     * display the path of this forward in its Address field).
     *
     * @return a newly created action forward that points to URL specified by
     *         <code>forwardUrl</code> parameter.
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
     *
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
     * It will use <code>AuthResponseParser</code> to unset the login state. If the
     * operation succeeds, it will forward to <em>logout</em>.
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
     */
    public ActionForward logout(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
        throws AuthResponseParsingException {
        try {
            // unset login state
            authResponseParser.unsetLoginState(request, response);

            // forward to logout
            return mapping.findForward("logout");
        } catch (AuthResponseParsingException e) {
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
        if (logger != null) {
            logger.log(Level.ERROR, e.getStackTrace());
        }
    }
}
