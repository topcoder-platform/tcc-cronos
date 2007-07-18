/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.struts.action.Action;

import com.topcoder.chat.user.profile.ChatUserProfileManager;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.security.authorization.ActionContext;
import com.topcoder.security.authorization.AuthorizationManager;
import com.topcoder.security.authorization.AuthorizationPersistenceException;
import com.topcoder.security.authorization.ConfigurationException;
import com.topcoder.security.login.LoginRemote;
import com.topcoder.security.login.LoginRemoteHome;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This is the base action of this component. It defines the common configurable properties of all the actions in this
 * component. The properties are almost initialized in constructor by being loaded from configuration manager, then
 * never changed later. Sub action classes can use them to decide where to forward after login, under which key to store
 * the user profile in the session, etc. It also provides convenient methods to help sub classes to access chat user
 * profile manager and EJB instances when using security manager. A method for logging is provided to support logging
 * for convenience.
 * </p>
 * <p>
 * Thread safety: This class contains mutable attributes, but are accessed in a thread safe manner.
 * </p>
 *
 * @author kakarotto, tyrian
 * @version 1.0
 */
abstract class LoginAction extends Action {
    /**
     * <p>
     * Represents the namespace used by the class to load the configuration parameters.
     * </p>
     */
    private static final String LOGIN_ACTION_NS = "com.cronos.im.LoginAction";

    /**
     * <p>
     * Represents the object factory namespace for creating the <code>ObjectFactory</code>.
     * </p>
     * <p>
     * It is initialized in constructor by being loaded from config manager, and never changed later. If failed to be
     * loaded then it is set to a default value "of_namespace". Cannot be <code>null</code> or empty. Can be accessed
     * by getter.
     * </p>
     */
    private final String objectFactoryNamespace;

    /**
     * <p>
     * Represents the chat profile manager key for creating the <code>ChatUserProfileManager</code> using
     * <code>ObjectFactory</code>.
     * </p>
     * <p>
     * It is initialized in constructor by being loaded from config manager, and never changed later. If failed to be
     * loaded then it is set to a default value "chatUserProfileManager". Cannot be <code>null</code> or empty. Can
     * be accessed by getter.
     * </p>
     */
    private final String chatProfileManagerKey;

    /**
     * <p>
     * Represents the logger used to do logging.
     * </p>
     * <p>
     * It is initialized in constructor and never changed later. It cannot be <code>null</code> (ensured by
     * LoggingWrapper component). Can be accessed by getter.
     * </p>
     */
    private final Log log;

    /**
     * <p>
     * Represents the session key for an object that hold the user's profile.
     * </p>
     * <p>
     * It is initialized in constructor by being loaded from config manager, and never changed later. If failed to be
     * loaded then it is set to a default value "userProfile". Cannot be <code>null</code> or empty. Can be accessed
     * by getter.
     * </p>
     */
    private final String userProfileKey;

    /**
     * <p>
     * Represents the property name which can be used to load a forward name from the config manager, the forward name
     * is used to find forward when login succeeds.
     * </p>
     * <p>
     * It is initialized in constructor by being loaded from config manager, and never changed later. It cannot be
     * <code>null</code> or empty, and if failed to be loaded then a default value "loginSucceed" will be used. Can be
     * accessed by getter.
     * </p>
     */
    private final String loginSucceedForwardName;

    /**
     * <p>
     * Represents the property name which can be used to load a forward name from the config manager, the forward name
     * is used to find forward when login fails.
     * </p>
     * <p>
     * It is initialized in constructor by being loaded from config manager, and never changed later. It cannot be
     * <code>null</code> or empty, and if failed to be loaded then a default value "loginFail" will be used. Can be
     * accessed by getter.
     * </p>
     */
    private final String loginFailForwardName;

    /**
     * <p>
     * Represents the property name which can be used to load a forward name from the config manager, the forward name
     * is used to find forward when login (Authorization) fails.
     * </p>
     * <p>
     * It is initialized in constructor by being loaded from config manager, and never changed later. It cannot be
     * <code>null</code> or empty, and if failed to be loaded then a default value "loginAuthFail" will be used. Can be
     * accessed by getter.
     * </p>
     */
    private final String loginAuthFailForwardName;
    
    /**
     * <p>
     * Represents the name of property "category" used when creating chat user profile.
     * </p>
     * <p>
     * It is initialized in constructor and never changed later. It cannot be <code>null</code> or empty, if cannot be
     * loaded from the config manager, then a default value "category" will be used. Can be accessed by getter.
     * </p>
     */
    private final String categoryKey;

    /**
     * <p>
     * Represents the name of property "role" used when creating chat user profile.
     * </p>
     * <p>
     * It is initialized in constructor and never changed later. It cannot be <code>null</code> or empty, if cannot be
     * loaded from the config manager, then a default value "role" will be used. Can be accessed by getter.
     * </p>
     */
    private final String roleKey;

