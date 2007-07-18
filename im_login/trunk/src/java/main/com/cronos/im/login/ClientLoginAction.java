/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.topcoder.chat.user.profile.ChatUserProfile;
import com.topcoder.chat.user.profile.ChatUserProfilePersistenceException;
import com.topcoder.chat.user.profile.DuplicateProfileException;
import com.topcoder.chat.user.profile.DuplicateProfileKeyException;
import com.topcoder.chat.user.profile.ProfileKeyManagerPersistenceException;
import com.topcoder.chat.user.profile.ProfileNotFoundException;
import com.topcoder.chat.user.profile.UnrecognizedDataSourceTypeException;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.util.log.Level;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class provides client login support. It is an extension of <code>LoginAction</code>, which defines the common
 * properties needed when doing login. It provides several additional configurable properties such as the configuration
 * of db connection factory and the key names needed when creating chat user profiles.
 * </p>
 * <p>
 * It needs to access a database table "category" directly to check whether the specified discussion category is
 * chattable, so the db connection factory is used to support db operations.
 * </p>
 * <p>
 * There are three possible forwards after the execution of this action, they are all specified by configurable
 * properties:
 * <ol>
 * <li>unchattableForward (default: "unchattable"): if the specified discussion category is unchattable.</li>
 * <li>loginSucceedForward (default: "loginSucceed"): if the login succeeded.</li>
 * <li>loginFailFoward (default: "loginFail"): if the login failed.</li>
 * </ol>
 * After successful login, a <code>ChatUserProfile</code> object will be stored to the session under a configurable
 * key specified by the field userProfileKey.
 * </p>
 * <p>
 * Thread safety: This class contains mutable attributes, but are accessed in a thread safe manner.
 * </p>
 *
 * @author kakarotto, tyrian
 * @version 1.0
 */
public class ClientLoginAction extends LoginAction {
    /**
     * Represents the action name used for logging.
     */
    private static final String ACTION_NAME = "client login";

    /**
     * Represents the role used to store in session.
     */
    private static final String CLIENT = "client";

    /**
     * <p>
     * Represents the namespace used by the class to load the configuration parameters.
     * </p>
     */
    private static final String CLIENT_LOGIN_ACTION_NS = "com.cronos.im.ClientLoginAction";

    /**
     * <p>
     * Represents the instance of <code>DBConnectionFactory</code> to create connections with.
     * </p>
     * <p>
     * It is initialized in the <code>{@link #initDBFactory()}</code> method and never changed later. Each time it is
     * used first checks whether this field is <code>null</code>, and if null, it is attempted to be initialized
     * using initDBFactory().
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Represents the property name which can be used to load a forward name from the config manager, the forward name
     * is used to find forward when the specified discussion category is unchattable.
     * </p>
     * <p>
     * It is initialized in constructor by being loaded from config manager, and never changed later. It cannot be
     * <code>null</code> or empty, and if failed to be loaded then a default value "unchattable" will be used. Can be
     * accessed by getter.
     * </p>
     */
    private final String unchattableForwardName;

    /**
     * <p>
     * Represents the connection name used when obtaining a connection from the db connection factory.
     * </p>
     * <p>
     * It is initialized in method <code>initDBFactory</code> and never changed later. It cannot be <code>null</code>
     * or empty, and if failed to be loaded then a default value "connectionName" will be used.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the name of property "family name" used when creating chat user profile.
     * </p>
     * <p>
     * It is initialized in constructor and never changed later. It cannot be <code>null</code> or empty, if cannot be
     * loaded from the config manager, then a default value "familyName" will be used.
     * </p>
     */
    private final String familyNameKey;

    /**
     * <p>
     * Represents the name of property "last name" used when creating chat user profile.
     * </p>
     * <p>
     * It is initialized in constructor and never changed later. It cannot be <code>null</code> or empty, if cannot be
     * loaded from the config manager, then a default value "lastName" will be used.
     * </p>
     */
    private final String lastNameKey;

    /**
     * <p>
     * Represents the name of property "company" used when creating chat user profile.
     * </p>
     * <p>
     * It is initialized in constructor and never changed later. It cannot be <code>null</code> or empty, if cannot be
     * loaded from the config manager, then a default value "company" will be used.
     * </p>
     */
    private final String companyKey;

    /**
     * <p>
     * Represents the name of property "title" used when creating chat user profile.
     * </p>
     * <p>
     * It is initialized in constructor and never changed later. It cannot be <code>null</code> or empty, if cannot be
     * loaded from the config manager, then a default value "title" will be used.
     * </p>
     */
    private final String titleKey;

