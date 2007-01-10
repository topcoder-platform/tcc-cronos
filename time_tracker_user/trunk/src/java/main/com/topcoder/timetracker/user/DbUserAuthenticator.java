/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.timetracker.user;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.topcoder.timetracker.common.DBUtils;
import com.topcoder.timetracker.common.Utils;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.encryption.EncryptionException;
import com.topcoder.security.authenticationfactory.AbstractAuthenticator;
import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.ConfigurationException;
import com.topcoder.security.authenticationfactory.InvalidPrincipalException;
import com.topcoder.security.authenticationfactory.MissingPrincipalKeyException;
import com.topcoder.security.authenticationfactory.Principal;
import com.topcoder.security.authenticationfactory.Response;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * An authenticator implementation that is capable of authenticating a username/password combination against the
 * Time Tracker datastore. It will connect to the specified data store and verify that the username/ password
 * combination is valid. It will automatically perform any necessary encryption/decryption that is needed when
 * interacting with the data store.
 * </p>
 * <p>
 * Thread Safety: - This class is conditionally thread safe. The thread safety depends on the usage of the
 * underlying data store, and the transaction isolation level that is currently in use. This class does not modify
 * the data store by itself, but if the data store is modified by some other class, then there is a possibility of
 * dirty reads and other issues.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public class DbUserAuthenticator extends AbstractAuthenticator {
    /**
     * <p>
     * This is the authenticator key that is used to retrieve the username attribute from the Principal.
     * </p>
     */
    public static final String USERNAME_KEY = "username";

    /**
     * <p>
     * This is the authenticator key that is used to retrieve the password attribute from the Principal.
     * </p>
     */
    public static final String PASSWORD_KEY = "password";

    /**
     * The connection name configuration property name.
     */
    private static final String CONNECTION_FACTORY_CONNECTION_NAME_PROPERTY = "connection_factory.connection_name";

    /**
     * The encryption algorithm property name.
     */
    private static final String ENCRYPTION_ALGORITHM_PROPERTY = "encryption_algorithm";

    /**
     * The conection factory namespace property name.
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PROPERTY = "connection_factory.namespace";

    /**
     * The connection factory class name property.
     */
    private static final String CONNECTION_FACTORY_CLASSNAME_PROPERTY = "connection_factory.classname";

    /**
     * The SQL query. Selects the password for the given user name.
     */
    private static final String SELECT_PASSWORD_QUERY = "SELECT password FROM user_account WHERE user_name = ?";

    /**
     * Constructor arguments signature.
     */
    private static final Class[] CTOR_STRING_PARAMS = new Class[] {String.class};

    /**
     * <p>
     * This is the connection factory where where connections to the data store will be retrieved. It may not be
     * null.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: setConnectionFactory
     * </p>
     * <p>
     * Accessed In: getConnectionFactory
     * </p>
     * <p>
     * Utilized In: doAuthenticate
     * </p>
     */
    private DBConnectionFactory connectionFactory;

    /**
     * <p>
     * This is the connection name which will be used to retrieve a database connection from the connection
     * factory. It may not be null.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: setConnectionName
     * </p>
     * <p>
     * Accessed In: getConnectionName
     * </p>
     * <p>
     * Utilized In:doAuthenticate
     * </p>
     */
    private String connectionName;

    /**
     * <p>
     * This is the algorithm name that is used when encrypting/decrypting the user password into the datastore.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Utilized In: doAuthenticate
     * </p>
     */
    private String algorithmName;

    /**
     * <p>
     * Constructs the DbUserAuthenticator according to the configuration details specified in the given namespace.
     * </p>
     *
     * @param namespace The namespace containing the necessary configuration details.
     * @throws ConfigurationException (from Authentication Factory) if a problem occurs during configuration.
     * @throws IllegalArgumentException if the namespace is an empty String.
     * @throws NullPointerException if <code>namespace</code> is <code>null</code>.
     */
    public DbUserAuthenticator(String namespace) throws ConfigurationException {
        super(namespace);

        this.algorithmName = getValue(namespace, ENCRYPTION_ALGORITHM_PROPERTY, true);
        this.connectionFactory = createDBfactory(namespace);
        this.connectionName = getValue(namespace, CONNECTION_FACTORY_CONNECTION_NAME_PROPERTY, false);
    }

    /**
     * Returns value form property.
     *
     * @param namespace the configuration namespace.
     * @param name the name of the value.
     * @param isRequired flag indicating if value is required.
     * @return configuration value.
     * @throws ConfigurationException if isRequired flag is true and value doesn't exists or the value is empty.
     */
    private static String getValue(String namespace, String name, boolean isRequired)
            throws ConfigurationException {
        ConfigManager cm = ConfigManager.getInstance();

        try {
            String value = cm.getString(namespace, name);
            if (isRequired && ((value == null) || (value.trim().length() == 0))) {
                throw new ConfigurationException("Missing required property: " + name + " in namespace: "
                        + namespace);
            }

            return value;
        } catch (UnknownNamespaceException ex) {
            throw new ConfigurationException("The namespace does not exist.", ex);
        }
    }

    /**
     * <p>
     * Creates the database factory object using the configuration form the <code>namespace</code>. If any
     * required property is missing or the object cannot be created, the ConfigurationException will be thrown.
     * </p>
     *
     * @param namespace the configuration namespace.
     * @return the DBConnectionFactory instance.
     * @throws ConfigurationException if any property is missing or other error occur.
     */
    private static DBConnectionFactory createDBfactory(String namespace) throws ConfigurationException {
        String className = getValue(namespace, CONNECTION_FACTORY_CLASSNAME_PROPERTY, true);
        String nameSpace = getValue(namespace, CONNECTION_FACTORY_NAMESPACE_PROPERTY, true);

        try {
            Class cl = Class.forName(className);
            if (!DBConnectionFactory.class.isAssignableFrom(cl)) {
                throw new ConfigurationException("Class [" + className + "] is not of ["
                        + DBConnectionFactory.class.getName() + "] type.");
            }

            return (DBConnectionFactory) cl.getConstructor(CTOR_STRING_PARAMS).newInstance(
                    new Object[] {nameSpace});
        } catch (ClassNotFoundException ex) {
            throw new ConfigurationException("Cannot find class: " + className, ex);
        } catch (SecurityException ex) {
            throw new ConfigurationException("Security error occurs while initializing class.", ex);
        } catch (InstantiationException ex) {
            throw new ConfigurationException("Error occurs while initializing class.", ex);
        } catch (IllegalAccessException ex) {
            throw new ConfigurationException("Cannot get the constructor.", ex);
        } catch (InvocationTargetException ex) {
            throw new ConfigurationException("Error occurs while creating object.", ex);
        } catch (NoSuchMethodException ex) {
            throw new ConfigurationException("Class [" + className + "] does not have constructor with"
                    + " String argument.", ex);
        }
    }

    /**
     * <p>
     * Authenticates the provided Principal against the configured data store.
     * </p>
     *
     * @return the authentication response.
     * @param principal The principal to authenticate. This principal should contain the username/password
     *        combination to authenticate.
     * @throws IllegalArgumentException if principal is null.
     * @throws MissingPrincipalKeyException if certain key is missing in the given principal.
     * @throws InvalidPrincipalException if the principal is invalid, e.g. the type of a certain key's value is
     *         invalid.
     * @throws AuthenticateException if error occurs during the authentication
     */
    protected Response doAuthenticate(Principal principal) throws AuthenticateException {
        Utils.checkNull(principal, "principal");
        // get the username and password
        String username = getStringValue(principal, USERNAME_KEY);
        String password = getStringValue(principal, PASSWORD_KEY);

        // encode the password using configured algorithm.
        try {
            password = Utils.encrypt(algorithmName, password);
        } catch (EncryptionException ex) {
            throw new AuthenticateException("Error occurs while encrypting password.", ex);
        }

        // get the password from database
        String dbPassword = retrievePassword(username);
        // if null - user not exists
        if (dbPassword == null) {
            return new Response(false, "No such principal.");
        }
        // if passwords are equal - return valid response
        if (password.equals(dbPassword)) {
            return new Response(true, "Valid password.");
        }

        // if passwords don't match - return failure response
        return new Response(false, "Password doesn't match.");
    }

    /**
     * <p>
     * Retrieves the password for <code>username</code> from the <code>user_account</code> table. If the user
     * with given <code>username</code> doesn't exist, <code>null</code> will be returned.
     * </p>
     *
     * @param username the user name.
     * @return the user password or <code>null</code>, if user not exists.
     * @throws AuthenticateException if any persistence error occur.
     */
    private String retrievePassword(String username) throws AuthenticateException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (connectionName == null) {
                conn = connectionFactory.createConnection();
            } else {
                conn = connectionFactory.createConnection(connectionName);
            }
            pstmt = conn.prepareStatement(SELECT_PASSWORD_QUERY);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }

            return null;
        } catch (SQLException ex) {
            throw new AuthenticateException("Error occur while database operation.", ex);
        } catch (DBConnectionException ex) {
            throw new AuthenticateException("Error occur while creating database connection.", ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(conn);
        }

    }

    /**
     * <p>
     * Returns the String value from given Principal object. If object not exists or is not a String, exception
     * will be thrown.
     * </p>
     *
     * @param principal the principal with property to retrieve.
     * @param key the property key.
     * @return the property String value.
     * @throws MissingPrincipalKeyException if principal does not have the <code>key</code>.
     * @throws InvalidPrincipalException if the principal value is not <code>String</code>.
     */
    private static String getStringValue(Principal principal, String key) {
        Object value = principal.getValue(key);
        if (value == null) {
            throw new MissingPrincipalKeyException("Missing required principal key: " + key);
        }

        if (!(value instanceof String)) {
            throw new InvalidPrincipalException("The principal value should be of String type. Key: " + key);
        }

        return (String) value;
    }
}