    /**
     * <p>
     * Represents the provider URL used for using EJB when authenticating by using security manager.
     * </p>
     * <p>
     * It is initialized in constructor by being loaded from config manager, and never changed later. It can be any
     * value including <code>null</code>, but invalid values such as null or wrong URL will cause an exception thrown
     * from the execute method. Can be accessed by getter.
     * </p>
     */
    private final String authProviderURL;

    /**
     * <p>
     * Represents the initial context factory used for using EJB when authenticating by using security manager.
     * </p>
     * <p>
     * It is initialized in constructor by being loaded from config manager, and never changed later. It can be any
     * value includes <code>null</code>, but invalid values such as <code>null</code> or wrong URL will cause an
     * <code>Exception</code> thrown from the execute method. Can be accessed by getter.
     * </p>
     */
    private final String authInitialContextFactory;

    /**
     * <p>
     * Represents the chat user profile manager.
     * </p>
     * <p>
     * It is initialized in constructor and never changed later. It can be <code>null</code>, but <code>null</code>
     * will cause an <code>Exception</code> thrown from the method execute. Can be accessed by getter.
     * </p>
     */
    private ChatUserProfileManager chatUserProfileManager;

    /**
     * <p>
     * Represents the authorization manager.
     * </p>
     * <p>
     * It is initialized first time the method <code>{@link #getAuthorizationManager()}</code> is called and never
     * changed later. Failure of constructing it will cause an exception thrown from the method execute. Can be accessed
     * by getter.
     * </p>
     */
    private AuthorizationManager authorizationManager;

    /**
     * <p>
     * Represents the action context for authorization.
     * </p>
     * <p>
     * It is initialized first time the method <code>{@link #getActionContext()}</code> is called and never changed
     * later. Failure of constructing it will cause an exception thrown from the method execute. Can be accessed by
     * getter.
     * </p>
     */
    private ActionContext actionContext;

