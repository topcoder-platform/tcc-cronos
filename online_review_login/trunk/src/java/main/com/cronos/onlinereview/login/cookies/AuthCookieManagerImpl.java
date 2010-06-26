/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */



package com.cronos.onlinereview.login.cookies;

import com.cronos.onlinereview.login.AuthCookieManagementException;
import com.cronos.onlinereview.login.AuthCookieManager;
import com.cronos.onlinereview.login.ConfigurationException;
import com.cronos.onlinereview.login.Util;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.security.authenticationfactory.Principal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * This class is an implementation of AuthCookieManager that retrieves information about user IDs and password from the
 * database. This class is configured using Configuration Manager component. It uses DB Connection Factory to get DB
 * connections. User specific data is retrieved from security_user table.
 * </p>
 * <p>
 * <b> Thread Safety:</b> This class is immutable and thread safe. It uses thread safe DBConnectionFactory instance.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class AuthCookieManagerImpl implements AuthCookieManager {

    /**
     * Represents the number 0xFF.
     */
    private static int SET_ALL_BITS_MASK = 0xFF;

    /**
     * Represents the character '0'.
     */
    private static char ZERO_CHAR = '0';

    /**
     * The connection name passed to DBConnectionFactory when establishing a DB connection. Is initialized in the
     * constructor and never changed after that. Cannot be empty. When is null, the default connection is used. Is used
     * in setAuthCookie() and checkAuthCookie().
     */
    private final String connectionName;

    /**
     * The name of HTTP cookie used for storing the authentication data (user ID and hashed password). Is initialized in
     * the constructor and never changed after that. Cannot be null or empty. Is used in setAuthCookie(),
     * removeAuthCookie() and checkAuthCookie().
     */
    private final String cookieName;

    /**
     * The DB connection factory used for creating DB connection when getting user ID or/and password from the database.
     * Is initialized in the constructor and never changed after that. Cannot be null. Is used in setAuthCookie() and
     * checkAuthCookie().
     */
    private final DBConnectionFactory dbConnectionFactory;

    /**
     * The key name of the user identifier that is stored in the request session if authentication with a cookie
     * succeeded. Is initialized in the constructor and never changed after that. Cannot be null or empty. Is used in
     * checkAuthCookie().
     */
    private final String userIdentifierKey;

    /**
     * Creates an instance of AuthCookieManagerImpl.
     *
     * @param namespace
     *            the namespace to create the instance
     * @throws IllegalArgumentException
     *             if namespace is null or empty
     * @throws ConfigurationException
     *             if failed to create the instance from given namespace
     */
    public AuthCookieManagerImpl(String namespace) throws ConfigurationException {
        Util.validateNotNullOrEmpty(namespace, "namespace");

        // Get the userIdentifierKey from the "user_identifier_key" property (using ConfigManager).
        this.userIdentifierKey = Util.getRequiredPropertyString(namespace, "user_identifier_key");

        // Get the cookieName from the "cookie_name" property (using ConfigManager).
        this.cookieName = Util.getRequiredPropertyString(namespace, "cookie_name");

        // Get the connectionName from the "connection_name" property (using ConfigManager).
        this.connectionName = Util.getOptionalPropertyString(namespace, "connection_name");

        // Get dbConnectionFactoryClassName and dbConnectionFactoryNamespace from "db_connection_factory.class" and
        // "db_connection_factory.namespace" properties.
        String dbConnectionFactoryClassName = Util.getRequiredPropertyString(namespace, "db_connection_factory.class");
        String dbConnectionFactoryNamespace = Util.getOptionalPropertyString(namespace,
                                                  "db_connection_factory.namespace");

        // Create dbConnectionFactory using reflection (use dbConnectionFactoryClassName to get Class instance)
        if ((dbConnectionFactoryNamespace == null) || (dbConnectionFactoryNamespace.trim().length() == 0)) {
            this.dbConnectionFactory = (DBConnectionFactory) Util.creatObject(dbConnectionFactoryClassName,
                    new Class[] {}, new Object[] {}, DBConnectionFactory.class);
        } else {
            this.dbConnectionFactory = (DBConnectionFactory) Util.creatObject(dbConnectionFactoryClassName,
                    new Class[] { String.class }, new Object[] { dbConnectionFactoryNamespace },
                    DBConnectionFactory.class);
        }
    }

    /**
     * Sets the authentication cookie to the given HTTP response for the given principal and HTTP request. Cookie value
     * has format "userId|hashedPassword".
     *
     * @param principal
     *            the principal
     * @param request
     *            the HTTP servlet request
     * @param response
     *            the HTTP servlet response
     * @throws IllegalArgumentException
     *             if any argument is null, or principal.userName is null/empty
     * @throws AuthCookieManagementException
     *             if some error occurred when setting the authentication cookie
     */
    public void setAuthCookie(Principal principal, HttpServletRequest request, HttpServletResponse response)
            throws AuthCookieManagementException {
        Util.validateNotNull(principal, "principal");
        Util.validateNotNull(request, "request");
        Util.validateNotNull(response, "response");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // Get user name from the principal
        try {
            String userName = (String) principal.getValue("userName");

            if (userName == null) {
                throw new IllegalArgumentException("The userName property of Principal should not be null");
            }

            if (userName.trim().length() == 0) {
                throw new IllegalArgumentException("The userName property of Principal should not be empty");
            }

            connection = createConnection();

            // Prepare query for retrieving user ID and password
            String query = "SELECT login_id, password FROM security_user WHERE user_id = ?";

            // Create prepared statement
            preparedStatement = prepareStatement(connection, query);

            // Set user name to the statement
            preparedStatement.setString(1, userName);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if result set is empty
            boolean hasNext = resultSet.next();

            if (!hasNext) {
                throw new AuthCookieManagementException("There should be one result by query with " + query);
            }

            // Get user ID from the result set
            long userId = resultSet.getLong(1);

            // Get password from the result set
            String password = resultSet.getString(2);

            // Get hashed password
            // Create cookie
            // Add cookie to the response
            response.addCookie(createCookie(true, userId + "|" + hashPassword(password), request.getServerName()));
        } catch (ClassCastException e) {
            throw new AuthCookieManagementException("Can not convert the userName value to String", e);
        } catch (SQLException e) {
            throw new AuthCookieManagementException("Can not interact with the database", e);
        } finally {

            // Close the statement, the result set is also closed
            closePreparedStatement(preparedStatement);

            // Close the connection
            closeConnection(connection);
        }
    }

    /**
     * Closes the database connection.
     *
     * @param connection
     *            the database connection to close
     */
    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {

            // ignore
        }
    }

    /**
     * Closes the prepared statement.
     *
     * @param preStmt
     *            the prepared statement to close
     */
    private void closePreparedStatement(PreparedStatement preStmt) {
        try {
            if (preStmt != null) {
                preStmt.close();
            }
        } catch (SQLException e) {

            // ignore
        }
    }

    /**
     * Creates the prepared statement with the specified query string.
     *
     * @param con
     *            the database connection
     * @param query
     *            the query string
     * @return the created prepared statement
     * @throws AuthCookieManagementException
     *             if fails to create the statement
     */
    private PreparedStatement prepareStatement(Connection con, String query) throws AuthCookieManagementException {
        try {
            return con.prepareStatement(query);
        } catch (SQLException e) {
            throw new AuthCookieManagementException("Can not create the prepared statement with " + query, e);
        }
    }

    /**
     * Creates the database connection.
     *
     * @return the created connection.
     * @throws AuthCookieManagementException
     *             if fails to create the database connection
     */
    private Connection createConnection() throws AuthCookieManagementException {
        try {
            if ((connectionName == null) || (connectionName.trim().length() == 0)) {

                // Create the default connection
                return dbConnectionFactory.createConnection();
            } else {

                // Create connection by name
                return dbConnectionFactory.createConnection(connectionName);
            }
        } catch (DBConnectionException e) {
            throw new AuthCookieManagementException("Can not create the database connection.", e);
        }
    }

    /**
     * Removes the authentication cookie from the HTTP client using the given HTTP request and response.
     *
     * @param request
     *            the HTTP servlet request
     * @param response
     *            the HTTP servlet response
     * @throws IllegalArgumentException
     *             if any argument is null
     */
    public void removeAuthCookie(HttpServletRequest request, HttpServletResponse response) {
        Util.validateNotNull(request, "request");
        Util.validateNotNull(response, "response");

        // Create cookie
        // Add cookie to the response
        response.addCookie(createCookie(false, "", request.getServerName()));
    }

    /**
     * Creates a Cookie instance.
     *
     * @param forSetAuthCookie
     *            whether the created cookie is for setAuthCookie or NOT.
     * @param cookieValue
     *            the cookie value
     * @param serverName
     *            the server name
     * @return the Cookie created.
     */
    private Cookie createCookie(boolean forSetAuthCookie, String cookieValue, String serverName) {
        Cookie cookie = new Cookie(cookieName, cookieValue);

        if (forSetAuthCookie) {

            // Make cookie never expire
            cookie.setMaxAge(Integer.MAX_VALUE);
        } else {

            // Make cookie expire immediately (i.e. be removed)
            cookie.setMaxAge(0);
        }

        // Get server name from the request
        // Set domain name to the cookie
        if (serverName != null) {
            cookie.setDomain(serverName);
        }

        // Set cookie path to root
        cookie.setPath("/");

        return cookie;
    }

    /**
     * Checks if the given HTTP request contains an authentication cookie. If it exists, this method extracts user ID
     * from the cookie and validates the user password. Cookie value has format "userId|hashedPassword".
     *
     * @param request
     *            the HTTP servlet request
     * @return the user ID if auth cookie exists and is valid, null otherwise
     * @throws IllegalArgumentException
     *             if request is null
     * @throws AuthCookieManagementException
     *             if some critical error occurred during the validation (but not if an error is in the cookie)
     */
    public Long checkAuthCookie(HttpServletRequest request) throws AuthCookieManagementException {
        Util.validateNotNull(request, "request");

        // Get cookies from the request
        Cookie[] cookies = request.getCookies();

        // no cookies, return null
        if (cookies == null) {
            return null;
        }

        Cookie authCookie = getAuthCookie(cookies);

        if (authCookie == null) {
            return null;
        }

        // Get auth cookie value
        String value = authCookie.getValue();

        if (value == null) {
            return null;
        }

        // Parse the cookie value
        String[] parts = value.split("\\|");

        if (parts.length != 2) {
            return null;
        }

        // Parse user ID from the cookie value
        long userId;

        try {
            userId = Long.parseLong(parts[0]);
        } catch (NumberFormatException e) {

            // return null if parsing error occurred
            return null;
        }

        String hashedPasswordInCookie = parts[1];
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = createConnection();

            // Prepare query for retrieving the password of the user from DB
            // Prepare SQL statement
            preparedStatement = prepareStatement(connection, "SELECT password FROM security_user WHERE login_id = ?");

            // Set use ID to the statement
            preparedStatement.setLong(1, userId);

            // Execute query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if result set is empty
            boolean hasNext = resultSet.next();

            if (!hasNext) {
                return null;
            }

            // Get password from the result set
            // Hash the real password
            String realHashedPassword = hashPassword(resultSet.getString(1));

            if (hashedPasswordInCookie.equals(realHashedPassword)) {
                return validateSession(hashedPasswordInCookie, realHashedPassword, request, userId);
            }
        } catch (SQLException e) {
            throw new AuthCookieManagementException("Can not interact with the database", e);
        } catch (IllegalStateException e) {
            throw new AuthCookieManagementException("Session has already been invalidated", e);
        } finally {

            // Close the statement, the result set is also closed
            closePreparedStatement(preparedStatement);

            // Close the connection
            closeConnection(connection);
        }

        return null;
    }

    /**
     * Validates the session.
     *
     * @param hashedPasswordInCookie
     *            the password in cookie after hashing
     * @param realHashedPassword
     *            the real hased password
     * @param request
     *            the http servlet request
     * @param userId
     *            the user id
     * @return the user id
     */
    private long validateSession(String hashedPasswordInCookie, String realHashedPassword, HttpServletRequest request,
                                 long userId) {

        // Get old session from the request
        HttpSession session = request.getSession(false);

        // Invalidate old session
        if (session != null) {
            session.invalidate();
        }

        // Get new session
        session = request.getSession();

        // Set user ID to the session attribute
        session.setAttribute(userIdentifierKey, new Long(userId));

        return userId;
    }

    /**
     * Gets the auth cookie.
     *
     * @param cookies
     *            the cookie array
     * @return the auth cookie.
     */
    private Cookie getAuthCookie(Cookie[] cookies) {
        Cookie authCookie = null;

        for (Cookie cookie : cookies) {

            // Get name of the cookie
            String curCookieName = cookie.getName();

            if (cookieName.equals(curCookieName)) {
                authCookie = cookie;

                break;
            }
        }

        return authCookie;
    }

    /**
     * Compute a one-way hash of a password.
     *
     * @param password
     *            the password to hash
     * @return the hashed password (not null)
     */
    private static String hashPassword(String password) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {

            // ignore exceptions, assume that "MD5" is always available
        }

        String secretString = "30A669BA1C7E4d4887459B3136129857";
        String data = secretString + password;
        byte[] plain = data.getBytes();
        byte[] raw = md.digest(plain);
        StringBuffer sb = new StringBuffer();

        for (byte rawByte : raw) {
            String hex = Integer.toHexString(rawByte & SET_ALL_BITS_MASK);

            if (hex.length() == 1) {
                sb.append(ZERO_CHAR);
            }

            sb.append(hex);
        }

        return sb.toString();
    }
}
