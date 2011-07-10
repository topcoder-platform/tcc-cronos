/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.basic;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.login.LoginRemote;
import com.topcoder.shared.security.LoginException;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.util.DBMS;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Email;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.BasicStrutsActionsConfigurationException;
import com.topcoder.web.reg.Constants;

/**
 * <p>
 * This action handles user login. It logs the user in using EJB, ensures the user's status is active, checks
 * if the user email is activated, uses BasicAuthentication to set remember-me cookies, and finally redirect
 * the user to specific result depending on whether it's the first time the user logs in, and if the user
 * tries to go somewhere before login.
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
 * &lt;action name="login" class="loginAction"&gt;
 *             &lt;result name="success"&gt;
 *                 &lt;!--this is up to configuration.
 *                 It's the page that the user is redirected to if the user visits login.jsp directly--&gt;
 *                 /mainPage.jsp
 *             &lt;/result&gt;
 *             &lt;result name="input"&gt;
 *                 /login.jsp
 *             &lt;/result&gt;
 *             &lt;result name="emailActivation"&gt;
 *                 &lt;!--this is up to configuration.
 *                  It's the page that allows the user to activate the email--&gt;
 *                 /emailActivation.jsp
 *             &lt;/result&gt;
 *             &lt;result name="firstTimeLogin"&gt;
 *                 &lt;!--this is up to configuration.
 *                  It's the page that the user will see if the user logins for the first time--&gt;
 *                 /firstTimeLogin.jsp
 *             &lt;/result&gt;
 *         &lt;/action&gt;
 * </pre>
 *
 * </p>
 *
 * @author mekanizumu, TCSDEVELOPER
 * @version 1.0
 */
public class LoginAction extends BaseUserDAOAwareAction implements ServletResponseAware {
    /**
     * <p>
     * The return value used when the user email is not activated. It's a constant. So it can only be that
     * constant value It is final and won't change once it is initialized as part of variable declaration to:
     * "emailActivation". It is used in execute().
     * </p>
     */
    public static final String EMAIL_ACTIVATION = "emailActivation";

    /**
     * <p>
     * The return value used when the user logins for the first time. It's a constant. So it can only be that
     * constant value It is final and won't change once it is initialized as part of variable declaration to:
     * "firstTimeLogin". It is used in execute().
     * </p>
     */
    public static final String FIRST_TIME_LOGIN = "firstTimeLogin";

    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = LoginAction.class.getName();

    /**
     * <p>
     * The Log object used for logging. It's a constant. So it can only be that constant value It is final and
     * won't change once it is initialized as part of variable declaration to:
     * LogManager.getLog(LoginAction.class.toString()). It is used throughout the class wherever logging is
     * needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(LoginAction.class.toString());

    /**
     * <p>
     * The status code representing active email. It is set through setter and doesn't have a getter. It can
     * be any value. It can be changed after it is initialized as part of variable declaration to: 1. It is
     * used in setActiveEmailStatus(), execute().
     * </p>
     */
    private int activeEmailStatus = 1;

    /**
     * <p>
     * The EJB used for logging in the user. It is set through setter and doesn't have a getter. It cannot be
     * null. (Note that the above statement applies only after the setter has been called as part of the IoC
     * initialization. This field's value has no restriction before the IoC initialization) It does not need
     * to be initialized when the instance is created. It is used in execute(), setLoginRemote(). Its value
     * legality is checked in checkConfiguration() method.
     * </p>
     */
    private LoginRemote loginRemote;

    /**
     * <p>
     * The name of the http parameter that represents the URI the user last requested. It is set through
     * setter and doesn't have a getter. It cannot be null or empty. (Note that the above statement applies
     * only after the setter has been called as part of the IoC initialization. This field's value has no
     * restriction before the IoC initialization) It does not need to be initialized when the instance is
     * created. It is used in execute(), setLastRequestedURIParameterName(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private String lastRequestedURIParameterName;

    /**
     * <p>
     * The http servlet response. This is injected by Struts. It is set through setter and doesn't have a
     * getter. It cannot be null. It does not need to be initialized when the instance is created. It is used
     * in execute(), setServletResponse().
     * </p>
     */
    private HttpServletResponse servletResponse;