    /**
     * <p>
     * The default constructor for this action. It will load all the required properties from the configuration.
     * </p>
     */
    protected LoginAction() {
        // initializing the logger with the class name
        log = LogFactory.getLog(this.getClass().getName());

        // load the required values from the configuration file
        loginSucceedForwardName = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "loginSucceedForward", "loginSucceed");
        loginFailForwardName = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "loginFailForward", "loginFail");
        loginAuthFailForwardName = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "loginAuthFailForward", "loginAuthFail");

        userProfileKey = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "userProfileKey", "userProfile");
        categoryKey = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "categoryKey", "category");
        roleKey = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "roleKey", "role");

        authInitialContextFactory = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "authInitialContextFactory", null);

        // the url value defaults to null in case of any error
        authProviderURL = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "authProviderURL", null);

        // load the properties for ChatUserProfileManager using ObjectFactory factory
        objectFactoryNamespace = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "ObjectFactoryNS",
                "com.cronos.im.ObjectFactory");
        chatProfileManagerKey = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "chatUserProfileManager",
                "chatUserProfileManager");
    }

    /**
     * <p>
     * Gets the log.
     * </p>
     *
     * @return the log instance
     */
    protected Log getLog() {
        return log;
    }

    /**
     * <p>
     * Gets the userProfileKey.
     * </p>
     *
     * @return the session key for user profile object
     */
    protected String getUserProfileKey() {
        return userProfileKey;
    }

    /**
     * <p>
     * Gets the loginSucceedForwardName.
     * </p>
     *
     * @return the forward name when login succeed
     */
    protected String getLoginSucceedForwardName() {
        return loginSucceedForwardName;
    }

    /**
     * <p>
     * Gets the loginFailForwardName.
     * </p>
     *
     * @return the forward name when login fail
     */
    protected String getLoginFailForwardName() {
        return loginFailForwardName;
    }
    
    /**
     * <p>
     * Gets the loginAuthFailForwardName.
     * </p>
     *
     * @return the forward name when login (Authorization) fail
     */
    protected String getLoginAuthFailForwardName() {
        return loginAuthFailForwardName;
    }
    

    /**
     * <p>
     * Does logging for the login operations. This method can be used by sub classes for convenience.
     * </p>
     * <p>
     * It will log a message comprised by the arguments, a timestamp is automatically inserted also, so the log message
     * format is: [user] attempts to perform [action] at [timestamp](message, the entity [entityID] is affected)
     * </p>
     *
     * @param user
     *            the ID of the user who want to login
     * @param action
     *            the action which does this log
     * @param entityID
     *            the ID of the entity effected
     * @param message
     *            the log message
     * @param level
     *            the logging level
     * @throws IllegalArgumentException
     *             if the level arg is null.
     * @throws IllegalArgumentException
     *             if action or message argument is null, or trimmed as an empty string
     */
    protected void doLog(String user, String action, String entityID, String message, Level level) {
        IMLoginHelper.validateNotNull(level, "level");
        IMLoginHelper.validateEmptyString(action, "action");
        IMLoginHelper.validateEmptyString(message, "message");

        Date timestamp = Calendar.getInstance().getTime();
        StringBuffer messageBuffer = new StringBuffer();

        // create the message in the given format
        // [user] attempts to perform [action] at [timestamp](message, the entity [entityID] is affected)
        messageBuffer.append("[");
        messageBuffer.append(user);
        messageBuffer.append("] ");
        messageBuffer.append("attempts to perform [");
        messageBuffer.append(action);
        messageBuffer.append("] ");
        messageBuffer.append("at [");
        messageBuffer.append(timestamp);
        messageBuffer.append("] (");
        messageBuffer.append(message);
        messageBuffer.append(", the entity [");
        messageBuffer.append(entityID);
        messageBuffer.append("] is affected)");

        getLog().log(level, messageBuffer.toString());
    }

    /**
     * <p>
     * Gets the categoryKey.
     * </p>
     *
     * @return the name of property "category" used when creating chat user profile
     */
    protected String getCategoryKey() {
        return categoryKey;
    }

    /**
     * <p>
     * Gets the roleKey.
     * </p>
     *
     * @return the name of property "role" used when creating chat user profile
     */
    protected String getRoleKey() {
        return roleKey;
    }

    /**
     * <p>
     * Gets the authProviderURL. It can be null.
     * </p>
     *
     * @return the provider URL used by EJB
     */
    protected String getAuthProviderURL() {
        return authProviderURL;
    }

    /**
     * <p>
     * Gets the authInitialContextFactory.
     * </p>
     *
     * @return the initial context factory used by EJB
     */
    protected String getAuthInitialContextFactory() {
        return authInitialContextFactory;
    }

    /**
     * <p>
     * Creates an instance of <code>LoginRemote</code> to authenticate user. It will wraps other exceptions to
     * <code>EJBException</code>.
     * </p>
     *
     * @return an instance of LoginRemote
     * @throws RemoteException
     *             if error occurs when looking up EJB instances (this exception is required by EJB specification)
     * @throws NamingException
     *             if a naming exception is encountered when looking up EJB instances
     * @throws CreateException
     *             if the creation of <code>LoginRemote</code> fails
     * @throws ClassCastException
     *             if the object cannot be casted to LoginRemote
     */
    protected LoginRemote getLoginRemote() throws NamingException, RemoteException, CreateException {
        // create the environment map
        Map environment = new Hashtable();
        if (getAuthProviderURL() != null) {
            environment.put(Context.PROVIDER_URL, getAuthProviderURL());
        }
        if (getAuthInitialContextFactory() != null) {
            environment.put(Context.INITIAL_CONTEXT_FACTORY, getAuthInitialContextFactory());
        }

        // initiate the naming context with the environment
        Context namingContext = new InitialContext((Hashtable) environment);

        // get the login remote using the JNDIUtils
        Object object = JNDIUtils.getObject(namingContext, LoginRemoteHome.EJB_REF_NAME, LoginRemoteHome.class);
        // ensuring that the desired object can be casted.
        LoginRemoteHome loginRemoteHome = (LoginRemoteHome) PortableRemoteObject.narrow(object, LoginRemoteHome.class);
        return loginRemoteHome.create();
    }

    /**
     * <p>
     * Gets the chatUserProfileManager. It can be <code>null</code>.
     * </p>
     *
     * @return the chat user profile manager
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
     */
    protected synchronized ChatUserProfileManager getChatUserProfileManager()
        throws SpecificationConfigurationException, IllegalReferenceException, InvalidClassSpecificationException {
        if (chatUserProfileManager == null) {
            ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(objectFactoryNamespace));
            chatUserProfileManager = (ChatUserProfileManager) of.createObject(chatProfileManagerKey);
        }
        return chatUserProfileManager;
    }

    /**
     * <p>
     * Gets the actionContext. If it is not created it will be created during this call.
     * </p>
     *
     * @return the action context
     * @throws ConfigurationException
     *             if cannot construct the authorization manager.
     * @throws AuthorizationPersistenceException
     *             if any error occurs with the authorization persistence.
     * @throws NoSuchElementException
     *             if no action context is available in persistence.
     */
    protected synchronized ActionContext getActionContext() throws ConfigurationException,
            AuthorizationPersistenceException {
        if (actionContext == null) {
            Collection temp = getAuthorizationManager().getPersistence().searchContexts("Sales IM");
            actionContext = (ActionContext) temp.iterator().next();
        }
        return actionContext;
    }

    /**
     * <p>
     * Gets the authorizationManager. If it is not created it will be created during this call.
     * </p>
     *
     * @return the authorization manager
     * @throws ConfigurationException
     *             if cannot construct the authorization manager.
     */
    protected synchronized AuthorizationManager getAuthorizationManager() throws ConfigurationException {
        if (authorizationManager == null) {
            String authorizationManagerNS = IMLoginHelper.getConfig(LOGIN_ACTION_NS, "authorizationManagerNamespace",
                    "com.cronos.im.AuthorizationManager");

            authorizationManager = new AuthorizationManager(authorizationManagerNS);
        }

        return authorizationManager;
    }
}