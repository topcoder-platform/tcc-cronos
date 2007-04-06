/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.security.authenticationfactory.ConfigurationException;
import com.topcoder.security.authenticationfactory.MissingPrincipalKeyException;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.security.authenticationfactory.Principal;
import com.topcoder.security.authenticationfactory.AbstractAuthenticator;
import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.timetracker.user.filterfactory.Util;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This is the default implementation of the <code>Authenticator</code> interface that
 * is used to authenticate a username-password combination in order to authenticate a user.
 * </p>
 *
 * <p>
 * It will retrieve the relevant authentication details from the data store and compare it
 * against the data in the principal.
 * </p>
 *
 * <p>
 * Thread Safety: The thread-safety of this class is dependent on the inner DAO. since
 * that is thread-safe, then this class is also thread-safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class UserAuthenticator extends AbstractAuthenticator {
    /**
     * <p>
     * This is a static constant that represents the principal key used to retrieve
     * the <b>username</b> authentication attribute from the provided principal.
     * </p>
     */
    public static final String USERNAME_KEY = "username";

    /**
     * <p>
     * This is a static constant that represents the principal key used to retrieve
     * the <b>password</b> authentication attribute from the provided principal.
     * </p>
     */
    public static final String PASSWORD_KEY = "password";

    /**
     * <p>
     * This is an instance of <code>UserDAO</code> that is used to retrieve the authentication
     * details from the persistent store.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final UserDAO userDao;

    /**
     * <p>
     * Creates a <code>UserAuthenticator</code> using the specified configuration details.
     * </p>
     *
     * @param namespace The configuration namespace to use.
     *
     * @throws IllegalArgumentException if namespace is a null or empty String.
     * @throws ConfigurationException if an exception occurs while initializing this <code>Authenticator</code>.
     */
    public UserAuthenticator(String namespace) throws ConfigurationException {
        super(Util.checkString(namespace, "namespace"));

        String ofNamespace = getString(namespace, "objectFactoryNamespace", true);

        try {
            ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(ofNamespace));

            String type = getString(namespace, "userDao.classname", true);
            String identifier = getString(namespace, "userDao.identifier", false);

            Object value = (identifier == null) ? of.createObject(type) : of.createObject(type, identifier);

            // verify the class type of the created object
            if (!(value instanceof UserDAO)) {
                throw new ConfigurationException("The created object is not an instance of UserDAO.");
            }

            this.userDao = (UserDAO) value;
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("SpecificationConfigurationException occurs "
                + "while creating ObjectFactory instance using namespace " + ofNamespace, e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("IllegalReferenceException occurs "
                + "while creating ObjectFactory instance using namespace " + ofNamespace, e);
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException("InvalidClassSpecificationException occurs while creating object.", e);
        }
    }

    /**
     * <p>
     * Authenticates the provided Principal against the login details in the datastore.
     * </p>
     *
     * <p>
     * The provided <code>Principal</code> must have keys specified according to the
     * {@link UserAuthenticator#USERNAME_KEY} and {@link UserAuthenticator#PASSWORD_KEY}.
     * </p>
     *
     * <p>
     * Note, this method is protected and the parameter <code>principal</code> is not null
     * promised by the class <code>AbstractAuthenticator</code>.
     * </p>
     *
     * @param principal The principal to authenticate.
     * @return the authentication response.
     *
     * @throws MissingPrincipalKeyException if the username or password key is missing
     * @throws AuthenticateException if a problem occurs during authentication, e.g. data accessing problem
     */
    protected Response doAuthenticate(Principal principal) throws AuthenticateException {
        String username = getPrincipalValue(principal, USERNAME_KEY);
        String password = getPrincipalValue(principal, PASSWORD_KEY);

        try {
            User[] users = userDao.searchUsers(userDao.getUserFilterFactory().createUsernameFilter(
                StringMatchType.EXACT_MATCH, username));

            if (users.length == 0) {
                return new Response(false, "The user name doesn't exist.");
            } else {
                return (users[0].getPassword().equals(password)) ? new Response(true,
                    "The user with the given password exists") : new Response(false, "The password is not correct.");
            }
        } catch (DataAccessException e) {
            throw new AuthenticateException("Failed to search the users.", e);
        }
    }

    /**
     * <p>
     * Returns the string value of the given key in the given principal.
     * </p>
     *
     * @param principal The principal to authenticate..
     * @param keyName the name of the principal key
     * @return the value for the given key in the principal
     *
     * @throws MissingPrincipalKeyException if the given key is missing
     * @throws AuthenticateException if the value of given key in the principal is not an string
     */
    private String getPrincipalValue(Principal principal, String keyName) throws AuthenticateException {
        Object value = principal.getValue(keyName);

        // The key is missing
        if (value == null) {
            throw new MissingPrincipalKeyException(keyName);
        }

        // the type must be String
        if (!(value instanceof String)) {
            throw new AuthenticateException("The value for the principal key [" + keyName + "] is not a string.");
        }

        return (String) value;
    }

    /**
     * <p>
     * Returns the value of the property in the given namespace.
     * </p>
     *
     * <p>
     * If the property is missing in the configuration, then if it is required, then
     * <code>ConfigurationException</code> will be thrown, otherwise <code>null</code>
     * will be returned.
     * </p>
     *
     * @param namespace the namespace to get
     * @param propertyName the name of property
     * @param required whether this property is required
     *
     * @return the value of the property
     *
     * @throws ConfigurationException if fail to load the config values
     */
    private String getString(String namespace, String propertyName, boolean required) throws ConfigurationException {
        try {
            String property = ConfigManager.getInstance().getString(namespace, propertyName);

            // Empty property value is not allowed
            if ((property != null) && (property.trim().length() == 0)) {
                throw new ConfigurationException("Property " + propertyName + " is empty.");
            }

            // This property is missed
            if (required && (property == null)) {
                throw new ConfigurationException("Property " + propertyName + " is missing.");
            }

            return property;
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("UnknownNamespaceException occurs when accessing ConfigManager.", e);
        }
    }
}
