/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.termsofuse.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cronos.termsofuse.dao.EntityNotFoundException;
import com.cronos.termsofuse.dao.TermsOfUseDaoConfigurationException;
import com.cronos.termsofuse.dao.TermsOfUsePersistenceException;
import com.cronos.termsofuse.dao.UserBannedException;
import com.cronos.termsofuse.dao.UserTermsOfUseDao;
import com.cronos.termsofuse.model.TermsOfUse;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is the default implementation of UserTermsOfUseDao. It utilizes the DB Connection Factory to get access
 * to the database. The configuration is done by the Configuration API.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;CMConfig&gt;
 *     &lt;Config name="userTermsOfUseDao"&gt;
 *      &lt;Property name="dbConnectionFactoryConfig"&gt;
 *        &lt;Property name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"&gt;
 *          &lt;Property name="connections"&gt;
 *                 &lt;Property name="default"&gt;
 *                     &lt;Value&gt;InformixJDBCConnection&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *              &lt;Property name="InformixJDBCConnection"&gt;
 *                  &lt;Property name="producer"&gt;
 *                      &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *                  &lt;/Property&gt;
 *                  &lt;Property name="parameters"&gt;
 *                      &lt;Property name="jdbc_driver"&gt;
 *                      &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="jdbc_url"&gt;
 *                              &lt;Value&gt;
 *                                  jdbc:informix-sqli://localhost:1526/common_oltp:informixserver=ol_topcoder
 *                              &lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="user"&gt;
 *                          &lt;Value&gt;informix&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                      &lt;Property name="password"&gt;
 *                          &lt;Value&gt;123456&lt;/Value&gt;
 *                      &lt;/Property&gt;
 *                  &lt;/Property&gt;
 *              &lt;/Property&gt;
 *          &lt;/Property&gt;
 *        &lt;/Property&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name="loggerName"&gt;
 *          &lt;Value&gt;loggerName&lt;/Value&gt;
 *      &lt;/Property&gt;
 *     &lt;/Config&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Create the configuration object
 * ConfigurationObject configurationObject = TestsHelper.getConfig(TestsHelper.CONFIG_USER_TERMS);
 * // Instantiate the dao implementation from configuration defined above
 * UserTermsOfUseDao userTermsOfUseDao = new UserTermsOfUseDaoImpl(configurationObject);
 *
 * // Create user terms of use to user link
 * userTermsOfUseDao.createUserTermsOfUse(3, 2);
 *
 * // Remove user terms of use to user link
 * userTermsOfUseDao.removeUserTermsOfUse(3, 3);
 *
 * // Retrieve terms of use. This will return user terms with ids 1 and 2.
 * List&lt;TermsOfUse&gt; termsList = userTermsOfUseDao.getTermsOfUseByUserId(1);
 *
 * // Retrieve users by terms of use. This will return ids 1 and 3.
 * List&lt;Long&gt; userIdsList = userTermsOfUseDao.getUsersByTermsOfUseId(2);
 *
 * // Check whether user has terms of use. Will return false
 * boolean hasTerms = userTermsOfUseDao.hasTermsOfUse(3, 3);
 *
 * // Check whether user has terms of use ban. Will return true
 * boolean hasTermsBan = userTermsOfUseDao.hasTermsOfUseBan(1, 3);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The class is immutable and thread safe.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public class UserTermsOfUseDaoImpl extends BaseTermsOfUseDao implements UserTermsOfUseDao {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = UserTermsOfUseDaoImpl.class.getName();

    /**
     * <p>
     * Represents the SQL string to insert the the fact of acceptance of specified terms of use by specified user.
     * </p>
     */
    private static final String INSERT_TERMS = "INSERT INTO user_terms_of_use_xref (user_id,terms_of_use_id)"
        + " VALUES (?,?)";

    /**
     * <p>
     * Represents the SQL string to delete the fact of acceptance of specified terms of use by specified user.
     * </p>
     */
    private static final String DELETE_TERMS = "DELETE FROM user_terms_of_use_xref WHERE user_id=?"
        + " AND terms_of_use_id=?";


    /**
     * <p>
     * Represents the SQL string to query the terms of use entities by user id.
     * </p>
     */
    private static final String QUERY_TERMS = "SELECT terms_of_use.terms_of_use_id, terms_of_use_type_id, title,"
        + " electronically_signable, url, member_agreeable FROM  user_terms_of_use_xref inner join terms_of_use"
        + " on user_terms_of_use_xref.terms_of_use_id = terms_of_use.terms_of_use_id WHERE user_id=?";

    /**
     * <p>
     * Represents the SQL string to query the user id by terms of use id.
     * </p>
     */
    private static final String QUERY_USER_ID = "SELECT user_id FROM user_terms_of_use_xref WHERE terms_of_use_id=?";


    /**
     * <p>
     * Represents the SQL string to query if there is a record on the fact of acceptance of specified terms of use by
     * specified user.
     * </p>
     */
    private static final String QUERY_HAS_TERMS = "SELECT '1' FROM user_terms_of_use_xref WHERE user_id=?"
        + " AND terms_of_use_id=?";

    /**
     * <p>
     * Represents the SQL string to query if there is a record on the fact of banning the specified user from
     * accepting the specified terms of use.
     * </p>
     */
    private static final String QUERY_HAS_TERMS_BAN = "SELECT '1' FROM user_terms_of_use_ban_xref WHERE user_id=?"
        + " AND terms_of_use_id=?";

    /**
     * This is the constructor with the ConfigurationObject parameter, which simply delegates the instance
     * initialization to the base class.
     *
     * @param configurationObject
     *            the configuration object containing the configuration.
     *
     * @throws IllegalArgumentException
     *             if the configurationObject is null.
     * @throws TermsOfUseDaoConfigurationException
     *             if any exception occurs while initializing the instance.
     */
    public UserTermsOfUseDaoImpl(ConfigurationObject configurationObject) {
        super(configurationObject);
    }

    /**
     * Records the fact of acceptance of specified terms of use by specified user.
     *
     * @param userId
     *            a long providing the user ID.
     * @param termsOfUseId
     *            a long providing the terms of use ID.
     *
     * @throws UserBannedException
     *             if the user is banned to create terms of use.
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public void createUserTermsOfUse(long userId, long termsOfUseId) throws UserBannedException,
        TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".createUserTermsOfUse(long userId, long termsOfUseId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"userId", "termsOfUseId"},
            new Object[] {userId, termsOfUseId});

        if (hasTermsOfUseBan(userId, termsOfUseId)) {
            // Log exception
            throw Helper.logException(log, signature, new UserBannedException("The user with id " + userId
                + " has a ban for following terms of use with id " + termsOfUseId + "."));
        }

        try {
            Helper.executeUpdate(getDBConnectionFactory(), INSERT_TERMS,
                new Object[] {userId, termsOfUseId});

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Removes the fact of acceptance of specified terms of use by specified user.
     *
     * @param userId
     *            a long providing the user ID.
     * @param termsOfUseId
     *            a long providing the terms of use ID.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     * @throws EntityNotFoundException
     *             if the entity was not found in the database.
     */
    public void removeUserTermsOfUse(long userId, long termsOfUseId) throws TermsOfUsePersistenceException,
        EntityNotFoundException {
        String signature = CLASS_NAME + ".removeUserTermsOfUse(long userId, long termsOfUseId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"userId", "termsOfUseId"},
            new Object[] {userId, termsOfUseId});

        try {
            Helper.executeUpdate(getDBConnectionFactory(), DELETE_TERMS,
                new Object[] {userId, termsOfUseId},
                Long.toString(userId) + ", " + termsOfUseId);

            // Log method exit
            Helper.logExit(log, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Retrieves terms of use entities associated with the given user from the database.
     *
     * @param userId
     *            a long containing the user id to retrieve terms of use.
     *
     * @return a TermsOfUse list with the requested terms of use or empty list if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<TermsOfUse> getTermsOfUseByUserId(long userId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getTermsOfUseByUserId(long userId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"userId"},
            new Object[] {userId});

        // Delegate to Helper.getTermsOfUse
        return Helper.getTermsOfUse(signature, log, getDBConnectionFactory(), QUERY_TERMS, userId, null);
    }

    /**
     * Retrieves user ids associated with the given terms of use from the database.
     *
     * @param termsOfUseId
     *            a long containing the terms of use id to retrieve the user ids.
     *
     * @return a list of user ids or empty list if not found.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public List<Long> getUsersByTermsOfUseId(long termsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".getUsersByTermsOfUseId(long termsOfUseId)";
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"termsOfUseId"},
            new Object[] {termsOfUseId});

        try {
            Connection conn = Helper.createConnection(getDBConnectionFactory());
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(QUERY_USER_ID);
                ps.setLong(1, termsOfUseId);

                List<Long> result = new ArrayList<Long>();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    result.add(rs.getLong(1));
                }

                // Log method exit
                Helper.logExit(log, signature, new Object[] {result});
                return result;
            } catch (SQLException e) {
                throw new TermsOfUsePersistenceException("A database access error has occurred.", e);
            } finally {
                // Close the prepared statement
                // (The result set will be closed automatically)
                Helper.closeStatement(ps);
                // Close the connection
                Helper.closeConnection(conn);
            }
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }

    /**
     * Checks if there is a record on the fact of acceptance of specified terms of use by specified user.
     *
     * @param userId
     *            a long providing the user ID.
     * @param termsOfUseId
     *            a long providing the terms of use ID.
     *
     * @return <code>true</code> if specified user accepted the specified terms of use; <code>false</code>
     *         otherwise.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public boolean hasTermsOfUse(long userId, long termsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".hasTermsOfUse(long userId, long termsOfUseId)";

        // Delegates to hasRecord
        return hasRecord(signature, QUERY_HAS_TERMS, userId, termsOfUseId);
    }

    /**
     * Checks if there is a record on the fact of banning the specified user from accepting the specified terms of
     * use.
     *
     * @param userId
     *            a long providing the user ID.
     * @param termsOfUseId
     *            a long providing the terms of use ID.
     *
     * @return <code>true</code> if specified user has ban for the specified terms of use; <code>false</code>
     *         otherwise.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    public boolean hasTermsOfUseBan(long userId, long termsOfUseId) throws TermsOfUsePersistenceException {
        String signature = CLASS_NAME + ".hasTermsOfUseBan(long userId, long termsOfUseId)";

        // Delegates to hasRecord
        return hasRecord(signature, QUERY_HAS_TERMS_BAN, userId, termsOfUseId);
    }

    /**
     * Checks if there is a record.
     *
     * @param signature
     *            the signature.
     * @param sql
     *            the SQL string.
     * @param userId
     *            a long providing the user ID.
     * @param termsOfUseId
     *            a long providing the terms of use ID.
     *
     * @return <code>true</code> if there is a record; <code>false</code> otherwise.
     *
     * @throws TermsOfUsePersistenceException
     *             if any persistence error occurs.
     */
    private boolean hasRecord(String signature, String sql, long userId, long termsOfUseId)
        throws TermsOfUsePersistenceException {
        Log log = getLog();

        // Log method entry
        Helper.logEntrance(log, signature,
            new String[] {"userId", "termsOfUseId"},
            new Object[] {userId, termsOfUseId});

        try {
            Connection conn = Helper.createConnection(getDBConnectionFactory());
            PreparedStatement ps = null;

            try {
                ps = conn.prepareStatement(sql);
                ps.setLong(1, userId);
                ps.setLong(2, termsOfUseId);

                boolean result = ps.executeQuery().next();

                // Log method exit
                Helper.logExit(log, signature, new Object[] {result});
                return result;
            } catch (SQLException e) {
                throw new TermsOfUsePersistenceException("A database access error has occurred.", e);
            } finally {
                // Close the prepared statement
                // (The result set will be closed automatically)
                Helper.closeStatement(ps);
                // Close the connection
                Helper.closeConnection(conn);
            }
        } catch (TermsOfUsePersistenceException e) {
            // Log exception
            throw Helper.logException(log, signature, e);
        }
    }
}
