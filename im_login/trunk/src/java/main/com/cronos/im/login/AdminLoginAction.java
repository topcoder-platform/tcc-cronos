/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.NoSuchElementException;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.authorization.Action;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.AuthorizationPersistenceException;
import com.topcoder.security.authorization.ConfigurationException;
import com.topcoder.security.authorization.Principal;
import com.topcoder.util.log.Level;

/**
 * <p>
 * This class provides administrator login support. It is an extension of <code>LoginAction</code>, which
 * defines the common properties needed when doing login.
 * </p>
 * <p>
 * There are two possible forwards after the execution of this action, they are all specified by configurable
 * properties:
 * <ol>
 * <li>loginSucceedForward (default: "loginSucceed"): if the login succeeded.</li>
 * <li>loginFailFoward (default: "loginFail"): if the login failed.</li>
 * </ol>
 * After successful login, an authorization principal object will be stored to the session under a configurable key
 * specified by the field userProfileKey.
 * </p>
 * <p>
 * Thread safety: This class contains mutable attributes, but are accessed in a thread safe manner.
 * </p>
 *
 * @author kakarotto, tyrian
 * @version 1.0
 */
public class AdminLoginAction extends LoginAction {

    /**
     * <p>
     * Represents the action name used for logging.
     * </p>
     */
    private static final String ACTION_NAME = "admin login";

    /**
     * <p>
     * Represents the action used for authorization.
     * </p>
     * <p>
     * It is initialized in the <code>{@link #getAction()}</code> method. It cannot be <code>null</code>.
     * </p>
     */
    private Action action;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public AdminLoginAction() {
    }

    /**
     * <p>
     * Performs the administrator login, first authenticate the user using security manager, then check the
     * permission using authorization manager. If login successfully, store the authorization principal in session.
     * If login succeeded, returns a forward specified by field loginSucceed; otherwise returns a forward specified
     * by field loginFail.
     * </p>
     *
     * @param mapping
     *            The ActionMapping used to select this instance
     * @param form
     *            The optional ActionForm bean for this request (if any)
     * @param request
     *            the http servlet request
     * @param response
     *            the http servlet response
     * @return If login succeeded, return a forward whose mapping name is <code>loginSucceedForwardName</code> If
     *         login failed, return a forward whose mapping name is <code>loginFailForwardName</code>
     * @throws CreateException
     *             if the creation of <code>LoginRemote</code> fails
     * @throws RemoteException
     *             if error occurs when using security manager (this exception is required by EJB specification).
     * @throws ConfigurationException
     *             if can not obtain the authorization manager.
     * @throws NamingException
     *             if a naming exception is encountered when looking up EJB instances.
     * @throws AuthorizationPersistenceException
     *             if any error happens to search the action from persistence.
     * @throws NoSuchElementException
     *             if there is no <code>Principal</code> for admin in persistence.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws RemoteException, NamingException, CreateException,
            ConfigurationException, AuthorizationPersistenceException {
        String user = request.getParameter(IMLoginHelper.USER);
        String password = request.getParameter(IMLoginHelper.PASSWORD);

        // authenticate using security manager
        TCSubject subject = null;
        try {
            subject = getLoginRemote().login(user, password);
        } catch (GeneralSecurityException e) {
            doLog(user, ACTION_NAME, "", "Failed to authenticate the admin user - " + e.getMessage(), Level.WARN);
            return mapping.findForward(getLoginFailForwardName());
        }

        // Get the principal from persistence.
        AuthorizationPersistence persistence = getAuthorizationManager().getPersistence();
        Collection temp = persistence.searchPrincipals(user);
        Principal principal = (Principal) temp.iterator().next();

        // authorize the user
        if (getAuthorizationManager().authorize(principal, getAction(), getActionContext())) {
            request.getSession().setAttribute(getUserProfileKey(), principal);
            doLog(user, ACTION_NAME, "" + subject.getUserId(), "Logged in the user as admin", Level.INFO);
            return mapping.findForward(getLoginSucceedForwardName());
        } else {
            doLog(user, ACTION_NAME, "" + subject.getUserId(), "Failed to authorize the admin user", Level.WARN);
            return mapping.findForward(getLoginAuthFailForwardName());
        }
    }

    /**
     * <p>
     * Gets the <code>Action</code> for admin login.
     * </p>
     *
     * @return the Admin Login action.
     * @throws AuthorizationPersistenceException
     *             if any error happens to search the action from persistence.
     * @throws ConfigurationException
     *             if cannot construct the authorization manager.
     * @throws NoSuchElementException
     *             if there is no action element in persistence.
     */
    private synchronized Action getAction() throws AuthorizationPersistenceException, ConfigurationException {
        if (action == null) {
            Collection temp = getAuthorizationManager().getPersistence().searchActions("Admin Login");
            action = (Action) temp.iterator().next();
        }
        return action;
    }

}