    /**
     * <p>
     * The username inputted by the user. It has both getter and setter. It cannot be null or empty. (Note that
     * the above statement applies only after the field has passed Struts validation. This field's value has
     * no restriction before then.) It does not need to be initialized when the instance is created. It is
     * used in execute(), getUsername(), setUsername().
     * </p>
     */
    private String username;

    /**
     * <p>
     * The password inputted by the user. It has both getter and setter. It cannot be null or empty. (Note that
     * the above statement applies only after the field has passed Struts validation. This field's value has
     * no restriction before then.) It does not need to be initialized when the instance is created. It is
     * used in execute(), getPassword(), setPassword().
     * </p>
     */
    private String password;

    /**
     * <p>
     * A flag that if true, means the user wants to be remembered. It has both getter and setter. It does not
     * need to be initialized when the instance is created. It is used in execute(), setRememberMe(),
     * isRememberMe().
     * </p>
     */
    private boolean rememberMe;

    /**
     * <p>
     * This is the default constructor for the class.
     * </p>
     */
    public LoginAction() {
        // Empty
    }

    /**
     * <p>
     * Execute the action logic of login.
     * </p>
     *
     * @return EMAIL_ACTIVATION if the user email is not activated, FIRST_TIME_LOGIN if the user logins for
     *         the first time, SUCCESS if the user directly goes to the login page; ERROR if the user account
     *         status is invalid, INPUT if the provided credentials are invalid, or null if the user is
     *         successfully redirected to the last requested page.
     * @throws BasicActionException
     *             if any error occurs
     */
    @Override
    public String execute() throws BasicActionException {
        // Log the entry
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(LOG, signature, null, null);

        try {
            // Login with the EJB (if any error is thrown from this call, or if the returned value is null,
            // call this.getActionErrors().add(getText("credential.invalid")) and then return INPUT:
            TCSubject tcSubject = null;
            try {
                tcSubject = loginRemote.login(username, password, DBMS.JTS_OLTP_DATASOURCE_NAME);
            } catch (RemoteException e) {
                LoggingWrapperUtility.logException(LOG, signature, e);
            } catch (GeneralSecurityException e) {
                LoggingWrapperUtility.logException(LOG, signature, e);
            }

            if (tcSubject == null) {
                this.addActionError(getText("credential.invalid"));

                // Log the exit
                LoggingWrapperUtility.logExit(LOG, signature, new Object[] {INPUT});

                return INPUT;
            }

            UserDAO userDAO = this.getUserDAO();
            // Find the user by id:
            User user = userDAO.find(tcSubject.getUserId());
            if (user == null) {
                throw new BasicActionException("user is not find by id:" + tcSubject.getUserId());
            }

            // Get the user status:
            char status = user.getStatus();
            // Get the authentication from session:
            WebAuthentication authentication = this.getWebAuthenticationFromSession();
            // If Arrays.binarySearch(Constants.ACTIVE_STATI, status) >= 0, which means the user status is
            // active
            if (Arrays.binarySearch(Constants.ACTIVE_STATI, status) >= 0) {
                // If user.getPrimaryEmailAddress().getStatusId() != activeEmailStatus, which means the email
                // is not activated
                Email primaryEmail = user.getPrimaryEmailAddress();
                ValidationUtility.checkNotNull(primaryEmail, "The primaryEmail of user with id:"
                    + user.getId(), BasicActionException.class);
                if (primaryEmail.getStatusId() != activeEmailStatus) {
                    // Log the user out:
                    authentication.logout(); // Redirect to email activation page:

                    // Log the exit
                    LoggingWrapperUtility.logExit(LOG, signature, new Object[] {EMAIL_ACTIVATION});

                    return EMAIL_ACTIVATION;
                }

                // Login the user:
                authentication.login(new SimpleUser(0, username, password), rememberMe);
                // This block clears the session for the case that they've been messing around as someone else
                // or as no one
                // Get the session map:
                Map<String, Object> session = this.getSession();
                session.put(Constants.USER, null);
                session.put(Constants.REG_TYPES, null);

                AuditDAO auditDAO = getAuditDAO();
                // Check if the user logs in for the first time:
                boolean firstTimeLogin = !auditDAO.hasOperation(username, this.getOperationType());
                // Audit this action:
                audit(username, null, null);
                // If firstTimeLogin then
                if (firstTimeLogin) {
                    // Log the exit
                    LoggingWrapperUtility.logExit(LOG, signature, new Object[] {FIRST_TIME_LOGIN});

                    return FIRST_TIME_LOGIN;
                }

                // Get the last requested URI:
                String redirectURI = this.getServletRequest().getParameter(lastRequestedURIParameterName);
                if (redirectURI != null && redirectURI.length() > 0) {
                    // Redirect to the last requested URI:
                    servletResponse.sendRedirect(redirectURI);
                } else {
                    // Log the exit
                    LoggingWrapperUtility.logExit(LOG, signature, new Object[] {SUCCESS});

                    return SUCCESS;
                }

                // Log the exit
                LoggingWrapperUtility.logExit(LOG, signature, null);

                return null;
            }

            // Audit this action:
            audit(username, null, null);
            // Logs the user out:
            authentication.logout();
            this.addActionError(getText("account.invalid"));

            // Log the exit
            LoggingWrapperUtility.logExit(LOG, signature, new Object[] {ERROR});

            return ERROR;
        } catch (BasicActionException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, e);
        } catch (LoginException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "LoginException occurs while logging in", e));
        } catch (IOException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "IOException occurs while redirecting", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(LOG, signature, new BasicActionException(
                "RuntimeException occurs while logging in", e));
        }
    }

    /**
     * <p>
     * Getter method for username, simply return the value of the namesake field.
     * </p>
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>
     * Setter method for the username, simply set the value to the namesake field.
     * </p>
     *
     * @param username
     *            The username inputted by the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>
     * Getter method for password, simply return the value of the namesake field.
     * </p>
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * Setter method for the password, simply set the value to the namesake field.
     * </p>
     *
     * @param password
     *            The password inputted by the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>
     * Getter method for rememberMe, simply return the value of the namesake field.
     * </p>
     *
     * @return the rememberMe
     */
    public boolean isRememberMe() {
        return rememberMe;
    }

    /**
     * <p>
     * Setter method for the rememberMe, simply set the value to the namesake field.
     * </p>
     *
     * @param rememberMe
     *            A flag that if true, means the user wants to be remembered
     */
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * <p>
     * Setter method for the activeEmailStatus, simply set the value to the namesake field.
     * </p>
     *
     * @param activeEmailStatus
     *            The status code representing active email
     */
    public void setActiveEmailStatus(int activeEmailStatus) {
        this.activeEmailStatus = activeEmailStatus;
    }

    /**
     * <p>
     * Setter method for the loginRemote, simply set the value to the namesake field.
     * </p>
     *
     * @param loginRemote
     *            The EJB used for logging in the user
     */
    public void setLoginRemote(LoginRemote loginRemote) {
        this.loginRemote = loginRemote;
    }

    /**
     * <p>
     * Setter method for the lastRequestedURIParameterName, simply set the value to the namesake field.
     * </p>
     *
     * @param lastRequestedURIParameterName
     *            The name of the http parameter that represents the URI the user last requested
     */
    public void setLastRequestedURIParameterName(String lastRequestedURIParameterName) {
        this.lastRequestedURIParameterName = lastRequestedURIParameterName;
    }

    /**
     * <p>
     * Setter method for the servletResponse, simply set the value to the namesake field.
     * </p>
     *
     * @param servletResponse
     *            The http servlet response. This is injected by Struts
     */
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    /**
     * <p>
     * This method is called right after the dependency of this class is fully injected. It checks if the
     * injected values are valid.
     * </p>
     *
     * @throws BasicStrutsActionsConfigurationException
     *             if any of the injected values is invalid.
     */
    @Override
    public void checkConfiguration() {
        super.checkConfiguration();
        // Check the value of the following fields according to their legal value specification in the field
        // variable documentation:
        ValidationUtility.checkNotNull(loginRemote, "loginRemote",
            BasicStrutsActionsConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(lastRequestedURIParameterName,
            "lastRequestedURIParameterName", BasicStrutsActionsConfigurationException.class);
    }
}