    /**
     * <p>
     * Represents the name of property "email" used when creating chat user profile.
     * </p>
     * <p>
     * It is initialized in constructor and never changed later. It cannot be <code>null</code> or empty, if cannot be
     * loaded from the config manager, then a default value "email" will be used.
     * </p>
     */
    private final String emailKey;

    /**
     * <p>
     * The default constructor for this action. It will load all the required properties from the configuration.
     * </p>
     */
    public ClientLoginAction() {
        // load the required values from the configuration file for this action.
        unchattableForwardName = IMLoginHelper.getConfig(CLIENT_LOGIN_ACTION_NS, "unchattableForward", "unchattable");
        familyNameKey = IMLoginHelper.getConfig(CLIENT_LOGIN_ACTION_NS, "familyNameKey", "familyName");
        lastNameKey = IMLoginHelper.getConfig(CLIENT_LOGIN_ACTION_NS, "lastNameKey", "lastName");
        companyKey = IMLoginHelper.getConfig(CLIENT_LOGIN_ACTION_NS, "companyKey", "company");
        titleKey = IMLoginHelper.getConfig(CLIENT_LOGIN_ACTION_NS, "titleKey", "title");
        emailKey = IMLoginHelper.getConfig(CLIENT_LOGIN_ACTION_NS, "emailKey", "email");
        connectionName = IMLoginHelper.getConfig(CLIENT_LOGIN_ACTION_NS, "connectionName", "connectionName");
    }

