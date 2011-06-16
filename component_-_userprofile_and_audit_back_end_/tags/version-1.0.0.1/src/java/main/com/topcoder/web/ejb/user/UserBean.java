/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.ejb.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.EJBException;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.web.common.IdGeneratorClient;
import com.topcoder.web.common.RowNotFoundException;
import com.topcoder.web.ejb.BaseEJB;

/**
 * <p>
 * This class represents a stateless EJB bean that performs managing of user data in persistence.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is immutable and thread safe (not taking into account the SessionContext
 * field in the base class that is actually never used). It's assumed that transactions are managed externally.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong> <code>
 * <pre>
 * //instantiate the class
 * UserBean userBean = new UserBean();
 *
 * // Create new user "test_user"
 *   long userId = userBean.createNewUser("test_user", 'U', "ds", "ds");
 *
 * // Set first name of the user
 *   userBean.setFirstName(userId, "John", "ds");
 *
 * // Set middle name of the user
 * userBean.setMiddleName(userId, "A", "ds");
 *
 * // Set last name of the user
 *  userBean.setLastName(userId, "Smith", "ds");
 *
 * // Set status of the user to active
 *   userBean.setStatus(userId, 'A', "ds");
 *
 * // Set activation code of the user
 *    userBean.setActivationCode(userId, "12345", "ds");
 *
 * // Set password of the user
 *   userBean.setPassword(userId, "test_pass", "ds");
 *
 * // Change handle of the user
 *    userBean.setHandle(userId, "first_user", "ds");
 * </pre>
 * </code>
 * </p>
 * @author saarixx, kalc
 * @version 1.0
 */
public class UserBean extends BaseEJB {

    /**
     * <p>
     * Generated Serial Version id.
     * </p>
     */
    private static final long serialVersionUID = -3405045653975466778L;

    /**
     * <p>
     * A variable representing value 3.
     * </p>
     */
    private static final int THREE = 3;

    /**
     * <p>
     * The logger used for logging debug information. Is initialized during class loading and never changed after that.
     * Cannot be null. Is used all methods.
     * </p>
     */
    private static final Logger LOG = Logger.getLogger(UserBean.class);

    /**
     * <p>
     * Creates an instance of UserBean.
     * </p>
     */
    public UserBean() {
        // nothing
    }

    /**
     * <p>
     * Creates a new user. The ID of the user is generated automatically.
     * </p>
     * @throws EJBException
     *             if handle is null/empty or dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param handle
     *            the handle of the user
     * @param status
     *            the status of the user
     * @param idDataSource
     *            the data source for the IDs
     * @return the generated ID of the user
     */
    public long createNewUser(String handle, char status, String dataSource, String idDataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(handle, "handle", EJBException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        long ret = 0;
        try {
            ret = IdGeneratorClient.getSeqId("USER_SEQ");
        } catch (IDGenerationException e) {
            throw new EJBException(e);
        }
        createUser(ret, handle, status, dataSource);
        return ret;
    }

    /**
     * <p>
     * Creates a user with the specified parameters.
     * </p>
     * @throws EJBException
     *             if handle is null/empty or dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param handle
     *            the handle of the user
     * @param status
     *            the status of the user
     * @param userId
     *            the ID of the user
     */
    public void createUser(long userId, String handle, char status, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(handle, "handle", EJBException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);

        LOG.debug("createUser called. user_id=" + userId + " " + "handle=" + handle + " " + "status=" + status);

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBMS.getConnection(dataSource);

            ps = conn.prepareStatement("INSERT INTO user (user_id,handle,status) VALUES (?,?,?)");
            ps.setLong(1, userId);
            ps.setString(2, handle);
            ps.setString(THREE, "" + status);

            int rc = ps.executeUpdate();
            checkRowCount("user", rc, 1);
            ps.close();

            ps = conn.prepareStatement("INSERT INTO user_profile (user_id) VALUES (?)");
            ps.setLong(1, userId);
            rc = ps.executeUpdate();

            checkRowCount("user_profile", rc, 1);
        } catch (SQLException sqle) {
            DBMS.printSqlException(true, sqle);
            throw (new EJBException(sqle.getMessage()));
        } finally {
            close(ps);
            close(conn);
        }
    }

