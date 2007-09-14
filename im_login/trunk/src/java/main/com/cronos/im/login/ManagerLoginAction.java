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

import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ChatUserProfilePersistenceException;
import com.topcoder.chat.user.profile.ProfileKeyManagerPersistenceException;
import com.topcoder.chat.user.profile.ProfileNotFoundException;
import com.topcoder.chat.user.profile.UnrecognizedDataSourceTypeException;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.authorization.Action;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.AuthorizationPersistenceException;
import com.topcoder.security.authorization.ConfigurationException;
import com.topcoder.security.authorization.Principal;
import com.topcoder.util.log.Level;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class provides manager login support. It is an extension of <code>LoginAction</code>, which defines the
 * common properties needed when doing login.
 * </p>
 * <p>
 * There are two possible forwards after the execution of this action, they are all specified by configurable
 * properties:
 * <ol>
 * <li>loginSucceedForward (default: "loginSucceed"): if the login succeeded.</li>
 * <li>loginFailFoward (default: "loginFail"): if the login failed.</li>
 * </ol>
 * After successful login, a chat user profile object will be stored to the session under a configurable key
 * specified by the field userProfileKey.
 * </p>
 * <p>
 * Thread safety: This class contains mutable attributes, but are accessed in a thread safe manner.
 * </p>
 *
 * @author kakarotto, tyrian
 * @version 1.0
 */
public class ManagerLoginAction extends LoginAction {
    /**
     * <p>
     * Represents the action name used for logging.
     * </p>
     */
    private static final String ACTION_NAME = "manager login";

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
    public ManagerLoginAction() {
    }

    /**
     * <p>
     * Performs the administrator login, first authenticate the user using security manager, then check the
     * permission using authorization manager. If login successfully, retrieve the chat user profile from chat user
     * profile manager and store it in session. If login succeeded, returns a forward specified by field
     * loginSucceed; otherwise returns a forward specified by field loginFail.
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
     * @return If login succeeded, return a forward whose mapping name is <code>loginSucceedForward</code> If
     *         login failed, return a forward whose mapping name is <code>loginFailForward</code>
     * @throws CreateException
     *             if the creation of <code>LoginRemote</code> fails
     * @throws RemoteException
     *             if error occurs when using security manager (this exception is required by EJB specification)
     * @throws ConfigurationException
     *             if can not obtain the authorization manager.
     * @throws NamingException
     *             if a naming exception is encountered when looking up EJB instances.
     * @throws ConfigurationException
     *             if cannot construct the authorization manager.
     * @throws AuthorizationPersistenceException
     *             if any error happens to search the action from persistence.
     * @throws UnrecognizedDataSourceTypeException
     *             if the source type given to the chat user profile is not recognized.
     * @throws ProfileKeyManagerPersistenceException
     *             if any error occurs with the profile key manager persistence.
     * @throws ChatUserProfilePersistenceException
     *             if any error occurs with chat profile persistence.
     * @throws ProfileNotFoundException
     *             if the specified user profile is not present.
     * @throws IllegalReferenceException
     *             if cannot properly match specifications given for <code>ChatUserProfileManager</code> to each
     *             other, or the properties are malformed.
     * @throws SpecificationConfigurationException
     *             if <code>ObjectFactory</code> creations fails.
     * @throws InvalidClassSpecificationException
     *             createObject method failure, if the specification is not valid and can't be used to create an
     *             object.
     * @throws ClassCastException
     *             if the object created is not of type <code>ChatUserProfileManager</code>.
     * @throws NoSuchElementException
     *             if failed to get the <code>Principal</code> from the persistence.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws RemoteException, NamingException, CreateException,
            ConfigurationException, AuthorizationPersistenceException, InvalidClassSpecificationException,
            SpecificationConfigurationException, IllegalReferenceException, ProfileNotFoundException,
            ChatUserProfilePersistenceException, ProfileKeyManagerPersistenceException,
            UnrecognizedDataSourceTypeException {
        String user = request.getParameter(IMLoginHelper.USER);
        String password = request.getParameter(IMLoginHelper.PASSWORD);
        String category = request.getParameter(IMLoginHelper.CATEGORY);

        // authenticate using security manager
        TCSubject subject = null;
        try {
            subject = getLoginRemote().login(user, password);
        } catch (GeneralSecurityException e) {
            doLog(user, ACTION_NAME, "", "Failed to authenticate the manager user - " + e.getMessage(), Level.WARN);
            return mapping.findForward(getLoginFailForwardName());
        }

        // Get the principal from persistence.
        AuthorizationPersistence persistence = getAuthorizationManager().getPersistence();
        Collection temp = persistence.searchPrincipals(user);
        Principal principal = null;
        
        if(temp != null && temp.iterator().hasNext()) {
            principal = (Principal) temp.iterator().next();
        } else {
            doLog(user, ACTION_NAME, "" + subject.getUserId(), "Failed to authorize the manager user, no Principal found!", Level.WARN);
            return mapping.findForward(getLoginAuthFailForwardName());
        }

        // authorize the user
        if (getAuthorizationManager().authorize(principal, getAction(), getActionContext())) {
            // when the user passed authorization, check if there is already an existing
            // profile in session, if it does, redirect to error page
            if(request.getSession().getAttribute(getUserProfileKey()) != null) {
                doLog(user, ACTION_NAME, "" + subject.getUserId(), "Failed login, User profile already exists in Session", Level.INFO);
                return mapping.findForward(getLoginSessionExistsFailureForwardName());
            }
            // get the chat user profile and save it in session
            ChatUserProfile profile = getChatUserProfileManager().getProfile(user, "Registered");
            profile.setProperty(getCategoryKey(), category);
            profile.setProperty(getRoleKey(), "Manager");
            request.getSession().setAttribute(getUserProfileKey(), profile);

            doLog(user, ACTION_NAME, "" + subject.getUserId(), "Logged in the user as manager", Level.INFO);

            return mapping.findForward(getLoginSucceedForwardName());
        } else {
            doLog(user, ACTION_NAME, "" + subject.getUserId(), "Failed to authorize the manager user", Level.WARN);

            return mapping.findForward(getLoginFailForwardName());
        }
    }

    /**
     * <p>
     * Gets the <code>Action</code> for admin login.
     * </p>
     *
     * @return the Manger Login action.
     * @throws AuthorizationPersistenceException
     *             if any error happens to search the action from persistence.
     * @throws ConfigurationException
     *             if cannot construct the authorization manager.
     * @throws NoSuchElementException
     *             if there is no action element in persistence.
     */
    private synchronized Action getAction() throws AuthorizationPersistenceException, ConfigurationException {
        if (action == null) {
            Collection temp = getAuthorizationManager().getPersistence().searchActions("Manager Login");
            action = (Action) temp.iterator().next();
        }
        return action;
    }
}