    /**
     * <p>
     * Executes the login action for Client Login. If the specified discussion category is not chattable, the request
     * will be forwarded to the corresponding unchattableForwardName. Based on the login type "unreg"/"reg", the request
     * will be processed. If the login is success, forwards to loginSucceedForwardName otherwise
     * loginFailureForwardName.
     * <p>
     *
     * @param mapping
     *            The <code>ActionMapping</code> used to select this instance
     * @param form
     *            The optional <code>ActionForm</code> bean for this request (if any)
     * @param request
     *            the http servlet request
     * @param response
     *            the http servlet response
     * @return If the specified category is unchattable return a forward whose mapping name is
     *         <code>unchattableForwardName</code> If login succeeded, return a forward whose mapping name is
     *         <code>loginSucceedForwardName</code> If login failed, return a forward whose mapping name is
     *         <code>loginFailureForwardName</code>
     * @throws SQLException
     *             if a database access error occurs
     * @throws RemoteException
     *             if error occurs when using security manager (this exception is required by EJB specification)
     * @throws DBConnectionException
     *             if any error occurs when obtaining the db connection
     * @throws ConfigurationException
     *             if error occurs when creating db connection factory
     * @throws CreateException
     *             if the creation of <code>LoginRemote</code> fails
     * @throws IllegalReferenceException
     *             if cannot properly match specifications given for <code>ChatUserProfileManager</code> to each
     *             other, or the properties are malformed.
     * @throws SpecificationConfigurationException
     *             if <code>ObjectFactory</code> creations fails.
     * @throws InvalidClassSpecificationException
     *             createObject method failure, if the specification is not valid and can't be used to create an
     *             object.
     * @throws NamingException
     *             if a naming exception is encountered when looking up EJB instances
     * @throws UnrecognizedDataSourceTypeException
     *             if the type sources provided is not registered with the <code>ChatUserProfileManager</code>.
     * @throws ProfileKeyManagerPersistenceException
     *             if any ProfileKey related persistence error occurs.
     * @throws ChatUserProfilePersistenceException
     *             if persistence related error occurs with the <code>ChatUserProfilePersistence</code>.
     * @throws ProfileNotFoundException
     *             if the chat user profile is not present in the persistence.
     * @throws DuplicateProfileException
     *             If the profile already exists in the persistence.
     * @throws DuplicateProfileKeyException
     *             If the manager requires that the key not already exist in the ProfileKeyManager
     *             then this exception may be thrown.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws ConfigurationException, DBConnectionException, SQLException,
            InvalidClassSpecificationException, SpecificationConfigurationException, IllegalReferenceException,
            RemoteException, ProfileNotFoundException, ChatUserProfilePersistenceException,
            ProfileKeyManagerPersistenceException, UnrecognizedDataSourceTypeException, NamingException,
            CreateException, DuplicateProfileException, DuplicateProfileKeyException {

        String user = request.getParameter(IMLoginHelper.USER);
        String category = request.getParameter(IMLoginHelper.CATEGORY);
        String type = request.getParameter(IMLoginHelper.TYPE);

        if ("unreg".equals(type)) {
            if (!checkChattable(category)) {
                doLog(user, ACTION_NAME, "", "The requested category is unchattable", Level.WARN);
                return mapping.findForward(unchattableForwardName);
            }
            unregisteredClientLogin(request);
            return mapping.findForward(getLoginSucceedForwardName());
        } else if ("reg".equals(type)) {
            if (registeredClientLogin(request)) {
                if (!checkChattable(category)) {
                    doLog(user, ACTION_NAME, "", "The requested category is unchattable", Level.WARN);
                    return mapping.findForward(unchattableForwardName);
                }
                return mapping.findForward(getLoginSucceedForwardName());
            }
        }
        // failure
        return mapping.findForward(getLoginFailForwardName());
    }

    /**
     * <p>
     * Creates a new chat user profile whose type is "Unregistered", and whose properties correspond to the input
     * parameters, and saves the selected discussion category and client role in the profile. Then stores the user
     * profile in session under the key whose value is <code>{@link #getUserProfileKey()}</code>.
     * </p>
     *
     * @param request
     *            the http request
     * @throws UnrecognizedDataSourceTypeException
     *             if the type sources provided is not registered with the <code>ChatUserProfileManager</code>.
     * @throws DuplicateProfileException
     *             If the profile already exists in the persistence.
     * @throws DuplicateProfileKeyException
     *             If the manager requires that the key not already exist in the ProfileKeyManager
     *             then this exception may be thrown.
     * @throws ChatUserProfilePersistenceException
     *             if persistence related error occurs with the <code>ChatUserProfilePersistence</code>.
     * @throws ProfileKeyManagerPersistenceException
     *             if any ProfileKey related persistence error occurs.
     * @throws IllegalReferenceException
     *             if cannot properly match specifications given for <code>ChatUserProfileManager</code> to each
     *             other, or the properties are malformed.
     * @throws SpecificationConfigurationException
     *             if <code>ObjectFactory</code> creations fails.
     * @throws InvalidClassSpecificationException
     *             createObject method failure, if the specification is not valid and can't be used to create an
     *             object.
     */
    private void unregisteredClientLogin(HttpServletRequest request) throws InvalidClassSpecificationException,
            SpecificationConfigurationException, IllegalReferenceException, ProfileKeyManagerPersistenceException,
            ChatUserProfilePersistenceException, DuplicateProfileException, DuplicateProfileKeyException,
            UnrecognizedDataSourceTypeException {
        String fName = request.getParameter("fname");
        String lName = request.getParameter("lname");
        String userName = fName + " " + lName;

        // Initiate a new profile for this unregistered user
        ChatUserProfile profile = new ChatUserProfile("-1", "Unregistered");

        profile.addProperty(getCategoryKey(), request.getParameter(IMLoginHelper.CATEGORY));
        profile.addProperty(getFamilyNameKey(), fName);
        profile.addProperty(getLastNameKey(), lName);
        profile.addProperty(getRoleKey(), CLIENT);
        profile.addProperty(getEmailKey(), request.getParameter("email"));
        // fix for TCIM-8834
        profile.addProperty("Name", fName + " " + lName);
        // company and title are optional
        String company = request.getParameter("company");

        if (company != null) {
            profile.addProperty(getCompanyKey(), company);
        }

        String title = request.getParameter("title");

        if (title != null) {
            profile.addProperty(getTitleKey(), title);
        }
        getChatUserProfileManager().createProfile(profile);
        request.getSession().setAttribute(getUserProfileKey(), profile);

        doLog(userName, ACTION_NAME, "" + profile.getId(), "Logging in the user as UnRegistered", Level.INFO);
    }

    /**
     * <p>
     * Authenticates the user with username and password, retrieves the chat user profile using the username and
     * "Registered" type, and saves the selected discussion category and client role in the profile. Then stores the
     * user profile in session under the key whose value is <code>{@link #getUserProfileKey()}</code>.
     * </p>
     *
     * @param request
     *            the http request
     * @return <code>true</code> if the user is authenticated, otherwise <code>false</code>
     * @throws RemoteException
     *             if error occurs when using security manager (this exception is required by EJB specification)
     * @throws NamingException
     *             if a naming exception is encountered when looking up EJB instances
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
     * @throws CreateException
     *             if the creation of <code>LoginRemote</code> fails.
     * @throws ClassCastException
     *             if the object created is not of type <code>ChatUserProfileManager</code>.
     */
    private boolean registeredClientLogin(HttpServletRequest request) throws RemoteException, NamingException,
            InvalidClassSpecificationException, SpecificationConfigurationException, IllegalReferenceException,
            ProfileNotFoundException, ChatUserProfilePersistenceException, ProfileKeyManagerPersistenceException,
            UnrecognizedDataSourceTypeException, CreateException {

        // get the required parameters from the request
        String user = request.getParameter(IMLoginHelper.USER);
        String password = request.getParameter(IMLoginHelper.PASSWORD);
        String category = request.getParameter(IMLoginHelper.CATEGORY);

        // authenticate using security manager
        TCSubject subject = null;
        try {
            // authenticate the user
            subject = getLoginRemote().login(user, password);
        } catch (GeneralSecurityException e) {
            // if login failed return failure
            doLog(user, ACTION_NAME, "", "Failed to authorize the user " + e.getMessage(), Level.WARN);
            return false;
        }

        // get the user profile using the chat profile manager and store it in the session
        ChatUserProfile profile = getChatUserProfileManager().getProfile(user, "Registered");
        profile.setProperty(getCategoryKey(), category);
        profile.setProperty(getRoleKey(), CLIENT);
        request.getSession().setAttribute(getUserProfileKey(), profile);

        doLog(user, ACTION_NAME, "" + subject.getUserId(), "Logging in the user as Registered", Level.INFO);
        return true;
    }