    /**
     * <p>
     * A Helper method to check the row count.
     * </p>
     * @param table
     *            the table at which exception occurred
     * @param actual
     *            the actual row count
     * @param expected
     *            the expected row count
     * @throws EJBException
     *             the exception constructed with given message.
     */
    private void checkRowCount(String table, int actual, int expected) {
        if (actual != expected) {
            throw new EJBException("Wrong number of rows inserted into" + table + ". Inserted " + actual
                    + ", should have inserted " + expected + ".");
        }
    }

    /**
     * <p>
     * Sets first name of the user.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @param firstName
     *            the first name of the user
     */
    public void setFirstName(long userId, String firstName, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);

        LOG.debug("setFirstName called. user_id=" + userId + " " + "first_name=" + firstName);

        updateStringById(dataSource, "UPDATE user_profile SET first_name=? WHERE user_id=?", firstName, userId);
    }

    /**
     * <p>
     * A private helper method to execute an update statement and check the results.
     * </p>
     * @param dataSource
     *            the DataSource to be used
     * @param sql
     *            the sql query to be executed
     * @param str
     *            the parameter to be updated
     * @param id
     *            the id of user whose data is being updated
     */
    private void updateStringById(String dataSource, String sql, String str, Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBMS.getConnection(dataSource);

            ps = conn.prepareStatement(sql);
            ps.setString(1, str);
            ps.setLong(2, id);

            int rc = ps.executeUpdate();
            checkRowCount("user", rc, 1);
        } catch (SQLException sqle) {
            DBMS.printSqlException(true, sqle);
            throw (new EJBException(sqle.getMessage()));
        } finally {
            close(ps);
            close(conn);
        }
    }

    /**
     * <p>
     * Sets last name of the user.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param lastName
     *            the last name of the user
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     */
    public void setLastName(long userId, String lastName, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("setLastName called. user_id=" + userId + " " + "last_name=" + lastName);

        updateStringById(dataSource, "UPDATE user_profile SET last_name=? WHERE user_id=?", lastName, userId);
    }

    /**
     * <P>
     * Sets middle name of the user.
     * </P>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param middleName
     *            the middle name of the user
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     */
    public void setMiddleName(long userId, String middleName, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("setMiddleName called. user_id=" + userId + " " + "middle_name=" + middleName);

        updateStringById(dataSource, "UPDATE user_profile SET middle_name=? WHERE user_id=?", middleName, userId);
    }

    /**
     * <p>
     * Sets activation code of the user.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @param code
     *            the activation code of the user
     */
    public void setActivationCode(long userId, String code, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("setActivationCode called. user_id=" + userId + " " + "code=" + code + " db=" + dataSource);

        updateStringById(dataSource, "UPDATE user SET activation_code=? WHERE user_id=?", code, userId);
    }

    /**
     * <p>
     * Sets status of the user.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param status
     *            the status of the user
     * @param userId
     *            the ID of the user
     */
    public void setStatus(long userId, char status, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("setStatus called. user_id=" + userId + " " + "status=" + status);

        updateStringById(dataSource, "UPDATE user SET status=? WHERE user_id=?", String.valueOf(status), userId);
    }

    /**
     * <p>
     * Retrieves the first name of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved first name of the user
     */
    public String getFirstName(long userId, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("getFirstName called. user_id=" + userId);

        return updateStringById(dataSource, "SELECT first_name FROM user_profile WHERE user_id=?", userId);
    }

    /**
     * <p>
     * A private helper method to get a response by executing the passed sql query.
     * </p>
     * @param dataSource
     *            the DataSource to be used
     * @param sql
     *            the query to be executed
     * @param userId
     *            the id of the user whose information to be fetched
     * @return the result of query execution
     */
    private String updateStringById(String dataSource, String sql, long userId) {
        String response = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBMS.getConnection(dataSource);

            ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);

            rs = ps.executeQuery();
            if (rs.next()) {
                response = rs.getString(1);
            } else {
                throwEJBExceptionForGet(userId);
            }
        } catch (SQLException sqle) {
            DBMS.printSqlException(true, sqle);
            throw (new EJBException(sqle.getMessage()));
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return response;
    }

    /**
     * <p>
     * A private helper method to construct and throw EJBException for all Get operations.
     * </p>
     * @param userid
     *            the userId for which Get operation failed
     */
    private void throwEJBExceptionForGet(long userid) {
        throw new EJBException("No rows found when selecting from 'user' with user_id = " + userid + ".");
    }

    /**
     * <p>
     * Retrieves the middle name of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved middle name of the user
     */
    public String getMiddleName(long userId, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("getMiddleName called. user_id=" + userId);

        return updateStringById(dataSource, "SELECT middle_name FROM user_profile WHERE user_id=?", userId);
    }

    /**
     * <p>
     * Retrieves the last name of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved last name of the user
     */
    public String getLastName(long userId, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("getLastName called. user_id=" + userId);

        return updateStringById(dataSource, "SELECT last_name FROM user_profile WHERE user_id=?", userId);
    }

    /**
     * <p>
     * Retrieves the activation code of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved activation code of the user
     */
    public String getActivationCode(long userId, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("getActivationCode called. user_id=" + userId + " db=" + dataSource);

        return updateStringById(dataSource, "SELECT activation_code FROM user WHERE user_id=?", userId);
    }

    /**
     * <p>
     * Sets handle of the user.
     * </p>
     * @throws EJBException
     *             if handle is null/empty or dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param handle
     *            the handle of the user
     * @param userId
     *            the ID of the user
     */
    public void setHandle(long userId, String handle, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(handle, "handle", EJBException.class);
        LOG.debug("setHandle called. user_id=" + userId);

        updateStringById(dataSource, "UPDATE user SET handle = ? WHERE user_id=?", handle, userId);
    }

    /**
     * <p>
     * Retrieves the handle of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved handle of the user
     */
    public String getHandle(long userId, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("getHandle called. user_id=" + userId);

        return updateStringById(dataSource, "SELECT handle FROM user WHERE user_id=?", userId);
    }

    /**
     * <p>
     * Retrieves the status of the user. If user with the given ID doesn't exist, EJBException is thrown.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved status of the user
     */
    public char getStatus(long userId, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        LOG.debug("getStatus called. user_id=" + userId);

        return updateStringById(dataSource, "SELECT status FROM user WHERE user_id=?", userId).charAt(0);
    }

    /**
     * <p>
     * Checks if a user with the specified ID exists in the data source.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return true if user exists, false otherwise
     */
    public boolean userExists(long userId, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;

        boolean userExists = false;

        try {
            conn = DBMS.getConnection(dataSource);

            ps = conn.prepareStatement("SELECT 'X' FROM user WHERE user_id=?");
            ps.setLong(1, userId);

            rs = ps.executeQuery();
            userExists = rs.next();
        } catch (SQLException sqle) {
            DBMS.printSqlException(true, sqle);
            throw (new EJBException(sqle.getMessage()));
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
        return userExists;
    }

    /**
     * <p>
     * Checks if a user with the specified handle exists in the data source.
     * </p>
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param handle
     *            the handle of the user
     * @return true if user exists, false otherwise
     */
    public boolean userExists(String handle, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;

        boolean userExists = false;

        try {

            conn = DBMS.getConnection(dataSource);
            ps = conn.prepareStatement("SELECT 'X' FROM user WHERE lower(handle) = lower(?)");
            ps.setString(1, handle);

            rs = ps.executeQuery();
            userExists = rs.next();
        } catch (SQLException sqle) {
            DBMS.printSqlException(true, sqle);
            throw (new EJBException(sqle.getMessage()));
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }

        return userExists;
    }

    /**
     * <p>
     * Sets password of the user.
     * </p>
     * @throws EJBException
     *             if password is null/empty or dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @param password
     *            the password of the user
     */
    public void setPassword(long userId, String password, String dataSource) {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(password, "password", EJBException.class);
        int ret = update("user", new String[] {"password"}, new String[] {password}, new String[] {"user_id"},
                new String[] {String.valueOf(userId)}, dataSource);
        checkRowCount("user", ret, 1);
    }

    /**
     * <p>
     * Retrieves the password of the user. If user with the given ID doesn't exist, RowNotFoundException is thrown.
     * </p>
     * @throws RowNotFoundException
     *             if user with the given ID doesn't exist
     * @throws EJBException
     *             if dataSource is null/empty or if some other error occurred
     * @param dataSource
     *            the data source
     * @param userId
     *            the ID of the user
     * @return the retrieved password of the user
     */
    public String getPassword(long userId, String dataSource) throws RowNotFoundException {
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(dataSource, "dataSource", EJBException.class);
        return selectString("user", "password", new String[] {"user_id"}, new String[] {String.valueOf(userId)},
                dataSource);
    }
}