    /**
     * <p>
     * Checks whether the category is chattable, it is done by accessing the database table "category" directly.
     * </p>
     *
     * @param category
     *            the category value
     * @return <code>true</code> if the specified discussion category is chattable, otherwise <code>false</code>
     * @throws IllegalArgumentException
     *             if the category is <code>null</code> or empty.
     * @throws DBConnectionException
     *             if any error occurs when obtaining the db connection
     * @throws SQLException
     *             if a database access error occurs
     * @throws com.topcoder.db.connectionfactory.ConfigurationException
     *             if error occurs when creating db connection factory
     */
    protected boolean checkChattable(String category) throws com.topcoder.db.connectionfactory.ConfigurationException,
            DBConnectionException, SQLException {
        IMLoginHelper.validateEmptyString(category, "category");
        initDBFactory();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = dbConnectionFactory.createConnection(connectionName);
            statement = connection.prepareStatement("select chattable_flag from category where category_id = ?");
            statement.setInt(1, Integer.parseInt(category));
            result = statement.executeQuery();
            if (result.next()) {
                return "Y".equals(result.getString("chattable_flag"));
            }
        } finally {
            if (result != null) {
                result.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return false;
    }

    /**
     * <p>
     * Initializes the db connection factory and connection name variables. It is synchronized and called each time this
     * action is executed.
     * </p>
     *
     * @throws com.topcoder.db.connectionfactory.ConfigurationException
     *             if error occurs when creating <code>DBConnectionFactory</code>.
     * @throws UnknownConnectionException
     *             if the <code>DBConnectionFactoryImpl</code> cannot find the given namespace.
     */
    private synchronized void initDBFactory() throws UnknownConnectionException,
            com.topcoder.db.connectionfactory.ConfigurationException {
        if (dbConnectionFactory != null) {
            return;
        }
        String dbNamespace = IMLoginHelper.getConfig(CLIENT_LOGIN_ACTION_NS, "DBConnectionFactoryNS",
                "DBConnectionFactoryNS");

        dbConnectionFactory = new DBConnectionFactoryImpl(dbNamespace);
    }

    /**
     * <p>
     * Gets the unchattableForwardName.
     * </p>
     *
     * @return the forward name when specified category is unchattable
     */
    protected String getUnchattableForwardName() {
        return unchattableForwardName;
    }

    /**
     * <p>
     * Gets the connectionName.
     * </p>
     *
     * @return the connection name
     */
    protected synchronized String getConnectionName() {
        return connectionName;
    }

    /**
     * <p>
     * Gets the familyNameKey.
     * </p>
     *
     * @return the name of property "family name" used when creating chat user profile
     */
    protected String getFamilyNameKey() {
        return familyNameKey;
    }

    /**
     * <p>
     * Gets the lastNameKey.
     * </p>
     *
     * @return the name of property "last name" used when creating chat user profile
     */
    protected String getLastNameKey() {
        return lastNameKey;
    }

    /**
     * <p>
     * Gets the titleKey.
     * </p>
     *
     * @return the name of property "title" used when creating chat user profile
     */
    protected String getTitleKey() {
        return titleKey;
    }

    /**
     * <p>
     * Gets the emailKey.
     * </p>
     *
     * @return the name of property "email" used when creating chat user profile
     */
    protected String getEmailKey() {
        return emailKey;
    }

    /**
     * <p>
     * Gets the companyKey.
     * </p>
     *
     * @return the name of property "company" used when creating chat user profile
     */
    protected String getCompanyKey() {
        return companyKey;
    }
}
